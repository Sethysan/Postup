import http from './httpService'; // Import the shared Axios instance

export default {

  login(user) {
    return http.post('/login', user)
  },
  register(user) {
    return http.post('/register', user)
  }

}
