import http from './httpService'; // Import the shared Axios instance

export default {
  // Create Forum
  createReply(postId, reply) {
    return http.post(`/api/post/${postId}/replies`, reply);
  },
  // Get all Forums
  getReplies(postId) {
    return http.get(`/api/posts/${postId}/replies`);
  },
  // Get Specific Forum
  getReply(id) {
    return http.get(`/api/replies/${id}`);
  },
  deleteReply(id) {
    return http.delete(`/api/replies/${id}`);
  }
};