<template>
  <div class="drop-downs">

    <!-- filter drop-down for posts -->
    <div v-if="posts.length > 0" class="filter-bar">
    <select name="filter" v-model="selectedFilter" @change="applyFilter" class="filter">
        <option value="">Sort</option>
        <option value="recent">Most Recent</option>
        <option value="popularity">Most Popular</option>
      </select>
    </div>
  </div>
  
  
  
  <div class="home">
    
    <!-- Status Display for Posts -->
    <status-display :isLoading="isloadingPost" :hasError="postLoadingError"
    errorMessage="Oops, it looks like popular posts couldn't load">
    <!-- Content to display when posts are successfully loaded -->
      <div class="trending-posts">
        <trending :filteredPosts="filteredPosts" />
      </div>
    </status-display>

    <!-- Status Display for Forums -->
    <status-display :isLoading="isloadingForum" :hasError="forumLoadingError"
      errorMessage="Oops, it looks like active forums couldn't load">
      <!-- Content to display when forums are successfully loaded -->

      <div class="active-forums">
        <div class="forums-grid">
          <active-forums :forums="forums" />
        </div>
      </div>
    </status-display>

  </div>
</template>

<script>
import ActiveForums from '../components/ActiveForums.vue';
import StatusDisplay from '../components/StatusDisplay.vue';
import Trending from '../components/Trending.vue';
import PageLoader from '../components/PageLoader.vue';
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';

export default {
  components: { ActiveForums, StatusDisplay, Trending, PageLoader },
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
      })
      .catch(error => {
        console.log("Failed to load posts: ", error)
        this.postLoadingError = true;
      });

    this.user = this.$store.getters.username;

    ForumService.getActiveForums()
      .then((res) => {
        this.forums = res.data;
        this.isloadingForum = false
      })
      .catch(err => {
        console.error("Failed to load forums: ", err);
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
.status {
  display: flex;
  text-align: center;
  font-weight: 900;
}

.home {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 3rem;
  align-items: center;
  z-index: 0
  ;
  

}

.title {
  text-align: center;
}

.filter-bar {
  display: flex;
  min-width: auto;
  flex-wrap: wrap;
  align-content: end;
}

.filter {
  border-radius: 8px;
  color: var(--orange);
  border-color: black;
  background-color: var(--nero);
  display: flex;
  flex-wrap: wrap;
  align-content: center;
  min-height: 16px;
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
  justify-content: center;
  height: 10.5vh;
  width: 100%;
  padding-right: 43.2%;
}

.trending-posts {
  display: flex;
  justify-content: center; /* Center horizontally */
  align-items: center; /* Center vertically */
  width: 100%;
  height: auto;
}

/* Grid for Forums (1 row, 5 columns) */
.forums-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  grid-gap: 40px;
}

</style>../components/Trending.vue
