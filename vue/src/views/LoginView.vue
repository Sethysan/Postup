<template>
  <div id="login">
    <form v-on:submit.prevent="login">
      <h1>Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div role="alert" v-if="bannedUser">
        {{ bannedMessage }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <div v-if="showWelcomeMessage" class="custom-alert">
        Welcome, {{ user.username }}!
      </div>
      <p>
        <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import { openModal } from "jenesius-vue-modal";
import LoggedInMessgae from "../components/LoggedInMessgae.vue";
import authService from "../services/AuthService";
import ModeratorService from "../services/ModeratorService";

export default {
  components: {LoggedInMessgae},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false,
      showWelcomeMessage: false,
      bannedUser: false,
      bannedMessage: "You have been banned.",
    };
  },
  methods: {
   login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            ModeratorService.getUserAccess(response.data.user.id)
            .then(res => {
              this.$store.dispatch('SET_ACCESS', res.data);
            });
            this.show()
          }
        })
        .catch(error => {

          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
          if (response.status === 403) {
            this.bannedUser = true;
            this.user = {
              username: "",
              password: ""
            };
          }
        });
    },
    async show(){
      const modal = await openModal(LoggedInMessgae, {username: this.user.username});
        let count = 2;

        modal.onclose = () => {
          count--;
          //The modal window will be closed after five attempts.
          if (count > 0) return false;
        }
        this.$router.push("/")
      }
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
}
.hidden { 
  display: none; 
}

</style>