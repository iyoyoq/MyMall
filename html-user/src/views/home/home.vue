<template>
  <div>
    <div style="display: flex; justify-content: center;align-items: center;">
      <var-link
          type="primary"
          :href="companyLink"
          target="_blank"
          underline="none"
          style="padding-right: 20px;"
      >{{ companyName }}
      </var-link>
      <div style="width:700px;">
        <var-tabs v-model:active="active">
          <var-tab>商品列表</var-tab>
          <var-tab>我的收藏</var-tab>
          <var-tab>购物车</var-tab>
          <var-tab>我的订单</var-tab>
          <var-tab>收货地址</var-tab>
          <var-tab>个人中心</var-tab>
        </var-tabs>
      </div>
      <var-switch v-model="drag" @change="changeTheme"/>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import { setDarkTheme, setLightTheme } from '@/main.js'

export default {
  setup () {
    const active = ref(0)

    const drag = ref(false)
    // watch(drag, (val) => {
    //   if (val) {
    //     setLightTheme()
    //   } else {
    //     setDarkTheme()
    //   }
    // })
    return {
      active,
      drag,
    }
  },
  data () {
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
      },
    }
  },

  methods: {
    changeTheme(){
      if(this.drag){
        setLightTheme()
      }else {
        setDarkTheme()
      }
    },
  },

  watch: {},

  mounted () {
  },
}
</script>

<style scoped>


</style>
