import http from './httpService'; // Import the shared Axios instance

export default {

  login(user) {
    return http.post('/login', user)
  },
  register(user) {
    console.log(console.log('User being registered:', user));
    return http.post('/register', user)
  }

}
