<template>
  <div class="moderator">
    <h1>Moderator</h1>
    <p>Welcome, Moderator!</p>
    <!-- for loop to list all usernames where role is equal to user -->
    <!-- make the users a table <tr>-->
    <!-- also inside loop button for promote to moderator -->
    <!-- will also be a table component <td> -->
    <!-- only show on forums page and if role is mod or admin -->
  </div>
</template>

<script>
import ModeratorService from '@/services/ModeratorService';
export default {
    props: {
        users: {
            type: Array,
            required: true
        }
    }
}
</script>

<style scoped>

</style>