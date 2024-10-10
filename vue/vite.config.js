import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  define: {
    '__VUE_PROD_DEVTOOLS__': false,
    '__VUE_OPTIONS_API__': true,
    '__VUE_PROD_HYDRATION_MISMATCH_DETAILS__': false
  }
});
