import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {

    createForum(forum) {
        return http.post('/forums', forum);
    },

    getForums() {
        return http.get('/forums');
    }
}