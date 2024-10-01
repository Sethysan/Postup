<template>
  <div class="post">
    <h2>place holder for title</h2>
    <p>{{ post.description }}</p>
    <div v-if="this.$store.user">
        <button :onclick="upvote">Upvote</button><p>{{ post.upvotes }}</p><button :onclick="downvote">Downvote</button><p>{{ post.downvotes }}</p>
    </div>
    <replies :replies="replies"></replies>
  </div>
</template>

<script>
import service from '../services/PostService';

export default {
    props: ['post', 'replies'],
    data () {
        return {
            upvoted: false,
            downvoted: false
        }
    },
    methods : {
        upvote(){
            alert("upvoting")
            if(this.upvoted){
                service.upvotePost(this.post.id)
                .then(res => {
                    this.post.upvotes++;
                    this.upvoted = !this.upvoted
                })
                .catch(err => {alert("failed to upvote")})
                return;
            }
            service.unvotingLike(this.post.id)
            .then(res => {
                this.post.upvotes--; 
                this.upvoted = !this.upvoted
            })
            .catch(err => alert("failed to undo upvote"))
        },
        downvote(){
            if(this.downvoted){
                service.upvotePost(this.post.id)
                .then(res => {
                    this.post.upvotes++;
                    this.upvoted = !this.upvoted
                })
                .catch(err => {alert("failed to upvote")})
                return;
            }
            service.unvotingDislike(this.post.id)
            .then(res => {
                this.post.upvotes--; 
                this.upvoted = !this.upvoted
            })
            .catch(err => alert("failed to undo upvote"))
        }
    }
}
</script>

<style>

</style>