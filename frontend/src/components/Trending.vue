<template>
    <section id="trending">
        <div v-if="filteredPosts.length > 0">
            <div class="trending-now">
                <h1> Trending Now </h1>
            </div>
            <div class="body">
                <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                    :centeredSlides="false" :spaceBetween="10" :slidesPerView="1"
                    :breakpoints="{ '640': { slidesPerView: 1, spaceBetween: 20, }, '1024': { slidesPerView: 3, spaceBetween: 30, }, }"
                    :coverflowEffect="{ rotate: 50, stretch: 0, depth: 100, modifier: 1, slideShadows: true, }"
                    :pagination="{ type: 'progressbar', }" :modules="modules" v-if="filteredPosts.length > 2">
                    <swiper-slide v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id" class="trending-post">
                        <post-snippet :post="post"></post-snippet>
                    </swiper-slide>
                </swiper>
                <div class="forum-slider" v-else>
                    <swiper ref="swiper" class="swiper-wrapper" :slidesPerView="filteredPosts.length > 1 ? 2 : 1"
                        :spaceBetween="filteredPosts.length > 1 ? 40 : 0" :breakpoints="{
                            '640': { slidesPerView: filteredPosts.length > 1 ? 1 : 1, spaceBetween: 20 },
                            '768': { slidesPerView: filteredPosts.length > 1 ? 2 : 1, spaceBetween: 30 },
                            '1024': { slidesPerView: filteredPosts.length > 1 ? 2 : 1, spaceBetween: 10 }
                        }" :pagination="{ type: 'progressbar' }" :modules="modules">
                        <swiper-slide v-for="(post) in filteredPosts.slice(0, 2)" :key="post.id" class="trending-post">
                            <post-snippet :post="post"></post-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import PostSnippet from '../components/PostSnippet.vue';
import dayjs from 'dayjs';
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination } from 'swiper/modules';
import { mapState, mapMutations } from 'vuex';


export default {
    props: ['filteredPosts'],
    components: { PostSnippet, Swiper, SwiperSlide },
    name: 'Trending',
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
    },
}
</script>

<style>
#trending {
    display: flex;
    flex-direction: column;
    max-width: 1300px;
    margin: 0 auto;
    border: 4px solid var(--nero);
    background: radial-gradient(circle, var(--nero) 75%, rgba(0, 0, 0, 0.389));
}

.trending-now {
    text-align: center;
    border-bottom: 4px solid transparent;
    border-image: linear-gradient(to right, var(--nero), rgb(24, 24, 24), rgb(23, 23, 23), rgb(22, 22, 22), rgb(25, 25, 25), rgb(25, 25, 25), var(--nero));
    border-image-slice: 1;
}

.swiper-wrapper {
    display: flex;
    align-items: center;
    width: 94%;
    padding: 20px;
    max-width: 100%;
}

.trending-post {
    display: flex;
    justify-content: center;
    /* Center the content horizontally */
    max-width: 100%;
    flex-grow: 2;
}

.swiper-slide {
    height: auto;
    /* Ensures the slide height adjusts to content */
    flex-shrink: 0;
    /* Prevents the slide from shrinking */
}

.swiper-pagination-progressbar {
    background: rgba(0, 0, 0, .25);
    position: absolute
}

.swiper-pagination-progressbar .swiper-pagination-progressbar-fill {
    background: #e50914 !important;
    position: absolute;
    left: 0;
    bottom: 0 !important;
    width: 100%;
    height: 100%;
    transform: scale(0);
    transform-origin: left bottom !important
}

.swiper-horizontal>.swiper-pagination-progressbar,
.swiper-pagination-progressbar.swiper-pagination-horizontal {
    bottom: 0 !important;
    top: auto !important;
}
</style>
