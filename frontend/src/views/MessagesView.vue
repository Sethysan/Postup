<template>
  <div class="messages">
    <button class="btn-message" @click="toggleUserList">
      {{ buttonMessage }}
    </button>
    <div v-show="!visible" class="messages-container">
      <h1 id="messages">Messages</h1>
      <contact-list :contacts="contacts"></contact-list>
    </div>
  </div>
  <UserList :users="users" v-show="visible">
    <template #default="{ user }">
      <button class="btn-message" @click="startMessage(user)">Message</button>
    </template>
  </UserList>
</template>

<script>
import ContactList from "../components/ContactList.vue"
import UserList from '@/components/UserList.vue';
import MessageService from '@/services/MessageService';

export default {
  components: { UserList, ContactList },
  data() {
    return {
      users: [],
      contacts: [],
      visible: false,
      user: ""
    };
  },
  computed: {
    buttonMessage() {
      return this.visible ? "Back To Messages" : "Send New Message";
    }
  },
  created() {
    MessageService.getContacts()
      .then(res => this.contacts = res.data)
      .catch(err => alert(err.response.status))
    MessageService.getUsers()
      .then((response) => {
        this.users = response.data.sort((a, b) =>
          a.username.localeCompare(b.username)
        );
      })
      .catch((error) => {
        this.$store.commit(
          'SET_NOTIFICATION',
          'Error fetching users: ' + error.response.data.message
        );
      });
  },
  methods: {
    toggleUserList() {
      if (!this.visible) {
        this.visible = true; // Show the user list
      } else {
        this.visible = false; // Hide the user list
      }
    },
    startMessage(user) {
      this.$router.push(`/messages/${user.id}`);
    }
  }
};
</script>

<style>
.btn-message {
  align-items: center;
  background-color: grey;
  color: black;
  font-weight: bold;
  border-color: black;
  /* Remove default border */
  border-radius: 5px;
  /* Rounded corners */
  padding: 2px 8px;
  /* Padding for size */
  font-size: 14px;
  /* Font size */
  cursor: pointer;
  /* Pointer cursor on hover */
  transition: background-color 0.3s;
  /* Smooth transition */

}

.messages {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}
</style>