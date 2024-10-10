<template>
  <div id="register" class="text-center">
    <form v-on:submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors" class="error-message">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" class="input-field" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" class="input-field" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" class="input-field" v-model="user.confirmPassword" required />
      </div>
      <div class="form-input-group">
        <label for="imageOption">Profile Image</label>
        <div>
          <input type="radio" id="urlOption" value="url" v-model="imageOption" />
          <label for="urlOption">Use Image URL</label>
        </div>
        <div>
          <input type="radio" id="stockOption" value="stock" v-model="imageOption" />
          <label for="stockOption">Choose from Stock Images</label>
        </div>
      </div>

      <div v-if="imageOption === 'url'" class="form-input-group">
        <label for="userImage">Profile Image URL</label>
        <input type="url" id="userImage" v-model="user.user_image" placeholder="Enter image URL" />
      </div>
      <button type="submit">Create Account</button>
      <p>
        <router-link v-bind:to="{ name: 'login' }">
          Already have an account? Log in.
        </router-link>
      </p>
    </form>
  </div>

  <div v-if="imageOption === 'stock'" class="form-input-group">
    <label for="stockImages">Select Stock Image</label>
    <div class="stock-image-preview">
      <div v-for="(image, index) in stockImages" :key="index" class="stock-image-option"
        @click="selectStockImage(image.url)" :class="{ selected: user.user_image === image.url }">
        <img :src="image.url" :alt="image.name" class="stock-image" />
        <p>{{ image.name }}</p>
      </div>
    </div>
  </div>
  <p class="credit">Image by <a
      href="https://www.freepik.com/free-vector/collection-hand-drawn-profile-icons-different-people_18098486.htm#query=user%20profile&position=25&from_view=keyword&track=ais_hybrid&uuid=647577a9-6e46-46bd-8ca1-ad23ee67778d">Freepik</a>
  </p>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        user_image: '',
        role: 'user',
      },
      imageOption: 'url',
      stockImages: [],
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  created() {
    this.loadStockImages();
  },
  methods: {
    loadStockImages() {
      this.stockImages = [

        { name: 'Default Avatar 1', url: '/public/images/avatars/stock1.jpg' },
        { name: 'Default Avatar 2', url: '/public/images/avatars/stock2.jpg' },
        { name: 'Default Avatar 3', url: '/public/images/avatars/stock3.jpg' },
        { name: 'Default Avatar 4', url: '/public/images/avatars/stock4.jpg' },
        { name: 'Default Avatar 5', url: '/public/images/avatars/stock5.jpg' },
        { name: 'Default Avatar 6', url: '/public/images/avatars/stock6.jpg' },
        { name: 'Default Avatar 7', url: '/public/images/avatars/stock7.jpg' },
        { name: 'Default Avatar 8', url: '/public/images/avatars/stock8.jpg' },
        { name: 'Default Avatar 9', url: '/public/images/avatars/stock9.jpg' },
      ];
    },
    selectStockImage(url) {
      this.user.user_image = url;
    },
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: User already exists';
              this.registrationErrors = true;
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
form {
  width: 50%;
}

.text-center {
  display: flex;
  padding-left: 4rem;
  padding: 1rem;
  box-sizing: border-box;
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
  margin-top: 1rem;
  max-width: 100%;
  margin-left: 0px;
  width: 75%;
  padding: .75rem;
  cursor: pointer;
  font-size: 1rem;
}

input[type="text"],
input[type="password"],
input[type="url"],
button {
  width: 75%;
  max-width: 100%;
  max-height: 1rem;
  margin-left: 0px;
  padding: 0.75rem;
  box-sizing: border-box;
  cursor: text;
}

.stock-image-preview {
  display: flex;
  gap: 3rem;
  flex-wrap: wrap;
}

.stock-image-option {
  text-align: center;
  cursor: pointer;
  border: 2px solid transparent;
  padding: 0.5rem;
  border-radius: 0.5rem;
  transition: border-color 0.3s;
}

.stock-image-option.selected {
  border-color: #007bff;
  /* Highlight color when selected */
}

.stock-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 0.5rem;
}

.error-message {
  font-family: Arial, Helvetica, sans-serif;
  color: #ff4d4d;
  background-color: #ffe6e6;
  border: 1px solid #ff4d4d;
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
}

.credit {
  padding-bottom: 20vh;
}

@media (max-width: 790px) {
  .form-input-group {
    padding: .5rem;
  }

  form {
    width: 75%;
  }

  button[type="submit"] {
    padding: .75rem;
    margin-left: 8px;
    width: 97%;
  }

  input[type="text"],
  input[type="password"],
  input[type="url"],
  button {
    width: 100%;
  }

}

@media (max-width: 620px) {
  .form-input-group {
    padding: 0 1rem;
  }

  form {
    width: 100%;
  }

  button[type="submit"] {
    margin-left: 16px;
    margin-right: 50px;
    width: 94%;
  }

  input[type="text"],
  input[type="password"],
  input[type="url"],
  button {
    width: 100%;
  }

  .stock-image {
    width: 60px;
    height: 60px;
  }

  .stock-image-preview {
    gap: 0.5rem;
  }

  button {
    padding: 0.5rem;
  }

}
</style>
