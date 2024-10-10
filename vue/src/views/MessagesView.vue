<template>
    <h1>Messages</h1>
  <contact-list :contacts="contacts"></contact-list>
  <button class="btn-message" :onclick="onVisible" v-if="!visible">Start New Message</button>
  <select v-if="visible" v-model="user">
    <option v-for="user in users" :key="user.id" :value="user">{{ user.username }}</option>
  </select>
  <button class="btn-message" :onclick="goToMessages" v-if="visible">Start Message With {{ filter }}</button>
</template>

<script>
import ContactList from "../components/ContactList.vue"
import service from '../services/MessageService'
import ModeratorService from "../services/ModeratorService";

export default {
  components: { ContactList },
  data(){
    return {
        contacts: [],
        visible: false,
        users: [],
        user: ""
    }
  },
  created(){
    service.getContacts()
        .then(res => this.contacts = res.data)
        .catch(err => alert(err.response.staus))
  },
  methods: {
    onVisible(){
      ModeratorService.getAllUsers()
        .then(res => {
          this.contacts = res.data;
          this.visible = true;
        })
    },
    goToMessages(){
      this.$router.push(`/messages/${this.user.id}`)
    }
  }
}
</script>

<style>
.btn-message {
  background-color: grey;
  color: white;
  border-color: black; /* Remove default border */
  border-radius: 5px; /* Rounded corners */
  padding: 2px 8px; /* Padding for size */
  font-size: 14px; /* Font size */
  cursor: pointer; /* Pointer cursor on hover */
  transition: background-color 0.3s; /* Smooth transition */
}
</style>