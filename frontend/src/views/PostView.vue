<template>
  <div v-if="posts.length < 1 && !isloading">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
    <div v-if="isloading">
      <p>loading . . .</p>
    </div>
    <div v-if="error">
      <p>Oops, it looks like this search couldn't load</p>
    </div>
  <post-list :forumId="forumId"></post-list>
</template>

<script>
import PostList from '../components/PostList.vue'
export default {
  components: { PostList },
  data() {
    return {
      forumId: 0,
      isloading: true,
      error: false
    }
  },
  created() {
    this.forumId = this.$route.params.forumId;
  }

}
</script>

<style></style>