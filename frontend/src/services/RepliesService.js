import http from "./httpService"; // Import the shared Axios instance

export default {

  createReply(postId, reply) {
    return http.post(`/api/post/${postId}/replies`, reply);
  },

  getReplies(postId) {
    return http.get(`/api/posts/${postId}/replies`);
  },

  getReply(id) {
    return http.get(`/api/replies/${id}`);
  },
  deleteReply(id) {
    return http.delete(`/api/replies/${id}`);
  },
  upvoteReplies(id) {
    return http.put(`/api/replies/${id}/upvote`);
  },
  downvoteReplies(id) {
    return http.put(`/api/replies/${id}/downvote`);
  },
  unvotingLikes(id) {
    return http.put(`/api/replies/${id}/upvote/unlike`);
  },
  unvotingDislikes(id) {
    return http.put(`/api/replies/${id}/downvote/undislike`);
  },
};
