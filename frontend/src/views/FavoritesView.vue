<template>
    <div v-if="favorites.length < 1 && !isloading">
      <p>Wow, such empty!</p>
      <img src=""/>
    </div>
    <div v-if="isloading">
      <p>loading . . .</p>
    </div>
    <div v-if="error">
      <p>Oops, it looks like this post couldn't load</p>
    </div>
    <div v-for="forum in favorites" :key="forum.id">
        <forum-snippet :forum="forum"></forum-snippet>
    </div>
</template>

<script>
import ForumSnippet from '../components/ForumSnippet.vue';
import service from '../services/ForumService';

export default {
    components: { ForumSnippet },
    data() {
        return {
            favorites: [],
            isloading: true,
            error: false
        }
    },
    created() {
        service.getFavoriteForums()
            .then(res => {
                // Check if the response data is valid
                if (res.data && Array.isArray(res.data)) {
                    this.favorites = res.data;
                    this.isloading = false
                } else {
                    console.error("Unexpected response format:", res);
                    this.error = true
                }
            })
            .catch(err => {
                console.error("Error fetching favorite forums:", err);
                alert("An error occurred while fetching your favorite forums. Please check your connection or try again later.");
            });
    }
}
</script>

<style></style>