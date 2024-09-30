<template>
    <form v-on:submit.prevent="submitForm">
        <div class="forum-form">
            <h1>Forum Form</h1>
            <label for="title">Title</label>
            <input type="text" id="title" name="title" v-model="editForum.topic" />
            <button class ="btn-submit" type="submit">Submit</button>
            <button class="btn-cancel" type="button" v-on:click="cancelForm">Cancel</button>
        </div>
    </form>
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
                author: this.forum.author,
                date: this.forum.date
            }
        }
    },
    methods: {
        submitForm() {
            if(!this.validateForm()) {
                return;
            }
                if (this.editForum.id === 0) {
                forumService.createForum(this.editForum)
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
            this.$router.push({name: 'NotFoundView'});
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

</style>