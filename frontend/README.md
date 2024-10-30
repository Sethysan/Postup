# Final Vue Capstone Project Seed
​
This is the Vue starter project for the final capstone. This document walks you through how to set up and run the project. It also explains the project's features, such as Vue Router, Vuex, and authentication.
​
## Project setup
​
The first thing you'll need to do is to download any dependencies by running this command:
​
```
npm install
```
​
Next take a moment to review the `.env` file that's located in the root of the project. You can store environment variables that you want to use throughout your application in this file. When you open it, it'll look like this:
​
```
VITE_REMOTE_API=http://localhost:9000
```
​
*Note:* The Java Spring Boot application is configured to run on port 9000 instead of 8080.
​
Start your Vue application with the following command:
​
```
npm run dev
```
​
## Authentication
​
When you first run the project and visit the base URL, you're taken to a login page. This is because the home route `/` is secured by default. If you look in `src/router/index.js`, you'll see the following code:
​
```js
router.beforeEach((to) => {

  // Vuex code...

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
​
  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});
```
​
This is a feature of Vue Router called [Navigation Guards](https://router.vuejs.org/guide/advanced/navigation-guards.html). You may not have learned about this in class, so take some time to read through the documentation to learn what they are and how they work.
​
The preceding code runs before each route. It first checks to see if the route requires authentication that's defined per route using the meta object key `requiresAuth`.
​
In the following configuration, you must be authenticated to view the `home` route while anyone can visit the `login`, `logout`, and `register` routes:
​
```js
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  }
];
```
​
Next, the navigation guard checks to see if the route requires authentication and if an authentication token exists.
​
If authentication isn't required, *or* the authentication token does exist—meaning it isn't an empty string—the user is routed to the requested route.
​
However, if authentication is required *and* the authentication token doesn't exist—meaning it's an empty string—the user is redirected to the `/login` route:
​
```js
// If it does and they are not logged in, send the user to "/login"
if (requiresAuth && store.state.token === '') {
  return {name: "login"};
}
// Otherwise, do nothing and they'll go to their next destination
```
> Note: the application stores the current user (if any) and their authentication token in a centralized store using Vuex.
​
### Vuex
​
The state for this application is stored in `src/store/index.js` using Vuex. The state object has two values: `token` and `user`. When you log in, the back-end service returns an authentication token along with your user credentials.
​
The authentication token is sent in the `Authorization` header to verify your identify. To persist this token when the application is closed or the page is refreshed, you'll store the token in local storage.
​
The default token either comes from local storage or it's set to an empty string. As you learned in the previous section, if the route requires authentication and this token is empty, it redirects the user to the login page:
​
```js
// src/main.js
const currentToken = localStorage.getItem('token');
if (currentToken) {
  // Set token axios requests
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}
​
// src/store/index.js
export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {}
    },
    // ...
  });
}
```
​
### Login
​
When you reach the `/login` route, you'll see a bare login page. This is intentional. It's up to you to style this page to fit within your application.
​
When you fill in a username and password and click the "Sign In" button, the method `login()` is called. The `login()` method uses the `src/services/AuthService.js` to send a `POST` request to your API's `/login` route.
​
If you look at `AuthService`, you'll notice the it sets the base URL for Axios and the value comes from the `.env` property file you saw earlier:


```js
import axios from 'axios';
​
const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {
​
  login(user) {
    return http.post('/login', user)
  },

  register(user) {
    return http.post('/register', user)
  }
​
}
```

Note the call to `axios.create()` to create an HTTP connection and sets it to the `http` variable, and that's used within the `login()` and `register()` service methods to POST the requests to the API with Axios.

If you get a successful response (200), it contains the authentication token and user object. You'll set these in Vuex by committing mutations:​

```js
// src/views/LoginView.vue
login() {
  authService
    .login(this.user)
    .then(response => {
      if (response.status == 200) {
        this.$store.commit("SET_AUTH_TOKEN", response.data.token);
        this.$store.commit("SET_USER", response.data.user);
        this.$router.push("/");
      }
    })
  // ...
}
```
​
When you call the `SET_AUTH_TOKEN` mutation, several things happen.
​
First, you set the `state.token` value to what's returned from the API's `/login` method. Next, you store that same value in local storage so that it persists across refreshes. Finally, you set the `Authorization` header in Axios so that every subsequent request contains the token. This way, you don't have to manually do this on every request:
​
```js
mutations: {
  SET_AUTH_TOKEN(state, token) {
    state.token = token;
    localStorage.setItem('token', token);
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  }
}
```
​
Once the `login()` method finishes updating the store by committing the mutations, it forwards the user back to the homepage. They'll be able to see the homepage because they're authenticated.
​
### Logout
​
There's a logout link in `App.vue` that forwards the user to the `/logout` route. When the user reaches this route, you'll commit this mutation in the store called `LOGOUT`:
​
```js
// src/views/LogoutView.vue
export default {
  created() {
    this.$store.commit("LOGOUT");
    this.$router.push("/login");
  }
};
```
​
When the mutation is called, the token is removed from local storage, the token and user state are cleared, and the user is redirected back to the homepage. The homepage then forwards the user to the login page because they're no longer logged in:
​
```js
mutations: {
  LOGOUT(state) {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    state.token = '';
    state.user = {};
    axios.defaults.headers.common = {};
  }
}
```
​
### Register
​
When you reach the `/register` route, you'll see a bare registration page. Like the login page, this is intentional. You'll need to style this page to fit within your application.
​
When you fill in a username, password, confirm the password role, and click the "Create Account" button, the method `register()` is called. This calls the `register()` method in `src/services/AuthService.js`. This passes your user details to your back-end application's REST API to create a new user:
​
```js
// src/views/RegisterView.vue
methods: {
  register() {
  // ...
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
    // ...
  }
}
```
