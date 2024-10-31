import { createApp } from 'vue'
import CapstoneApp from './App.vue'
import { createStore } from './store'
import router from './router'
import axios from 'axios'
// import { library } from '@fortawesome/fontawesome-svg-core';
// import { faThumbsUp, faThumbsDown } from '@fortawesome/free-regular-svg-icons';
// import { faCircleArrowUp, faCircleArrowDown} from '@fortawesome/free-solid-svg-icons';
// import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
// import Vue3SocialSharingPlugin from "vue3-social-sharing";
// import { library } from '@fortawesome/fontawesome-svg-core';
// import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
// import { faFacebookF, faTwitter } from '@fortawesome/free-brands-svg-icons'
import 'font-awesome/css/font-awesome.css';
import { MDBIcon } from 'mdb-vue-ui-kit';
// import 'vue-toastification/dist/index.css';

// library.add(faThumbsUp, faThumbsDown,faCircleArrowUp,faCircleArrowDown, faFacebookF, faTwitter);


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
// app.use(Vue3SocialSharingPlugin);
app.use(store);
app.use(router);
// app.component('font-awesome-icon', FontAwesomeIcon);
app.component('MDBIcon', MDBIcon);
// app.use(Toast, {
//   position: POSITION.BOTTOM_CENTER,
//   timeout: 3000, // Adjust the timeout as needed
// });
app.mount('#app');