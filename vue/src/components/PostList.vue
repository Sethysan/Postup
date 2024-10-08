<template>
  <div class="posts">
    <div>
      <select name="filter" v-model="filter">
        <option value="" v-if="!filter">Sort</option>
        <option value="recent">Most Recent</option>
        <option value="popularity">Most Popular</option>
      </select>
    </div>
    <div v-if="posts.length === 0">
      <p>Wow, such empty</p>
      <img src="http://localhost:9000/images/Cheems.jpg" alt="placeholder" class="place-holder" />
    </div>
    <div v-for="post in filteredPosts" :key="post.id">
      <header>
        <img src="" alt="user-logo" />
        <router-link :to="{ name: 'post', params: { post: post.id } }">
          <h1>{{ post.title }}</h1>
        </router-link>
        <div class="post-meta">
          <p class="post-author">{{ post.creator_username }}</p>
          <p class="post-time">{{ getTimeElapsed(post.timeOfCreation) }}</p>
        </div>
      </header>
      <section>
        <img v-if="post.image" :src="post.image" class="post-image" />
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
import dayjs from 'dayjs';

export default {
  props: ['posts'],
  components: { Post },
  data() {
    return {
      filter: "",
      user: this.$store.getters.username,
    }
  },
  computed: {
    filteredPosts() {
      let sortedPosts = [...this.posts];
      if (this.filter === 'recent') {
        sortedPosts.sort((a, b) => b.id - a.id);
      } else if (this.filter === 'popularity') {
        sortedPosts.sort((a, b) => (b.upvotes - b.downvotes) - (a.upvotes - a.downvotes));
      }
      return sortedPosts;
    },
  },
  methods: {
    // filterPosts() {
    //   // This triggers a re-evaluation of `filteredPosts` computed property.
    //   console.log(this.filteredPosts);
    // },
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

.post-image {
  width: 100%;
  background-color: black;
  max-height: 400px;
  object-fit: contain;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s ease;
}
.post-meta {
    display: flex;
    align-items: center;
    gap: 5px;
    /* Adjust spacing between author and time */
    font-size: 0.875rem;
}
.post-author{
    font-size: 1.25rem;
}
.post-time{
    color:rgb(107, 105, 105) ;
}
</style>