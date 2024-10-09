<template>
  <div v-if="replies.length < 1">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
  <div class="replies-list">
    <reply v-for="reply in replies" :key="reply.id" :reply="reply" :indent="0"></reply>
  </div>
</template>

<script>
import Reply from './Reply.vue';

export default {
    components: {Reply},
    props: ['replies']
}
</script>

<style>

</style>