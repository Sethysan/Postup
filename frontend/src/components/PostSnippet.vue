<template>
    <div class="post-snippet">

        <div v-if="post">
            <router-link :to="{ name: 'post', params: { post: post.id } }" class="post-link">
                <section class="image-container">
                    <img v-if="post.image" :src="post.image" class="post-image" />
                    <footer>
                        <div class="post-meta">
                            <div class="user-image-frame">
                                <img v-if="post.creator_image" :src="post.creator_image" class="user-image" />
                                <img v-else src="/images/avatars/no-image.jpg" class="user-image" />
                            </div>
                            <p class="post-author">{{ post.creator_username }} </p>
                            <p class="post-time">• {{ getTimeElapsed(post.timeOfCreation) }}</p>
                        </div>
                        <h2 class="title">{{ post.title }}</h2>
                    </footer>
                </section>
            </router-link>
            <p class="post-description">{{ post.description }}</p>
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
.post-link {
    text-decoration: none;
}

.post-snippet {
    position: relative;
    border-radius: 5px;
    overflow: hidden;
    padding: 0px;
    cursor: pointer;
    border: solid .01px;
    border-color: var(--nero);
    box-shadow: 0 40px 160px rgb(0, 0, 0);
    transition: transform 0.3s ease, box-shadow 0.2s ease;

}

.post-snippet:hover {
    transform: scale(1.02);
    box-shadow: 0 15px 30px  rgb(87, 122, 199, 0.3);
    /* box-shadow: 0 4px 15px rgba(234, 85, 11, 0.5); */
}

.image-container:after {
    content: "";
    left: 0;
    right: 0;
    position: absolute;
    box-shadow: 0px 0 120px 80px var(--nero);
}


.image-container {
    position: relative;
    width: 100%;
    height: 22rem;
}

footer {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    line-height: 1.5rem;
    font-weight: 600;
    padding: 8px;
    color: #fff;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    z-index: 5;
    /* Make sure footer stays on top of the image */
}

.post-meta {
    padding: 0.5;
    display: flex;
    align-items: center;

}

.post-author,
.post-time {
    margin-left: 10px;
    color: var(--Primary);
}

.post-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
}

.user-image-frame {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    overflow: hidden;
    margin-right: 10px;
}

.user-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
    /* Ensure the user image is circular */
}

.post-description {
    opacity: 0;
    /* Hidden by default */
    transition: opacity 0.5s ease;
    /* Smooth transition for visibility */
    max-height: 0;
    /* Collapse the description initially */
    overflow: hidden;
    /* Hide overflowing content */
    line-height: 1.5rem;
    border-radius: 10px;
    color: var(--Primary);
    /* Adjust text color */
    font-size: 0.9rem;
    padding: 9px 0;
    position: absolute;
    /* Position relative to the snippet */
    bottom: 5px;
    /* Adjust position as needed */
    left: 10px;
    right: 10px;
}

.post-snippet:hover .post-description {
    opacity: 1;
    background-color: rgba(0, 0, 0, 0.915);
    max-height: 10rem;
    max-width: 95%;
    z-index: 30;
}

</style>