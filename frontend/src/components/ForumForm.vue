<template>
    <div class="create-forum">
        <h2>Create a Forum</h2>
        <form v-on:submit.prevent="submitForm">
            <div class="form-group">
                <label for="title">Forum Title:</label>
                <textarea class="input" v-model="editForum.topic" id="title" required autofocus></textarea>
            </div>

            <div class="form-group">
                <label for="description">Forum Description:</label>
                <textarea class="input" v-model="editForum.description" id="description" required></textarea>
            </div>
            <div class="button-div">
                <button class="cancel-button" type="button" v-on:click="cancelForm">Cancel</button>
                <button class="submit-button" :disabled="!isFormValid" :class="{ 'disabled-button': !isFormValid }"
                    type="submit">
                    Submit
                </button>
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
        },
        isFormValid() {
            // Check if both topic and description have text
            return this.editForum.topic.trim().length > 0 && this.editForum.description.trim().length > 0;
        },
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
    width: 100%;
}

.cancel-button,
.submit-button {
    display: inline-block;
    padding: 6px 15px;
    margin-bottom: 10px;
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

.submit-button:disabled {
    background-color: grey;
    cursor: not-allowed;
}

.disabled-button {
    background-color: grey;
    cursor: not-allowed;
}

.input {
    padding-top: 5px;
    width: 97%;
    height: 20px;
}

#description {
    height: 100px;
}

.create-forum {
    width: 80%;
    position: fixed;
    bottom: 10%;
    left: 10%;
    padding: 20px;
    color: black;
    background-color: var(--blue);
    background: radial-gradient(circle, rgb(87, 122, 199) 60%, var(--blue));
    border: 2px solid rgb(87, 122, 199);
    border-radius: 12px;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
}

.create-forum h2 {
    margin-top: 0;
    margin-bottom: 20px;
    display: block;
    display: flex;
    justify-content: center;
    margin-bottom: 5px;
}

.form-group {
    margin-bottom: 15px;
}
</style>