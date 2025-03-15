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
        <var-tabs v-model:active="active" @change="changeTabs">
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
   <div style="width: 1200px;display: flex;justify-content: center;">
     <router-view></router-view>
   </div>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import { setDarkTheme, setLightTheme } from '@/main.js'
import router from '@/router'  // 添加这行导入

export default {
  setup () {
    // 获取当前路径对应的选项卡下标
    const currentPath = router.currentRoute.value.path
    const pathKeyMap = {
      '/products': '0',
      '/favorites': '1',
      '/cart': '2',
      '/orders': '3',
      '/address': '4',
      '/profile': '5',
    }

    const initialActive = Number(pathKeyMap[currentPath] || '0')

    console.log(pathKeyMap[currentPath])
    const active = ref(initialActive)

    const drag = ref(false)
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
    changeTabs(){
      const path = this.keyPathMap[this.active.toString()]
       router.push(path)  // 使用导入的 router
    },
    updateActiveTab() {
      const currentPath = router.currentRoute.value.path
      const activeIndex = this.pathKeyMap[currentPath]
      this.active = Number(activeIndex)
    }
  },

  mounted () {
    this.updateActiveTab()
  },

  watch: {
    '$route'(to) {
      this.updateActiveTab()
    }
  },
}
</script>

<style scoped>


</style>
