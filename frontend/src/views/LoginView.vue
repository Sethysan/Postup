<template>
  <div id="login" class="text-center">
    <form v-on:submit.prevent="login" class="postup-form">
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
        <input type="text" id="username" v-model="user.username" required ref="usernameInput" />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <div v-if="showWelcomeMessage" class="welcome-message">
        Welcome, {{ user.username }}!
      </div>
      <p class='register-link'>
        <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import { openModal } from "jenesius-vue-modal";
import LoggedInMessage from "../components/LoggedInMessage.vue";
import authService from "../services/AuthService";
import ModeratorService from "../services/ModeratorService";


export default {
  components: { LoggedInMessage },
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
  mounted() {
    this.$refs.usernameInput.focus();
  },
  methods: {
    async login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$store.commit("SET_USER_IMAGE", response.data.user.userImage);

            ModeratorService.getUserAccess(response.data.user.id)
              .then(res => {
                this.$store.dispatch('SET_ACCESS', res.data);
                this.$router.push("/");
              });

            // Show the welcome modal
            this.showModal({
              header: `Welcome, ${this.user.username}`,
              message: "You are now logged into Post-Up"
            });
          }
        })
        .catch(error => {
          const response = error.response;
          if (response.status === 401) {
            this.invalidCredentials = true;
          }
          if (response.status === 403) {
            this.bannedUser = true;
            this.user = { username: "", password: "" };
            this.showModal({
              header: `Sorry, login unsuccessful`,
              message: "This user has been banned"
            });
          }
        });
    },

    async showModal(props) {
      const modal = await openModal(LoggedInMessage, props);
    }
  }
};

</script>

<style scoped>
p a {
  text-decoration: none;
  color: #888;
}

p a:hover {
  color: var(--orange);
}

.register-link {
  color: #888;
  transition: transform 0.6s, color .3s;
}

.register-link:hover {
  color: var(--orange);
  transform: scale(1.1);
  text-decoration: underline;
}

.text-center {
  position: relative;
  padding: 0 10rem 10rem 10rem;
}

.text-center::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: url('/images/POST-UP_logo.png');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  opacity: 0.4;
  /* Adjust the transparency of the logo */
  pointer-events: none;
  /* Ensures the overlay does not interfere with form interactions */

}

.text-center {
  display: flex;
  box-sizing: border-box;
}

h1 {
  color: #888;
  font-weight: 700;
}

.form-input-group {
  /* margin-bottom: 1rem; */
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.postup-form {
  color: var(--orange);
  font-weight: 550;
  z-index: 1;
}

.form-input-group {
  display: flex;
  flex-direction: column;
  padding: 0 1rem;
}

button[type="submit"] {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 86%;
  max-width: 100%;
  padding: 0.75rem;
  box-sizing: border-box;
  margin-top: 1.5rem;
  cursor: pointer;
  font-size: 1rem;
  margin-left: 17px;
  transition: transform 0.3s, background-color .3s;
}

button[type="submit"]:hover {
  background-color: #014ca2;
  transform: scale(1.05);
}

input[type="text"],
input[type="password"],
button {
  width: 100%;
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
  font-weight: bolder;
  padding: 10px;
  margin: 10px auto;
  margin-left: 0px;
  border-radius: 5px;
  width: 70%;
  max-width: 100%;
  box-sizing: border-box;
}

.error-message {
  color: #ff4d4d;
  background-color: #ffe6e6;
  border: 1px solid #ff4d4d;
}

.welcome-message {
  color: #177099;
  background-color: #ddd7d7;
  border: 1px solid #424f73;
}

@media (max-width: 850px) {
  .form-input-group {
    padding: 0 1rem;
    padding-bottom: 1rem;
    width: 100%;
  }


  button {
    padding: 0.5rem;
  }

  button[type="submit"] {
    margin-left: 17px;
    width: 100%;
  }

  .text-center {
    display: flex;
    justify-content: center;
    margin-bottom: 75rem;
  }

  p {
    padding: 2rem;
  }

  .error-message {
    margin-left: 1rem;
    margin-top: 40px;
    margin-bottom: 20px;
  }

  h1 {
    margin-left: 1rem;
  }
}
</style>