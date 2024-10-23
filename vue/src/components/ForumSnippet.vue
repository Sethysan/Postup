<template>
    <div class="forum-snippet">

        <div v-if="forum">
            <router-link :to="{ name: 'forum', params: { id: forum.id } }" class="forum-link">
                <div class="forum-header">
                    <h2 class="forum-topic">{{ forum.topic }}
                        <div class="sub-header">
                            <!-- formatted time elapsed display with styling -->
                            <p class="inline-time"> {{ forum.author }} </p>
                            <p class="inline-time"> {{ getTimeElapsed(forum.timeOfCreation) }} </p>
                        </div>
                    </h2>
                    <p class="forum-description">{{ forum.description }}</p>
                </div>
            </router-link>
        </div>
        <div v-else>
            <p>No forum data available.</p>
        </div>

    </div>
</template>

<script>
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
dayjs.extend(relativeTime);

export default {
    props: ['forum'],
    methods: {
        getTimeElapsed(creationTime) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(creationTime).fromNow();
        }
    }
}
</script>

<style>
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

.forum-snippet {
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    align-content: space-evenly;
    background: radial-gradient(circle, rgb(137, 181, 206) 45%, rgb(239, 149, 70));
    border: 2px solid #ccc;
    border-color: black;
    border-radius: 25px;
    padding-left: 15px;
    padding-right: 15px;
    margin: 3vh;
    min-height: 200px;
    max-width: 600px;
    /* Adjust the width as needed */
    width: 100%;
    /* Ensures it is responsive */
}
</style>