<template>
  <div v-for="contact in contacts" :key=" contact.id">
    <router-link :to="{name: 'direct-messages', params: {user: contact.id}}" class="user-message-link">
        <h2>{{ contact.username }}</h2>
        <p class="unread" v-if="contact.unread > 0">{{ contact.unread }} Unread Messages</p>
        <p>Latest Message Sent {{ contact.latest }}</p>
    </router-link>
  </div>
</template>

<script>
export default {
    props: ['contacts']
}
</script>

<style>
    .unread {
        color: red;
    }
    .user-message-link{
      text-decoration: none;
      color: rgb(21, 20, 20);
    }
</style>