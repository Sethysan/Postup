<template>
    <div v-if="isImageFullscreen" class="fullscreen-container" @click="toggleImageFullscreen">
        <img :src="post.image" class="post-image-fullscreen" />
    </div>
    <div v-else class="post">
        <div class="post-header">
            <!-- Back Button -->
            <div class="back-button" @click="goBack">
                <svg fill="currentColor" height="16" icon-name="back-outline" viewBox="0 0 20 20" width="16"
                    xmlns="http://www.w3.org/2000/svg">
                    <path
                        d="M19 9.375H2.51l7.932-7.933-.884-.884-9 9a.625.625 0 0 0 0 .884l9 9 .884-.884-7.933-7.933H19v-1.25Z">
                    </path>
                </svg>
            </div>
            <!-- Author and Metadata -->
            <div class="post-meta">
                <img v-if="post.creator_image" :src="post.creator_image" class="user-image" />
                <span class="post-author">{{ post.creator_username }}</span>
                <span class="post-time">• {{ getTimeElapsed(post.timeOfCreation) }}</span>
            </div>
        </div>
        <h1 class="post-title">{{ post.title }}</h1>
        <p class="post-description">{{ post.description }}</p>
        <div class="post-image-container">
            <img v-if="post.image" :src="post.image" class="post-image" @click="toggleImageFullscreen" />

            <!-- Post Footer: Votes, Comments, Delete Button -->
            <div class="post-footer">
                <!-- Voting Buttons -->
                <div :class="['vote-container', { 'active-upvote': upvoted, 'active-downvote': downvoted }]">
                    <button @click="upvote" :class="{ 'active-upvote': upvoted, 'downvote-active': downvoted }"
                        class="vote-button">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 20 20"
                            class="vote-icon">
                            <path
                                d="M12.877 19H7.123A1.125 1.125 0 0 1 6 17.877V11H2.126a1.114 1.114 0 0 1-1.007-.7 1.249 1.249 0 0 1 .171-1.343L9.166.368a1.128 1.128 0 0 1 1.668.004l7.872 8.581a1.25 1.25 0 0 1 .176 1.348 1.113 1.113 0 0 1-1.005.7H14v6.877A1.125 1.125 0 0 1 12.877 19ZM7.25 17.75h5.5v-8h4.934L10 1.31 2.258 9.75H7.25v8Z" />
                        </svg>
                    </button>
                    <span class="vote-count">{{ post.upvotes - post.downvotes }}</span>
                    <button @click="downvote" :class="{ 'active-downvote': downvoted, 'active-upvote': upvoted }"
                        class="vote-button">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 20 20"
                            class="vote-icon">
                            <path
                                d="M10 20a1.122 1.122 0 0 1-.834-.372l-7.872-8.581A1.251 1.251 0 0 1 1.118 9.7 1.114 1.114 0 0 1 2.123 9H6V2.123A1.125 1.125 0 0 1 7.123 1h5.754A1.125 1.125 0 0 1 14 2.123V9h3.874a1.114 1.114 0 0 1 1.007.7 1.25 1.25 0 0 1-.171 1.345l-7.876 8.589A1.128 1.128 0 0 1 10 20Zm-7.684-9.75L10 18.69l7.741-8.44H12.75v-8h-5.5v8H2.316Zm15.469-.05c-.01 0-.014.007-.012.013l.012-.013Z" />
                        </svg>
                    </button>
                </div>
                <!-- Comments Count -->
                <button aria-label="View comments. {{ replies.length }} replies available" class="comment-button">
                    <svg aria-hidden="true" class="icon-comment" fill="currentColor" height="20" viewBox="0 0 20 20"
                        width="20" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M7.725 19.872a.718.718 0 0 1-.607-.328.725.725 0 0 1-.118-.397V16H3.625A2.63 2.63 0 0 1 1 13.375v-9.75A2.629 2.629 0 0 1 3.625 1h12.75A2.63 2.63 0 0 1 19 3.625v9.75A2.63 2.63 0 0 1 16.375 16h-4.161l-4 3.681a.725.725 0 0 1-.489.191ZM3.625 2.25A1.377 1.377 0 0 0 2.25 3.625v9.75a1.377 1.377 0 0 0 1.375 1.375h4a.625.625 0 0 1 .625.625v2.575l3.3-3.035a.628.628 0 0 1 .424-.165h4.4a1.377 1.377 0 0 0 1.375-1.375v-9.75a1.377 1.377 0 0 0-1.374-1.375H3.625Z">
                        </path>
                    </svg>
                    <span>{{ replies.length }} Comments</span>
                </button>
                <!-- Delete Button -->
                <button v-if="post.creator_username === user" class="delete-button" @click="deletePost">Delete</button>
            </div>
            <!-- Reply Form -->
            <div v-if="user">
                <!-- Textarea for adding a comment, expanding when clicked -->
                <div class="reply-container">
                    <textarea v-model="newReply.description" placeholder="Add a comment" @focus="expandTextarea"
                        :class="{ expanded: formVisibility }" @blur="formVisibility || cancelReply()"></textarea>
                    <!-- Buttons appear only when the textarea is expanded -->
                    <div v-if="formVisibility" class="comment-buttons">
                        <button @click="addReply" class="submit-button">Submit</button>
                        <button @click="cancelReply" class="cancel-button">Cancel</button>
                    </div>
                </div>
            </div>
            <!-- Replies Component -->
            <div v-if="!isImageFullscreen">
                <replies :replies="replies"></replies>
            </div>
        </div>
    </div>

</template>

<script>
import dayjs from 'dayjs';
import service from '../services/PostService';
import replySerive from '../services/RepliesService'
import Replies from './Replies.vue';
import { useToast } from 'vue-toastification';

export default {
    setup() {
        const toast = useToast();
        function handleError(err, message) {
            if (err.response?.status === 401) {
                toast("You must be logged in to vote", {
                    position: "bottom-center",
                });
            } else {
                toast.error(`${message} Status code: ${err.response?.status || 'Unknown'}`);
            }
        }
        return {
            handleError,
            toast
        };
    },
    props: {
        post: {
            type: Object,
            required: true
        },
        replies: {
            type: Array,
            required: true
        }
    },
    components: { Replies },
    data() {
        return {
            isImageFullscreen: false,
            upvoted: false,
            downvoted: false,
            user: this.$store.getters.username,
            formVisibility: false,
            newReply: {}
        }
    },
    created() {
        this.user = this.$store.getters.username,
            this.upvoted = this.post.hasUpvoted,
            this.downvoted = this.post.hasDownvoted;

    },
    props: ['post', 'replies'],
    methods: {
        goBack() {
            this.$router.go(-1); // This navigates back in the history stack
        },
        getTimeElapsed(timeOfCreation) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(timeOfCreation).fromNow();
        },
        toggleImageFullscreen() {
            this.isImageFullscreen = !this.isImageFullscreen;
        },
        expandTextarea() {
            this.formVisibility = true;  // Make the form visible
        },
        addReply() {
            if (!this.newReply.description.trim()) {
                alert('Comment cannot be empty.');
                return;
            }
            replySerive.createReply(this.post.id, this.newReply)
                .then(res => {
                    this.replies.unshift(res.data)
                    this.newReply = {}
                    this.formVisibility = false;
                })
                .catch(err => alert("error " + err.message))
        },
        cancelReply() {
            this.newReply = {};
            this.formVisibility = false;
        },
        upvote() {
            if (this.upvoted) {
                // Remove the upvote
                service.unvotingLike(this.post.id)
                    .then(res => {
                        this.post.upvotes--;
                        this.upvoted = false;
                    })
                    .catch(err => this.handleError(err, "Failed to undo upvote."));
            } else {
                // Add the upvote
                service.upvotePost(this.post.id)
                    .then(() => {
                        // If already downvoted, remove downvote
                        if (this.downvoted) {
                            this.post.downvotes--;
                            this.downvoted = false;
                        }
                        this.post.upvotes++;
                        this.upvoted = true;

                    })
                    .catch(err => this.handleError(err, "Failed to upvote."));
            }
        },
        downvote() {
            if (this.downvoted) {
                // Remove the downvote
                service.unvotingDislike(this.post.id)
                    .then(() => {
                        this.post.downvotes--;
                        this.downvoted = false; // Remove downvoted state
                    })
                    .catch(err => this.handleError(err, "Failed to undo downvote."));
            } else {
                // Add the downvote
                service.downvotePost(this.post.id)
                    .then(() => {
                        if (this.upvoted) {
                            this.post.upvotes--;
                            this.upvoted = false;
                        }
                        this.post.downvotes++;
                        this.downvoted = true;
                    })
                    .catch(err => this.handleError(err, "Failed to downvote."));
            }
        },
        deletePost() {
            if (confirm("Are you sure you want to delete this post? This action cannot be undone.")) {
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
                    })
                    .finally(() => {
                        this.fetchReplies();
                    });
            }
        }
    },
}
</script>

<style>
.post-footer {
    display: flex;
    gap: 20px;
    align-items: center;
    justify-content: start;
    margin-top: 1rem;
    padding-left: 1rem;
}

/* Voting container */
.vote-container {
    display: flex;
    align-items: center;
    background-color: rgb(228, 228, 228);
    border-radius: 30px;
    transition: background-color 0.3s ease;

}

.vote-button {
    background-color: transparent;
    border-radius: 30px;
    border: none;
    cursor: pointer;
    display: inline-flex;
    justify-content: center;
    align-items: center;
    padding: 8px;
}

.vote-button:hover {
    background-color: rgb(218, 217, 217);
}


.active-upvote.vote-button:hover,
.upvote-active:hover {
    background-color: rgb(214, 96, 17);
}

.active-downvote.vote-button:hover,
.downvote-active:hover {
    background-color: rgb(4, 0, 255);
}

.vote-icon {
    transition: fill 0.3s ease;
}

/* Active state: Upvote */
.vote-button.active-upvote .vote-icon {
    fill: rgb(250, 250, 250);
}

/* Active state: Downvote */
.vote-button.active-downvote .vote-icon {
    fill: rgb(230, 230, 230);
}

/* Fill color for upvoted container */
.vote-container.active-upvote {
    background-color: rgb(240, 108, 19);

}

/* Fill color for downvoted container */
.vote-container.active-downvote {
    background-color: rgb(44, 44, 255);

}

/* Comment button */
.comment-button,
.delete-button {
    padding-top: 5px;
    padding-bottom: 5px;
    text-justify: center;
    background-color: rgb(228, 228, 228);
    border-radius: 30px;
    transition: background-color 0.3s ease;

}

.comment-count {
    align-items: center;
    background-color: rgb(228, 228, 228);
    border-radius: 30px;
    transition: background-color 0.3s ease;

}

.comment-buttons {
    background-color: transparent;
    position: absolute;
    bottom: 10px;
    right: 10px;
    display: flex;
    gap: 10px;
}

button[type="submit"] {
    background-color: #0366d6;
    color: #fff;
}

.submit-button {
    background-color: #0366d6;
    color: white;
    padding: 6px 12px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.submit-button:hover {
    background-color: #025bb5;
}

.cancel-button {
    background-color: #e1e1e1;
}

.cancel-button:hover {
    background-color: #c7c7c7;
}

textarea {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ddd;
    transition: height 0.3s ease, padding 0.3s ease;
    height: 40px;
    resize: none;
}

textarea.expanded {
    height: 75px;
    padding-bottom: 40px;
    /* Space for buttons */
}

.reply-container {
    position: relative;
    margin-top: 20px;
}

.vote-count {
    font-size: 1rem;
}

.vote-button {
    background: none;
    border: none;
    cursor: pointer;
}

.post-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 12px;
}

.post-image {
    width: 100%;
    background-color: black;
    max-height: 400px;
    object-fit: contain;
    border-radius: 8px;
    cursor: pointer;
    transition: transform 0.3s ease;
}

.fullscreen-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: black;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    cursor: zoom-out;
}

.post-image-fullscreen {
    width: 100vw;
    height: 100vh;
    max-width: none;
    max-height: none;
    object-fit: contain;
    cursor: zoom-out;

}

.post-image-container img {
    width: 100%;
    max-height: 400px;
    object-fit: contain;
    border-radius: 8px;
    cursor: zoom-in;
}

.post-description {
    font-size: 1rem;
    margin-bottom: 12px;
}

.post-meta {
    display: flex;
    align-items: center;
    gap: 5px;
    /* Adjust spacing between author and time */
    font-size: 0.875rem;
}
.user-image {
    width: 40px; 
    height: 40px; 
    border-radius: 50%; 
    object-fit: cover; 
    /* border: 2px solid #ddd;  */
}
.post-author{
    font-size: 1.25rem;
}
.post-time{
    color:rgb(107, 105, 105) ;
}

.post-header {
    display: flex;
    align-items: center;
    margin-top: 20px;
    margin-bottom: 12px;
    gap: 10px;
    /* Adjust spacing between the back button and metadata */
}

.post-title {
    margin-top: 10px;
    /* Add space between metadata and title */
    font-size: 1.5rem;
    font-weight: 600;
}

.back-button {
    background-color: #E4E4E4;
    /* Change to your desired background color */
    border-radius: 50%;
    /* Makes the background circular */
    padding: 8px;
    /* Adjusts the size of the circle */
    display: inline-flex;
    /* Centers the SVG within the container */
    align-items: center;
    justify-content: center;
    transition: .3;
    cursor: pointer;
}

.back-button:hover {
    background-color: rgb(218, 217, 217);
}

</style>