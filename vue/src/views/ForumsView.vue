<template>
    <router-link :to="{ name: 'create-forums' }">Add Forum</router-link>
    <div v-for="forum in forums" :key="forum.id" v-bind:value=" forum.id" class="container">
        <!-- Forum Topic name  -->
        <h2>{{ forum.topic }}</h2>
        <p>{{ forum.description }}</p>
        <p> {{ forum.timeOfCreation }} </p>

        <!-- Topic Timestamp -->
    
    </div>
</template>

<script>
import ForumService from "../services/ForumService.js";
export default {
    data() {
        return {
            forums: []
        }
    },
    created() {
        if (this.$route.params.topicId) {
            ForumService.getForumsByTopic(this.$route.params.topicId).then(
                (response) => {
                    // alert(response.status)
                    this.forums = response.data;
                }
            )
        } else {
            ForumService.getForums().then(
                (response) => {
                    // alert(response.status);
                    this.forums = response.data;
                }
            )
            .catch(err => alert(err))
        }
    },

}
</script>


<style scoped>

</style>