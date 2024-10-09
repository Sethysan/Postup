<template>
  <div id="capstone-app">
    <div v-if="isLoggedIn">
      <p>Logged in as {{ userName }}</p>
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
}
  ;
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
#capstone-app{
  font-family: Arial, Helvetica, sans-serif;

}

.nav-btn {
  background-color: transparent;
  color: white;
  border: none;
  font-size: 16px;
  padding: 5px;
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
