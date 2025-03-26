<template>
  <div></div>
</template>

<script>
import router from '@/router'

export default {
  beforeRouteEnter (to, from, next) {
    next(() => {
      router.replace(from.fullPath)
    })
  },
  setup () {},
}
</script>
