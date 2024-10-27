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
}

.active-forum {
    position: relative;

}

.most-active {
    text-align: center;
}



.swiper-wrapper {
    display: flex;
    align-items: center;
    background-color: var(--nero);
}

.swiper-slide {
    background-color: transparent;
    height: auto;
    flex-shrink: 0;
    width: 100%;
}
</style>
