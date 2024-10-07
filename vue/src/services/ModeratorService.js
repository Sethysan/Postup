import http from './httpService'; // Import the shared Axios instance

export default {
    promoteToModerator(userName, forumId) {
        return http.post(`/moderation/forum/${forumId}/promote`, userName);
    },
    getUsers() {
        return http.get(`/users/filtered`);
    }
}
