<template>
    <h1>Messages</h1>
  <contact-list :contacts="contacts"></contact-list>
</template>

<script>
import ContactList from "../components/ContactList.vue"
import service from '../services/MessageService'

export default {
  components: { ContactList },
  data(){
    return {
        contacts: []
    }
  },
  created(){
    service.getContacts()
        .then(res => this.contacts = res.data)
        .catch(err => alert(err.response.staus))
  }
}
</script>

<style>

</style>