import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

export default {
  // Get all Forums
  getPostsByForum(forum) {
    return http.get(`/posts/${forum}`);
  },
  getRecentPostByForum(forum) {
    return http.get(`/posts/${forum}?filter=recent`)
  },
  getPopularPostByForum(forum){
    return http.get(`/posts/${forum}?filter=popular`)
  }
};
