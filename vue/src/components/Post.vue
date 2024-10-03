<template>
    <div class="post">
        <h2>{{ post.title }}</h2>
        <p>{{ post.description }}</p>
        <div v-if="user">
            <div class="interactive-buttons">
                <button @click="upvote" class="thumbsUp">
                    <font-awesome-icon :icon="['far', 'thumbs-up']" />
                </button>
                <p>{{ post.upvotes }}</p><button @click="downvote" class="thumbsDown">
                    <font-awesome-icon :icon="['far', 'thumbs-down']" />
                </button>
                <p>{{ post.downvotes }}</p>
                <button v-if="post.creator_username === user" class="deletePost"
                    :onclick="deletePost">Delete</button>
            </div>
        </div>
        <button :onclick="() => {formVisibility = true}">Add Reply</button>
        <div v-if="formVisibility">
            <form  v-on:submit.prevent="addReply">
                <textarea v-model="newReply.description"></textarea>
                <button type="submit">Sumbit</button><button :onclick="() => {formVisibility = false; newReply = {}}">Cancel</button>
            </form>
        </div>
        <replies :replies="replies"></replies>
    </div>
</template>

<script>
import service from '../services/PostService';
import replySerive from '../services/RepliesService'
import Replies from './Replies.vue';

export default {
    components: { Replies },
    props: ['post', 'replies'],
    data() {
        return {
            upvoted: false,
            downvoted: false,
            user: undefined,
            formVisibility: false,
            newReply: {}
        }
    },
    created() {
        this.user = this.$store.getters.username
    },
    methods: {
        addReply(){
            alert(this.newReply)
            replySerive.createReply(this.post.id, this.newReply)
                .then(res => {
                    this.replies.unshift(res.data)
                })
                .catch(err => alert("error " + err.message))
        },
        upvote() {
            alert("upvoting")
            if (!this.upvoted) {
                service.upvotePost(this.post.id)
                    .then(res => {
                        this.post.upvotes++;
                        this.upvoted = !this.upvoted
                    })
                    .catch(err => { alert("failed to upvote: status code " + err) })
                return;
            }
            else {
                service.unvotingLike(this.post.id)
                    .then(res => {
                        this.post.upvotes--;
                        this.upvoted = !this.upvoted
                    })
                    .catch(err => alert("failed to undo upvote: status code " + err.response.status))
            }
        },
        downvote() {
            if (!this.downvoted) {
                service.downvotePost(this.post.id)
                    .then(res => {
                        this.post.downvotes++;;
                        this.downvoted = !this.downvoted
                    })
                    .catch(err => { alert("failed to downvote: status code " + err.response.status) })
                return;
            }
            else {
                service.unvotingDislike(this.post.id)
                    .then(res => {
                        this.post.downvotes--;
                        this.downvoted = !this.downvoted
                    })
                    .catch(err => alert("failed to downvote: status code " + err.response.status))
            }
        },
        deletePost() {
            if(confirm("Are you sure you want to delete this post? This action cannot be undone.")){
                let postId = this.post.id;
                alert(postId)
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
.thumbsUp, .thumbsDown{
    display: inline-block;
    width: 40px;
    padding: 10px 0;
    margin: 5px; 
    text-align: center; 
    border: 1px solid #ccc; 
    border-radius: 5px; 
    background-color: #f0f0f0; 
    cursor: pointer; 
}
.deletePost{
    display: inline-block;
    width: 80px; 
    padding: 10px 0; 
    margin: 5px; 
    text-align: center; 
    border: 1px solid #ccc; 
    border-radius: 5px; 
    background-color: #f0f0f0; 
    cursor: pointer;
}
.interactive-buttons{
    display: flex;
    justify-content: flex-start;
}
</style>