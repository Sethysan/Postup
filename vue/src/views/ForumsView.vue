<template>
    <router-link :to="{ name: 'create-forums' }">Add Forum</router-link>
    <div v-for="forum in forums" :key="forum.id" v-bind:value="forum.id" class="forum-item">
        <router-link :to="{ name: 'forum', params: { id: forum.id } }" class="forum-link">
            <h2>{{ forum.topic }}
                <!-- formatted time elapsed display with styling -->
                <p class="inline-time"> {{ forum.author }} |</p>
                <p class="inline-time"> {{ getTimeElapsed(forum.timeOfCreation) }} </p>
            </h2>
            <p>{{ forum.description }}</p>
        </router-link>
    </div>
    <div v-if="this.$route.query.topic" class="return-button">
        <router-link :to="{ name: 'forums' }">
            <button>Return to Forums</button>
        </router-link>
    </div>

</template>

<script>
import PostService from "../services/PostService.js";
import ForumService from "../services/ForumService.js";

// importing a library to handle time conversions
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(relativeTime);

export default {
    data() {
        return {
            forums: [],
            posts:[]
        }
    },
    created() {
        this.fetchForums();
    },
    watch: {
        '$route.query.topic': function () {
            this.fetchForums();
        }
    },
    methods: {
        fetchForums() {
            if (this.$route.query.topic) {
                // Fetch forums by topic
                ForumService.getForumTopic(this.$route.query.topic).then(
                    (response) => {
                        this.forums = response.data;
                    }
                ).catch((error) => {
                    console.error("Error fetching forums by topic: ", error);
                });
            } else {
                // Fetch all forums if no topic is provided
                ForumService.getForums().then(
                    (response) => {
                        this.forums = response.data;
                    }
                ).catch(err => alert(err));
            }
        },
        getTimeElapsed(creationTime) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(creationTime).fromNow();
        }
    }
}
</script>


<style scoped>
/* Styling the forum item container */
.forum-item {
    border: 1px solid #ddd;
    padding: 1rem;
    margin-bottom: 2rem;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, .2);
}

.forum-item:hover {
    transform: scale(1.002);
    /* Slightly enlarge */
    box-shadow: 0 4px 15px rgba(234, 85, 11, 0.5);

}

.forum-link {
    display: block;
    text-decoration: none;
    color: inherit;
}

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