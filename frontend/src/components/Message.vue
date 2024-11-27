<template>
    <div class="message-container">

        <div v-if="!deleted" class="message">
            <div>
                <h4>{{ message.sender.username }}</h4>
                <p>Sent {{ formattedTimestamp }}</p>
                <p v-if="!updateMode">{{ message.message }} <span v-if="message.edited">*</span></p>
                <textarea v-if="updateMode" v-model="message.message"></textarea>
                <button v-if="updateMode" @click="update">Update</button>
                <p v-if="message.read">Read &#x2713;</p>
            </div>
            <div v-if="isSender" class="options">
                <button @click="toggleOptions">Options</button>
                <div v-if="showOptions">
                    <button @click="deleteMessage">Delete</button>
                    <button @click="enableUpdateMode">Edit</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import service from '../services/MessageService';
import dayjs from 'dayjs'; // Assuming you are using Day.js for consistent timestamps

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

    /* Enable vertical scrolling */
    padding: 1rem;
}

.message {
    padding: 1rem;
    border-bottom: 1px solid var(--primary);
}

.options {
    margin-top: 0.5rem;
}



textarea {
    width: 100%;
    margin-top: 0.5rem;
}

p {
    margin: 0.5rem 0;
}
</style>