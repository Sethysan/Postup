import { createStore as _createStore } from 'vuex';
import axios from 'axios';
// import service from '../services/SocketService'

const NOTIFICATION_TIMEOUT = 5000;

export function createStore(currentToken, currentUser, currentAccess, userList = []) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      users: userList,
      access: currentAccess || []
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
        localStorage.removeItem('access')
        state.token = '';
        state.user = {};
        state.access = []
        axios.defaults.headers.common = {};
      },
      SET_ACCESS(state, access){
        localStorage.setItem('access', JSON.stringify(access));
        state.access = access;
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
    actions: {
      SET_ACCESS({state, commit}, access){
        localStorage.setItem('access', JSON.stringify(access))
        state.access = access;
      }
    },
    getters: {
      username(state, getters) {
        if(state.token != ''){
          return JSON.parse(localStorage.getItem('user')).username;
        }
      },
      role(state) {
        if(state.user == {}){
          return 'ROLE_USER';
        }
        return state.user.authorities.length > 0 ? state.user.authorities[0].name : 'ROLE_USER'
      },
      users(state) {
        return state.user;
      },
      userId(state){
        return state.user.id
      },
      access(state){
        return state.access
      }
    }
  });
  return store;
}
