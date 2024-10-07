<template>
  <div v-for="message in messages" :key="message.id">
    <p :class="message.sender.username == username ? 'user-message' : 'others-message'">{{ message.message }}</p>
    <p>sent {{ message.receivedOn }}</p>
    <p v-if="message.read">read A CHECK HERE</p>
  </div>
</template>

<script>
export default {
    props: ['messages'],
    data(){
        return {
            username: this.$store.getters.username,
        }
    },
}
</script>

<style>

</style>