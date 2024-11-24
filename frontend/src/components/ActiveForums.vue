<template>
    <section id="active">
        <div v-if="forums.length > 0">
            <div v-if="isTabletOrDesktop" class="most-active">
                <div class="most-active">
                    <h1>Active Forums</h1>
                </div>
                <div class="forum-slider">
                    <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :slidesPerView="3"
                        :spaceBetween="40" :breakpoints="{
                            '640': { slidesPerView: 1, spaceBetween: 10 },
                            '768': { slidesPerView: 2, spaceBetween: 20 },
                            '1024': { slidesPerView: 3, spaceBetween: 20 }
                        }" :coverflowEffect="{ rotate: 50, stretch: 80, depth: 140, modifier: 1, slideShadows: false, }"
                        :pagination="{ type: 'progressbar', }" :modules="modules" v-if="forums.length > 2">
                        <swiper-slide v-for="(forum) in forums.slice(0, 10)" :key="forum.id" class="active-forum">
                            <forum-snippet :forum="forum"></forum-snippet>
                        </swiper-slide>
                    </swiper>
                </div>
            </div>
            <!-- Display forum-results-container for smaller screens -->
            <div v-else class="forum-results-container">
                <h2 v-if="searchDisplayed" class="search-results-header">Search Results</h2>
                <div class="forum-results">
                    <!-- Display forums based on search or default -->
                    <div v-for="(forum) in forums.slice(0, 10)" :key="forum.id">
                        <forum-snippet :forum="forum">
                            <router-link :to="{ name: 'forum', params: { id: forum.id || forum.forum.id } }"
                                class="forum-link">
                                <p class="inline-time">{{ forum.author || forum.forum.author }}</p>
                                <p class="inline-time"> created {{ getTimeElapsed(forum.timeOfCreation ||
                                    forum.forum.timeOfCreation) }}</p>
                                <h2>
                                    <span
                                        v-html="searchDisplayed ? highlightSearchTerm(forum.forum?.topic || forum.topic) : forum.topic"></span>
                                </h2>
                                <p
                                    v-html="searchDisplayed ? highlightSearchTerm(forum.forum?.description || forum.description) : forum.description">
                                </p>
                            </router-link>
                        </forum-snippet>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>

import ForumSnippet from '../components/ForumSnippet.vue';
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination } from 'swiper/modules';
import { mapState, mapMutations } from 'vuex';
import { ref, onMounted, onUnmounted } from 'vue';

export default {
    props: ['forums', 'searchDisplayed', 'searchForums', 'highlightSearchTerm', 'getTimeElapsed'],
    components: { ForumSnippet, Swiper, SwiperSlide },
    name: 'Active',
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

};
</script>

<style>
#active {
    display: flex;
    flex-direction: column;
    width: auto;
    max-width: 2100px;
    margin-top: 8rem;
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

.forum-slider {
    width: 100%;
}
</style>
