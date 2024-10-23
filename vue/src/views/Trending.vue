<template>
    <section id="trending">

        <div class="trending-now">
            <h1> Trending Now </h1>
        </div>
        <div class="body">
            <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true" :centeredSlides="false"
                :spaceBetween="10" :slidesPerView="1"
                :breakpoints="{ '640': { slidesPerView: 1, spaceBetween: 20, }, '768': { slidesPerView: 2, spaceBetween: 30, }, '1024': { slidesPerView: 3, spaceBetween: 30, }, }"
                :coverflowEffect="{ rotate: 50, stretch: 0, depth: 100, modifier: 1, slideShadows: true, }"
                :pagination="{ type: 'progressbar', }" :modules="modules">
                <swiper-slide v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id" class="trending-post">
                    <post-snippet :post="post"></post-snippet>
                </swiper-slide>
            </swiper>
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
    height: auto;
    background-color:  var(--nero);
}
.trending-post{
    box-shadow: 0 10px 30px rgba(0, 0, 0, .7);
}

.trending-now {
    /* Ensure the text inside is centered */
    text-align: center;
}
.body{
    padding-top: 1rem;
}
</style>
