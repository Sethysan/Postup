<template>
    <section id="trending" v-if="filteredPosts.length > 0">
        <div v-if="isTabletOrDesktop" class="trending-now">
            <div class="trending-now">
                <h1> Trending Now </h1>
            </div>
            <div class="body">
                <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                    :centeredSlides="false" :spaceBetween="10" :slidesPerView="3" :breakpoints="{
                        '640': { slidesPerView: 1, spaceBetween: 20 },
                        '768': { slidesPerView: 2, spaceBetween: 20 },
                        '1024': { slidesPerView: 3, spaceBetween: 30 },
                    }" :coverflowEffect="{ rotate: 50, stretch: 0, depth: 100, modifier: 1, slideShadows: true, }"
                    :pagination="{ type: 'progressbar', }"   :modules="modules" v-if="filteredPosts.length > 2">
                    <swiper-slide v-for="(post) in filteredPosts.slice(0, 10)" :key="post.id" class="trending-post">
                        <post-snippet :post="post"></post-snippet>
                    </swiper-slide>
                </swiper>
                <div class="forum-slider" v-else-if="posts.length === 1">
                    <swiper ref="swiper" class="swiper-wrapper" :slidesPerView="1" :spaceBetween="0"
                    :pagination="{ type: 'progressbar' }" :modules="modules">
                        <swiper-slide v-for="(post) in filteredPosts" :key="post.id" class="trending-post">
                            <post-snippet :post="post"></post-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
                <div class="forum-slider" v-else-if="posts.length === 2">
                    <swiper ref="swiper" class="swiper-wrapper" :slidesPerView="2" :spaceBetween="40"
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
        }
    },
}
</script>

<style>
/* #trending {
    display: flex;
    flex-direction: column;
    max-width: 1300px;
    margin: 0 auto;
    border: 4px solid var(--nero);
    background: radial-gradient(circle, var(--nero) 75%, rgba(0, 0, 0, 0.389));
} */

#trending {
    display: flex;
    flex-direction: column;
    max-width: 1600px;
    margin: 0 auto;
    border: none;
    background: radial-gradient(circle, var(--nero) 75%, rgba(0, 0, 0, 0.5));
    padding: 10px;
}

/* .trending-now {
    text-align: center;
    border-bottom: 4px solid transparent;
    border-image: linear-gradient(to right, var(--nero), rgb(24, 24, 24), rgb(23, 23, 23), rgb(22, 22, 22), rgb(25, 25, 25), rgb(25, 25, 25), var(--nero));
    border-image-slice: 1;
} */

.trending-now {
    text-align: center;
    padding: 10px 0;
    border-bottom: 4px solid transparent;
    border-image: linear-gradient(to right, var(--nero), rgb(24, 24, 24), rgb(23, 23, 23), rgb(22, 22, 22), rgb(25, 25, 25), rgb(25, 25, 25), var(--nero));
    border-image-slice: 1;
}

/* .swiper-wrapper {
    display: flex;
    align-items: center;
    width: 94%;
    padding: 20px;
    max-width: 100%;
} */

.swiper-wrapper {
    display: flex;
    flex-direction: column;
    /* Stack items vertically */
    align-items: center;
    
    padding: 10px;
    max-width: 95%;
}

.trending-post {
    display: flex;
    justify-content: center;
    max-width: 100%;
    flex-grow: 2;
    margin: 10px 0;
}

.swiper-slide {
    height: auto;
    flex-shrink: 0;
    width: 100%;
}

.body {
    width: 100%;
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

/* .swiper-pagination-progressbar {
    background: rgba(0, 0, 0, 0.25);
}

.swiper-pagination-progressbar .swiper-pagination-progressbar-fill {
    background: #e50914 !important; 
}*/

.swiper-horizontal>.swiper-pagination-progressbar,
.swiper-pagination-progressbar.swiper-pagination-horizontal {
    bottom: 0 !important;
    top: auto !important;
}

@media (min-width: 768px) {
    #trending {
        max-width: 90%;
        /* Constrain width on tablets */
        padding: 15px;
    }

    .trending-now {
        font-size: 22px;
        /* Slightly larger text */
        padding: 15px 0;
    }

    .swiper-wrapper {
        flex-direction: row;
        /* Align items horizontally */
        padding: 15px;
    }

    .trending-post {
        margin: 2rem;
        width: calc(50% - 20px);
        /* Two posts per row */
    }
}

/* Desktop Breakpoint (1024px and Up) */
@media (min-width: 1024px) {
    #trending {
        max-width: 1200px;
        border: 4px solid var(--nero);
    }

    .trending-now {
        font-size: 26px;
        padding: 20px 0;
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

    .swiper-wrapper {
        padding: 3rem;
    }

    .trending-post {
        width: calc(33.33% - 20px);
        /* Three posts per row */
        margin: 0 10px;
    }
}
</style>
