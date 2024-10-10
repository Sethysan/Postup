<template>
    <div class="forum-buttons-top">
        <button class="btn-create" type="button" v-on:click="createNewForm"> Create Forum </button>
        <input type="search" id="forum-search" v-model="searchTerm" placeholder="Enter search term..." />
        <button class="search-button" @click="searchForForums">Search</button>
        <button class="clear-button" v-if="searchDisplayed" @click="clearSearch">Clear Search</button>
    </div>
    <div v-if="errorMessage" class="error-message">
        <p>{{ errorMessage }}</p>
    </div>
    <div v-if="searchDisplayed && searchForums.length > 0" class="forum-results">
        <h2>Search Results</h2>

        <div v-for="forum in searchForums" :key="forum.forum.id" v-bind:value="forum.forum.id" class="forum-item">
            <router-link :to="{ name: 'forum', params: { id: forum.forum.id } }" class="forum-link">
                <h2>
                    <span v-html="highlightSearchTerm(forum.forum.topic)"></span>
                    <p class="inline-time">{{ forum.forum.author }}</p>
                    <p class="inline-time">created {{ getTimeElapsed(forum.forum.timeOfCreation) }}</p>
                </h2>
                <p v-html="highlightSearchTerm(forum.forum.description)"></p>

            </router-link>
            <div v-if="forum.posts.length > 0">
                <h3>Related Posts</h3>
                <ul>
                    <li v-for="post in forum.posts" :key="post.id" v-bind:value="post.id" class="post-item">
                        <router-link :to="{ name: 'post', params: { post: post.id } }" class="post-link">
                            <span v-html="highlightSearchTerm(post.title)"></span>
                            <p v-html="highlightSearchTerm(post.description)"></p>
                        </router-link>
                    </li>
                </ul>

            </div>
        </div>
    </div>
    <div v-if="!searchDisplayed">
        <div v-for="forum in forums" :key="forum.id" v-bind:value="forum.id" class="forum-item">
            <router-link :to="{ name: 'forum', params: { id: forum.id } }" class="forum-link">
                <h2>{{ forum.topic }}
                    <!-- formatted time elapsed display with styling -->
                    <p class="inline-time"> {{ forum.author }} </p>
                    <p class="inline-time"> {{ getTimeElapsed(forum.timeOfCreation) }} </p>
                </h2>
                <p>{{ forum.description }}</p>
            </router-link>
        </div>
    </div>
    <div v-if="this.$route.query.topic" class="return-button">
        <router-link :to="{ name: 'forums' }">
            <button>Return to Forums</button>
        </router-link>
    </div>

</template>

<script>
import PostService from "../services/PostService.js";
import ForumService from "../services/ForumService.js";

// importing a library to handle time conversions
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(relativeTime);

export default {
    data() {
        return {
            forums: [],
            posts: [],
            searchForums: [],
            searchTerm: '',
            searchDisplayed: false,
            errorMessage: ''
        }
    },
    created() {
        this.fetchForums();

    },
    watch: {
        '$route.query.topic': function () {
            this.fetchForums();
        }
    },
    methods: {
        fetchForums() {
            this.errorMessage = '';
            if (this.$route.query.topic) {
                // Fetch forums by topic
                ForumService.getForumTopic(this.$route.query.topic).then(
                    (response) => {
                        this.forums = response.data;
                    }
                ).catch((error) => {
                    console.error("Error fetching forums by topic: ", error);
                    this.errorMessage = 'Unable to fetch forums. Please try again later.';
                });
            } else {
                // Fetch all forums if no topic is provided
                ForumService.getForums().then(
                    (response) => {
                        this.forums = response.data;
                    }
                ).catch(err => {
                    console.error(err);
                    this.errorMessage = 'Unable to fetch forums. Please try again later.';
                });
            }
        },
        createNewForm() {
            this.$router.push({ name: "create-forums" });
        },
        getTimeElapsed(creationTime) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(creationTime).fromNow();
        },
        searchForForums() {
            this.errorMessage = '';
            // If no search term entered, return early
            if (this.searchTerm.trim() === '') {
                this.clearSearch();
                return;
            }

            ForumService.searchForumsByTopicAndDescription(this.searchTerm)
                .then((res) => {
                    this.searchForums = res.data;
                    this.searchDisplayed = true;
                    if (this.searchForums.length === 0) {
                        this.errorMessage = 'No forums found matching your search.';
                    }
                })
                .catch(error => {
                    console.error(error);
                    this.searchForums = [];
                    this.searchDisplayed = true;
                    this.errorMessage = 'No forums found matching your search.';
                });
        },
        clearSearch() {
            this.searchTerm = '';
            this.searchForums = [];
            this.searchDisplayed = false;
            this.fetchForums();
        },
        highlightSearchTerm(text) {
            // If no search term, return the original text
            if (!this.searchTerm.trim()) return text;

            // Escape special characters in the search term
            const searchTermEscaped = this.searchTerm.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');

            // Create a new regular expression with the search term, ensuring case-insensitivity
            const regExp = new RegExp(searchTermEscaped, 'gi');

            // Replace the search term with highlighted version
            return text.replace(regExp, (match) => `<span class="highlight">${match}</span>`);
        }

    }
}
</script>


<style scoped>
/* Styling the forum item container */
.forum-item {
    border: 1px solid #ddd;
    padding: 1rem;
    margin-bottom: 2rem;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, .2);
}

.forum-item:hover {
    transform: scale(1.002);
    /* Slightly enlarge */
    box-shadow: 0 4px 15px rgba(234, 85, 11, 0.5);

}

.forum-link {
    display: block;
    text-decoration: none;
    color: inherit;
}

/* <p> is displayed inline with <h2> */
.inline-time {
    display: inline;
    font-size: 1rem;
    /* Adds spacing between the topic and the timestamp */
    margin-left: 10px;
    color: #4e4949;

}

.container {
    margin-bottom: 4rem;
}

:deep .highlight {
    background-color: yellow;
    font-weight: bold;
}

.forum-buttons-top {
    margin-top: 20px;
    margin-bottom: 20px;
}

.btn-create {
    margin-right: 10px;
}

.btn-create,
.clear-button {
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

.clear-button {
    margin-left: 10px;
}
.search-button{
    margin-bottom: 15px;
    color: white;
    background-color:grey ;
    cursor: pointer;
    transition: .5s;
    margin-top: 20px;
    display: inline-block;
    transition: .2s;
}

.btn-create:hover,
.clear-button:hover {
    background-color: #a33908;
}
.search-button:hover{
    color: white;
    background-color: rgb(4, 0, 255);
}

.error-message {
    font-family: Arial, Helvetica, sans-serif;
    color: #ff4d4d;
    /* Red color for the error text */
    background-color: #ffe6e6;
    /* Light red background to make it more noticeable */
    border: 1px solid #ff4d4d;
    /* Red border to emphasize the error */
    padding: 10px;
    /* Some padding to give it breathing space */
    margin: 10px 0;
    /* Add spacing around the error message */
    border-radius: 5px;
    /* Rounded corners for a softer look */
}

@media (max-width: 768px) {
    .forum-item {
        padding: 0.5rem;
    }

    .inline-time {
        display: block;
        /* Stack time and author vertically */
        margin-left: 0;
    }
}
</style>