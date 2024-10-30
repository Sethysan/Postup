import http from './httpService'; // Import the shared Axios instance

export default {
    getUsers() {
        return http.get('/users/admin');
    },
    promoteToAdmin(id) {
        return http.put(`/moderation/user/${id}/promote/admin`);
    },
    banUser(id) {
        return http.put(`/user/${id}/ban`);
    },
    unbanUser(id) {
        return http.put(`/user/${id}/unban`);
    },
    
}
