<template>
  <div class="user-list">
    <input type="text" v-model="searchQuery" placeholder="Search users..." class="search-input" autofocus />
    <div class="user-list-container">
      <table v-if="filteredUsers.length > 0">
        <thead>
          <tr>
            <th>Username</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in filteredUsers" :key="user.id" class="user-row">
            <td>{{ user.username }}</td>
            <td>
              <slot :user="user"></slot> <!-- Slot for custom actions -->
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else>No users found.</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    users: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      searchQuery: ''
    };
  },
  computed: {
    filteredUsers() {
      return this.users.filter((user) => {
        return (
          user.username &&
          user.username.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      });
    }
  }
};
</script>

<style scoped>
.user-list {
  color: #888;
  text-align: center;
  height: 100vh;
  /* Full view height */
  overflow: hidden;
  /* Hide overflow for the main container */
  display: flex;
  flex-direction: column;
}

.search-input {
  background-color: #888;
  color: black;
  font-weight: bold;
  margin-bottom: 10px;
  padding: 5px;
  width: 90%;
  /* Make the input fit within the container */
  margin: 10px auto;
  /* Center the input horizontally */
}

.user-list-container {
  flex: 1;
  /* Take the remaining space */
  overflow-y: auto;
  /* Make the container scrollable */
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  border: 2px solid var(--orange);
}

th,
td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid var(--orange);
}

th {
  font-size: 1.1rem;
  color: #888;
}

.user-row {
  cursor: default;
}
</style>
