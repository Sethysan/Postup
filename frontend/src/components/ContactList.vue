<template>
  <div v-for="contact in contacts" :key="contact.id" class="message-list">
    <router-link :to="{ name: 'direct-messages', params: { user: contact.id } }" class="user-message-link">
      <h4>{{ contact.username }}</h4>
      <p class="unread" v-if="contact.unread > 0">
        {{ contact.unread }} Unread Messages
      </p>
      <p class="unread-sent" v-if="contact.unreadSent > 0">
        {{ contact.unreadSent }} Unread Sent Messages
      </p>
      <p class="timestamp" v-if="contact.latest">Latest Message Sent {{ contact.latest }}</p>
    </router-link>
  </div>
</template>

<script>
export default {
  props: ['contacts']
}
</script>

<style scoped>
.message-list {
  margin-bottom: 10px;
}

.unread-user-message {
  background-color: rgba(255, 165, 0, 0.2);
  /* Light orange for emphasis */
  border: 1px solid orange;
}

.unread-others-message {
  background-color: rgba(0, 128, 255, 0.2);
  /* Light blue for emphasis */
  border: 1px solid blue;
}

.user-message {
  text-align: right;
}

.others-message {
  text-align: left;
}

.user-message-link {
  text-decoration: none;
  color: #888;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  position: relative;
}

.username {
  font-size: 1rem;
  /* Adjust font size if needed */
  margin: 0;
}

.username:hover {
  transform: scale(1.1);
}

.timestamp {
  display: none;
  /* Hide timestamp by default */
  font-size: 0.8rem;
  color: var(--blue);
  /* Secondary color for timestamp */
  position: absolute;
  left: 0;
  /* Adjust position if needed */
  bottom: 60%;
  /* Adjust position if needed */
  margin-bottom: 5px;
  /* Space between username and timestamp */
}

/* Show timestamp when hovering over the username */
.user-message-link:hover .timestamp {
  display: block;
}
</style>