import { createApp } from 'vue';
import CapstoneApp from './App.vue';
import { createStore } from './store';
import router from './router';
import axios from 'axios';
import { MDBIcon } from 'mdb-vue-ui-kit';
import Vue3SocialSharing from 'vue3-social-sharing';

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = import.meta.env.VITE_API_URL;

/*
 * The authorization header is set for axios when you login but what happens when 
 * you come back or the page is refreshed. When that happens you need to check 
 * for the token in local storage and if it exists you should set the header 
 * so that it will be attached to each request.
 */
let currentToken = localStorage.getItem('token');
let currentUser = JSON.parse(localStorage.getItem('user'));
let access = localStorage.getItem('access');

if (currentToken) {
  // Set token axios requests
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

// Create the Vuex store passing in the stored credentials
const store = createStore(currentToken, currentUser, access)

const app = createApp(CapstoneApp);
app.use(Vue3SocialSharing);
app.use(store);
app.use(router);
app.component('MDBIcon', MDBIcon);
app.mount('#app');