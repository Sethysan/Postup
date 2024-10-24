<template>
    <section id="active">
        <div v-if="forums.length > 0">
            <div class="most-active">
                <h1>Active Forums</h1>
            </div>
            <ul class="forum-wrapper">
                <li v-for="(forum) in forums.slice(0, 5)" :key="forum.id" class="active-forum">
                    <forum-snippet :forum="forum"></forum-snippet>
                </li>
            </ul>
        </div>
    </section>
</template>

<script>

import ForumSnippet from '../components/ForumSnippet.vue';
import dayjs from 'dayjs';
export default {
    props: ['forums'],
    components: { ForumSnippet },
    name: 'Active',
};
</script>

<style scoped>
#active {
    display: flex;
    flex-direction: column;
    height: auto;
    background-color: var(--nero);
}

.active-forum {
    box-shadow: 0 10px 30px rgba(0, 0, 0, .7);
}

.most-active {
    /* Ensure the text inside is centered */
    text-align: center;
}

.forum-wrapper {
    padding-top: 1rem;
    padding-bottom: 1rem;
}
</style>