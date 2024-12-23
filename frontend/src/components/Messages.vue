<template>
  <div class="user-message-link" v-for="message in localMessages" :key="message.id">
    <Message :message="message" @messageDeleted="removeMessage"></Message>
  </div>
  <div class="send-message">
    <textarea v-model="message.message" placeholder="Enter your message" @focus="expandTextarea"
      :class="{ expanded: formVisibility }" @blur="!formVisibility || cancelReply()"></textarea>
    <!-- Buttons appear only when the textarea is expanded -->
    <div v-if="formVisibility" class="comment-buttons">
      <button @click="sendMessage" class="submit-button">Submit</button>
      <button @click="cancelReply" class="cancel-button">Cancel</button>
    </div>
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
      localMessages: [...this.messages], 
      id: this.$store.getters.userId,
      username: this.$store.getters.username,
      formVisibility: false,
      message: {
        sender: { username: this.$store.getters.username },
        message: '' 
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
    expandTextarea() {
      this.formVisibility = true;  
    },
    sendMessage() {
      service.createMessage(this.contact, this.message)
        .then(res => {
          this.localMessages.push(res.data); // Modify the local copy
          this.message.message = ''; // Clear the input after sending
          this.formVisibility = false; // Hide the textarea
        })
        .catch(err => alert(err.response.status));
    },
    cancelReply() {
      this.message.message = '';  // Clear the message
      this.formVisibility = false;
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
  position: relative;
  margin-top: 20px;
  width: 90%;
}
</style>