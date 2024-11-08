<template>
    <div class="post-search-button">
      <input v-model="keyword" type="text" placeholder="search" class="search-input"/>
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
  color: var(--primary);
  border-radius: 5px; /* Rounded corners */
  padding: 1px 8px; /* Padding for size */
  font-size: 14px; /* Font size */
  cursor: pointer; /* Pointer cursor on hover */
  transition: color .3s, background-color 0.3s; /* Smooth transition */
}
.btn-post-search:hover{
  color: white;
  background-color: var(--nero);
  
}

.post-search-button{
  display: flex;
  gap: 10px;
  align-items: flex-end;
}
</style>
  