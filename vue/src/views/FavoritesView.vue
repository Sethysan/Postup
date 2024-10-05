<template>
  
</template>

<script>
import service from '../services/ForumService';

export default {
    data(){
        favorites: []
    },
    created(){
        service.getFavoriteForums()
            .then(res => this.favorties = res.data)
            .catch(err => alert("error"))
    }
}
</script>

<style>

</style>