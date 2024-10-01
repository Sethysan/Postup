<template>
    <div class="forum">
        <h1>{{ forum.topic }}</h1>
        <p>{{ forum.description }}</p>
    </div>
</template>

<script>
import service from '../services/ForumService'

export default {
    props: ['form'],
    data(){
        return {
            currentForum: {}
        }
    },
    created(){
        this.currentForum = this.forum ? this.forum : service.getForum(this.$route.params.forum).then(res => this.currentForum = res.data);
    }
}
</script>

<style>

</style>