import { createStore as _createStore } from 'vuex';
import axios from 'axios';
// import service from '../services/SocketService'

const NOTIFICATION_TIMEOUT = 5000;

export function createStore(currentToken, currentUser, userList = []) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      users: userList
    },
    mutations:{
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        console.log('Storing user in localStorage:', user);
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
      SET_NOTIFICATION(state, notification) {
        if (state.notification) {
          this.commit('CLEAR_NOTIFICATION');
        }
        if (typeof notification === 'string') {
          notification = {
            message: notification,
            type: 'error',
            timeout: NOTIFICATION_TIMEOUT
          }
        } else {
          notification.type = notification.type || 'error';
          notification.timeout = notification.timeout || NOTIFICATION_TIMEOUT;
        }
        state.notification = notification;
        notification.timer = window.setTimeout(() => {
          this.commit('CLEAR_NOTIFICATION');
        }, notification.timeout);
      },
      CLEAR_NOTIFICATION(state) {
        if (state.notification && state.notification.timer) {
          window.clearTimeout(state.notification.timer);
        }
        state.notification = null;
      },
      PROMOTE_USER(state, userName) {
        const user = state.user.find(u => u.username === userName);
        if (user) {
          user.role = 'ROLE_MODERATOR';
        } else {
          console.error('User not found for promotion:', userName);
        }
      },
      BAN_USER(state, userName) {
        const user = state.user.find(u => u.username === userName);
        if (user) {
          user.banned = true;
        } else {
          console.error('User not found for ban:', userName);
        }
      },
      UNBAN_USER(state, userName) {
        const user = state.user.find(u => u.username === userName);
        if (user) {
          user.banned = false;
        } else {
          console.error('User not found for unban:', userName);
        }
      }
    },
    getters: {
      username(state, getters) {
        if(state.token != ''){
          return JSON.parse(localStorage.getItem('user')).username;
        }
      },
      role(state) {
<<<<<<< HEAD
        return state.user.authorities.length > 0 ? state.user.authorities[0].name : 'ROLE_USER'
=======
        return state.user.role ? state.user.role : null;
>>>>>>> a71e210cf325e0818d196f3f5b0205910098ca65
      },
      users(state) {
        return state.user;
      },
      userId(state){
        return state.user.id
      }
    }
  });
  return store;
}
