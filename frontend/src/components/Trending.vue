<template>
    <section id="trending" v-if="filteredPosts.length > 0">
        <div v-if="isTabletOrDesktop" class="trending-now">
            <div class="trending-now">
                <h1> Trending Now </h1>
            </div>
            <div class="body">
                <div v-if="filteredPosts.length > 2">
                    <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                        :centeredSlides="false" :spaceBetween="10" :slidesPerView="3" :breakpoints="{
                            '1': { slidesPerView: 1, spaceBetween: 10 },
                            '1000': { slidesPerView: 2, spaceBetween: 100 },
                            '1024': { slidesPerView: 3, spaceBetween: 20 }
                        }"
                        :coverflowEffect="{ rotate: 50, stretch: 80, depth: 140, modifier: 1, slideShadows: false, }"
                        :pagination="{ type: 'progressbar', }" :modules="modules">
                        <swiper-slide v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id" class="trending-post">
                            <post-snippet :post="post"></post-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
                <div v-else-if="filteredPosts.length === 1">
                    <div class="trending-results">
                        <!-- Display posts based on search or default -->
                        <div v-for="(post) in filteredPosts" :key="post.id">
                            <post-snippet :post="post" :style="getSlideWidthStyle(filteredPosts.length)"></post-snippet>
                        </div>
                    </div>
                </div>
                <div v-else-if="filteredPosts.length === 2" class="forums-grid ">
                    <div v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id">
                        <post-snippet :post="post"></post-snippet>
                    </div>
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
                <!-- Display posts based on search or default -->
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


export default {
    props: ['filteredPosts', 'getTimeElapsed'],
    components: { PostSnippet, Swiper, SwiperSlide },
    name: 'Trending',
    setup() {
        const modules = [Pagination, EffectCoverflow];
        const isTabletOrDesktop = ref(window.innerWidth >= 640);

        // Listener to update the screen size dynamically
        const updateScreenSize = () => {
            isTabletOrDesktop.value = window.innerWidth >= 640;
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
        },
        getSlideWidthStyle(length) {
            if (length === 1) {
                return { width: '100%' }; // Single post takes 80% of the wrapper
            } else if (length === 2) {
                return { width: '50%' }; // Two posts take 40% each
            }
            return { width: 'auto' }; // Default for other cases
        },
    },
}
</script>

<style>
#trending {
    display: flex;
    width: auto;
    flex-direction: column;
    max-width: 2100px;
    border: none;
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
    width: 100%;
}

.body {
    width: 100%;
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

@media (min-width: 768px) {
    /* #trending {
        max-width: 90%;
    } */

    .swiper-wrapper {
        flex-direction: row;
    }

    .trending-post {
        width: calc(50% - 20px);
        /* Two posts per row */
    }
}

/* Desktop Breakpoint (1024px and Up) */
@media (min-width: 1024px) {
    /* #trending {
        max-width: 1300px;
        border: 4px solid var(--nero);
    } */

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

    /* .trending-post {
        width: calc(33.33% - 20px); */


}

/* @media(min-width: 1400px){
    #trending {
        max-width: 1700px;
    }
}
@media(min-width: 1700px){
    #trending {
        max-width: 2100px;
    }
} */
</style>
