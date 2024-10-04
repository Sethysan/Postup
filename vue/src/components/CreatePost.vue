<template>
  <div class="create-post">
    <h2>Create a Post</h2>

    <!-- Form to create a post -->
    <form @submit.prevent="createPost">
      <div class="form-group">
        <label for="title">Post Title:</label>
        <textarea class="title-input" v-model="postTitle" id="title" required></textarea>
      </div>

      <div class="form-group">
        <label for="description">Post Description:</label>
        <textarea v-model="postDescription" id="description" required></textarea>
      </div>

      <div class="form-group">
        <label for="image">Image URL (optional):</label>
        <input v-model="imageUrl" id="image" type="text" placeholder="http://localhost:9000/images/moon-moon.jpg"
          class="place-holder" />
      </div>
      <div class="button-div">
        <button class="submit-button" type="submit">Submit</button>
        <router-link v-if="forum && forum.topic" :to="{ name: 'forums', query: { topic: forum.topic } }"
          class="forum-link">
          <button class="cancel-button">Cancel</button>
        </router-link>
      </div>
    </form>

  </div>
</template>

<script>
import ForumService from '../services/ForumService.js';
import PostService from '../services/PostService.js';

export default {
  props: ['id'],
  data() {
    return {
      postTitle: '',
      postDescription: '',
      imageUrl: '',
      forum: null // Initialize forum as null
    };
  },
  created() {
    console.log('Forum ID:', this.id);
    ForumService.getForum(this.id)
      .then(res => {
        this.forum = res.data;
      })
      .catch(err => {
        console.error("Error fetching forum:", err);
      });
  },
  methods: {
    createPost() {
      if (!this.id) {
        alert("invalid forum ID.");
        return;
      }
      const newPost = {
        title: this.postTitle,
        description: this.postDescription,
        image: this.imageUrl,
        author: this.$store.state.user.username,
        forumId: this.forumId
      };

      PostService.createPost(this.id, newPost)
        .then(() => {
          console.log("Redirecting to forum with id:", this.id);
          this.$router.push({ name: 'forum', params: { id: String(this.id) } });
        })
        .catch((error) => {
          console.error("Error creating post: ", error);
          alert("Failed to create post." + error.response.status);
        });
    }
  }
};
</script>

<style scoped>
.button-div {
  display: flex;
  justify-content: space-between;
}

.cancel-button,
.submit-button {
  display: inline-block;
  padding: 5px 10px;
  margin-bottom: 15px;
  background-color: #e15d20;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: .5s;
  margin-top: 20px;
  display: inline-block;
}

.cancel-button:hover, .submit-button:hover  {
  background-color: #a33908;
}

.title-input {
  padding-top: 5px;
  width: 100%;
  height: 20px;
}

.create-post {
  margin: 20px;
}

.form-group {
  margin-bottom: 15px;
}

textarea {
  width: 100%;
  height: 100px;
}

button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.place-holder {
  max-width: 30%;
}
</style>