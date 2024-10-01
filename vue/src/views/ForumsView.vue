<template>
    <router-link :to="{ name: 'create-forums' }">Add Forum</router-link>
    <div v-for="forum in forums" :key="forum.id" v-bind:value="forum.id" class="container">
        <!-- Forum Topic name  -->
        <h2>{{ forum.topic }}
            <!-- formatted time elapsed display with styling -->
            <p class="inline-time"> * {{ getTimeElapsed(forum.timeOfCreation) }} </p>
        </h2>
        <p>{{ forum.description }}</p>
        <p></p>

        <!-- Topic Timestamp -->

    </div>
</template>

<script>
import ForumService from "../services/ForumService.js";
// importing a library to handle time conversions
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
dayjs.extend(relativeTime);

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
    methods: {
        getTimeElapsed(creationTime) {
            // dayjs converts time in to readable format and calculates the elapsed time
            return dayjs(creationTime).fromNow();
        }
    }

}
</script>


<style scoped>
/* <p> is displayed inline with <h2> */
.inline-time {
    display: inline;
    font-size: 1rem;
    /* Adds spacing between the topic and the timestamp */
    margin-left: 10px;   
    color: #4e4949;
    
}

.container {
    margin-bottom: 4rem;
}

</style>