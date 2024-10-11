<template>
    <div class="forum">
        <div class="forum-header">
            <h1 class="forum-topic">{{ forum.topic }}</h1>
            <p class="forum-description">{{ forum.description }}</p>
            <div class="sub-header">
                <button :class="forum.favorited ? 'favorited' : 'not-favorited'" @click="favorite">
                    {{ forum.favorited ? 'Favorited' : 'Favorite?' }}
                </button>
                <!-- eventually a heart will likely go inside the favorite button or the button will be a heart  -->
                <!-- Router link to create a new post in this forum -->
                <router-link :to="{ name: 'create-post', params: { forumId: forum.Id } }">
                    <button class="create-post-button">Create Post</button>
                </router-link>
                <router-link :to="{ name: 'promote', params: { forumId: forum.Id } }">
                    <button v-if="checkIfMod || role === 'ROLE_ADMIN'" class="promote-button">Promote to
                        Moderator</button>
                </router-link>
            </div>
        </div>
    </div>
</template>
<script>
import PostSearchButton from './PostSearchButton.vue';
import service from '../services/ForumService'
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import ModeratorService from '../services/ModeratorService';

export default {
    props: ['forum', 'forumId'],
    components: {
        PostSearchButton
    },
    data() {
        return {
            currentForum: {},
            color: 'white',
            role: this.$store.getters.role,
            listOfModsOfForum: [],
            isMod: false,
            forumId: 0,
        }
    },
    created() {
        if (this.forum) {
            // If forum data is already passed as a prop, use it
            this.currentForum = this.forum;
        } else {
            // Otherwise, fetch the forum from the service
            service.getForum(this.forumId).then(res => {
                this.currentForum = res.data;
                this.checkIfMod();
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
                    .catch(err => service.addFavorite(this.forum.id)
                    .then(res => {
                        this.color = "red"
                        this.forum.favorited = true;
                    }))
            }
            else {
                service.addFavorite(this.forum.id)
                    .then(res => {
                        this.color = "red"
                        this.forum.favorited = true;
                    })
                    .catch(err => service.removeFavorite(this.forum.id)
                    .then(res => {
                        this.color = "white"
                        this.forum.favorited = false;
                    })
                    .catch(err => alert(err)))
                    .catch(err => alert(err))
            }
        },
        getTimeElapsed(creationTime) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(creationTime).fromNow();
        },
    },
    computed: {
        checkIfMod() {
            const access = this.$store.getters.access;
            if (Array.isArray(access)) {
                return access.map(item => item.forumId).findIndex(id => id === this.forumId) !== -1
            }
            try {
                const parsedAccess = JSON.parse(access);
                if (Array.isArray(parsedAccess)) {
                    return parsedAccess.map(item => item.forumId).findIndex(id => id === this.forum.id) !== -1;
                }
            } catch (error) {
                console.error("Failed to parse access:", error);
            }
            return false; // Return false if access is not an array or parsing fails
        }
    }
}
</script>

<style scoped>
.forum-header {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-weight: bolder;
    font-size: xx-large;
    
}
.forum-description {
    font-size: medium;
    margin-bottom: 10px; /* Adjust space between the description and sub-header */
    text-align: center; /* Center the description text */
}

.sub-header{
    display: flex;
    flex-direction: row;
    align-content: space-evenly;
}
.sub-header {
    display: flex;
    flex-direction: row; /* Make the sub-header items appear in a row */
    justify-content: center; /* Center-align the buttons */
    gap: 10px; /* Space between buttons */
    margin-top: 10px; /* Add some space between the description and sub-header */
}

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

.promote-button {
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