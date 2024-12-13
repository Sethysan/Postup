<template>
    <div id="preloader" v-if="loading"></div>
    <section v-else id="trending" v-if="filteredPosts.length > 0">
        <div v-if="isTabletOrDesktop" class="trending-now">
            <div class="trending-now">
                <h1>Trending Now</h1>
            </div>
            <div class="body">
                <div v-if="filteredPosts.length > 2 && !loading">
                    <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                        :centeredSlides="false" :spaceBetween="10" :slidesPerView="3" :breakpoints="{
                            '1': { slidesPerView: 1, spaceBetween: 10 },
                            '1000': { slidesPerView: 2, spaceBetween: 100 },
                            '1324': { slidesPerView: 3, spaceBetween: 100 }
                        }"
                        :coverflowEffect="{ rotate: 30, stretch: 100, depth: 200, modifier: 1, slideShadows: false, }"
                        :pagination="{ type: 'progressbar' }" :modules="modules">
                        <swiper-slide v-for="(post) in filteredPosts" :key="post.id" class="trending-post">
                            <post-snippet :post="post"></post-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
                <div v-else-if="filteredPosts.length <= 2" v-if="!loading" class="forums-grid ">
                    <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                        :centeredSlides="false" :spaceBetween="10" :slidesPerView="1"
                        :coverflowEffect="{ rotate: 40, stretch: 80, depth: 140, modifier: 1, slideShadows: false, }"
                        :pagination="{ type: 'progressbar' }" :modules="modules">
                        <swiper-slide v-for="(post) in filteredPosts" :key="post.id" class="trending-post">
                            <post-snippet :post="post"></post-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
                <div v-else>
                    <p>No posts available.</p>
                </div>
            </div>
        </div>
        <!-- Display trending-results-container for smaller screens -->
        <div v-else class="forum-results-container">
            <div class="trending-results">
                <h1> Trending Now </h1>
                <div v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id">
                    <post-snippet :post="post"></post-snippet>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import PostSnippet from '../components/PostSnippet.vue';
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination } from 'swiper/modules';
import { mapState, mapMutations } from 'vuex';
import { ref, onMounted, onUnmounted } from 'vue';
import { nextTick } from 'vue';

export default {
    props: ['filteredPosts', 'getTimeElapsed'],
    components: { PostSnippet, Swiper, SwiperSlide },
    name: 'Trending',
    setup() {
        const modules = [Pagination, EffectCoverflow];
        const isTabletOrDesktop = ref(window.innerWidth >= 840);
        const swiperRef = ref(null);
        const loading = ref(true);

        // Listener to update the screen size dynamically
        const updateScreenSize = () => {
            isTabletOrDesktop.value = window.innerWidth >= 840;
        };

        onMounted(() => {
            window.addEventListener('resize', updateScreenSize);
            loading.value = false; // Hide preloader
        });

        onUnmounted(() => {
            window.removeEventListener('resize', updateScreenSize);
        });

        return {
            modules,
            isTabletOrDesktop,
            swiperRef,
            loading,
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
    },
}
</script>

<style>
#trending {
    display: flex;
    width: auto;
    flex-direction: column;
    border: 4px solid var(--nero);
    background: radial-gradient(circle, var(--nero) 75%, rgba(0, 0, 0, 0.5));
}

.trending-now {
    text-align: center;
    border-bottom: 4px solid transparent;
    border-image: linear-gradient(to right, var(--nero), rgb(24, 24, 24), rgb(23, 23, 23), rgb(22, 22, 22), rgb(25, 25, 25), rgb(25, 25, 25), var(--nero));
    border-image-slice: 1;
}

.swiper-wrapper {
    display: flex;
    flex-direction: column;
    /* Stack items vertically */
    align-items: center;
    padding: 1.5% 0;
    max-width: 95vw;
}

.trending-post {
    display: flex;
    justify-content: center;
    max-width: 100%;
    flex-grow: 0;
    /* margin: 10px 0; */
}

.swiper-slide {
    height: auto;
    flex-shrink: 0;
    width: 95vw;
}

.body {
    max-width: 100%;
}

.swiper-pagination-progressbar .swiper-pagination-progressbar-fill {
    background: #e50914 !important;
    left: 0;
    bottom: 0 !important;
    transform-origin: left bottom !important
}

.swiper-horizontal>.swiper-pagination-progressbar,
.swiper-pagination-progressbar.swiper-pagination-horizontal {
    bottom: 0 !important;
    top: auto !important;
}

.trending-results {
    max-height: 100vh;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    overflow-x: hidden;
    overflow-y: auto;
}

@media (min-width: 840px) {

    .swiper-wrapper {
        flex-direction: row;
    }

    .trending-post {
        width: calc(50% - 20px);

    }
}

/* Desktop Breakpoint (1000px and Up) */
@media (min-width: 1000px) {

    .trending-now {
        font-size: 26px;
        border-bottom: 4px solid transparent;
        border-image: linear-gradient(to right,
                var(--nero),
                rgb(24, 24, 24),
                rgb(23, 23, 23),
                rgb(22, 22, 22),
                rgb(25, 25, 25),
                var(--nero));
        border-image-slice: 1;
    }
}

@media (max-width: 840px) {
    .trending-results {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 2fr));
        max-height: 70vh;
        overflow-x: hidden;
        overflow-y: auto;
    }
}
</style>
