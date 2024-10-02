<template>
  <post-list :forumId="forumId"></post-list>
</template>

<script>
import PostList from '../components/PostList.vue'
export default {
  components: { PostList },
  data() {
    return {
      forumId: 0,
    }
  },
  created() {
    this.forumId = this.$route.params.forumId;
  }

}
</script>

<style></style>