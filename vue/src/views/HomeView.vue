<template>
  <div class="home">
    <router-link v-bind:to="{ name: 'admin' }">Admin</router-link> 
    <h1 class="title">Todays Top 10 Popular Posts</h1>
    <div v-if="posts.length < 1 && !isloadingPost">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
    <div v-if="isloadingPost">
      <p>loading . . .</p>
    </div>
    <div v-if="postLoadingError">
      <p>Oops, it looks like popular posts couldn't load</p>
    </div>
    <post-list :posts="posts"></post-list>
    <h1>Top 5 Most Active Forums</h1>
    <div v-if="forums.length < 1 && !isloadingForum">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
    <div v-if="isloadingForum">
      <p>loading . . .</p>
    </div>
    <div v-if="postLoadingError">
      <p>Oops, it looks like actie forums couldn't load</p>
    </div>
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
      forums: [],
      isloadingPost: true,
      isloadingForum: true,
      postLoadingError:false,
      forumLoadingError: false
    };
  },
  created() {
    PostService.getPopularPosts()
      .then(res => {
        console.log('Fetched posts:', res.data);
        this.posts = res.data;
        this.isloadingPost = false
      })
      .catch(error => {
        this.postLoadingError = true;
      });
    this.user = this.$store.getters.username;
    ForumService.getActiveForums()
          .then(res => {
            console.log('Fetched forums:', res.data);
            this.forums = res.data;
            this.isloadingForum = false
          })
          .catch(err => this.forumLoadingError = true)
  }
};


</script>

<style>
.title {
  text-align: center;
  margin-top: 20px;
}

</style>
