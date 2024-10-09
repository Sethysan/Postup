import http from './httpService'; // Import the shared Axios instance

export default {
  // Get all Forums
  getPopularPosts() {
    return http.get(`/api/posts/?filter=popularity&limit=10&today=true`);
  },
  getPostByKeyword(keyword){
    return http.get(`/api/posts`, {
      params: {
        keyword: keyword,
        filter: 'popularity'
      }
    });
  },
  getForumPosts(forum) {
    return http.get(`/api/forum/${forum}/posts`);
  },
  getForumPostByKeyword(forum, keyword){
    return http.get(`/api/forum/${forum}/posts`, {
      params: {
        keyword: keyword,
        filter: 'popularity'
      }
    });
  },
  getPostById(id) {
    return http.get(`/api/posts/${id}`);
  },
  createPost(forum, post) {
    return http.post(`/api/forum/${forum}/posts`, post);
  },
  updatePost(id, post) {
    // TODO: is this a put or a post?
    return http.post(`/api/posts/${id}`, post);
  },
  deletePost(id) {
    return http.delete(`/api/posts/${id}/delete`);
  },
  upvotePost(id) {
    return http.put(`/api/posts/${id}/upvote`);
  },
  downvotePost(id) {
    return http.put(`/api/posts/${id}/downvote`);
  },
  unvotingLike(id) {
    return http.put(`/api/posts/${id}/upvote/unlike`);
  },
  unvotingDislike(id) {
    return http.put(`/api/posts/${id}/downvote/undislike`);
  }
 
};
