<template>
  <post :post="post"></post>
</template>

<script>
import Post from '../components/Post.vue'
import service from '../services/PostService'
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

  }
}
</script>

<style>

</style>