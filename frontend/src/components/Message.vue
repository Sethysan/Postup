<template>
    <div class="message-container">
        <div v-if="!deleted" :class="['message', isSender ? 'sender' : 'recipient']">
            <p class="message-status">
                <span :class="[isSender ? 'sent' : 'received']" class="type-status">
                    {{ isSender ? 'Sent' : 'Received' }}
                </span>
                <span class="timestamp">{{ formattedTimestamp }}</span>
            </p>

            <div class="body">
                <span class="user-name">
                    <h4>{{ message.sender.username }}</h4>
                </span>
                <p v-if="!updateMode">
                    {{ message.message }}
                    <span v-if="message.edited">*</span>
                </p>
                <textarea v-if="updateMode" v-model="message.message"></textarea>
                <button v-if="updateMode" @click="update">Update</button>
                <div class="message-read">
                    <p v-if="message.read" class="checked">&#x2713;</p>
                    <span class="read">Read </span>
                </div>
            </div>
            <div v-if="isSender" class="options">
                <div v-if="!showOptions">
                    <button @click="toggleOptions">Options</button>
                </div>
                <div v-if="showOptions">
                    <div class="buttons">
                        <button @click="toggleOptions">Options</button>
                        <button @click="deleteMessage">Delete</button>
                        <button @click="enableUpdateMode">Edit</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import service from '../services/MessageService';
import dayjs from 'dayjs';

export default {
    props: ['message'],
    data() {
        return {
            showOptions: false,
            updateMode: false,
            deleted: false,
            observer: null,
            id: this.$store.getters.userId,
            username: this.$store.getters.username,
        };
    },
    computed: {
        isSender() {
            return this.message.sender.username === this.username;
        },
        formattedTimestamp() {
            return dayjs(this.message.receivedOn).format('MMM D, YYYY h:mm A'); // Adjust the format as needed
        }
    },
    methods: {
        toggleOptions() {
            this.showOptions = !this.showOptions;
        },
        enableUpdateMode() {
            if (this.isSender) {
                this.updateMode = true;
            }
        },
        deleteMessage() {
            service.deleteMessage(this.message.sender.id, this.message.id)
                .then(() => {
                    this.deleted = true;
                    this.$emit('messageDeleted', this.message.id); // Emit the event with the message ID
                })
                .catch(err => alert(err.message));
        },
        update() {
            if (this.isSender) {
                service.updateMessage(this.message.sender.id, this.message.id, this.message)
                    .then(() => {
                        this.updateMode = false;
                        this.message.edited = true; // Mark message as edited
                    })
                    .catch(err => alert(err.message));
            }
        },
        markAsRead() {
            //  mark messages  read that were sent from the contact
            if (this.isSender || this.message.read) { // Only mark unread messages as read
                console.log('Message already read', this.message.read);
                console.log('Message from sender', this.isSender);
                return;
            } else {
                service.markRead(this.id, this.message.id) // API to mark as read
                    .then(() => {
                        this.message.read = true; // Update local state
                    })
                    .catch(err => console.error('Error marking message as read:', err));
            }
        },
        observeMessageVisibility() {
            // Observe if the message is visible in the viewport
            const observerCallback = (entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting) {
                        this.markAsRead(); // Mark as read when it is in view
                    }
                });
            };

            this.observer = new IntersectionObserver(observerCallback, {
                root: null, // Observe relative to the viewport
                threshold: 0.1, // Trigger when 10% of the message is visible
            });

            const messageElement = this.$el; // The root DOM element of this component
            this.observer.observe(messageElement);
        },
        disconnectObserver() {
            if (this.observer) {
                this.observer.disconnect(); // Clean up observer when the component is destroyed
            }
        },
    },
    mounted() {
        this.observeMessageVisibility();
    },
    beforeDestroy() {
        this.disconnectObserver();
    },
};
</script>

<style>
.message-container {
    display: flex;
    gap: 1rem;
    padding-bottom: .5rem;
    overflow-y: auto;
    max-height: 80vh;
    background-image: url('/images/patina.png');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  height: 100%;
}

.message {
    display: inline-flex;
    flex-direction: column;
    align-self: flex-start;
    max-width: 80%;
    padding: .5rem 1rem;
    border: 1px solid var(--primary);
    border-radius: 8px;
    background-color: rgba(255, 251, 251, 0.874);
    box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.8);
    word-wrap: break-word;
}

.body {
    display: flex;
    flex-direction: column;
}

.recipient .body .user-name {
    justify-content: flex-end;
    /* margin-right: 1rem; */
}

.user-name {
    display: flex;
}

.user-name h4 {
    text-transform: capitalize;
}

.sender {
    align-self: flex-end;
}

.recipient {
    align-self: flex-start;
}

.message h4 {
    margin: 0;
    font-size: 1.1rem;
    color: var(--blue);
}

.message p {
    margin: 0.2rem 0;
    line-height: 1.4;
    color: var(--secondary);
    position: relative;
}

.message-status {
    display: flex;
    font-size: 0.9rem;
    color: var(--text-color);
}

.message-status .sent {
    display: flex;
    justify-content: space-between;
}

.recipient .message-status {
    display: flex;
    flex-direction: row-reverse;
    justify-content: space-between;
}

.sender .message-read {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    gap: 0.5rem;
}

.recipient .message-read {
    display: flex;
    flex-direction: row-reverse;
    align-items: center;
    justify-content: flex-start;
    gap: 0.5rem;
}

.timestamp,
.read {
    visibility: hidden;
    opacity: 0;
    transition: visibility 0s, opacity 0.3s ease;
    color: gray;
    margin-left: 0.5rem;
}

.type-status:hover+.timestamp,
.checked:hover+.read {
    visibility: visible;
    opacity: 1;
}

.options {
    display: flex;
    justify-content: flex-end;
}

.buttons {
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    gap: 0.4rem;
}

textarea {
    width: 100%;
    resize: none;
    margin-top: 0.5rem;
    border: 1px solid var(--primary);
    border-radius: 4px;
    padding: 0.5rem;
    font-size: 1rem;
}

button {
    padding: 5px 10px;
    background-color: var(--orange);
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.5s;
    display: inline-block;
}

button:hover {
    background-color: var(--orange-dark);
    /* Slightly darker shade for hover */
}

.deleted-message {
    font-style: italic;
    color: gray;
}
</style>