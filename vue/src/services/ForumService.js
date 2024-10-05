import http from './httpService'; // Import the shared Axios instance

export default {
  // Create Forum
  createForum(forum, config) {
    return http.post("/forums/create", forum, config);
  },
  // Get all Forums
  getForums() {
    return http.get("/forums");
  },
  // Get Specific Forum
  getForum(forumId) {
    return http.get(`/forums/${forumId}`);
  },
  //Get Specific Topic
  //adjusted for back end changes to include Request Param
  getForumTopic(topic) {
    return http.get(`/forums/topic`, { params: { topic } });
  },
  getActiveForums(){
    return http.get("/forums?isMostActive=true");
  },
  //Get forum with included search params
  searchForumsByTopicAndDescription(searchTerm) {
    return http.get(`/forums/search?searchTerm=${encodeURIComponent(searchTerm)}`);
  },
  getFavoriteForums(){
    return http.get(`/forums/favorites`)
  },
  addFavorite(id){
    return http.post(`/forum/${id}/favorites`);
  },
  removeFavorite(id){
    return http.delete(`/forum/${id}/favorites`);
  }
};
