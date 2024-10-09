<template>
    <div class="thread">
        {{reply.user.user_image }}
        <div class="reply-header">
            <div class="reply-meta">
                <img v-if="reply.user.user_image" :src="reply.image" class="reply-user-image" />
                <span class="reply-user">{{ reply.user.username }}</span>
                <span class="reply-time">• {{ getTimeElapsed(reply.timeOfCreation) }}</span>
            </div>
        </div>
        <p>{{ reply.description }}</p>
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
                <div>{{ reply.upvotes - reply.downvotes }}</div>
                <button @click="downvote" :class="{ 'active-downvote': downvoted, 'upvote-active': upvoted }"
                    class="vote-button">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 20 20"
                        class="vote-icon">
                        <path
                            d="M10 20a1.122 1.122 0 0 1-.834-.372l-7.872-8.581A1.251 1.251 0 0 1 1.118 9.7 1.114 1.114 0 0 1 2.123 9H6V2.123A1.125 1.125 0 0 1 7.123 1h5.754A1.125 1.125 0 0 1 14 2.123V9h3.874a1.114 1.114 0 0 1 1.007.7 1.25 1.25 0 0 1-.171 1.345l-7.876 8.589A1.128 1.128 0 0 1 10 20Zm-7.684-9.75L10 18.69l7.741-8.44H12.75v-8h-5.5v8H2.316Zm15.469-.05c-.01 0-.014.007-.012.013l.012-.013Z" />
                    </svg>
                </button>
            </div>
        </div>
        <button v-if="reply.user.username === user || role==='ROLE_MODERATOR' || role==='ROLE_ADMIN'" class="deletePost" @click="deleteReply">Delete</button>
        <button @click="() => { formVisibility = true }">Reply</button>
        <div v-if="formVisibility">
            <form v-on:submit.prevent="addReply">
                <textarea v-model="newReply.description"></textarea>
                <button type="submit">Sumbit</button>
                <button :onclick="() => { formVisibility = false; newReply = {} }">Cancel</button>
            </form>
        </div>
        <div class="comments" :style="{ marginLeft: `${indent + 20}px` }">
            <reply v-for="comment in reply.replies" :key="comment.id" :reply="comment" :indent="indent + 20"></reply>
        </div>
    </div>
</template>

<script>
import dayjs from 'dayjs';
import replySerive from '../services/RepliesService';
export default {
    props: ['reply', 'indent'],
    data() {
        return {
            styles: { margin: this.indent },
            formVisibility: false,
            newReply: {
                respondsTo: this.reply.id,
                description: ""
            },
            user: this.$store.getters.username,
            image: 'afsdf',
            upvoted: false,
            downvoted: false,
            role: this.$store.getters.role
        }
    },
    created() {
        this.user = this.$store.getters.username,
            this.upvoted = this.reply.hasUpvoted,
            this.downvoted = this.reply.hasDownvoted;
    },
    methods: {
        addReply() {
            if (this.newReply.description != "") {
                replySerive.createReply(this.reply.postId, this.newReply)
                    .then(res => {
                        this.reply.replies.push(res.data);
                        this.newReply = {}
                        this.formVisibility = false;
                    })
                    .catch(err => alert("error " + err.response.status));
            }
        },
        upvote() {
            if (this.upvoted) {
                // Remove the upvote
                replySerive.unvotingLikes(this.reply.id)
                    .then(res => {
                        this.reply.upvotes--;
                        this.upvoted = false;
                        // Remove upvoted state
                    })
                    .catch(err => this.handleError(err, "Failed to undo upvote."));
            } else {
                // Add the upvote
                replySerive.upvoteReplies(this.reply.id)
                    .then(() => {
                        // If already downvoted, remove downvote
                        if (this.downvoted) {
                            this.reply.downvotes--;
                            this.downvoted = false;
                        }
                        this.reply.upvotes++;
                        this.upvoted = true;
                        // Set upvoted state
                    })
                    .catch(err => this.handleError(err, "Failed to upvote."));
            }
        },
        downvote() {
            if (this.downvoted) {
                // Remove the downvote
                replySerive.unvotingDislikes(this.reply.id)
                    .then(() => {
                        this.reply.downvotes--;
                        this.downvoted = false;  // Remove downvoted state
                    })
                    .catch(err => this.handleError(err, "Failed to undo downvote."));
            } else {
                // Add the downvote
                replySerive.downvoteReplies(this.reply.id)
                    .then(res => {
                        // If already upvoted, remove upvote
                        if (this.upvoted) {
                            this.reply.upvotes--;
                            this.upvoted = false;
                        }
                        this.reply.downvotes++;
                        this.downvoted = true;
                        // Set downvoted state
                    })
                    .catch(err => this.handleError(err, "Failed to downvote."));
            }
        },
        handleError(err, message) {
            if (err.response?.status === 401) {
                toDisplayString("You must be logged in to perform this action.")
            } else {
                alert(`${message} Status code: ${err.response?.status || 'Unknown'}`);
            }
        },
        deleteReply() {
            if (confirm("Are you sure you want to delete this post? This action cannot be undone.")) {
                replySerive.deleteReply(this.reply.id)
                    .then(response => {
                        this.$store.commit('SET_NOTIFICATION', `Post ${this.reply.id} was deleted.`);
                        this.$forceUpdate();

                    })
                    .catch(error => {
                        alert("error " + error.response.status)
                    });
            }
        },
        getTimeElapsed(timeOfCreation) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(timeOfCreation).fromNow();
        },
    }
}
</script>

<style>
.reply-header {
    display: flex;
    align-items: center;
    margin-top: 20px;
    margin-bottom: 12px;
    gap: 10px;
}

.reply-meta {
    display: flex;
    align-items: center;
    gap: 5px;
    font-size: 0.875rem;
}

.reply-user-image {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    object-fit: cover;
    /* border: 2px solid #ddd;  */
}

.reply-user {
    font-size: 1rem;
}

.reply-time {
    color: rgb(107, 105, 105);
}


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