<template>
  <div class="replies-list">
    {{ replies }}
    <reply v-for="reply in replies" :key="reply.id" :reply="reply"></reply>
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