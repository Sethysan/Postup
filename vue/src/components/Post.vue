<template>
    <div class="post">
        <h2>{{ post.title }}</h2>
        <p>{{ post.description }}</p>
        <div v-if="user">
            <div class="post-footer flex items-center justify-start mt-md px-md xs:px-0">
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
                    <div>{{ post.upvotes - post.downvotes }}</div>

                    <button @click="downvote" :class="{ 'active-downvote': downvoted, 'upvote-active': upvoted }"
                        class="vote-button">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 20 20"
                            class="vote-icon">
                            <path
                                d="M10 20a1.122 1.122 0 0 1-.834-.372l-7.872-8.581A1.251 1.251 0 0 1 1.118 9.7 1.114 1.114 0 0 1 2.123 9H6V2.123A1.125 1.125 0 0 1 7.123 1h5.754A1.125 1.125 0 0 1 14 2.123V9h3.874a1.114 1.114 0 0 1 1.007.7 1.25 1.25 0 0 1-.171 1.345l-7.876 8.589A1.128 1.128 0 0 1 10 20Zm-7.684-9.75L10 18.69l7.741-8.44H12.75v-8h-5.5v8H2.316Zm15.469-.05c-.01 0-.014.007-.012.013l.012-.013Z" />
                        </svg>
                    </button>

                </div>

                <!-- Comments Button -->
                <button aria-label="View comments. {{ replies.length }} replies available" class="comment-button">
                    <svg aria-hidden="true" class="icon-comment" fill="currentColor" height="20" viewBox="0 0 20 20"
                        width="20" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M7.725 19.872a.718.718 0 0 1-.607-.328.725.725 0 0 1-.118-.397V16H3.625A2.63 2.63 0 0 1 1 13.375v-9.75A2.629 2.629 0 0 1 3.625 1h12.75A2.63 2.63 0 0 1 19 3.625v9.75A2.63 2.63 0 0 1 16.375 16h-4.161l-4 3.681a.725.725 0 0 1-.489.191ZM3.625 2.25A1.377 1.377 0 0 0 2.25 3.625v9.75a1.377 1.377 0 0 0 1.375 1.375h4a.625.625 0 0 1 .625.625v2.575l3.3-3.035a.628.628 0 0 1 .424-.165h4.4a1.377 1.377 0 0 0 1.375-1.375v-9.75a1.377 1.377 0 0 0-1.374-1.375H3.625Z">
                        </path>
                    </svg>
                    <span> {{ post.comments }} </span>
                    {{ replies.length }}</button>

                <button v-if="post.creator_username === user" class="deletePost" @click="deletePost">Delete</button>
                <button @click="() => { formVisibility = true }">Add Reply </button>
                <div v-if="formVisibility">
                    <form v-on:submit.prevent="addReply">
                        <textarea v-model="newReply.description"></textarea>
                        <button type="submit">Sumbit</button>
                        <button :onclick="() => { formVisibility = false; newReply = {} }">Cancel</button>
                    </form>
                </div>
            </div>
            <replies :replies="replies"></replies>
        </div>
    </div>
</template>

<script>
import service from '../services/PostService';
import replySerive from '../services/RepliesService'
import Replies from './Replies.vue';

export default {
    data() {
        return {
            isBouncing: false, // State to control the bounce animation
        };
    },
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
        addReply() {
            replySerive.createReply(this.post.id, this.newReply)
                .then(res => {
                    this.replies.unshift(res.data)
                    this.newReply = {}
                    this.formVisibility = false;
                })
                .catch(err => alert("error " + err.message))
        },
        upvote() {
            if (this.upvoted) {
                // Remove the upvote
                service.unvotingLike(this.post.id)
                    .then(res => {
                        this.post.upvotes--;
                        this.upvoted = false; 
                         // Remove upvoted state
                    })
                    .catch(err => alert("failed to undo upvote: status code " + err.response.status));
            } else {
                // Add the upvote
                service.upvotePost(this.post.id)
                    .then(res => {
                        // If already downvoted, remove downvote
                        if (this.downvoted) {
                            this.post.downvotes--;
                            this.downvoted = false;
                        }
                        if(res.status == 202){
                            this.post.upvotes++;
                        }
                        this.upvoted = true;  // Set upvoted state
                    })
                    .catch(err => { alert("failed to upvote: status code " + err) });
            }
        },
        downvote() {
            if (this.downvoted) {
                // Remove the downvote
                service.unvotingDislike(this.post.id)
                    .then(res => {
                        this.post.downvotes--;
                        this.downvoted = false;  // Remove downvoted state
                    })
                    .catch(err => alert("failed to undo downvote: status code " + err.response.status));
            } else {
                // Add the downvote
                service.downvotePost(this.post.id)
                    .then(res => {
                        // If already upvoted, remove upvote
                        if (this.upvoted) {
                            this.post.upvotes--;
                            this.upvoted = false;
                        }
                        this.post.downvotes++;
                        this.downvoted = true;  // Set downvoted state
                    })
                    .catch(err => { alert("failed to downvote: status code " + err.response.status) });
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
                    });
            }
        },
        triggerBounce() {
            this.isBouncing = true;
            // Reset bounce state after animation duration (about 2 seconds)
            setTimeout(() => {
                this.isBouncing = false;
            }, 2000);
        }
    }
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
.comment-button {
    background-color: transparent;
    border: none;
    display: flex;
    align-items: center;
    font-size: 14px;
    cursor: pointer;
}

.comment-button svg {
    margin-right: 5px;
    fill: lightgray;
}

.comment-button:hover svg {
    fill: gray;
}
</style>