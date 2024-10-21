<template>swiper-wrapper
    <section id="trending">
        <div class="container">
            <div class="">
                <h1> Trending Now </h1>
            </div>
            <div class="body">
                <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                    :centeredSlides="false" :spaceBetween="10" :slidesPerView="1"
                    :breakpoints="{'640': {slidesPerView: 1,spaceBetween: 20,},'768': {slidesPerView: 2,spaceBetween: 30,},'1024': {slidesPerView: 3,spaceBetween: 30,},}"
                    :coverflowEffect="{ rotate: 50, stretch: 0, depth: 100, modifier: 1, slideShadows: true, }"
                    :pagination="{ type: 'progressbar', }" :modules="modules" >
                    <swiper-slide v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id">
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
    components: { PostSnippet, Swiper, SwiperSlide},
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

<style scoped>
.justify-content-center {
    justify-content: center !important;
}

#featured>.container .swiper-scrollbar .swiper-scrollbar-drag {
    background: rgba(255, 255, 255, 0.3);
}

#trending {
    z-index: 2;
    position: relative;
}

#trending.watch-page {
    margin-top: unset;
}

#trending .head {
    text-shadow: 0 1px 2px #000;
    color: #cecece;
}

#trending .body {
    overflow: hidden;
}



#trending .body .swiper .swiper-wrapper {
    transition-duration: 0ms;
    transform: translate3d(-1104px, 0px, 0px);
    width: unset;
    margin: 0 -.01rem;
}

#trending .body .swiper .swiper-wrapper .swiper-slide {
    width: 33.3%;
    padding: 0 0.8rem;
}

.swiper-slide {
    display: block;
    width: 100%;
}

.post-snippet {
    width: 100%;
    height: auto;
    border: 1px solid var(--secondary);
    padding: 10px;
    background-color: rgb(12, 12, 12);
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Adjust Swiper navigation buttons */
.swiper-button-next,
.swiper-button-prev {
    color: #fff;
    background: rgba(0, 0, 0, 0.5);
    padding: 10px;
    border-radius: 50%;
    transition: background 0.3s ease;
}

.swiper-button-next:hover,
.swiper-button-prev:hover {
    background: rgba(0, 0, 0, 0.8);
}

.swiper-pagination-bullet {
    background: #fff;
    opacity: 0.7;
}

.swiper-pagination-bullet-active {
    background: #007bff;
    opacity: 1;
}

#trending .body .swiper .swiper-wrapper .swiper-slide>a {
    padding: 1.3rem 2rem;
    border-radius: 1rem;
    background-color: var(--secondary);
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
    height: 15rem;
    position: relative;
    display: flex;
    justify-content: flex-end;
    flex-direction: column;
    border: 1px solid transparent;
    box-shadow: inset 0 0 80px transparent;
    border: 1px solid var(--secondary);
    transition: box-shadow 0.4s, border-color 0.4s;
}

#trending .body .swiper .swiper-wrapper .swiper-slide>a.poster-bg {
    background-position: center 15%;
}

.swiper-pagination {
    color: var(--primary);
    height: 6px;
    background: var(--secondary);
}

.swiper-pagination-progressbar 
.swiper-pagination-progressbar-fill {
    color: var(--primary);
    position: bottom ;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    transform: scale(0);
    transform-origin: left top;
}

#trending .body .swiper .swiper-wrapper .swiper-slide>a:before {
    content: "\f141";
    position: absolute;
    top: 10px;
    right: 18px;
    font-family: bootstrap-icons;
    font-size: 1.7rem;
    color: #fff;
    opacity: 0.1;
    transition: opacity 0.4s;
}

#trending .body .swiper .swiper-wrapper .swiper-slide>a:after {
    content: "";
    bottom: 0;
    left: 0;
    right: 0;
    position: absolute;
    box-shadow: 1px 0 150px 90px var(--nero);
}

#trending .body .swiper .swiper-wrapper .swiper-slide>a:hover {
    box-shadow: inset 0 0 80px var(--primary);
    border: 1px solid var(--primary);
}

#trending .body .swiper .swiper-wrapper .swiper-slide>a:hover:before {
    opacity: 0.9;
}

#trending .body .swiper .swiper-wrapper .swiper-slide>a .title {
    font-size: 1.4rem;
    font-weight: 600;
    text-transform: uppercase;
    color: #fff;
    max-width: 400px;
    line-height: 1.5rem;
    z-index: 5;
    position: relative;
}


#trending .body .trending-navi {
    margin-left: 1.2rem;
    display: flex;
    flex-direction: column;
    flex-shrink: 0;
}

#trending .foot {
    position: relative;
    margin-top: 2rem;
}


section .head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
}

section .head .start {
    display: flex;
    align-items: center;
}

section .head .start .title {
    margin: unset;
    text-transform: uppercase;
    font-weight: 600;
    font-size: 1.9rem;
    color: #fff;
    margin-right: 1rem;
}
</style>
