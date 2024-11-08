<template>
    <div class="admin">
        <h1 class='header'>Admin Access </h1>
        <input type="text" v-model="searchQuery" placeholder="Search users..." class="search-input" />

        <table v-if="filteredUsers.length > 0">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Promote</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in filteredUsers" :key="user.id" class="users">
                    <td>{{ user.username }}</td>
                    <td>
                        <button class="btn-admin" @click="promoteToAdmin(user)">Promote to Admin</button>
                    </td>
                    <td>
                        <button class="btn-admin" v-if="!user.isBanned" @click="banUser(user)">BAN</button>
                        <button class="btn-admin" v-else @click="unbanUser(user)">UNBAN</button>
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
            users: [],
            searchQuery: '',
        }
    },
    computed: {
        filteredUsers() {
            return this.users.filter(user => {
                return user.username && user.username.toLowerCase().includes(this.searchQuery.toLowerCase());
            });
        }
    },
    methods: {
        getUsers() {
            AdminService.getUsers()
                .then(response => {
                    console.log(response.data);
                    this.users = response.data.map(user => ({
                        ...user,
                        isBanned: user.role === 'ROLE_BANNED'
                    }))
                        .sort((a, b) => a.username.localeCompare(b.username)); //sort alphabetically
                })
                .catch(error => {
                    this.$store.commit('SET_NOTIFICATION', "Error fetching users.", error.response.data.message);
                });
        },
        promoteToAdmin(user) {
            if (confirm(`Promote ${user.username} to admin?`)) {
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
            if (confirm(`Ban ${user.username}?`)) {
                AdminService.banUser(user.id)
                    .then(() => {
                        user.isBanned = true;
                        this.$store.commit('BAN_USER', user.username);
                    })
                    .catch(error => {
                        this.$store.commit('SET_NOTIFICATION', user.username + " not found.");
                    });
            }
        },
        unbanUser(user) {
            if (confirm(`Unban ${user.username}?`)) {
                AdminService.unbanUser(user.id)
                    .then(() => {
                        user.isBanned = false;
                        this.$store.commit('UNBAN_USER', user.username);
                    })
                    .catch(error => {
                        this.$store.commit('SET_NOTIFICATION', user.username + " not found.");
                    });
            }
        },
        banOrUnban(user) {
            if (user.role == 'ROLE_BANNED') {
                this.unban = true;
            } else {
                this.unban = false;
            }
        }
    },
    mounted() {
        this.getUsers();
    }
}
</script>

<style>

.header {
    margin-bottom: 1.5rem;
}

.search-input {
    background-color: #888;
    color: black;
    font-weight: bolder;
}

input::placeholder {
    color: black;
}

.users {
    cursor: default;
}

.admin {
    color: #888;
    text-align: center;
}

h1 {
    font-size: 2rem;
    margin-bottom: 10px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    border: 2px solid var(--primary);
}

th,
td {
    padding: 10px;
    text-align: left;
    border-bottom: 1px solid var(--primary);
}

th {
    font-size: 1.1rem;
    color: var(--blue);
}

td:first-child {
    font-weight: bold;
}

.btn-admin {
    background-color: grey;
    /* Primary color */
    color: var(--primary);
    /* Text color */
    border: none;
    /* Remove default border */
    border-radius: 5px;
    /* Rounded corners */
    padding: 5px 15px;
    /* Padding for size */
    font-size: 14px;
    /* Font size */
    cursor: pointer;
    /* Pointer cursor on hover */
    transition: background-color 0.3s;
    /* Smooth transition */
}

.btn-admin:hover {
    background-color: #555;
    /* Darker grey on hover */
}

/* Media Queries for Responsive Design */
@media (max-width: 1024px) {
    .admin {
        padding: 15px;
    }

    h1 {
        font-size: 1.8rem;
    }

    .btn-admin {
        font-size: 13px;
        padding: 5px 10px;
    }
}

@media (max-width: 768px) {
    .admin {
        padding: 10px;
    }

    h1 {
        font-size: 1.5rem;
    }

    table {
        font-size: 0.9rem;
    }

    .btn-admin {
        font-size: 12px;
        padding: 4px 8px;
    }
}

@media (max-width: 480px) {
    .admin {
        padding: 5px;
    }

    h1 {
        font-size: 1.2rem;
    }

    table {
        font-size: 0.8rem;
    }

    .btn-admin {
        font-size: 11px;
        padding: 3px 6px;
    }

    th,
    td {
        padding: 5px;
    }
}
</style>