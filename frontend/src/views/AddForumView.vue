<template>
    <div class="forum-form">
        <forum-form v-bind:forum="forum" />
    </div>
</template>

<script>
import ForumForm from '../components/ForumForm.vue';

export default {
    components: {
        ForumForm
    },

data() {
    return {
        forum: {
            id: 0,
            topic: '',
            author: '',
            date: ''
            }   
        }
    }
}
</script>

<style scoped>
</style>
