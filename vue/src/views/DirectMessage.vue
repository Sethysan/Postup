<template>
  <div v-if="messages.length < 1 && isloading">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
   <div v-if="error">
      <p>Oops, it looks like the chat couldn't load</p>
    </div>
<div class="message-container" v-if="!error">
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
        messages: [],
        error: false,
        isloading: true
    }
  },
  created(){
    service.getMessages(this.$route.params.user)
        .then(res => this.messages = res.data)
        .catch(err => {
          this.error = true
          this.isloading = false
        })
  },
}
</script>

<style>
.message-container {
    margin: 0 auto;
    width: 75%;
}
</style>