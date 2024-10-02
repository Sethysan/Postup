<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <h1>Todays Top 10 Popular Posts</h1>
    <post-list></post-list>
  </div>
</template>

<script>
import PostList from '../components/PostList.vue';
export default {
  components: { PostList },
};
</script>
