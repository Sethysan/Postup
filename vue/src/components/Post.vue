<template>
  <div class="post">
    <h2>place holder for title</h2>
    <p>{{ curretPost.description }}</p>
    <button></button><p>{{ curretPost.upvotes }}</p><button></button><p>{{ curretPost.downvotes }}</p>
  </div>
</template>

<script>
import service from '../services/PostService';

export default {
    props: ['post'],
    data () {
        return {
            curretPost: {}
        }
    },
    create () {
        this.curretPost = this.post ? this.post : service.getPostById(this.$route.params.post).then(res => this.curretPost = res.data)
    }
}
</script>

<style>

</style>