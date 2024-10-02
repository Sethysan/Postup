<template>
  <div>
    <forum :forum="forum"></forum>
    <post-list :forum="forumId"></post-list>
    <router-link :to="{ name: 'create-post', params: { id: String(forumId) } }">
      <button>Create Post</button>
    </router-link>
  </div>
</template>

<script>
import PostList from '../components/PostList.vue';
import service from '../services/ForumService'

export default {
  components: { PostList },
  data() {
    return {
      forum: {},
      forumId: 0
    }
  },
  created() {
    // Check if the URL contains the query parameter 'topic'
    if (this.$route.query.topic) {
      // Fetch forums by the topic query parameter
      service.getForumTopic(this.$route.query.topic).then(res => {
        this.forum = res.data;
      }).catch((error) => {
        console.error("Error fetching forums by topic: ", error);
      });
    } else {
      // Fetch forum by ID if 'topic' is not provided
      this.forumId = this.$route.params.id;
      service.getForum(this.forumId).then(res => {
        this.forum = res.data;
      }).catch((error) => {
        console.error("Error fetching forum by ID: ", error);
      });
    }
  }
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
  background-color: #45a049;
}
</style>