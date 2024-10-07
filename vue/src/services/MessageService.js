import http from './httpService';

export default {
    getContacts(){
        return http.get("/api/contacts/");
    },
    getMessages(contact){
        return http.get(`/api/contacts/${contact}/messages`);
    },
    createMessage(contact, message){
        return http.post(`/api/contacts/${contact}`, message);
    }
}