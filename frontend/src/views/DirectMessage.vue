<template>
  <div class="back-button" @click="goBack">
      <svg fill="currentColor" height="16" icon-name="back-outline" viewBox="0 0 20 20" width="16"
           xmlns="http://www.w3.org/2000/svg">
        <path
            d="M19 9.375H2.51l7.932-7.933-.884-.884-9 9a.625.625 0 0 0 0 .884l9 9 .884-.884-7.933-7.933H19v-1.25Z">
        </path>
      </svg>
    </div>
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
    width: 80%;
    display: flex;
    flex-direction: column;
    max-height: 80vh;
    overflow-y: auto;
}
</style>