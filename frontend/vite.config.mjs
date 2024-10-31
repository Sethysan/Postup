
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "src/styles/variables.scss";`,
      },
    },
  },
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
      "vue": "vue/dist/vue.esm-bundler.js", // Explicitly use ESM version of Vue
      'vue3-social-sharing': path.resolve(__dirname, 'node_modules/vue3-social-sharing/dist/index.js'),
    },
  },
  base: './', //added after netlify error
  optimizeDeps: {
    include: ["vue"], // Ensure Vue is pre-bundled by Vite
  },
  build: {
    rollupOptions: {
      output: {
        format: "es",
      },
    },
  },
  define: {
    __VUE_PROD_DEVTOOLS__: false,
    __VUE_OPTIONS_API__: true,
    __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: false,
  },
});
