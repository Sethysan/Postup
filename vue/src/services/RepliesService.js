import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

export default {
  // Create Forum
  createReply(postId, reply, config) {
    return http.post(`/api/posts/${postId}/replies`, reply, config);
  },
  // Get all Forums
  getReplies(postId) {
    return http.get(`/api/posts/${postId}/replies`);
  },
  // Get Specific Forum
  getReply(id) {
    return http.get(`/api/replies/${id}`);
  }
};