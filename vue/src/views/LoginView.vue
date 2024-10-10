<template>
  <div id="login" class="text-center">
    <form v-on:submit.prevent="login">
      <h1>Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials" class="error-message">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration" class="welcome-message">
        Thank you for registering, please sign in.
      </div>
      <div role="alert" v-if="bannedUser" class="error-message">
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
      <div v-if="showWelcomeMessage" class="welcome-message">
        Welcome, {{ user.username }}!
      </div>
      <p>
        <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import ModeratorService from "../services/ModeratorService";

export default {
  components: {},
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
            this.$store.commit('SUBSCRIBE_TO_CHAT');
            this.$store.commit('SUBCRIBE_TO_POST');
            ModeratorService.getUserAccess(response.data.user.id)
              .then(res => {
                alert(res.data)
                this.$store.dispatch('SET_ACCESS', res.data);
              });
            alert(`Welcome, ${this.user.username}`);
            this.$router.push("/");
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
    }
  }
};
</script>

<style scoped>

form{
  width: 50%;
}

.text-center{
  display: flex;
  justify-content: center;
  margin-bottom: 75rem;
  margin-top: 5rem;
}


.form-input-group {
  /* margin-bottom: 1rem; */
  display: flex;
  flex-direction: column;

}

button[type="submit"] {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70%;
  max-width: 100%;
  padding: 0.75rem;
  box-sizing: border-box;
  margin-top: 1rem;
  cursor: pointer;
  font-size: 1rem;
 
}

input[type="text"],
input[type="password"],
button {
  width: 70%;
  max-width: 100%;
  max-height: 1rem;
  margin-left: 0px;
  padding: 0.75rem;
  box-sizing: border-box;
  cursor: text;
}

.hidden {
  display: none;
}

.error-message,
.welcome-message {
  font-family: Arial, Helvetica, sans-serif;
  padding: 10px;
  margin: 10px auto;
  border-radius: 5px;
  width: calc(100% - 2rem);
  max-width: 500px;
  /* Limit the maximum width */
  box-sizing: border-box;
}

.error-message {
  color: #ff4d4d;
  background-color: #ffe6e6;
  border: 1px solid #ff4d4d;
}

.welcome-message {
  color: #4dc7ff;
  background-color: #ddd7d7;
  border: 1px solid #424f73;
}

@media (max-width: 850px) {
  .form-input-group {
    padding: 0 1rem;
    width: 100%;
  }


  button {
    padding: 0.5rem;
  }
  button[type="submit"] {
    margin-left: 17px;
    width: 70%;
  }
  .text-center{
  display: flex;
  justify-content: center;
  margin-bottom: 75rem;
}
p{
  padding: 1rem;
}
}
</style>