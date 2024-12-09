<!-- ForumPosts.vue -->
<template>
    <section id="trending">
        <div v-if="posts.length > 0">
            <div v-if="isTabletOrDesktop" class="trending-now">
                <div id="trending-now">
                    <h1>Posts</h1>
                </div>
                <div class="body">
                    <div v-if="posts.length > 2">
                        <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                            :centeredSlides="false" :spaceBetween="10" :slidesPerView="3" :breakpoints="{
                                '1': { slidesPerView: 1, spaceBetween: 10 },
                                '1000': { slidesPerView: 2, spaceBetween: 100 },
                                '1324': { slidesPerView: 3, spaceBetween: 100 }
                            }"
                            :coverflowEffect="{ rotate: 40, stretch: 80, depth: 140, modifier: 1, slideShadows: false, }"
                            :pagination="{ type: 'progressbar' }" :modules="modules">
                            <swiper-slide v-for="(post) in posts" :key="post.id" class="trending-post">
                                <post-snippet :post="post"></post-snippet>
                            </swiper-slide>
                        </swiper>
                    </div>
                    <div v-else-if="posts.length === 2" class="forums-grid ">
                        <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                            :centeredSlides="false" :spaceBetween="10" :slidesPerView="1"
                            :coverflowEffect="{ rotate: 40, stretch: 80, depth: 140, modifier: 1, slideShadows: false, }"
                            :pagination="{ type: 'progressbar' }" :modules="modules">
                            <swiper-slide v-for="(post) in posts" :key="post.id" class="trending-post">
                                <post-snippet :post="post"></post-snippet>
                            </swiper-slide>
                        </swiper>
                    </div>
                    <div v-else-if="posts.length === 1" class="forum-results-container">
                        <!-- Display posts based on search or default -->
                        <div v-for="(post) in posts" :key="post.id" class="trending-results" >
                            <post-snippet :post="post"></post-snippet>
                        </div>
                    </div>
                    <div v-else>
                        <p>No posts available.</p>
                    </div>
                </div>
            </div>
            <div v-else class="forum-results-container">
                <div class="trending-results">
                    <!-- Display posts based on search or default -->
                    <div v-for="(post) in posts" :key="post.id">
                        <post-snippet :post="post"></post-snippet>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import { Swiper, SwiperSlide } from 'swiper/vue';
import { EffectCoverflow, Pagination } from 'swiper/modules';
import { mapState, mapMutations } from 'vuex';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import PostSnippet from './PostSnippet.vue';
import { ref, onMounted, onUnmounted } from 'vue';

export default {
    props: ['posts', 'getTimeElapsed'],
    components: { Swiper, SwiperSlide, PostSnippet },
    name: 'ForumPosts',
    setup() {
        const modules = [Pagination, EffectCoverflow];
        const isTabletOrDesktop = ref(window.innerWidth >= 840);

        // Listener to update the screen size dynamically
        const updateScreenSize = () => {
            isTabletOrDesktop.value = window.innerWidth >= 840;
        };
        onMounted(() => {
            window.addEventListener('resize', updateScreenSize);
        });

        onUnmounted(() => {
            window.removeEventListener('resize', updateScreenSize);
        });

        return {
            modules,
            isTabletOrDesktop,
        };
    },
    computed: {
        ...mapState(['currentPage']),
    },
    methods: {
        ...mapMutations(['setCurrentPage']),
        handleSlideChange(swiper) {
            this.setCurrentPage(swiper.realIndex + 1);
        }

        // getSlideWidthStyle(length) {
        //     if (length === 1) {
        //         return { width: '100%' }; 
        //     } else if (length === 2) {
        //         return { width: '50%' }; 
        //     }
        //     return { width: 'auto' };
        // },
    }
};
</script>

<style></style>