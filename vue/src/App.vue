<template >
  <div id="nav">
    <div class="logged-in" v-if="isLoggedIn">
    <p>Logged in as {{ userName }}</p>
  </div>
    <button class="nav-btn">
      <router-link v-bind:to="{ name: 'home' }" :class="getLinkClass('home')">HOME</router-link>&nbsp;
    </button>
    <!-- <div class="separator"></div> -->
    <button class="nav-btn">
      <router-link v-bind:to="{ name: 'forums' }" :class="getLinkClass('forums')">FORUMS</router-link>&nbsp;
    </button>
    <!-- <div v-if="this.isLoggedIn" class="separator"></div> -->
    <button v-if="isLoggedIn" class="nav-btn">
      <router-link v-bind:to="{ name: 'favorites' }" v-if="userName"
        :class="getLinkClass('favorites')">FAVORITES</router-link>&nbsp;
    </button>
    <button v-if="isLoggedIn" class="nav-btn">
      <router-link v-bind:to="{ name: 'messages' }" v-if="userName"
        :class="getLinkClass('messages')">MESSAGES</router-link>&nbsp;
    </button>
    <!-- <div v-if="this.isLoggedIn" class="separator"></div> -->
    <button v-if="isLoggedIn" class="nav-btn">
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''"
        class="router-link-nonactive">LOGOUT</router-link>
    </button>
    <!-- <div v-if="!this.isLoggedIn" class="separator"></div> -->
    <button v-if="!this.isLoggedIn" class="nav-btn">
      <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"
        class="router-link-nonactive">LOGIN</router-link>
    </button>
  </div>
  <div id="capstone-app">
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
  // mounted() {
  //   if (this.isLoggedIn) {
  //     this.startTokenCountdown(); 
  //     // Start the countdown when the component is mounted
  //   }
  // },
  // beforeUnmount() {
  //   // Clear the interval when the component is destroyed
  //   clearInterval(this.interval);
  // }
}
  ;
</script>
<style>
#nav {
  display: flex;
  justify-content: space-evenly;
  left: 0;
  width: 100%;
  z-index: 1000;
  background-color: rgba(240, 107, 19, 0.909);

}

body,
html {
  margin: 0;
  padding: 0;
}

#capstone-app {
  font-family: Arial, Helvetica, sans-serif;
  background: radial-gradient(circle, rgb(255, 255, 255) 50%, rgb(240, 107, 19));

}

.nav-btn {
    background-color: transparent;
    color: white;
    border: none;
    font-size: 25px;
    padding: 5px;
    transition: transform 0.3s ease-in-out; /* Smooth transition for hover effects */
}

.nav-btn .router-link-active,
.router-link-exact-active {
  text-decoration: none;
  font-size: 30px;
  color: white;
  transition: .3s ease-in-out;
}

.router-link-nonactive {
  text-decoration: none;
  color: black;
  transition: .3s ease-in-out;
}
#logged-in{
  color: black;
}

.router-link-nonactive:hover {
  text-decoration: none;
  transform: scaleX(3);
  transition: transform 0.3s ease-in-out;
}

.separator {
  align-content: center;
}
</style>
