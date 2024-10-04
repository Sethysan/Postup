import http from './httpService'; // Import the shared Axios instance

export default {
    promoteToModerator(userId, forumId) {
        return http.post(`/moderation/forum/${forumId}/promote`, userId);
    }
}
