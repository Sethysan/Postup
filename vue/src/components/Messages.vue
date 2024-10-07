<template>
  <div v-for="message in messages" :key="message.id" :class="message.sender.username === username ? 'user-message' : 'others-message'">
    <p>{{ message.message }}</p>
    <p>{{ message.sender.username }}</p>
    <p>sent {{ message.receivedOn }}</p>
    <p v-if="message.read">read A CHECK HERE</p>
  </div>
  <textarea v-model="message.message" placeholder="Enter your message"></textarea>
  <button :onclick="sendMessage">Send</button>
</template>

<script>
import service from '../services/MessageService';

export default {
    props: ['messages', 'contact'],
    data(){
        return {
            username: this.$store.getters.username,
            message: {
                sender: {username: this.$store.getters.username}
            }
        }
    },
    methods: {
        sendMessage(){
            service.createMessage(this.contact, this.message)
                .then(res => {
                    this.messages.unshift(res.data);
                    this.message = {}
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