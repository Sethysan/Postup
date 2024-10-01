import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

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
};
