<template>
  <div class="status">
    <!-- Display the loader if isLoading is true -->
    <div v-if="isLoading">
      <p>Loading . . .</p>
      <page-loader />
    </div>

    <!-- Display the error message if hasError is true -->
    <div v-else-if="hasError">
      <p>{{ errorMessage }}</p>
    </div>

    <!-- Render the content when neither loading nor error is true -->
    <div v-else>
      <slot></slot>
    </div>
  </div>
</template>

<script>
import PageLoader from '../components/PageLoader.vue';
export default {
  components: {PageLoader},
  props: {
    isLoading: Boolean,
    hasError: Boolean,
    errorMessage: String
  }
}
</script>

<style scoped>
.status {
  text-align: center;
  font-weight: 900;
}
</style>