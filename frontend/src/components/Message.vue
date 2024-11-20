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
    props: ['message', 'username'],
    data() {
        return {
            showOptions: false,
            updateMode: false,
            deleted: false
        };
    },
    computed: {
        isSender() {
            return this.message.sender.username === this.$store.getters.username;
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
        }
    }
};
</script>

<style >
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