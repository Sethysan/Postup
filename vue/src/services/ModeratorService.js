import http from './httpService'; // Import the shared Axios instance

export default {
    promoteToModerator(username, forumId) {
        return http.post(`/moderation/forum/${forumId}/promote`, username);
    },
    getUsers() {
        return http.get(`/users/filtered`);
    }
}
