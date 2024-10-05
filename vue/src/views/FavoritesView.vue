<template>
    <div v-for="forum in favorites" :key="forum.id">
        <forum-snippet :forum="forum"></forum-snippet>
    </div>
</template>

<script>
import ForumSnippet from '../components/ForumSnippet.vue';
import service from '../services/ForumService';

export default {
  components: { ForumSnippet  },
    data(){
        return{
        favorites: []
        }
    },
    created(){
        service.getFavoriteForums()
            .then(res => this.favorites = res.data)
            .catch(err => alert("error"))
    }
}
</script>

<style>

</style>