<template>
  <div class="posts">
    <div v-if="forumId">
      <select name="filter" v-model="filter">
        <option value="" v-if="!filter">Sort</option>
        <option value="recent">Most Recent</option>
        <option value="popularity">Most Popular</option>
      </select>
      <button class="sort" :onclick="filterPosts">Sort</button>
    </div>
    <div v-if="posts.length === 0">
      <p>Wow, such empty</p>
      <img src="http://localhost:9000/images/pets/moon-moon.jpg" alt="placeholder" class="place-holder" />
    </div>
    <div v-for="post in posts" :key="post.id">
      <header>
        <img src="" alt="user-logo" />
        <h1>{{ post.title }}</h1>
        <p> {{ post.creator_username }} </p>
      </header>
      <section>
        <img v-if="post.image" :src="post.image" />
        <p> {{ post.description }} </p>
      </section>
    </div>
  </div>
</template>

<script>
import Post from '../components/Post.vue';
import PostService from '../services/PostService';
import RepliesService from '../services/RepliesService';

export default {
  props: ['posts'],
  components: { Post },
  data() {
    return {
      filter: "",
    }
  },
  methods: {
    filterPosts() {
      if(this.filter == 'recent'){
        this.filterPostsByRecent();
      }
      else if(this.filter == 'popularity'){
        this.filterByPopularity();
      }
    },
    filterPostsByRecent() {
      this.posts = [...this.posts].sort((a, b) => b.id - a.id);
    },
    filterByPopularity() {
      this.posts = [...this.posts].sort((a, b) => (b.upvotes - b.downvotes) - (a.upvotes - a.downvotes))
    }
  }
};
</script>

<style>
.place-holder {
  max-width: 15%;
}
.sort{
  margin-bottom: 15px;
  margin-right: 10px;
}
</style>