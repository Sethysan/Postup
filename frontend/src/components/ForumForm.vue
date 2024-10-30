<template>
    <div class="create-forum">
        <h2>Create a Forum</h2>
        <form v-on:submit.prevent="submitForm">
            <div class="form-group">
                <label for="title">Forum Title:</label>
                <textarea class="title-input" v-model="editForum.topic" id="title" required autofocus></textarea>
            </div>

            <div class="form-group">
                <label for="description">Forum Description:</label>
                <textarea v-model="editForum.description" id="description" required></textarea>
            </div>
            <div class="button-div">
                <button class="submit-button" type="submit">Submit</button>
                <button class="cancel-button" type="button" v-on:click="cancelForm">Cancel</button>
            </div>
        </form>

    </div>
</template>


<script>
import forumService from '../services/ForumService.js';

export default {
    props: {
        forum: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            editForum: {
                id: this.forum.id,
                topic: this.forum.topic,
                description: this.description,
                author: this.forum.author,
                date: this.forum.date
            }
        }
    },
    computed: {
        author() {
            return this.$store.state.user?.username || JSON.parse(localStorage.getItem('user')).username || 'Unknown';
        }
    },
    methods: {
        submitForm() {
            if (!this.validateForm()) {
                return;
            }
            const token = this.$store.state.authToken || localStorage.getItem("token");
            const config = {
                headers: {
                    Authorization: `Bearer ${token}` // Add token to the Authorization header
                }
            };
            if (this.editForum.id === 0) {
                forumService.createForum(this.editForum, config)
                    .then(() => {
                        this.$store.commit('SET_NOTIFICATION', 'Forum created successfully.');
                        this.$router.push('/forums');
                    })
                    .catch(error => {
                        this.handleErrorResponse(error, 'adding');
                    });
            }
        },
        cancelForm() {
            this.$router.back();
        },
        handleErrorResponse(error, verb) {
            if (error.response) {
                if (error.response.status == 404) {
                    this.$router.push({ name: 'NotFoundView' });
                } else {
                    this.$store.commit('SET_NOTIFICATION',
                        `Error ${verb} forum. Response received was "${error.response.statusText}".`);
                }
            } else if (error.request) {
                this.$store.commit('SET_NOTIFICATION', `Error ${verb} forum. Server could not be reached.`);
            } else {
                this.$store.commit('SET_NOTIFICATION', `Error ${verb} forum. Request could not be created.`);
            }
        },
        validateForm() {
            let msg = '';

            this.editForum.topic = this.editForum.topic.trim();
            if (this.editForum.topic.length === 0) {
                msg += 'The new forum must have a title. ';
            }

            if (msg.length > 0) {
                this.$store.commit('SET_NOTIFICATION', msg);
                return false;
            }
            return true;
        }
    }
}
</script>

<style scoped>
.button-div {
    display: flex;
    justify-content: space-between;
}

.cancel-button,
.submit-button {
    display: inline-block;
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

.cancel-button:hover,
.submit-button:hover {
    background-color: #a33908;
}

.title-input {
    padding-top: 5px;
    width: 100%;
    height: 20px;
}

.create-forum {
    margin: 20px;
    padding-bottom: 20%;
    padding-right: 65%;
}

.form-group {
    margin-bottom: 15px;
}

textarea {
    width: 100%;
    height: 100px;
}

button {
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #45a049;
}
</style>