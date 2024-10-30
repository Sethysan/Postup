<template>
  <div class="posts">
    
    <div v-for="post in posts" :key="post.id" class="post-list">
      <div v-if="post">
        <router-link :to="{ name: 'post', params: { post: post.id } }" class="post-link">
          <header>
            <div class="post-meta">
              <div class="user-image-frame">
                <img v-if="post.creator_image" :src="post.creator_image" class="user-image" />
                <img v-else src="/images/avatars/no-image.jpg" class="user-image"/>
              </div>
              <p class="post-author">{{ post.creator_username }} </p>
              <p class="post-time">• {{ getTimeElapsed(post.timeOfCreation) }}</p>
            </div>
              <h2 class="post-list-title">{{ post.title }}</h2>
          </header>

          <section>
            <img v-if="post.image" :src="post.image" class="post-image" />
            <!-- <p> {{ post.description }} </p> -->
          </section>
        </router-link>
        <button v-if="post.creator_username === user" class="btn btn-delete deletePost"
          @click="deletePost(post.id)">Delete</button>
      </div>
      <div v-else>
        <p>No post data available.</p>
      </div>
    </div>
  </div>
</template>
<script>
// import Post from '../components/Post.vue';
import PostService from '../services/PostService';
import dayjs from 'dayjs';

export default {
  props: {
    posts: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      filter: "",
      user: this.$store.getters.username,
    }
  },
  methods: {
    deletePost(postId) {
      if (
        confirm("Are you sure you want to delete this post? This action cannot be undone.")
      ) {
        PostService.deletePost(postId)
          .then(response => {
            this.$store.commit('SET_NOTIFICATION', `Post ${postId} was deleted.`);
            this.$router.push({ name: 'forums' });
          })
          .catch(error => {
            if (error.response) {
              if (error.response.status === 404) {
                this.$store.commit('SET_NOTIFICATION',
                  "Error: Post " + postId + " was not found. This post may have been deleted or you have entered an invalid post ID.");
                this.$router.push({ name: 'forum' });
              } else {
                this.$store.commit('SET_NOTIFICATION',
                  "Error getting post " + postId + ". Response received was '" + error.response.statusText + "'.");
              }
            } else if (error.request) {
              this.$store.commit('SET_NOTIFICATION', "Error getting post. Server could not be reached.");
            } else {
              this.$store.commit('SET_NOTIFICATION', "Error getting post. Request could not be created.");
            }
          });
      }
    },
    getTimeElapsed(timeOfCreation) {
      return dayjs(timeOfCreation).fromNow();
    }
  }
};
</script>
<style>
.place-holder {
  max-width: 15%;
}

.sort {
  margin-bottom: 15px;
  margin-right: 10px;
}

.post-image {
  width: 100%;
  background-color: rgb(0, 0, 0);
  max-height: 400px;
  object-fit: contain;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.post-list-meta {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 0.875rem;
}

.post-author {
  font-size: 1.25rem;
}

.post-time {
  color: rgb(107, 105, 105);
}

.post-list-title {
  margin-bottom: 16px;
  margin-left: 2vw;
  margin-right: 0px;
  margin-top: 0px;
  color: rgba(0, 0, 0, 0.767);
}
</style>