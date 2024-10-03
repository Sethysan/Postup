import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

export default {
  // Get all Forums
  getPopularPosts() {
    return http.get(`/api/posts/?filter=popularity&limit=10&today=true`)
  },
  getForumPosts(forum) {
    return http.get(`/api/forum/${forum}/posts`);
  },
  getPostById(id) {
    return http.get(`/api/posts/${id}`);
  },
  createPost(forum, post) {
    const token = localStorage.getItem("token");
    return http.post(`/api/forum/${forum}/posts`, post, {
      headers: {
        Authorization: `Bearer ${token}`, // Add the Authorization header
      },
    });
  },
  updatePost(id, post) {
    return http.post(`/api/posts/${id}`, post);
  },
  deletePost(id) {
    const token = localStorage.getItem("token");
    return http.delete(`/api/posts/${id}/delete`, {
      headers: {
        Authorization: `Bearer ${token}`, // Add the Authorization header
      },
    });
  },
  upvotePost(id) {
    return http.put(`/api/posts/${id}/upvote`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}` // Add token to the Authorization header
  }});
  },
  downvotePost(id) {
    return http.put(`/api/posts/${id}/downvote`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}` // Add token to the Authorization header
    }}
    );
  },
  unvotingLike(id) {
    return http.put(`/api/posts/${id}/upvote/unlike`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}` // Add token to the Authorization header
    }}
    );
  },
  unvotingDislike(id) {
    return http.put(`/api/posts/${id}/downvote/undislike`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}` // Add token to the Authorization header
    }}
    );
  },
};
