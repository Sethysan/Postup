<template>
    <router-link :to="{ name: 'forum', params: { id: forum.id } }" class="forum-link">
        <div class="forum-snippet"></div>
        <h2>{{ forum.topic }}
            <!-- formatted time elapsed display with styling -->
            <p class="inline-time"> {{ forum.author }} </p>
            <p class="inline-time"> {{ getTimeElapsed(forum.timeOfCreation) }} </p>
        </h2>
        <p>{{ forum.description }}</p>
    </router-link>
</template>

<script>
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';

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
</style>