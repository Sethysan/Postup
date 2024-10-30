<template>
  <div class="post-header">
    <!-- Back Button -->
    <div class="back-button" @click="goBack">
      <svg fill="currentColor" height="16" icon-name="back-outline" viewBox="0 0 20 20" width="16"
        xmlns="http://www.w3.org/2000/svg">
        <path d="M19 9.375H2.51l7.932-7.933-.884-.884-9 9a.625.625 0 0 0 0 .884l9 9 .884-.884-7.933-7.933H19v-1.25Z">
        </path>
      </svg>
    </div>
  </div>
  <div v-if="posts.length < 1 && !isloading">
    <p>Wow, such empty!</p>
    <img src="" />
  </div>
  <div v-if="isloadingPost">
    <p>loading . . .</p>
  </div>
  <div v-if="error">
    <p>Oops, it looks like this search couldn't load</p>
  </div>
  <post-list :posts="posts"></post-list>
</template>

<script>
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';
import PostSearchButton from '../components/PostSearchButton.vue';
import PostList from '../components/PostList.vue';

export default {
  components: { PostSearchButton, PostList },
  data() {
    return {
      forum: {},
      posts: [],
      forumId: 0,
      keyword: "",
      isloading: true,
      error: false,
    }
  },
  created() {
    this.forumId = this.$route.params.forumId;
    this.keyword = this.$route.query.keyword;
    ForumService.getForum(this.forumId)
      .then(res => {
        this.forum = res.data;
        PostService.getForumPostByKeyword(this.forumId, this.keyword)
          .then(res => {
            this.posts = res.data
            this.isloading = false
          })
          .catch(err => this.error = true)
      })
      .catch(err => this.error = true)
  },
  methods: {
    goBack() {
      this.$router.go(-1); // This navigates back in the history stack
    },
  }
}
</script>

<style></style>