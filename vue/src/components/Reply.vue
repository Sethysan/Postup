<template>
    <div class="thread">
        <div class="reply-body">
            <div class="reply-header">
                <div class="reply-meta">
                    <div class="reply-user-image-frame">
                        <img v-if="reply.user && reply.user.user_image" :src="imageSrc" class="reply-user-image" />
                        <img v-else src="/images/avatars/no-image.jpg" class="reply-user-image"/>
                    </div>
                    <span class="reply-user">{{ currentReply.user.username }}</span>
                    <span class="reply-time">• {{ getTimeElapsed(reply.timeOfCreation) }}</span>
                </div>
            </div>
            <div class="reply-description">
                <p>{{ currentReply.description }}</p>
            </div>
            <div class="reply-footer">
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
                <button @click="() => { formVisibility = true }"
                    aria-label="View comments. {{ post.replyCount }} replies available" class="comment-button">
                    <svg aria-hidden="true" class="icon-comment" height="20" viewBox="0 0 20 20" width="20"
                        xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M7.725 19.872a.718.718 0 0 1-.607-.328.725.725 0 0 1-.118-.397V16H3.625A2.63 2.63 0 0 1 1 13.375v-9.75A2.629 2.629 0 0 1 3.625 1h12.75A2.63 2.63 0 0 1 19 3.625v9.75A2.63 2.63 0 0 1 16.375 16h-4.161l-4 3.681a.725.725 0 0 1-.489.191ZM3.625 2.25A1.377 1.377 0 0 0 2.25 3.625v9.75a1.377 1.377 0 0 0 1.375 1.375h4a.625.625 0 0 1 .625.625v2.575l3.3-3.035a.628.628 0 0 1 .424-.165h4.4a1.377 1.377 0 0 0 1.375-1.375v-9.75a1.377 1.377 0 0 0-1.374-1.375H3.625Z"
                            fill="#272525">
                        </path>
                    </svg>
                    <span>Reply</span>
                </button>
                <button v-if="reply.user.username === user || checkIfMod || role === 'ROLE_ADMIN'" class="reply-option-btn"
                    @click="deleteReply">Delete</button>
            </div>
            <div v-if="formVisibility">
                <form v-on:submit.prevent="addReply">
                    <textarea v-model="newReply.description"></textarea>
                    <button class="reply-option-btn" type="submit">Sumbit</button>
                    <button :onclick="() => { formVisibility = false; newReply = {} }" class="reply-option-btn">Cancel</button>
                </form>
            </div>
        </div>
        <div class="comments" :style="{ marginLeft: `${indent + 30}px` }">
            <reply v-for="comment in reply.replies" :key="comment.id" :reply="comment" :indent="indent + 20"
                :forumId="forumId"></reply>
        </div>
    </div>
</template>

<script>
import dayjs from 'dayjs';
import replySerive from '../services/RepliesService';

export default {
    props: ['reply', 'indent', 'forumId'],
    // props: {
    //     reply: Object,
    //     indent: Number,
    //     forumId: Number
    // },
    data() {
        return {
            styles: { margin: this.indent },
            formVisibility: false,
            newReply: {
                respondsTo: this.reply.id,
                description: ""
            },
            user: this.$store.getters.username,
            upvoted: false,
            downvoted: false,
            role: this.$store.getters.role,
            access: this.$store.getters.access,
            currentReply: {}
        }
    },
    created() {
        this.user = this.$store.getters.username,
            this.upvoted = this.reply.hasUpvoted,
            this.downvoted = this.reply.hasDownvoted;
        this.currentReply = this.reply;
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
                    .then(res => {
                        this.$store.commit('SET_NOTIFICATION', `Post ${this.reply.id} was deleted.`);
                        this.currentReply.user.username = 'removed';
                        this.currentReply.description = 'removed';
                        this.currentReply.user.user_image = '/images/avatars/removed.jpg';

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
    },
    computed: {
        imageSrc() {
            const userImage = this.reply.user.user_image.startsWith('http');
            return userImage ? this.reply.user.user_image : `${this.reply.user.user_image}`;
        },
        checkIfMod() {
            const access = this.$store.getters.access;
            if (Array.isArray(access)) {
                return access.map(item => item.forumId).findIndex(id => id === this.forumId) !== -1
            }
            try {
                const parsedAccess = JSON.parse(access);
                if (Array.isArray(parsedAccess)) {
                    return parsedAccess.map(item => item.forumId).findIndex(id => id === this.forumId) !== -1;
                }
            } catch (error) {
                console.error("Failed to parse access:", error);
            }
            return false; // Return false if access is not an array or parsing fails
        }
    }
}
</script>

<style>


.reply-header {
    display: flex;
    align-items: center;
    background-color: rgb(240, 226, 162);
    border-radius: 13px;
    margin-top: 20px;
    padding: 5px;
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

}
.reply-user-image-frame {
  width: 44px; 
  height: 44px;
  border-radius: 50%;
  background-color: rgba(0, 0, 0, 0.587); 
  display: flex;
  align-items: center;
  justify-content: center; 
}

.reply-user {
    font-size: 1rem;
}

.reply-time {
    color: rgb(107, 105, 105);
}

.reply-footer {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 10px;
    margin-top: auto;
    margin-bottom: 1vh;
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

.vote-button:hover svg {
    transform: scale(1.02);
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
    color: black;
}
.vote-icon:hover svg{
    transform: scale(1.02);
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
    transform: scale(1.02);
}

.reply-body {
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    background: radial-gradient(circle, rgb(137, 181, 206) 45%, rgb(239, 149, 70));
    border: 2px solid #ccc;
    border-color: black;
    border-radius: 25px;
    padding-left: 15px;
    padding-right: 15px;
    padding-bottom: 10px;
    margin: 3vh;
    min-height: 200px;
    max-width: 600px; /* Adjust the width as needed */
    width: 100%; /* Ensures it is responsive */
}
.reply-body textarea {
    width: 100%; /* Ensures the textarea fills the available width */
    resize: vertical; /* Allows resizing only vertically */
    padding: 1px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

.reply-description {
    margin-top: 10px;
    margin-bottom: 5px;
    background: rgba(241, 231, 199, 0.363);
    border-radius: 10px;
    padding-right: 5px;
    padding-left: 5px;
    display: block; 
    width: auto; 
    max-width: 100%; 
    word-wrap: break-word; 
    overflow-wrap: break-word; 
}
.reply-option-btn {
    align-self: flex-start; /* Aligns buttons to the start (left) */
    padding: 5px 10px;
    border-radius: 5px;
    cursor: pointer;
}
.reply-option-btn:hover {
    transform: scale(1.02);
}

</style>