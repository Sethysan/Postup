<template>
  <div>
  <div v-if="replies.length < 1">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
  <div class="replies-list">
    <reply v-for="reply in replies" :key="reply.id" :reply="reply" :indent="100" :forumId="forumId"></reply>
  </div>
</div>
</template>

<script>
import Reply from './Reply.vue';

export default {
    components: {Reply},
    props: ['replies', 'forumId']
}
</script>

<style>
.replies-list {
  background-image: url("/images/patina.jpg");
}
</style>