<template>
    <div class="forum">
        <!-- todo: add styling -->
        <div class="forum-header">
            <h1 class="forum-topic">{{ forum.topic }}</h1>
            <p class="forum-description">{{ forum.description }}</p>
        </div>
        <button :class="forum.favorited ? 'favorited' : 'not-favorited'" @click="favorite">
            {{ forum.favorited ? 'Favorited' : 'Favorite?' }}
        </button> <!-- eventually a heart will likely go inside the favorite button or the button will be a heart  -->
        <!-- Router link to create a new post in this forum -->
        <router-link :to="{ name: 'create-post', params: { forumId: forum.Id } }">
            <button class="create-post-button">Create Post</button>
        </router-link>
        <router-link :to="{ name: 'promote', params: { forumId: forum.Id } }">
            <button v-if="checkIfMod || role === 'ROLE_ADMIN'" class="promote-button">Promote to Moderator</button>
        </router-link>
    </div>
</template>
<script>
import service from '../services/ForumService'
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import ModeratorService from '../services/ModeratorService';

export default {
    props: ['forum', 'forumId'],
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
            // this.checkIfMod();
        } else {
            // Otherwise, fetch the forum from the service
            service.getForum(this.forumId).then(res => {
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
        },
        getTimeElapsed(creationTime) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(creationTime).fromNow();
        },
    },
    computed: {
        checkIfMod() {
    const access = this.$store.getters.access;

    // Log the access value for debugging
    console.log("Access:", access);

    // Check if access is an array
    if (Array.isArray(access)) {
        const foundIndex = access.map(item => item.forumId).findIndex(id => id === this.forumId);
        console.log("Access is an array. ForumId:", this.forumId, "Found Index:", foundIndex);
        return foundIndex !== -1; // true if found, false otherwise
    }

    // If access is a JSON string, parse it
    try {
        const parsedAccess = JSON.parse(access);
        console.log("Parsed Access:", parsedAccess);
        
        if (Array.isArray(parsedAccess)) {
            const foundIndex = parsedAccess.map(item => item.forumId).findIndex(id => id === this.forum.id);
            console.log("Parsed access is an array. ForumId:", this.forum.id, "Found Index:", foundIndex);
            return foundIndex !== -1; // true if found, false otherwise
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