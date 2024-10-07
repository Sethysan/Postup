<template>
<div class="message-container">
  <messages :messages="messages" :contact="this.$route.params.user"></messages>
</div>
</template>

<script>
import Messages from '../components/Messages.vue'
import service from '../services/MessageService'

export default {
  components: { Messages },
  data(){
    return {
        messages: []
    }
  },
  created(){
    service.getMessages(this.$route.params.user)
        .then(res => this.messages = res.data)
  }
}
</script>

<style>
.message-container {
    margin: 0 auto;
    width: 75%;
}
</style>