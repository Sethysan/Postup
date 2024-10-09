<template>
    {{ id }}
        <div v-for="message in messages" :key="message.id" :class="message.sender.username === username ? 'user-message' : 'others-message'" @messageDeleted="(id) => this.messages = this.messages.filter(item => item.id != id)">
            <message :message="message" @messageDeleted="removeMessage"></message>
        </div>
        <textarea v-model="message.message" placeholder="Enter your message"></textarea>
        <button :onclick="sendMessage">Send</button>
</template>

<script>
import service from '../services/MessageService';
import Message from './Message.vue';
import socket from '../services/SocketService'

export default {
    components: {Message},
    props: ['messages', 'contact'],
    data(){
        return {
            username: this.$store.getters.username,
            id: this.$store.getters.userId,
            message: {
                sender: {username: this.$store.getters.username}
            },
        }
    },
    created(){
            socket.connect(this.$store.token, ()=>socket.subscribeToChat(message=>{
                if(message.from = this.contact){
                    alert("received")
                    const newMessage = {};
                    newMessage.message = message.message;
                    newMessage.sender.username = newMessage
                    newMessage.sender.id = this.contact;
                    newMessage.receivedOn = message.time;
                    newMessage.read = true;
                    this.messages.push(newMessage);
                }
            }));
        },
    methods: {
        sendMessage(){
            service.createMessage(this.contact, this.message)
                .then(res => {
                    this.messages.unshift(res.data);
                    const messagePayload = {
                        from: this.username, // Current user's username
                        to: this.contact,                  // Recipient's username
                        message: this.message.message,               // Message text
                    };
                    this.message = {}
                    // Sending the message using the Vuex action
                    socket.sendChatMessage(messagePayload);
                })
                .catch(err => alert(err.response.status))
    }
    }
}
</script>

<style>
    .user-message {
        text-align: right;
    }
</style>