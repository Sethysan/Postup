<template>
  <div>
    <forum :forum="forum" :forumId="forum.id"></forum>
    <div class="forum-head">
      <div class="back-button" @click="goBack">
        <svg fill="currentColor" height="16" icon-name="back-outline" viewBox="0 0 20 20" width="16"
          xmlns="http://www.w3.org/2000/svg">
          <path d="M19 9.375H2.51l7.932-7.933-.884-.884-9 9a.625.625 0 0 0 0 .884l9 9 .884-.884-7.933-7.933H19v-1.25Z">
          </path>
        </svg>
      </div>
      <post-search-button :forumId="forum.id"></post-search-button>
    </div>
    <div v-if="posts.length < 1 && !isloading">
      <p>Wow, such empty!</p>
    </div>
    <status-display :isLoading="isloading" :hasError="postLoadingError"
      errorMessage="Oops, it looks like this forum couldn't load">
      <!-- <p>Oops, it looks like this forum couldn't load</p> -->
      <div class="forum-posts">
        <forum-posts :posts="posts" />
      </div>
    </status-display>
  </div>
</template>
<script>
import StatusDisplay from '../components/StatusDisplay.vue';
import Forum from '../components/Forum.vue';
import ForumService from '../services/ForumService'
import ForumPosts from '../components/ForumPosts.vue';
import PostService from '../services/PostService';
import PostSearchButton from '../components/PostSearchButton.vue';
export default {
  components: { StatusDisplay, Forum, PostSearchButton, ForumPosts },
  name: 'ForumView',
  data() {
    return {
      forum: {},
      forumId: 0,
      posts: [],
      isloading: true,
      postLoadingError: false,
      error: false
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  },
  created() {
    // Check if the URL contains the query parameter 'topic'
    if (this.$route.query.topic) {
      // Fetch forums by the topic query parameter
      ForumService.getForumTopic(this.$route.query.topic)
        .then(res => {
          this.forum = res.data;
          this.forumId = this.forum.id //added 10/19
          PostService.getForumPosts(this.forumId)
            .then(res => {
              this.posts = res.data;
              // console.log(this.post);
              this.isloading = false
            })
        }).catch((error) => {
          console.error("Error fetching forums by topic: ", error);
          this.error = true
        }).catch(err => this.error = true)
    }
    else {
      // Fetch forum by ID if 'topic' is not provided
      this.forumId = this.$route.params.id;
      ForumService.getForum(this.forumId).then(res => {
        this.forum = res.data;
        PostService.getForumPosts(this.forumId)
          .then(res => {
            this.posts = res.data;
            this.isloading = false
          })
      }).catch((error) => {
        console.error("Error fetching forum by ID: ", error);
      });
    }
  },
}
</script>
<style>
.forum-head {
  display: flex;
  gap: 10px;
  justify-content: space-between;
  align-items: flex-end;
}

.return-button {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

#forum-posts {
  height: auto;
  width: 100%;
  border: 4px solid var(--nero);
  background: radial-gradient(circle, var(--nero) 75%, rgba(0, 0, 0, 0.389));
}

#posts {
  text-align: center;
}

</style>