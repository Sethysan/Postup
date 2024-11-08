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
                    :pagination="{ type: 'progressbar', }" :modules="modules">
                    <swiper-slide v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id" class="trending-post">
                        <post-snippet :post="post"></post-snippet>
                    </swiper-slide>
                </swiper>
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
.swiper-wrapper {
    display: flex;
    align-items: center;
    /* Vertically centers the slides */
    padding: 20px;
}

.swiper-slide {
    height: auto;
    /* Ensures the slide height adjusts to content */
    flex-shrink: 0;
    /* Prevents the slide from shrinking */
}

#trending {
    display: flex;
    flex-direction: column;
    max-width: 1500px;
    margin: 0 auto;
    background-color: var(--nero);

}

.trending-post {
    box-shadow: 0 15px 30px rgba(0, 0, 0, .9);

}

.trending-now {
    /* Ensure the text inside is centered */
    text-align: center;
}

.body {
    position: relative;
    width: 100%;
    min-height: 350px;
    overflow: hidden;
}

.swiper-pagination-progressbar {
    background:  rgba(0, 0, 0, .25);
    position: absolute
}

.swiper-pagination-progressbar .swiper-pagination-progressbar-fill {
    background: #e50914 !important;
    position: absolute;
    left: 0;
    bottom: 0 !important;
    width: 100%;
    height: 100%;
    transform: scale(0) ;
    transform-origin: left bottom !important
}

.swiper-horizontal>.swiper-pagination-progressbar, .swiper-pagination-progressbar.swiper-pagination-horizontal {
    bottom: 0 !important;
}

</style>
