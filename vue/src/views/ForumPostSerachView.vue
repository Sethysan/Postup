<template>
    <router-link :to="{name: 'forum', params: {id: forumId}}">Back</router-link>
    <div v-if="posts.length < 1 && !isloading">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
    <div v-if="isloadingPost">
      <p>loading . . .</p>
    </div>
    <post-list :posts="posts"></post-list>
</template>

<script>
import PostService from '../services/PostService';
import ForumService from '../services/ForumService';
import PostSearchButton from '../components/PostSearchButton.vue';
import PostList from '../components/PostList.vue';

export default {
  components: { PostSearchButton, PostList},
    data(){
        return {
            forum: {},
            posts: [],
            forumId: 0,
            keyword: "",
            isloading: true
        }
    },
    created(){
        this.forumId = this.$route.params.forumId;
        this.keyword = this.$route.query.keyword;
        ForumService.getForum(this.forumId)
            .then(res => {
                this.forum = res.data;
                PostService.getForumPostByKeyword(this.forumId, this.keyword)
                    .then(res => {
                        this.posts = res.data
                        this.isloading = false
            })
                    .catch(err => err.status)
            })
            .catch(err => alert(err.status))
    },
}
</script>

<style>

</style>