<template>
  <div v-for="contact in contacts" :key="contact.id" class="message-list">
    <router-link :to="{ name: 'direct-messages', params: { user: contact.id } }" class="user-message-link">
      <h4 class="username">{{ contact.username }}</h4>
      <p class="unread" v-if="contact.unread > 0">
        {{ contact.unread }} {{ contact.unread === 1 ? ' New Message' : 'New Messages' }}
      </p>
      <div class="message-count">
        <button aria-label="View messages. {{ contacts.totalMessages }} replies available" class="comment-button">
          <svg aria-hidden="true" class="icon-comment" height="20" viewBox="0 0 20 20" width="20"
            xmlns="http://www.w3.org/2000/svg">
            <path
              d="M7.725 19.872a.718.718 0 0 1-.607-.328.725.725 0 0 1-.118-.397V16H3.625A2.63 2.63 0 0 1 1 13.375v-9.75A2.629 2.629 0 0 1 3.625 1h12.75A2.63 2.63 0 0 1 19 3.625v9.75A2.63 2.63 0 0 1 16.375 16h-4.161l-4 3.681a.725.725 0 0 1-.489.191ZM3.625 2.25A1.377 1.377 0 0 0 2.25 3.625v9.75a1.377 1.377 0 0 0 1.375 1.375h4a.625.625 0 0 1 .625.625v2.575l3.3-3.035a.628.628 0 0 1 .424-.165h4.4a1.377 1.377 0 0 0 1.375-1.375v-9.75a1.377 1.377 0 0 0-1.374-1.375H3.625Z"
              fill="#272525">
            </path>
          </svg>
          <span>{{ contact.totalMessages }} {{ contact.totalMessages === 1 ? 'Message' : 'Messages' }}</span>
        </button>
      </div>
      <p class="last-message" v-if="contact.latest">Last Message Sent {{ formattedTimestamp(contact) }}</p>
    </router-link>
  </div>
</template>

<script>

import dayjs from 'dayjs';

export default {
  props: ['contacts'],
  computed: {
    formattedTimestamp() {
      return contact => dayjs(contact.latest).format('MMM D, YYYY h:mm A');
    }
  }
}


</script>

<style scoped>
.message-list {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding: 1rem;
  border-bottom: 1px solid #ccc;
}

.unread {
  color: var(--red);
}

.user-message-link {
  text-decoration: none;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
  position: relative;
  gap: 3rem;
  transition: all .1s ease-in;
}

.username {
  font-size: 1rem;
  margin: 0;
  margin-right: 2rem;
  color: var(--blue);
}

.message-count {
  display: flex;
  align-items: flex-end;
}

.last-message {
  font-size: 0.6rem;
  color: var(--orange);
}
</style>