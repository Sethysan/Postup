<template>
  <div>
  <p v-if="isLoading">Loading...</p>
  <div v-if="error">
      <p>Oops, it looks like this post couldn't load</p>
  </div>
  <post v-if="!isLoading" :post="postData" :replies="replies"></post>
</div>
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
        postData: {},
        replies: [],
        isLoading: true,
        error: false
    }
  },
  created(){
    this.postId = this.$route.params.post;
    if(this.postId){
         service.getPostById(this.postId)
          .then(res => {
            this.postData = res.data
            // console.log(this.postData)
            reviewService.getReplies(this.postId)
              .then(res => {
                  this.replies = res.data
                  // console.log(this.replies)
                  this.isLoading = false;
                })
              .catch(err => this.error = true)
          })
        .catch(err => this.error = true);
    }
  }
}
</script>

<style>

</style>