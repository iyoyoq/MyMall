import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { vitePluginForArco } from '@arco-plugins/vite-vue'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(), vitePluginForArco({
      style: 'css',
    })],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
})
