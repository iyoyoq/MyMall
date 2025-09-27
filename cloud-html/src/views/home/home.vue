<template>
  <div>
    <div v-if="$route.path.startsWith('/admin-end')">
      <admin-home></admin-home>
    </div>
    <div v-else>
      <user-home></user-home>
    </div>
  </div>
</template>
<script>
import UserHome from '@/views/home/userHome.vue'
import AdminHome from '@/views/home/adminHome.vue'

export default {
  name: 'home',
  components: { AdminHome, UserHome },
}
</script>



<style scoped>

</style>
