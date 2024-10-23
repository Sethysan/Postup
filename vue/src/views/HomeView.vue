<template>
  <div class="home">
    <div class="drop-downs">

      <button v-if="role === 'ROLE_ADMIN'" class="admin-button">
        <router-link v-bind:to="{ name: 'admin' }">Admin Access</router-link>
      </button>

      <div v-if="posts.length < 1 && !isloadingPost" class="status">
        <div>
          <p>Wow, such empty!</p>
        </div>

        <div v-if="isloadingPost">
          <p>loading . . .</p>
        </div>

        <div v-if="postLoadingError">
          <p>Oops, it looks like popular posts couldn't load</p>
        </div>
      </div>

      <div v-if="posts.length > 1 && !isloadingPost && !postLoadingError" class="filter-bar">
        <select name="filter" v-model="selectedFilter" @change="applyFilter" class="filter">
          <option value="">Sort</option>
          <option value="recent">Most Recent</option>
          <option value="popularity">Most Popular</option>
        </select>
      </div>

    </div>
    <!-- trending section -->
    <div class="trending-posts">
      <trending :filteredPosts="filteredPosts" />
    </div>

    <div class="active-forums">
      <h1>Top 5 Most Active Forums</h1>

      <div v-if="forums.length < 1 && !isloadingForum">
        <p>Wow, such empty!</p>
      </div>

      <div v-if="isloadingForum">
        <p>loading . . .</p>
      </div>

      <div v-if="forumLoadingError">
        <p>Oops, it looks like active forums couldn't load</p>
      </div>

      <!-- Forums grid -->
      <div class="forums-grid">
        <forum-snippet v-for="(forum) in forums.slice(0, 5)" :key="forum.id" :forum="forum" />
      </div>
    </div>

  </div>
</template>

<script>
import Trending from './Trending.vue';
import ForumSnippet from '../components/ForumSnippet.vue';
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';

export default {
  components: { ForumSnippet, Trending },
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
        this.posts = res.data;
        this.isloadingPost = false;

        this.$nextTick(() => {
          if (this.$refs.swiper) {
            this.$refs.swiper.swiper.update();
          }
        });
      })
      .catch(error => {
        this.postLoadingError = true;
      });

    this.user = this.$store.getters.username;

    ForumService.getActiveForums()
      .then((res) => {
        this.forums = res.data;
        this.isloadingForum = false
      })
      .catch(err => {
        this.forumLoadingError = true;
      });
  },
  methods: {

    applyFilter() {
      // This triggers the computed property `filteredPosts` to re-evaluate
      console.log(`Applying filter: ${this.selectedFilter}`);
    },
  },
};


</script>

<style>
.home {
  display: flex;
  flex-direction: column;
}

.title {
  text-align: center;
}

.filter-bar {
  display: flex;
  height: 5vh;
  min-width: auto;
  flex-wrap: wrap;
  align-content: end;

}

.filter {
  border-radius: 8px;
  color: var(--primary);
  border-color: black;
  background-color: var(--nero);
  display: flex;
  flex-wrap: wrap;
  align-content: center;
  max-height: 2.5vh;
  font-weight: 800;
  cursor: pointer;
  transition: .3s ease-in;
  padding: 5px;
}

select:hover,
select:focus {
  background-color: #000000;
  outline: none;
}

option {
  outline: none;
}

.drop-downs {
  display: flex;
  justify-content: space-around;
  margin-top: 5rem;
  padding-bottom: 1rem;
  height: 5vh;
}

.trending-posts {
  height: 60vh;
  width: 100%;
}

/* Grid for Forums (1 row, 5 columns) */
.forums-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  grid-gap: 40px;
  margin: 20px 0;
}

.admin-button {
  display: flex;
  height: 5vh;
  padding: 5px;
  min-width: auto;
  flex-wrap: wrap;
  align-content: end;
  background-color: var(--nero);
  color: var(--primary);
  border-color: black;
  border-radius: 8px;
  max-height: 2.5vh;
  font-weight: 800;
  cursor: pointer;
  transition: background-color 0.3s;

}

.admin-button:hover {
  background-color: black;
}

.admin-button a {
  text-decoration: none;
  /* Remove underline from link */
  color: black
  /* Link text color */
}
</style>
