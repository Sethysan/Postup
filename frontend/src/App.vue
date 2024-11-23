<template>
  <div class="wrapper">
    <div id="nav">
      <div class="user-info" v-if="isLoggedIn">
        <div class="user-image-container" @mouseover="showEditTooltip = true" @mouseleave="showEditTooltip = false"
          @click="toggleEditForm">
          <img v-if="userImage" :src="userImage" alt="User Image" class="user-portrait" />
          <span v-if="showEditTooltip" class="edit-tooltip">Do you want to change you profile picture?</span>
          <p class="name">{{ userName }}</p>
        </div>
      </div>
      <button v-if="role === 'ROLE_ADMIN'" class="nav-btn">
        <div :style="getButtonStyle('admin')">
          <router-link v-bind:to="{ name: 'admin' }" v-if="userName" :class="getLinkClass('admin')">ADMIN</router-link>
        </div>&nbsp;
      </button>
      <button class="nav-btn">
        <div :style="getButtonStyle('home')">
          <router-link v-bind:to="{ name: 'home' }" :class="getLinkClass('home')">HOME</router-link>
        </div>&nbsp;
      </button>
      <button class="nav-btn">
        <div :style="getButtonStyle('forums')">
          <router-link v-bind:to="{ name: 'forums' }" :class="getLinkClass('forums')">FORUMS</router-link>
        </div>&nbsp;
      </button>
      <button v-if="isLoggedIn" class="nav-btn">
        <div :style="getButtonStyle('favorites')">
          <router-link v-bind:to="{ name: 'favorites' }" v-if="userName"
            :class="getLinkClass('favorites')">FAVORITES</router-link>
        </div>&nbsp;
      </button>
      <button v-if="isLoggedIn" class="nav-btn">
        <div :style="getButtonStyle('messages')">
          <router-link v-bind:to="{ name: 'messages' }" v-if="userName"
            :class="getLinkClass('messages')">MESSAGES</router-link>
        </div>&nbsp;
      </button>
      <button v-if="isLoggedIn" class="nav-btn">
        <div :style="getButtonStyle('logout')">
          <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''"
            class="router-link-nonactive">LOGOUT</router-link>
        </div>&nbsp;
      </button>
      <button v-if="!this.isLoggedIn" class="nav-btn">
        <div :style="getButtonStyle('login')">
          <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"
            class="router-link-nonactive">LOGIN</router-link>
        </div>&nbsp;
      </button>
      <div class="logo-container">
        <img src="/images/POST-UP_logo.png" alt="Logo" class="logo" />
      </div>
    </div>

    <!-- form for update user image -->
    <div v-if="editFormVisible" class="edit-form">
      <form @submit.prevent="updateUserDetails">
        <div class="image-group">
          <label for="userImage">Profile Image URL</label>
          <input type="text" ref="myInput" v-model="updatedUserImage" placeholder="Enter new image URL" />
        </div>
        <div class="edit-pic-btns">
          <button class="pic-save" type="submit">Save</button>
          <button class="pic-cancel" type="button" @click="toggleEditForm">Cancel</button>
        </div>
      </form>
    </div>


    <WidgetContainerModal />

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
      role: this.$store.getters.role,
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
    getButtonStyle(routeName) {
      if (this.$route.name === routeName) {
        return {
          boxShadow: 'inset 0 -18px 4px  linear-gradient(to bottom, var(--secondary),rgb(87, 122, 199, 0.83))', // linear-gradient(to bottom, var(--secondary),rgb(87, 122, 199, 0.23));
        };
      } else {
        return {
          boxShadow: 'inset 0 -18px 4px  linear-gradient(to bottom, var(--secondary),',// Shared shadow for non-active buttons
          background: `
        radial-gradient(circle, rgba(128, 93, 32, 0.075) 50%, rgba(128, 93, 32, 0.01)),
        linear-gradient(to bottom, rgb(9, 9, 9), rgb(9, 9, 9), var(--secondary), var(--secondary), var(--secondary), rgba(128, 93, 32, .1), rgba(128, 93, 32, 0.124))`,
        };
      }
    },
    toggleEditForm() {
      this.editFormVisible = !this.editFormVisible;
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

  },
  watch: {
    // Watch the editFormVisible property
    editFormVisible(newVal) {
      if (newVal) {
        // Use $nextTick to ensure the input is in the DOM before trying to focus it
        this.$nextTick(() => {
          if (this.$refs.myInput) {
            this.$refs.myInput.focus();
          }
        });
      }
    }
  }
};
</script>

<style>
.wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100%;
  background-color: var(--secondary);
  align-items: center
}

::after,
::before {
  box-sizing: border-box;
}

/* Global scrollbar styling */
::-webkit-scrollbar {
  width: 16px;
  height: 16px;
}

::-webkit-scrollbar-track {
  background: #4b4a4a;
}

::-webkit-scrollbar-thumb {
  background-color: #888;
  transition: background-color 0.3s ease-in-out;
}

::-webkit-scrollbar-thumb:hover {
  cursor: grab;
  background-color: rgba(234, 85, 11, 0.5);
}

::-webkit-scrollbar-thumb:active {
  cursor: grabbing;
  /* Change to grabbing when holding left click */
}


body,
html {
  font-family: "Work Sans", sans-serif;
  font-weight: 400;
  line-height: 1.5;
  color: var(--grey);
  background-color: var(--secondary);
  text-align: left;
  height: 100%;
  min-height: 100%;
  margin: 0;
}

body {
  padding: 1%;
}

:root {
  --primary: rgb(60, 184, 255);
  --secondary: #0f0f0f;
  --grey: #969696;
  --blend: #434242;
  --nero: #1f1f1f;
  --blue: rgb(60, 184, 255);
  --deepblue: rgb(87, 122, 199);
  --orange: rgb(248, 133, 29);
}

#nav {
  display: flex;
  flex-direction: column;
  /* Stack items vertically by default */
  align-items: center;
  background-color: var(--secondary);
  width: 100%;
  padding: 10px;
  position: relative;
  z-index: 1000;
}


.logo-container {
  margin: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
}

.logo {
  height: 100%;
  object-fit: contain;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-image-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  cursor: pointer;
}

.name {
  position: absolute;
  bottom: -12px;
  color: white;
  background-color: rgba(0, 0, 0, 0.733);
  width: 100%;
  padding: 3px 7px;
  text-align: center;
  border-radius: 10px;
  transform: translateY(50%);
}

.user-portrait {
  width: calc(2 * 60px);
  height: calc(2 * 60px);
  border-radius: 50%;
  object-fit: cover;
  z-index: 0;
}

.edit-tooltip {
  position: absolute;
  top: 0;
  left: 60px;
  color: #888;
  background-color: rgba(0, 0, 0, 0.733);
  padding: 5px;
  border-radius: 8px;
  font-size: 12px;
  white-space: nowrap;
  z-index: 100;
}

.edit-form {
  position: fixed;
  justify-content: center;
  display: flex;
  top: 100px;
  left: 0;
  right: 65%;
  margin: 0 auto;
  max-width: 15%;
  background: radial-gradient(circle, var(--nero) 55%, rgba(0, 0, 0, 0.699));
  border: 1px solid black;
  padding: 20px;
  padding-bottom: 10px;
  padding-bottom: 10px;
  border-radius: 5px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, .7);
  z-index: 10;
  cursor: move;
  transition: transform 0.1s ease-out;
  cursor: move;
  transition: transform 0.1s ease-out;
}

.image-group label {
  display: block;
  display: flex;
  justify-content: center;
  margin-bottom: 5px;
}

.form-group input {
  display: flex;
  justify-content: center;
  background-color: rgb(139, 138, 138);
  width: 100%;
  margin-top: 15px;
  margin-right: 15px;
  padding: 5px;
  border: 2px solid #000000;
  border-radius: 8px;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
}

.form-group input:focus {
  outline: none;
}

.edit-pic-btns {
  display: flex;
  margin-top: 15px;
  margin-bottom: 5px;
  justify-content: space-between;
}

.pic-save,
.pic-cancel {
  background-color: rgb(248, 133, 29) !important;
  transition: transform 0.3s ease-in-out;
  border-radius: 5px;
  width: 70px;
  padding: 5px;
  color: white;
}

.pic-save:hover,
.pic-cancel:hover {
  background-color: rgb(185, 98, 22) !important;
  ;
}

.pic-save:hover,
.pic-cancel:hover {
  background-color: rgb(185, 98, 22) !important;
  ;

}

.nav-btn .router-link-nonactive{
  transition: box-shadow 0.3s ease-in-out;
}

.nav-btn {
  font-size: 18px;
  /* Smaller font size for mobile */
  margin: 5px 0;
  /* Space between buttons */
  padding: 10px 5px;
  text-align: center;
  /* Make buttons wider for easier tapping */
  background-color: transparent;
  color: white;
  border: none;
  border-radius: 8px;
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  ;
}

.nav-btn:hover .router-link-nonactive {
  border:none;
  text-decoration: none;
  box-shadow: 0px 15px 25px rgba(87, 122, 199, 0.3);
  background:
    radial-gradient(circle, rgba(87, 122, 199, 0.099) 50%, rgba(87, 122, 199, 0.01)),
    linear-gradient(to bottom, rgb(9, 9, 9), var(--secondary), var(--secondary), rgba(19, 26, 41, 0.4), rgba(10, 20, 40, 0.721));
}

router-view {
  position: relative;
  z-index: 1;
  /* Ensure the router-view content appears above the video */
}

.nav-btn .router-link-active,
.router-link-exact-active {
  position: relative;
  text-decoration: none;
  font-size: 30px;
  background-color: transparent;
  color: var(--deepblue);
  box-shadow: 0px 15px 25px rgba(87, 122, 199, 0.3);
  transition: 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  z-index: 2;
}

.nav-btn .router-link-active::after,
.router-link-exact-active::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background:
    radial-gradient(circle, rgba(87, 122, 199, 0.099) 50%, rgba(87, 122, 199, 0.01)),
    linear-gradient(to bottom, rgb(9, 9, 9), var(--secondary), var(--secondary), rgba(87, 122, 199, 0.1), rgba(74, 110, 160, 0.181));
  z-index: -1;
  border-radius: inherit;

}

.router-link-nonactive {
  text-decoration: none;
  color: var(--grey);
  box-shadow: 0 15px 25px rgba(128, 93, 32, 0.282);
  transition: color .3s ease-in-out, box-shadow 0.5s ease-in-out;
  z-index: 2;
  display: inline-block;
}


#logged-in {
  color: black;
}

.post-author {
  color: var(--blue);
  text-decoration: none;
}

.modal-container {
  z-index: 2;
}

.swiper-3d .swiper-slide-shadow-left {
  right: 8.5% !important;
}

.swiper-3d .swiper-slide-shadow-right {
  left: 8.5% !important;
}

/* Media Query for Tablets */
@media (min-width: 768px) {
  #nav {
    flex-direction: row;
    /* Switch to horizontal layout */
    justify-content: space-around;
    /* Evenly space buttons */
    padding: 15px 20px;
  }

  .nav-btn {
    font-size: 20px;
    margin: 0 10px;
    /* Space buttons horizontally */
    width: auto;
    /* Allow buttons to size dynamically */
  }

  .logo-container {
    height: 75px;
  }
}

/* Media Query for Desktops */
@media (min-width: 1024px) {
  #nav {
    justify-content: space-between;
    /* Spread out content */
    padding: 20px 40px;
  }

  .nav-btn {
    font-size: 25px;
    padding: 10px 20px;
  }

  .logo-container {
    height: 100px;
  }
}
</style>