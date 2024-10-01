<template>
  <div class="replies-list">
    <reply v-for="reply in replies" :key="reply.id" :reply="reply"></reply>
  </div>
</template>

<script>
export default {
    props: ['replies']
}
</script>

<style>

</style>