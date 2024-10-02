<template>
  <div class="post">
    <h2>place holder for title</h2>
    <p>{{ post.description }}</p>
    <div v-if="this.$store.user">
        <button :onclick="upvote">Upvote</button><p>{{ post.upvotes }}</p><button :onclick="downvote">Downvote</button><p>{{ post.downvotes }}</p>
        <button class="btn btn-delete deletePost" :onclick="deletePost">Delete</button>
    </div>
    <replies :replies="replies"></replies>
  </div>
</template>

<script>
import service from '../services/PostService';
import Replies from './Replies.vue';

export default {
  components: { Replies },
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
            if(!this.upvoted){
                service.upvotePost(this.post.id)
                .then(res => {
                    this.post.upvotes++;
                    this.upvoted = !this.upvoted
                })
                .catch(err => {alert("failed to upvote")})
                return;
            }
            else {
                service.unvotingLike(this.post.id)
                .then(res => {
                    this.post.upvotes--; 
                    this.upvoted = !this.upvoted
                })
                .catch(err => alert("failed to undo upvote"))
            }
        },
        downvote(){
            if(!this.downvoted){
                service.downvotePost(this.post.id)
                .then(res => {
                    this.post.upvotes++;
                    this.upvoted = !this.upvoted
                })
                .catch(err => {alert("failed to upvote")})
                return;
            }
            else {
                service.unvotingDislike(this.post.id)
                .then(res => {
                    this.post.upvotes--; 
                    this.upvoted = !this.upvoted
                })
                .catch(err => alert("failed to undo upvote"))
            }
        },
        deletePost() {
            if (
                confirm("Are you sure you want to delete this post? This action cannot be undone.")
            ) {
                let postId = this.post.id;
                service.deletePost(postId)
                .then(response => {
                    this.$store.commit('SET_NOTIFICATION', `Post ${postId} was deleted.`);
                    this.$router.push({ name: 'forum' });
                })
                .catch(error => {
                    if (error.response) {
                    if (error.response.status === 404) {
                        this.$store.commit('SET_NOTIFICATION',
                        "Error: Post " + postId + " was not found. This post may have been deleted or you have entered an invalid post ID.");
                        this.$router.push({ name: 'forum' });
                    } else {
                        this.$store.commit('SET_NOTIFICATION',
                        "Error getting post " + postId + ". Response received was '" + error.response.statusText + "'.");
                    }
                    } else if (error.request) {
                    this.$store.commit('SET_NOTIFICATION', "Error getting post. Server could not be reached.");
                    } else {
                    this.$store.commit('SET_NOTIFICATION', "Error getting post. Request could not be created.");
                    }
                });
            }
        }
    }
}
</script>

<style>

</style>