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
        <!-- <div class="forums-grid"> -->
          <trending :filteredPosts="filteredPosts" />
        <!-- </div> -->
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
/* Default Styling (Mobile-First) */
.status {
  display: flex;
  text-align: center;
  font-weight: 900;
}

.home {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;


}

.filter-bar {
  display: flex;
  min-width: auto;
  flex-wrap: wrap;
  align-content: end;
}

.title {
  text-align: center;
}

.filter {
  border-radius: 8px;
  color: var(--orange);
  border: 1px solid black;
  background-color: var(--nero);
  padding: 5px;
  display: flex;
  flex-wrap: wrap;
  align-content: center;
  min-height: 16px;
  font-weight: 800;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.filter:hover,
.filter:focus {
  background-color: #000;
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

.trending-posts, .active-forums {
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* Center horizontally */
  align-items: center;
  /* Center vertically */
  width: 100%;
  gap: 20px;
  height: auto;
}

.forums-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  grid-gap: 40px;
}

/* Responsive Styling */
@media (min-width: 768px) {
  .home {
    padding: 30px;
  }

  .filter-bar {
    justify-content: flex-start;
    gap: 20px;
    width: 90%;
  }

  .trending-posts, .active-forums {
    flex-direction: row;
    justify-content: space-evenly;
  }

}

@media (min-width: 1024px) {
  .home {
    padding: 40px;
  }

  .filter-bar {
    justify-content: space-between;
    margin-bottom: 30px;
    width: 80%;
  }

  .forums-grid {
  grid-template-columns: repeat(2, 1fr); /* Ensure exactly two columns */
  grid-gap: 40px;
  justify-content: center; /* Center the grid container */
  align-items: center; /* Vertically center items */
}

}

@media (max-width: 480px) {
  .filter-bar {
    flex-direction: column;
    align-items: center;
    gap: 10px;
  }

  .forums-grid {
    grid-template-columns: 1fr;
    /* Single column on small screens */
  }

  .trending-posts, .active-forums {
    flex-direction: column;
    gap: 10px;
  }
}
</style>
