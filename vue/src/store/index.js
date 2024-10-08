import { createStore as _createStore } from 'vuex';
import axios from 'axios';
import service from '../services/SocketService'

const NOTIFICATION_TIMEOUT = 5000;

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      newMessages: [], // should have structure of sender: number
      newPosts: [], // should have the structure of forum: number
      socketConnection: false
    },
    mutations:{
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
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
      SET_CONNECTION_STATUS(state, status){
        state.socketConnection = status
      },
      ADD_MESSAGE(state, message){
        state.message.push({sender: message.from, time: message.time});
      },
      REMOVE_MESSAGE(state, message){
        state.message = state.message.filter(item => item.from == message.from);
      },
      ADD_POST(state, message){
        state.message.push({sender: message.from, time: message.time});
      },
      REMOVE_POST(state, message){
        state.message = state.message.filter(item => item.from == message.from);
      },
    },
    actions: {
      CONNECT({ state, commit }){
        alert("connecting")
        service.connect(state.token, ()=>{
          commit('SET_CONNECTION_STATUS', true);}, 
          (err)=>alert("error while connecting " + err.message))
      },
      SUBSCRIBE_TO_CHAT({ state, commit }){
        if(state.isConnected){
          service.subscribeToChat(message => {commit('ADD_MESSAGE', JSON.parse(message))
             alert("message received");
            console.log("message received")});
        }
      },
      SUBSCRIBE_TO_POST({ state, commit }){
        if(state.isConnected){
          service.subscribeToPosts(message => commit('ADD_POST', JSON.parse(message)))
        }
      },
      DISCONNECT(){
        socket.disconnect({ state, commit });
        commit('SET_CONNECTION_STATUS', false);
      }
    },
    getters: {
      username(state, getters) {
        if(state.token != ''){
          return JSON.parse(localStorage.getItem('user')).username;
        }
      },
      role(state) {
        return state.user.role ? state.user.role : null;
      },
      users(state) {
        return state.user;
      },
      isConnected(state){
        return state.socketConnection;
      }
    }
  });
  return store;
}
