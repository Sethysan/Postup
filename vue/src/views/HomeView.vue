<template>
  <div class="home">
    <h1>Home</h1>
    <div class="forum search">
      <label for="forum-search">Search for Forums by Title or Description: </label>
      <input type="search" id="forum-search" v-model="searchTerm" placeholder="Enter search term..." />
      <button class="search-button" @click="searchForums">Search</button>
      <button class="clear-button" v-if="searchDisplayed" @click="clearSearch">Clear Search</button>
    </div>

    <!-- Display Search Results -->
    <div v-if="searchDisplayed && forums.length > 0" class="forum-results">
      <h2>Search Results</h2>
      <ul>
        <li v-for="forum in forums" :key="forum.forumId">{{ forum.topic }} - {{ forum.description }}</li>
      </ul>
    </div>
    <div v-else-if="searchDisplayed && searchTerm.length > 0" class="no-results">
      <p>No forums found.</p>
    </div>
    <div v-if="!searchDisplayed">
      <h1>Todays Top 10 Popular Posts</h1>
      <post-list :posts="posts"></post-list>
      <h1>Top 5 Most Active Forums</h1>
    </div>
  </div>
</template>

<script>
import PostList from '../components/PostList.vue';
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';

export default {
  components: { PostList },
  data() {
    return {
      posts: [],
      forums: [],
      searchTerm: '',
      searchDisplayed: false

    };
  },
  created() {
    PostService.getPopularPosts()
      .then(res => {
        this.posts = res.data;
      })
      .catch(error => {
        console.error("Error fetching popular posts:", error);
      });
  },
  methods: {
    searchForums() {
      // If no search term entered, return early
      if (this.searchTerm.trim() === '') {
        this.forums = [];
        this.searchDisplayed = false;
        return;
      }

      ForumService.searchForumsByTopicAndDescription(this.searchTerm)
        .then(res => {
          this.forums = res.data;
          this.searchDisplayed = true;
        })
        .catch(error => {
          console.error("Error searching forums:", error);
          this.forums = [];
          this.searchDisplayed = true;
        });
    },
    clearSearch() {
      this.searchTerm = '';
      this.forums = [];
      this.searchDisplayed = false;
    }
  }
};


</script>

<style>


</style>
