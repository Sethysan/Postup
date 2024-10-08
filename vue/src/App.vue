<template>
  <div id="capstone-app">
    <div v-if="isLoggedIn">
      <p>Logged in as {{ userName }}, session time remaining: {{ tokenExpiration.hours }}h {{ tokenExpiration.minutes
        }}m {{ tokenExpiration.seconds }}s</p>
    </div>
    <div id="nav">
      <button class="nav-btn">
        <router-link v-bind:to="{ name: 'home' }" :class="getLinkClass('home')">Home</router-link>&nbsp;
      </button>
      <div class="separator">|</div>
      <button class="nav-btn">
        <router-link v-bind:to="{ name: 'forums' }" :class="getLinkClass('forums')">Forums</router-link>&nbsp;
      </button>
      <div v-if="this.isLoggedIn" class="separator">|</div>
      <button v-if="isLoggedIn" class="nav-btn">
        <router-link v-bind:to="{ name: 'favorites' }" v-if="userName"
          :class="getLinkClass('favorites')">Favorites</router-link>&nbsp;
      </button>
      <button v-if="isLoggedIn" class="nav-btn">
        <router-link v-bind:to="{ name: 'messages' }" v-if="userName"
          :class="getLinkClass('favorites')">Messages</router-link>&nbsp;
      </button>
      <div v-if="this.isLoggedIn" class="separator">|</div>
      <button v-if="isLoggedIn" class="nav-btn">
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''"
          class="router-link-nonactive">Logout</router-link>
      </button>
      <div v-if="!this.isLoggedIn" class="separator">|</div>
      <button v-if="!this.isLoggedIn" class="nav-btn">
        <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"
          class="router-link-nonactive">Login</router-link>
      </button>
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
    },
    getLinkClass(routeName) {
      return this.$route.name === routeName ? 'router-link-active' : 'router-link-nonactive';
    }
  },
  mounted() {
    if (this.isLoggedIn) {
      this.startTokenCountdown(); 
      this.$store.dispatch('CONNECT');
      // Start the countdown when the component is mounted
    }
  },
  beforeUnmount() {
    // Clear the interval when the component is destroyed
    clearInterval(this.interval);
    this.$store.dispatch('DISCONNECT');
  }
};
</script>
<style>
#nav {
  display: flex;
  justify-content: space-evenly;
  padding-bottom: 1vh;
  padding-top: 1vh;
  background-color: rgb(240, 108, 19);
  color: white;
  border-radius: 8px;

}

.nav-btn {
  background-color: transparent;
  color: white;
  border: none;
  font-size: 16px;
  padding: 5px;
  font-family: Arial, Helvetica, sans-serif;
}

.nav-btn .router-link-active,
.nav-btn .router-link-exact-active {
  text-decoration: none;
  color: white;
  
}

.nav-btn .router-link-nonactive {
  text-decoration: none;
  color: black;

}
.nav-btn .router-link-nonactive:hover {
  text-decoration: none;
  color: white;

}
.separator {
  align-content: center;
}
</style>
