<!-- ForumPosts.vue -->
<template>
    <section id="forum-posts">
        <div v-if="posts.length > 0">
            <div id="posts">
                <h1>Posts</h1>
            </div>
            <div class="body">
                <swiper ref="swiper" class="swiper-wrapper" :effect="'coverflow'" :grabCursor="true"
                    :centeredSlides="false" :spaceBetween="10" :slidesPerView="1" :breakpoints="breakpoints"
                    :coverflowEffect="coverflowEffect" :pagination="{ type: 'progressbar' }" :modules="modules">
                    <swiper-slide v-for="(post) in posts" :key="post.id" class="forum-post">
                        <post-snippet :post="post"></post-snippet>
                    </swiper-slide>
                </swiper>
            </div>
        </div>
    </section>
</template>

<script>
import { Swiper, SwiperSlide } from 'swiper/vue';
import { EffectCoverflow, Pagination } from 'swiper/modules';
import { mapState, mapMutations } from 'vuex';
import dayjs from 'dayjs';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import PostSnippet from './PostSnippet.vue';

export default {
    components: { Swiper, SwiperSlide, PostSnippet },
    props: {
        posts: {
            type: Array,
            required: true
        }
    },
    name: 'ForumPosts',
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
    data() {
        return {
            modules: [Pagination, EffectCoverflow],
            breakpoints: {
                640: {
                    slidesPerView: 1,
                    spaceBetween: 20
                },
                1024: {
                    slidesPerView: 3,
                    spaceBetween: 30
                }
            },
            coverflowEffect: {
                rotate: 50,
                stretch: 0,
                depth: 100,
                modifier: 1,
                slideShadows: true
            }
        };
    }
};
</script>

<style>
#forum-posts {
    display: flex;
    flex-direction: column;
    max-width: 1500px;
    margin: 0 auto;
    background-color: var(--nero);
}

.forum-post {
    box-shadow: 0 15px 30px rgba(0, 0, 0, .9);
}

#posts {
    text-align: center;
}

.body {
    padding: 20px;
}
</style>