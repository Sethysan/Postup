import http from './httpService';

export default {
    getUsers(){
        return http.get(`/users`);
    },
    getContacts(){
        return http.get("/api/contacts/");
    },
    getMessages(contact){
        return http.get(`/api/contacts/${contact}/messages`);
    },
    createMessage(contact, message){
        return http.post(`/api/contacts/${contact}`, message);
    },
    updateMessage(contact, id, message){
        return http.put(`/api/contacts/${contact}/messages/${id}`, message)
    },
    deleteMessage(contact, id){
        return http.delete(`/api/contacts/${contact}/messages/${id}`)
    },
    markRead(contact, id){
        return http.put(`/api/contacts/${contact}/messages/${id}/read`)
    }
}