<template>
    <div class="forum">
        <h1>{{ forum.topic }}</h1>
        <p>{{ forum.description }}</p>
        <button :class="forum.favorited ? 'favorited' : 'not-favorited'" @click="favorite">
            {{ forum.favorited ? 'Favorited' : 'Favorite?' }}
        </button> <!-- eventually a heart will likely go inside the favorite button or the button will be a heart  -->
        <!-- Router link to create a new post in this forum -->
        <router-link :to="{ name: 'create-post', params: { forumId: forum.Id } }">
            <button class="create-post-button">Create Post</button>
        </router-link>
        <router-link :to="{ name: 'promote', params: { forumId: forum.Id } }">
            <button class="promote-button">Promote to Moderator</button>
        </router-link>
    </div>
</template>

<script>
import service from '../services/ForumService'

export default {
    props: ['forum'],
    data() {
        return {
            currentForum: {},
            color: 'white'
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
    },
    methods: {
        favorite() {
            if (this.forum.favorited) {
                service.removeFavorite(this.forum.id)
                    .then(res => {
                        this.color = "white"
                        this.forum.favorited = false;
                    })
                    .catch(err => alert(err))
            }
            else {
                service.addFavorite(this.forum.id)
                    .then(res => {
                        this.color = "red"
                        this.forum.favorited = true;
                    })
                    .catch(err => alert(err))
            }
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

.create-post-button {
    margin-left: 10px;
}
.promote-button{
    margin-left: 10px;
}

.favorited {
    background-color: rgb(255, 0, 0);
}

.not-favorited {
    background-color: #e15d20;
}
.favorited:hover {
    background-color: rgb(174, 1, 1);
}
</style>