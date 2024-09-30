<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <router-link v-bind:to="{ name: 'forums' }">Forums</router-link>
  </div>
</template>

<script>
export default {
};
</script>
