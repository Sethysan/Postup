import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

export default {
  // Create Forum
  createForum(forum) {
    return http.post("/forums/create", forum);
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
  getForumTopic(topicId) {
    return http.get(`/forums/${topicId}`);
  },
};
