<template>
    <div class="forum">
        <h1>{{ forum.topic }}</h1>
        <p>{{ forum.description }}</p>

        <!-- Router link to view posts in this forum -->
        <router-link :to="{ name: 'forums', query: { topic: forum.topic } }" class="forum-link">
            View Forum
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
.create-post-button, .forum_link {
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 20px;
    display: inline-block;
}

.create-post-button:hover {
    background-color: #45a049;
}
</style>