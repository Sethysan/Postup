import http from './httpService'; // Import the shared Axios instance
export default {
    
    updateUserDetails(userId,updates){
        return http.put(`/api/user/${userId}`, updates, {
            headers: {
                'Content-Type': 'application/json'
              }
        });
    }
};