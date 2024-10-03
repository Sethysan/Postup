import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

export default {
  // Create Forum
  createReply(postId, reply) {
    return http.post(`/api/post/${postId}/replies`, reply, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`, // Add the Authorization header
      },
    });
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