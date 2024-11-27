<template>
  <div class="user-message-link" v-for="message in localMessages" :key="message.id" :class="{
    'user-message': message.sender.username === username,
    'others-message': message.sender.username !== username,
    'unread-user-message': message.sender.username === username && !message.has_read,
    'unread-others-message': message.sender.username !== username && !message.has_read
  }">
    <Message :message="message" @messageDeleted="removeMessage"></Message>
  </div>
  <div class="send-message">
    <textarea v-model="message.message" placeholder="Enter your message"></textarea>
    <button @click="sendMessage" class="reply-option-btn">Send</button>
  </div>
</template>

<script>
import service from '../services/MessageService';
import Message from './Message.vue';

export default {
  components: { Message },
  props: ['messages', 'contact'],
  data() {
    return {
      localMessages: [...this.messages], // Create a local copy of the messages array
      id: this.$store.getters.userId,
      username: this.$store.getters.username,
      message: {
        sender: { username: this.$store.getters.username },
        message: '' // Ensure this is initialized
      },
    };
  },
  watch: {
    messages: {
      immediate: true,
      handler(newMessages) {
        this.localMessages = [...newMessages];
      }
    }
  },
  methods: {
    sendMessage() {
      service.createMessage(this.contact, this.message)
        .then(res => {
          this.localMessages.unshift(res.data); // Modify the local copy
          this.message.message = ''; // Clear the input after sending
        })
        .catch(err => alert(err.response.status));
    },
    goBack() {
      this.$router.go(-1); // Go back to the previous page
    },
    removeMessage(id) {
      // Remove the message with the given id from the localMessages array
      this.localMessages = this.localMessages.filter(item => item.id !== id);
    }
  }
};
</script>

<style>
.user-message {
  text-align: right;
}

.user-message-link {
  text-decoration: none;
}

.send-message {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-top: 1rem;
  width: 90%;
}
</style>