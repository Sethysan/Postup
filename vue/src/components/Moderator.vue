<template>
  <div class="moderator">
    <h1>Moderator</h1>
    <p>Welcome, Moderator!</p>
    <!-- for loop to list all usernames where role is equal to user -->
    <!-- only show on forums page and if role is mod or admin -->
    <table v-if="canPromote && filteredUsers.length > 0">
        <thead>
            <!-- make the usernames a table <tr>-->
            <tr>
                <th>Username</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="user in filteredUsers" :key="user.id">
                <td>{{ user.username }}</td>
                <!-- also inside loop button for promote to moderator -->
                <!-- will also be a table component <td> -->
                <td>
                    <button @click="promoteUser(user.username)">Promote to Moderator</button>
                </td>
            </tr>
        </tbody>
    </table>
    <p v-else>No users to promote.</p>
  </div>
</template>

<script>
import ModeratorService from '@/services/ModeratorService';
export default {
    props: {
        forumId: {
            type: String,
            required: true
        }
    },
    computed: {
        canPromote() {
            const isModOrAdmin = this.userRole === 'ROLE_MODERATOR' || this.userRole === 'ROLE_ADMIN';
            return isModOrAdmin;
        },
        filteredUsers() {
            return this.users.filter(user => user.authorities.name === 'ROLE_USER');
        }
    },
    methods: {
        promoteUser(userName) {
            userName = this.filteredUsers.username;
            ModeratorService.promoteUser(userName, this.forumId)
                .then(() => {
                    // Update the user's role in the Vuex store
                    this.$store.commit('PROMOTE_USER', userName); //TODO:Make PROMOTE_USER in STORE
                })
                .catch(error => {
                    this.$store.commit('SET_NOTIFICATION', userName + " not found.");
                });
        },
        getUsers() {
            ModeratorService.getUsers()
                .then(response => {
                    this.users = response.data;
                })
                .catch(error => {
                    this.$store.commit('SET_NOTIFICATION', "Error fetching users.");
                });
        }
    },
    data() {
        return {
            users: [],
            userRole: this.$store.getters.role
        }
    },
    mounted() {
        this.getUsers();
    }
    // method to see if in forums path and if role is mod or admin canPromote
    // method to promote user to moderator
}
</script>

<style scoped>

</style>