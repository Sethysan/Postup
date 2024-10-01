<template>
    <div>
        <h1>Add New Forum</h1>
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
