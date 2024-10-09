<template>
    <div class="admin">
        <h1>Admin</h1>
        <p>Admin page</p>
        <table v-if="users.length > 0">
            <thead>
                <tr>
                    <th>UserName</th>
                    <th>Promote</th>
                    <th>Action</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in users" :key="user.id">
                    <td>{{ user.username }}</td>
                    <td>
                        <button @click="promoteToAdmin(user)">Promote to Admin</button>
                    </td>
                    <td>
                        <button @click="banUser(user)">Ban</button>
                    </td>
                    <td>
                        <button @click="unbanUser(user)">Unban</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <p v-else>No users to promote.</p>
    </div>
</template>

<script>
import AdminService from '@/services/AdminService';
export default {
    data() {
        return {
            users: []
        }
    },
    methods: {
        getUsers() {
            AdminService.getUsers()
                .then(response => {
                    this.users = response.data;
                })
                .catch(error => {
                    this.$store.commit('SET_NOTIFICATION', "Error fetching users.");
                });
        },
        promoteToAdmin(user) {
            if(confirm(`Promote ${user.username} to admin?`)) {
                AdminService.promoteToAdmin(user.id)
                    .then(() => {
                        this.users = this.users.filter(u => u.id !== user.id);
                        this.$store.commit('PROMOTE_USER', user.username);
                    })
                    .catch(error => {
                        this.$store.commit('SET_NOTIFICATION', user.username + " not found.");
                    });
            }
        },
        banUser(user) {
            if(confirm(`Ban ${user.username}?`)) {
                AdminService.banUser(user.id)
                    .then(() => {
                        this.users = this.users.filter(u => u.id !== user.id);
                        this.$store.commit('BAN_USER', user.username);
                    })
                    .catch(error => {
                        this.$store.commit('SET_NOTIFICATION', user.username + " not found.");
                    });
            }
        },
        unbanUser(user) {
            if(confirm(`Unban ${user.username}?`)) {
                AdminService.unbanUser(user.id)
                    .then(() => {
                        this.users = this.users.filter(u => u.id !== user.id);
                        this.$store.commit('UNBAN_USER', user.username);
                    })
                    .catch(error => {
                        this.$store.commit('SET_NOTIFICATION', user.username + " not found.");
                    });
            }
        }
    },
    mounted() {
        this.getUsers();
    }
}
</script>

<style>

</style>