<template>
    <div>
      <input v-model="keyword" type="text" placeholder="search" />
      <button type="button" :onclick="search">Search</button>
    </div>
  </template>
  
  <script>
  export default {
    props: ['forumId'],
    data() {
      return {
        keyword: '',
      };
    },
    methods: {
      search() {
        this.$router.push({ name: 'forum-post-search', params: { forumId: this.forumId }, query: { keyword: this.keyword } });
      }
    }
  };
  </script>
  