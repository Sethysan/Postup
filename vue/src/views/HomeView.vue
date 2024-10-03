<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
      <h1>Todays Top 10 Popular Posts</h1>
      <post-list :posts="posts"></post-list>
      <h1>Top 5 Most Active Forums</h1>
      <div v-for="forum in forums" :key="forum.id">
        <forum-snippet :forum="forum"></forum-snippet>
      </div>
    
  </div>
</template>

<script>
import PostList from '../components/PostList.vue';
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';
import ForumSnippet from '../components/ForumSnippet.vue';

export default {
  components: { PostList, ForumSnippet },
  data() {
    return {
      posts: [],
      forums: []
    };
  },
  created() {
    PostService.getPopularPosts()
      .then(res => {
        this.posts = res.data;
        ForumService.getActiveForums()
          .then(res => {
            this.forums = res.data;
          })
      })
      .catch(error => {
        console.error("Error fetching popular posts:", error);
      });
      this.user = this.$store.getters.username;
  }
};


</script>

<style></style>
