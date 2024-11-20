<!-- ForumPosts.vue -->
<template>
    <section id="forum-posts">
        <div v-if="posts.length > 0">
            <div id="posts">
                <h1>Posts</h1>
            </div>
            <div class="body">
                <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                    :centeredSlides="false" :spaceBetween="10" :slidesPerView="1" :breakpoints="breakpoints"
                    :coverflowEffect="coverflowEffect" :pagination="{ type: 'progressbar' }" :modules="modules"
                    v-if="posts.length > 2">
                    <swiper-slide v-for="(post) in posts" :key="post.id" class="forum-post">
                        <post-snippet :post="post"></post-snippet>
                    </swiper-slide>
                </swiper>
                <div class="forum-slider" v-else-if="posts.length === 1">
                    <swiper ref="swiper" class="swiper-wrapper" :slidesPerView="1" :spaceBetween="0"
                        :pagination="{ type: 'progressbar' }" :modules="modules">
                        <swiper-slide v-for="(post) in posts" :key="post.id" class="trending-post">
                            <post-snippet :post="post"></post-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
                <div class="forum-slider" v-else-if="posts.length === 2">
                    <swiper ref="swiper" class="swiper-wrapper" :slidesPerView="2" :spaceBetween="40"
                        :pagination="{ type: 'progressbar' }" :modules="modules">
                        <swiper-slide v-for="(post) in posts.slice(0, 2)" :key="post.id" class="trending-post">
                            <post-snippet :post="post"></post-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
                <div v-else>
                    <p>No posts available.</p>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import { Swiper, SwiperSlide } from 'swiper/vue';
import { EffectCoverflow, Pagination } from 'swiper/modules';
import { mapState, mapMutations } from 'vuex';
import dayjs from 'dayjs';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import PostSnippet from './PostSnippet.vue';

export default {
    components: { Swiper, SwiperSlide, PostSnippet },
    props: ['posts'],
    name: 'ForumPosts',
    setup() {
        return {
            modules: [Pagination, EffectCoverflow],
        };
    },
    computed: {
        ...mapState(['currentPage']),
    },
    methods: {
        getTimeElapsed(timeOfCreation) {
            return dayjs(timeOfCreation).fromNow();
        },
        ...mapMutations(['setCurrentPage']),
        handleSlideChange(swiper) {
            this.setCurrentPage(swiper.realIndex + 1);
        }
    }
};
</script>

<style>
#forum-posts {
    display: flex;
    flex-direction: column;
    max-width: 1500px;
    margin: 0 auto;
    background-color: var(--nero);
}

.forum-post {
    box-shadow: 0 15px 30px rgba(0, 0, 0, .9);
}

#posts {
    text-align: center;
}

.body {
    padding: 20px;
}
</style>