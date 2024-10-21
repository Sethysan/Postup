<template>
    <div class="post-snippet">

        <div v-if="post">
            <router-link :to="{ name: 'post', params: { post: post.id } }" class="post-link">
                <header>
                    <div class="post-meta">
                        <div class="user-image-frame">
                            <img v-if="post.creator_image" :src="post.creator_image" class="user-image" />
                            <img v-else src="/images/avatars/no-image.jpg" class="user-image"/>
                        </div>
                        <p class="post-author">{{ post.creator_username }} </p>
                        <p class="post-time">• {{ getTimeElapsed(post.timeOfCreation) }}</p>
                    </div>
                        <h2 class="title">{{ post.title }}</h2>
                </header>

                <section>
                    <img v-if="post.image" :src="post.image" class="post-image" />
                    <!-- <p> {{ post.description }} </p> -->
                </section>
            </router-link>
        </div>
        <div v-else>
            <p>No post data available.</p>
        </div>

    </div>
</template>

<script>
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
dayjs.extend(relativeTime);

export default {
    props: ['post'],
    methods: {
        getTimeElapsed(creationTime) {
            // dayjs converts time into a readable format and calculates the elapsed time
            return dayjs(creationTime).fromNow();
        }
    }
}
</script>

<style scoped>
.post-link{
    text-decoration: none;
}

.post-snippet {
    border-radius: 5px;
    padding: 10px;
    margin-bottom: 20px;
}


.post-meta {
    display: flex;
    align-items: center;
}

.post-time {
    margin-left: 10px;
    color: #888;
}

.post-image {
    width: 100%;
    max-height: 200px;
    object-fit: cover;
    margin-top: 10px;
    border-radius: 5px;
}

.btn-delete {
    margin-top: 10px;
    background-color: red;
    color: white;
    border: none;
    border-radius: 3px;
    padding: 5px 10px;
    cursor: pointer;
}

.btn-delete:hover {
    background-color: darkred;
}

</style>