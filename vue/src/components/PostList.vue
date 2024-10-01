<template>
    <div class="posts">
        <div v-if="this.forumId">
        <select name="filter" v-model="this.filter">
            <option value="" v-if="!filter">Sort</option>
            <option value="recent">Most Recent</option>
            <option value="popularity">Most Popular</option>
        </select>
        <button :onclick="filterPosts">Sort</button>
    </div>
      <p v-if="error">Failed To Load Posts</p>
      <p v-if="loading">Loading</p>
      <div v-if="!loading && posts.length==0">
        <p >Wow, such empty</p>
        <img src="" alt="cute animated picture of shiba inu, such as Reddit"/>
    </div>
      <div v-for="post in posts" :key="post.id">
          <header>
              <img src="" alt="user-logo"/> 
              <h1>{{ post.description }}</h1>
              <p> {{ post.creator_username }} </p>
          </header>
          <section>
              <img v-if="post.image" :src="post.image"/>
              <p> place holder for a post body? </p>
          </section>
      </div>
    </div>
  </template>
  
  <script>
  import service from '../services/PostService'
  
  export default {
    props: ['forum'],
      data(){
          return {
              posts: [],
              forumId: 0,
              filter: "",
              loading: true,
              error: ""
          }  
      },
      created(){
          this.forumId = this.forum ? this.forum : this.$route.params.forum;
          if(this.forumId){
          service.getForumPosts(this.forumId, this.filter)
            .then(res => {
                this.posts = res.data;
            })
            .catch(err => {
                this.error = err.response;
            })
            this.loading = false;
        }
        else {
            service.getPopularPosts()
                .then(res => {
                    this.posts = res.data;
                })
                .catch(err => {
                    this.error = err.reesponse;
                })
                this.loading = false;
        }
      },
      methods: {
        filterPosts(){
            if(this.filter == "popularity"){
                this.filterByPopularity()
            }
            else {
                this.filterPostsByRecent()
            }
        },
        filterPostsByRecent(){
            this.posts = [...this.posts].sort((a, b) => b.id - a.id);
        },
        filterByPopularity(){
            this.posts = [...this.posts].sort((a, b) => (b.upvotes - b.downvotes) - (a.upvotes - a.downvotes))
        }
      }
  }
  </script>
  
  <style>
  
  </style>