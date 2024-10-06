<template>
  <div class="posts">
    <div>
      <select name="filter" v-model="filter">
        <option value="" v-if="!filter">Sort</option>
        <option value="recent">Most Recent</option>
        <option value="popularity">Most Popular</option>
      </select>
      <button class="sort" :onclick="filterPosts">Sort</button>
    </div>
    <div v-if="posts.length === 0">
      <p>Wow, such empty</p>
      <img src="http://localhost:9000/images/Cheems.jpg" alt="placeholder" class="place-holder" />
    </div>
    <div v-for="post in posts" :key="post.id">
      <header>
        <img src="" alt="user-logo" />
        <router-link :to="{ name: 'post', params: { post: post.id } }">
          <h1>{{ post.title }}</h1>
        </router-link>
        <p class="inline-time">{{ post.creator_username }}</p>
        <p class="inline-time">{{ getTimeElapsed(post.timeOfCreation) }}</p>
        <p> {{ post.creator_username }} </p>
      </header>
      <section>
        <img v-if="post.image" :src="post.image" />
        <p> {{ post.description }} </p>
      </section>
      <button v-if="post.creator_username === user" class="btn btn-delete deletePost"
        @click="deletePost(post.id)">Delete</button>
    </div>
  </div>
</template>
<script>
import Post from '../components/Post.vue';
import PostService from '../services/PostService';
import RepliesService from '../services/RepliesService';
import dayjs from 'dayjs';
export default {
  props: ['posts'],
  components: { Post },
  data() {
    return {
      filter: "",
      user: this.$store.getters.username
    }
  },
  methods: {
    filterPosts() {
      if (this.filter == 'recent') {
        this.filterPostsByRecent();
      }
      else if (this.filter == 'popularity') {
        this.filterByPopularity();
      }
    },
    filterPostsByRecent() {
      this.posts = [...this.posts].sort((a, b) => b.id - a.id);
    },
    filterByPopularity() {
      this.posts = [...this.posts].sort((a, b) => (b.upvotes - b.downvotes) - (a.upvotes - a.downvotes));
    },
    deletePost(postId) {
      if (
        confirm("Are you sure you want to delete this post? This action cannot be undone.")
      ) {
        PostService.deletePost(postId)
          .then(response => {
            this.$store.commit('SET_NOTIFICATION', `Post ${postId} was deleted.`);
            this.$router.push({ name: 'forums' });
          })
          .catch(error => {
            if (error.response) {
              if (error.response.status === 404) {
                this.$store.commit('SET_NOTIFICATION',
                  "Error: Post " + postId + " was not found. This post may have been deleted or you have entered an invalid post ID.");
                this.$router.push({ name: 'forum' });
              } else {
                this.$store.commit('SET_NOTIFICATION',
                  "Error getting post " + postId + ". Response received was '" + error.response.statusText + "'.");
              }
            } else if (error.request) {
              this.$store.commit('SET_NOTIFICATION', "Error getting post. Server could not be reached.");
            } else {
              this.$store.commit('SET_NOTIFICATION', "Error getting post. Request could not be created.");
            }
          });
      }
    },
    getTimeElapsed(timeOfCreation) {
      // dayjs converts time into a readable format and calculates the elapsed time
      return dayjs(timeOfCreation).fromNow();
    }
  }
};
</script>
<style>
.place-holder {
  max-width: 15%;
}

.sort {
  margin-bottom: 15px;
  margin-right: 10px;
}
</style>