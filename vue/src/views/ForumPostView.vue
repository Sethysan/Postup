<template>
  <post :post="post" :replies="replies"></post>
</template>

<script>
import Post from '../components/Post.vue'
import service from '../services/PostService'
import reviewService from '../services/RepliesService'

export default {
  components: { Post },
  data (){
    return {
        postId: 0,
        post: {},
        replies: []
    }
  },
  created(){
    this.postId = this.$route.params.post;
    if(this.postId){
        this.post = service.getPostById(this.postId)
        .then(res => this.post = res.data)
        .catch(err => alert("unable to fetch"));
    }
    reviewService.getReplies(this.postId)
      .then(res => this.replies = res.data)
      .catch(err => alert("unable to fetch"))
  }
}
</script>

<style>

</style>