<template>
  <div class="home">
    <button v-if="role === 'ROLE_ADMIN'" class="admin-button"><router-link v-bind:to="{ name: 'admin' }">Admin
        Access</router-link></button>

    <h1 class="title">Todays Top 10 Popular Posts</h1>
    <div v-if="posts.length < 1 && !isloadingPost">
      <p>Wow, such empty!</p>
      <img src="" />
    </div>

    <div v-if="isloadingPost">
      <p>loading . . .</p>
    </div>

    <div v-if="postLoadingError">
      <p>Oops, it looks like popular posts couldn't load</p>
    </div>

    <div>
      <select name="filter" v-model="selectedFilter" @change="applyFilter">
        <option value="">Sort</option>
        <option value="recent">Most Recent</option>
        <option value="popularity">Most Popular</option>
      </select>
    </div>

    <!-- Posts Grid -->
    <div class="posts-grid">
      <post-list v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id" :post="post" />
    </div>

    <h1>Top 5 Most Active Forums</h1>

    <div v-if="forums.length < 1 && !isloadingForum">
      <p>Wow, such empty!</p>
      <img src="" />
    </div>

    <div v-if="isloadingForum">
      <p>loading . . .</p>
    </div>

    <div v-if="postLoadingError">
      <p>Oops, it looks like actie forums couldn't load</p>
    </div>

    <!-- Forums grid -->
    <div class="forums-grid">
      <forum-snippet v-for="(forum) in forums.slice(0, 5)" :key="forum.id" :forum="forum" />
    </div>


  </div>
</template>

<script>
import PostList from '../components/PostList.vue';
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';
import ForumSnippet from '../components/ForumSnippet.vue';
import dayjs from 'dayjs';

export default {
  components: { PostList, ForumSnippet },
  data() {
    return {
      posts: [],
      forums: [],
      role: this.$store.getters.role,
      isloadingPost: true,
      isloadingForum: true,
      postLoadingError: false,
      forumLoadingError: false,
      selectedFilter: "",
    };
  },
  computed: {
    filteredPosts() {
      let sortedPosts = [...this.posts];
      if (this.selectedFilter === 'recent') {
        // sortedPosts.sort((a, b) => b.id - a.id);
        sortedPosts.sort((a, b) => new Date(b.timeOfCreation) - new Date(a.timeOfCreation));
      } else if (this.selectedFilter === 'popularity') {
        sortedPosts.sort((a, b) => (b.upvotes - b.downvotes) - (a.upvotes - a.downvotes));
      }
      return sortedPosts;
    },
  },
  created() {
    PostService.getPopularPosts()
      .then((res) => {
        console.log('Fetched posts:', res.data);
        this.posts = res.data;
        this.isloadingPost = false;
      })
      .catch(error => {
        this.postLoadingError = true;
      });

    this.user = this.$store.getters.username;

    ForumService.getActiveForums()
      .then((res) => {
        console.log('Fetched forums:', res.data);
        this.forums = res.data;
        this.isloadingForum = false
      })
      .catch(err => this.forumLoadingError = true)
  },
  methods: {
    getTimeElapsed(timeOfCreation) {
      return dayjs(timeOfCreation).fromNow();
    },
    applyFilter() {
      // This triggers the computed property `filteredPosts` to re-evaluate
      console.log(`Applying filter: ${this.selectedFilter}`);
    },
  },
};


</script>

<style>
.title {
  text-align: center;
  margin-top: 20px;
}

.home {
  padding: 20px;
}

/* Grid for Posts (2 rows, 5 columns) */
.posts-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  grid-gap: 30px;
  margin: 20px 0;
}

.post-list {
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  background-color: #f9f9f9;
}

/* Grid for Forums (1 row, 5 columns) */
.forums-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  grid-gap: 40px;
  margin: 20px 0;
}

.forum-snippet {
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  background-color: #f1f1f1;
}

.admin-button {
  background-color: grey;
  /* Primary color */
  color: white;
  /* Text color */
  border-color: black;
  /* Remove default border */
  border-radius: 5px;
  /* Rounded corners */
  padding: 2px 8px;
  /* Padding for size */
  font-size: 16px;
  /* Font size */
  cursor: pointer;
  /* Pointer cursor on hover */
  transition: background-color 0.3s;
  /* Smooth transition */
}

.admin-button:hover {
  background-color: rgb(240, 107, 19);
  /* Darker shade on hover */
}

.admin-button a {
  text-decoration: none;
  /* Remove underline from link */
  color: white;
  /* Link text color */
}
</style>
