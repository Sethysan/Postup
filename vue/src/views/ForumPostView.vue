<template>
  
  <p v-if="isLoading">Loading...</p>
  <post v-if="!isLoading" :post="postData" :replies="replies"></post>
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
    }
  },
  created(){
    this.postId = this.$route.params.post;
    if(this.postId){
         service.getPostById(this.postId)
          .then(res => {
            this.postData = res.data
            console.log(this.postData)
            reviewService.getReplies(this.postId)
              .then(res => {
                  this.replies = res.data
                  this.isLoading = false;
                })
              .catch(err => alert("unable to fetch replies"))
          })
        .catch(err => alert("unable to fetch post"));
    }
  }
}
</script>

<style>

</style>