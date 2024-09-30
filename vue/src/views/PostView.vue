<template>
  <div class="posts">
    <div v-for="post in posts" :key="post.id">
        <header>
            <img src="" alt="user-logo"/>   <!--In case we want to do this, I'm just copying reddit because I can't think straight-->
            <h1>{{ post.name }}</h1>
            <p> {{ post.creator.name }} </p>
        </header>
        <section>
            <img v-if="post.image" :src="post.image"/>
            <p>{{ post.body }}</p>
        </section>
    </div>
  </div>
</template>

<script>
import service from '../services/PostService'

export default {
    data(){
        return {
            posts: [],
            forumId: 0,
            filter: "",
            error: ""
        }  
    },
    created(){
        this.forumId = this.$route.param.forum;
        if(this.filter === ""){
            // if I had multiple arguements in my call back. Let's say I cerate a function that takes res and filter
            //.then((res, filter) => {}) like that because you have 2 arguements in the arguements list
            // nope, that is just a style choice
            service.getPostsByForum(forum)
                .then(res => {
                    this.posts = res.data;
                })
                .catch(err => {
                    this.error = this.err;
                })
        }
        else if(this.filter === "recent"){
            service.getRecentPostByForum(forum)
                .then(res => this.posts = res.data)
        }
        else if (this.filter === "popularity"){
            service.getPopularPostByForum(forum)
                .then(res => this.posts = res.data)
        }
    }
}
</script>

<style>

</style>