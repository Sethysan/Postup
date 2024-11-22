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
    background-color: transparent;
    background: radial-gradient(circle, var(--nero) 55%, rgba(0, 0, 0, 0.512));
}

.forum-item:hover{
    transform: scale(1.02);
    box-shadow: 0 15px 30px rgb(87, 122, 199, 0.3);
}

.forum-link {
    text-decoration: none;
    color: inherit;
}

.forum-header{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
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
    display: flex;
    flex-direction: column;
    justify-content: center; 
    align-items: center; 
    border-radius: 20px;
    padding: 1rem;
    background: radial-gradient(circle, var(--nero) 55%, rgba(0, 0, 0, 0.512));
    border: 1px solid var(--deepblue);
    margin: 1rem auto; 
    min-height: 200px;
    max-width: 600px; 
    /* width: 100%; */
    box-shadow: 0 40px 160px rgb(0, 0, 0);
    transition: transform 0.2s ease, box-shadow 0.3s ease;
}

.forum-snippet:hover{
    transform: scale(1.02);
    box-shadow: 0 15px 30px rgb(87, 122, 199, 0.3);
}

.forum-description{
    color: #888;
    text-align: center;
}
</style>