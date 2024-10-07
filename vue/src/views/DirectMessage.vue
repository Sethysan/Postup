<template>
  <messages :messages="messages" :contact="this.$route.params.user"></messages>
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

</style>