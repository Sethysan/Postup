<template>
  <div class="posts">
    <div v-if="forumId">
      <select name="filter" v-model="filter">
        <option value="" v-if="!filter">Sort</option>
        <option value="recent">Most Recent</option>
        <option value="popularity">Most Popular</option>
      </select>
      <button :onclick="filterPosts">Sort</button>
    </div>
    <p v-if="error">Failed To Load Posts</p>
    <p v-if="loading">Loading</p>
    <div v-if="!loading && posts.length === 0">
      <p>Wow, such empty</p>
      <img src="http://localhost:9000/images/pets/moon-moon.jpg" alt="placeholder" class="place-holder"/>
      {{ this.id }}
    </div>
    <div v-for="post in posts" :key="post.id">
      <header>
        <img src="" alt="user-logo" />
        <h1>{{ post.description }}</h1>
        <p> {{ post.creator_username }} </p>
      </header>
      <section>
        <img v-if="post.image" :src="post.image" />
        <p> place holder for a post body </p>
      </section>
    </div>
  </div>
</template>

<script>
import Post from '../components/Post.vue'
import PostService from '../services/PostService'
import RepliesService from '../services/RepliesService'

export default {
  props: ['forumId'],
  // components: { Post },
  data() {
    return {
      post: {},
      replies: [],
      posts: [],
      forumId: 0,
      filter: "",
      loading: true,
      error: ""

    }
  },
  created() {
    // Fetch posts for the forum when the component is created
    this.fetchPosts();
  },
  methods: {
    fetchPosts() {
      // Check if forumId exists
      if (this.forumId) {
        // Fetch posts for the specific forum using forumId
        PostService.getForumPosts(this.forumId, this.filter ||'recent')
          .then(res => {
            this.posts = res.data;
            this.loading = false;
          })
          .catch(err => {
            this.error = "Failed to load posts for this forum.";
            this.loading = false;
            console.error("Error loading posts:", err);
          });
      } else {
        this.loading = false;
        this.error = "No forum ID provided.";
      }
    },
    filterPosts() {
      this.fetchPosts();
    },
    filterPostsByRecent() {
      this.posts = [...this.posts].sort((a, b) => b.id - a.id);
    },
    filterByPopularity() {
      this.posts = [...this.posts].sort((a, b) => (b.upvotes - b.downvotes) - (a.upvotes - a.downvotes));
    }
  }
}
</script>

<style>
.place-holder{
  max-width: 15%;
}
</style>