<template>
    <div class="status">
      <div v-if="isLoading">
        <p>Loading . . .</p>
        <page-loader />
      </div>
      <div v-else-if="hasError">
        <p>{{ errorMessage }}</p>
      </div>
      <div v-else>
                <slot></slot>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      isLoading: Boolean,
      hasError: Boolean,
      errorMessage: String
    }
  }
  </script>