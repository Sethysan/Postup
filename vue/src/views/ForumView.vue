<template>
  <forum :forum="forum"></forum>
  <post-list :forum="forumId"></post-list>
</template>

<script>
import PostList from '../components/PostList.vue';
import service from '../services/ForumService'

export default {
  components: { PostList },
    data(){
        return {
            forum: {},
            forumId: 0
        }
    },
    created(){
        this.forumId = this.$route.params.id;
        service.getForum(this.forumId).then(res => this.forum = res.data);
    }
}
</script>

<style>

</style>