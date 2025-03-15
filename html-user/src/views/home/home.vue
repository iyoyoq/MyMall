<template>
  <div>
      home
        <router-view></router-view>

  </div>
</template>

<script>
export default {
  data() {
    return {
      companyLink: 'https://www.baidu.com',  // 公司链接
      companyName: 'MyMall 在线商城',
      activeKey: '0',
      // 路由路径和菜单键值的映射
      pathKeyMap: {
        '/products': '0',
        '/favorites': '1',
        '/cart': '2',
        '/orders': '3',
        '/address': '4',
        '/profile': '5',
      },
      // 菜单键值和路由路径的映射
      keyPathMap: {
        '0': '/products',
        '1': '/favorites',
        '2': '/cart',
        '3': '/orders',
        '4': '/address',
        '5': '/profile',
      }
    }
  },

  methods: {

  },

  watch: {

  },

  mounted() {
  }
}
</script>

<style scoped>



</style>
