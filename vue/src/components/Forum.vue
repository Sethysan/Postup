<template>
    <div class="forum">
        <h1>{{ forum.topic }}</h1>
        <p>{{ forum.description }}</p>

        <!-- Router link to view posts in this forum -->
        <router-link :to="{ name: 'forums', query: { topic: forum.topic } }">
            <button class="view-forum-button">View Forums</button>
        </router-link>
        <!-- Router link to create a new post in this forum -->
        <router-link :to="{ name: 'create-post', params: { forumId: forum.Id } }">
            <button class="create-post-button">Create Post</button>
        </router-link>
    </div>
</template>

<script>
import service from '../services/ForumService'

export default {
    props: ['forum'],
    data() {
        return {
            currentForum: {}
        }
    },
    created() {
        if (this.forum) {
            // If forum data is already passed as a prop, use it
            this.currentForum = this.forum;
        } else {
            // Otherwise, fetch the forum from the service
            service.getForum(this.$route.params.forumId).then(res => {
                this.currentForum = res.data;
            });
        }
    }
}
</script>

<style scoped>
button {
    padding: 5px 10px;
    margin-bottom: 15px;
    background-color: #e15d20;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: .5s;
    margin-top: 20px;
    display: inline-block;
}

button:hover {
    background-color: #a33908;
}
.view-forum-button{
    margin-right: 10px;
}
</style>