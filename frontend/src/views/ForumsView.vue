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

    <div class="forum-results-container">
        <h2 v-if="searchDisplayed" class="search-results-header">Search Results</h2>
        <div class="all-forums">
            <!-- Display forums based on search or default -->
            <div v-for="forum in searchDisplayed ? searchForums : forums" :key="forum.id || forum.forum.id"
                class="forum-item">
                <router-link :to="{ name: 'forum', params: { id: forum.id || forum.forum.id } }" class="forum-link">
                        <p class="inline-time">{{ forum.author || forum.forum.author }}</p>
                        <p class="inline-time"> created {{ getTimeElapsed(forum.timeOfCreation ||
                            forum.forum.timeOfCreation) }}</p>                    
                    <h2>
                        <span
                            v-html="searchDisplayed ? highlightSearchTerm(forum.forum?.topic || forum.topic) : forum.topic"></span>
                    </h2>
                    <p
                        v-html="searchDisplayed ? highlightSearchTerm(forum.forum?.description || forum.description) : forum.description">
                    </p>
                </router-link>
            </div>
        </div>
    </div>

    <div v-if="this.$route.query.topic" class="return-button">
        <router-link :to="{ name: 'forums' }">
            <button>Return to Forums</button>
        </router-link>
    </div>
</template>


<script>
import ForumService from "../services/ForumService.js";
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


<style>
.forum-results-container {
    text-align: center;
    width: 95vw;
}

.search-results-header {
    margin-bottom: 1rem;
    font-size: 1.5rem;
    text-align: center;
}

.all-forums {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); 
    gap: 1.5rem;
    max-height: 80vh;
    overflow-y: auto;
    padding: 1rem;
}

/* Styling each forum item */
.forum-item {
    border: 1px solid var(--deepblue);
    padding: 1rem;
    background: radial-gradient(circle, var(--nero) 55%, rgba(0, 0, 0, 0.512));
    border-radius: 20px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    transition: transform 0.2s ease, box-shadow 0.2s ease;
}

/* Hover effect for forum items */
.forum-item:hover {
    transform: scale(1.02);
    box-shadow: 0 15px 30px rgb(87, 122, 199, 0.3);
}



/* Styling the inline time and author */
.inline-time {
    display: inline;
    font-size: 1rem;
    margin-left: 10px;
    color: var(--deepblue);
}

/* Highlight search terms */
:deep .highlight {
    background-color: yellow;
    font-weight: bold;
}

@media (min-width: 1024px) {
    .forum-results {
        grid-template-columns: repeat(3, 1fr); /* Exactly three columns */
    }
}

/* Responsive styles */
@media (max-width: 768px) {
    .forum-results {
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        max-height: 70vh;
    }

    .forum-item, .forum-snippet {
        padding: 0.5rem;
    }

    .inline-time {
        display: block;
        margin-left: 0;
    }
}

@media (max-width: 480px) {
    .forum-results {
        grid-template-columns: 1fr;
        max-height: 70vh;
    }

    .forum-item, .forum-snippet {
        padding: 0.5rem;
    }
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

.search-button {
    margin-bottom: 15px;
    color: white;
    background-color: grey;
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

.search-button:hover {
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
</style>