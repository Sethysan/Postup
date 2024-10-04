<template>
  <div id="capstone-app">
    <div v-if="isLoggedIn">
      <p>Logged in as {{ userName }}, session time remaining: {{ tokenExpiration.hours }}h {{ tokenExpiration.minutes }}m {{ tokenExpiration.seconds }}s</p>
    </div>
    <div v-else>
      <router-link v-bind:to="{ name: 'login' }">Your session has expired. Please login again.</router-link>
    </div>
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|
      <router-link v-bind:to="{ name: 'forums' }">Forums</router-link> |
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Login</router-link>
    </div>
    <router-view />
  </div>
</template>
<script>
import jwt_decode from 'jwt-decode';

export default {
  data() {
    return {
      tokenExpiration: {
        hours: 0,
        minutes: 0,
        seconds: 0
      },
      interval: null, // Holds the reference to the setInterval
    };
  },
  computed: {
    // Get the user's name from Vuex
    userName() {
      return this.$store.getters.username;
    },

    // Compute whether the user is logged in
    isLoggedIn() {
      return this.$store.state.token !== '';
    }
  },
  methods: {
    calculateTimeRemaining(token) {
      const decodedToken = jwt_decode(token);
      const currentTime = Date.now() / 1000; // Current time in seconds
      const timeRemaining = decodedToken.exp - currentTime;

      if (timeRemaining > 0) {
        const hours = Math.floor(timeRemaining / 3600);
        const minutes = Math.floor((timeRemaining % 3600) / 60);
        const seconds = Math.floor(timeRemaining % 60);
        this.tokenExpiration = { hours, minutes, seconds };
      } else {
        this.handleTokenExpiration(); // Handle token expiration when time runs out
      }
    },
    handleTokenExpiration() {
      // Clear token and user information from Vuex store and localStorage
      clearInterval(this.interval); // Stop the countdown interval
      this.$store.commit('LOGOUT');
      this.$router.push({ name: 'login' }); // Optionally redirect to login page
    },
    startTokenCountdown() {
      const token = this.$store.state.token;
      if (token) {
        // Calculate the time immediately
        this.calculateTimeRemaining(token);

        // Set up an interval to update the remaining time every second
        this.interval = setInterval(() => {
          this.calculateTimeRemaining(token);
        }, 1000); // Update every second
      }
    }
  },
  mounted() {
    if (this.isLoggedIn) {
      this.startTokenCountdown(); // Start the countdown when the component is mounted
    }
  },
  beforeUnmount() {
    // Clear the interval when the component is destroyed
    clearInterval(this.interval);
  }
};
</script>
