<template>
  <div>
    <forum :forum="forum"></forum>
    <post-search-button :forumId="forum.id"></post-search-button>
    <div v-if="posts.length < 1 && !isloading">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
    <div v-if="isloading">
      <p>loading . . .</p>
    </div>
    <post-list :posts="posts"></post-list>
  </div>
</template>
<script>
import PostList from '../components/PostList.vue';
import Forum from '../components/Forum.vue';
import ForumService from '../services/ForumService'
import PostService from '../services/PostService';
import PostSearchButton from '../components/PostSearchButton.vue';
export default {
  components: {
    PostList,
    Forum,
    PostSearchButton
  },
  data() {
    return {
      forum: {},
      forumId: 0,
      posts: [],
      isloading: true
    }
  },
  created() {
    // Check if the URL contains the query parameter 'topic'
    if (this.$route.query.topic) {
      // Fetch forums by the topic query parameter
      ForumService.getForumTopic(this.$route.query.topic)
        .then(res => {
          this.forum = res.data;
          PostService.getForumPosts(this.forumId)
          .then(res => {
            this.posts = res.data;
            this.isloading = false
          })
      }).catch((error) => {
        console.error("Error fetching forums by topic: ", error);
      }).catch(err => alert("error"))
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
<style scoped>
.return-button {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #45A049;
}

</style>