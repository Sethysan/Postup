<template>
    <div>
      <input v-model="keyword" type="text" placeholder="search" />
      <button class="btn-post-search" type="button" :onclick="search">Search</button>
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

  <style>
.btn-post-search{
  background-color: grey;
  color: white;
  border-color: black; /* Remove default border */
  border-radius: 5px; /* Rounded corners */
  padding: 2px 8px; /* Padding for size */
  font-size: 14px; /* Font size */
  cursor: pointer; /* Pointer cursor on hover */
  transition: background-color 0.3s; /* Smooth transition */
}
</style>
  