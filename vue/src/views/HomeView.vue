<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <router-link v-bind:to="{ name: 'forums' }">Forums</router-link>
    <h1>todays top 10 popular posts</h1>
    <post-list></post-list>
  </div>
</template>

<script>
import PostList from '../components/PostList.vue';
export default {
  components: { PostList },
};
</script>
