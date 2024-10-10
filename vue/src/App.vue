<template>
  <div id="nav">
    <div class="user-info" v-if="isLoggedIn">
      <div class="user-image-container" @mouseover="showEditTooltip = true" @mouseleave="showEditTooltip = false"
        @click="toggleEditForm">
        <img :src="userImage" alt="User Image" class="user-image" />
        <span v-if="showEditTooltip" class="edit-tooltip">Edit Details</span>
      </div>
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
    <div class="logo-container">
      <img src="/images/POST-UP_logo.png" alt="Logo" class="logo" />
    </div>
  </div>

  <div v-if="editFormVisible" class="edit-form">
    <h3>Edit Your Details</h3>
    <form @submit.prevent="updateUserDetails">
      <div class="form-group">
        <label for="userImage">Profile Image URL:</label>
        <input type="text" v-model="updatedUserImage" placeholder="Enter new image URL" />
      </div>
      <button type="submit">Save Changes</button>
      <button type="button" @click="toggleEditForm">Cancel</button>
    </form>
  </div>
  <WidgetContainerModal />
  <div id="capstone-app">
    <router-view />
  </div>
</template>
<script>
import UserService from './services/UserService.js';
import ModeratorService from './services/ModeratorService';
import { container } from 'jenesius-vue-modal'

export default {
  components: { WidgetContainerModal: container },
  name: "App",
  data() {
    return {
      showEditTooltip: false,
      editFormVisible: false,
      updatedUserImage: '',
      role: this.$store.getters.role
    };
  },
  created() {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user && user.user_image) {
      const sanitizedImageUrl = user.user_image.trim().replace(/"/g, '');
      this.$store.commit("SET_USER_IMAGE", sanitizedImageUrl);
    }
  },
  computed: {
    // Get the user's name from Vuex
    userName() {
      return this.$store.getters.username;
    },
    // Compute whether the user is logged in
    isLoggedIn() {
      return this.$store.state.token !== '';
    },
    userImage() {
      return this.$store.getters.userImage;
    },
  },
  methods: {
    getLinkClass(routeName) {
      return this.$route.name === routeName ? 'router-link-active' : 'router-link-nonactive';
    },
    toggleEditForm() {
      this.editFormVisible = !this.editFormVisible;
      if (this.editFormVisible) {
        // Populate the form with the current details
        this.updatedUserImage = this.userImage;
      }
    },
    updateUserDetails() {
      const sanitizedImageUrl = this.updatedUserImage.trim().replace(/"/g, '');
      const updates = JSON.stringify(sanitizedImageUrl);
      UserService.updateUserDetails(this.$store.getters.userId, updates)
        .then(() => {
          this.$store.commit('SET_USER_IMAGE', this.updatedUserImage);
          alert('User details updated successfully.');
          this.toggleEditForm(); // Close the form after saving
        }).catch(error => {
          console.error('Error updating user details:', error);
          alert('Failed to update user details.');
        });
    }
  }
};
</script>
<style>
#nav {
  display: flex;
  justify-content: space-evenly;
  left: 0;
  width: 100%;
  z-index: 1000;
  background-color: rgb(240, 107, 19);
  padding: 10px;
  margin-bottom: 0px;

}

.logo-container {

  top: 0;
  right: 20px;
  height: calc(2 * 60px);
  /* Adjust '60px' to match the height of your nav */
  display: flex;
  align-items: center;
}

.logo {
  height: 100%;
  object-fit: contain;
  /* Ensures the logo scales nicely */
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-image-container {
  position: relative;
  cursor: pointer;
}

body,
html {
  margin: 0;
  padding: 0;
}

.user-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
}

.edit-tooltip {
  position: absolute;
  top: 0;
  left: 60px;
  background-color: #333;
  color: #fff;
  padding: 5px;
  border-radius: 5px;
  font-size: 12px;
  white-space: nowrap;
}

.edit-form {
  position: fixed;
  top: 70px;
  /* Adjust this value based on the height of your #nav */
  left: 0;
  right: 50%;
  margin: 0 auto;
  max-width: 600px;
  background: #fff;
  border: 1px solid #ddd;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 999;
  /* Make sure the form is above other elements */
}

.form-group {
  margin-bottom: 10px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}


#capstone-app {
  font-family: Arial, Helvetica, sans-serif;
  background: radial-gradient(circle, rgb(255, 166, 107) 35%, rgba(240, 107, 19, 0.927));
  padding-left: 2vw;
  padding-right: 2vw;
  margin-top: 0px;
  padding-top: 10px;
  margin-bottom: 0px;
  padding-bottom: 8vh;
}

.nav-btn {
  background-color: transparent;
  color: white;
  border: none;
  font-size: 25px;
  padding: 5px;
  transition: transform 0.3s ease-in-out;
  /* Smooth transition for hover effects */
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

#logged-in {
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

.post-author {
  color: black;
  text-decoration: none;
}
</style>
