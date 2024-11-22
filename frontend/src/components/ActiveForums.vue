<template>
    <section id="active">
        <div v-if="forums.length > 0">
            <div class="most-active">
                <h1>Active Forums</h1>
            </div>
            <div class="forum-slider">
                <swiper ref="swiper" class="swiper-wrapper" :slidesPerView="2" :spaceBetween="40" :breakpoints="{
                    '640': { slidesPerView: 1, spaceBetween: 20 },
                    '768': { slidesPerView: 2, spaceBetween: 30 },
                    '1024': { slidesPerView: 2, spaceBetween: 10 }
                }" :pagination="{ type: 'progressbar', }" :modules="modules">
                    <swiper-slide v-for="(forum) in forums.slice(0, 6)" :key="forum.id" class="active-forum">
                        <forum-snippet :forum="forum"></forum-snippet>
                    </swiper-slide>
                </swiper>
            </div>
        </div>
    </section>
</template>

<script>
import ForumSnippet from '../components/ForumSnippet.vue';
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/css';
import 'swiper/css/pagination';
import { Pagination } from 'swiper/modules';

export default {
    props: ['forums'],
    components: { ForumSnippet, Swiper, SwiperSlide },
    name: 'Active',
    setup() {
        return {
            modules: [Pagination],
        };
    }
};
</script>

<style scoped>
#active {
    display: flex;
    flex-direction: column;
    max-width: 1300px;
    margin: 0 auto;
    border: 4px solid var(--nero);
    background: radial-gradient(circle, var(--nero) 75%, rgba(0, 0, 0, 0.389));
}

.most-active {
    text-align: center;
    border-bottom: 4px solid transparent;
    border-image: linear-gradient(to right, var(--nero), rgb(24, 24, 24), rgb(23, 23, 23), rgb(22, 22, 22), rgb(25, 25, 25), rgb(25, 25, 25), var(--nero));
    border-image-slice: 1;
}

.active-forum {
    display: flex;
    justify-content: center;
    max-width: 100%;
    flex-grow: 2;
}

.swiper-slide {
    height: auto;
    flex-shrink: 0;
}

</style>
