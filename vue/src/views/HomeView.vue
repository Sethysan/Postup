<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <h1>Todays Top 10 Popular Posts</h1>
    <post-list :posts="posts"></post-list>
    <h1>Top 5 Most Active Forums</h1>
  </div>
</template>

<script>
import PostList from '../components/PostList.vue';
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';

export default {
  components: { PostList },
  data () {
    return {
      posts: [],
      forums: []
    }
  },
  created () {
    PostService.getPopularPosts()
      .then(res => {
        this.posts = res.data;
      })
  }
};
</script>
