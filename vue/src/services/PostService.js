import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

export default {
  // Get all Forums
  getPopularPosts() {
    return http.get(`/api/posts/?filter=popularity&limit=10`)
  },
  getForumPosts(forum, filter) {
    return http.get(`/api/forum/${forum}/posts?filter=${filter}`)
  },
  getPostById(id){
    return http.get(`/api/posts/${id}`)
  },
  createPost(forum, post){
    return http.post(`/api/forum/${forum}/posts`, post);
  },
  updatePost(id, post){
    return http.post(`api/posts/${id}`, post);
  },
  deletePost(id){
    return http.delete(`api/posts/${id}`);
  },
};
