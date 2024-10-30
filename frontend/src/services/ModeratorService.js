import http from './httpService'; // Import the shared Axios instance

export default {
    promoteToModerator(username, forumId) {
        return http.post(`/moderation/forum/${forumId}/promote`, {
            username: username
        });
    },
    getUsers(forumId) {
        return http.get(`/users/filtered/${forumId}`);
    },
    getAllUsers(){
        return http.get(`/users`);
    },
    getListOfMods(forumId) {
        return http.get(`/moderation/forum/${forumId}`);
    } ,
    getUserAccess(user) {
        return http.get(`/moderation/user/${user}`);
    } 
}
