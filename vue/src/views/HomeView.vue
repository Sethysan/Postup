<template>
  <div class="home">
    <button ><router-link v-bind:to="{ name: 'admin' }">Admin</router-link></button>
    <h1 class="title">Todays Top 10 Popular Posts</h1>
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
        console.log('Fetched posts:', res.data);
        this.posts = res.data;
        ForumService.getActiveForums()
          .then(res => {
            console.log('Fetched forums:', res.data);
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

<style>
.title {
  text-align: center;
  margin-top: 20px;
}

</style>
