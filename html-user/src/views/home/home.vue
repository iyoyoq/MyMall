<template>
  <div>
    <a-layout>
      <a-layout-header style="padding: 0 20px; ">
        <div style="padding: 0 20px; display: flex; align-items: center;  justify-content: center; ">
          <div class="company-logo">
            <div class="company-name">{{ companyName }}</div>
          </div>
          <div style="width: 720px">
            <a-menu mode="horizontal" :default-selected-keys="[activeKey]">
              <a-menu-item key="0" @click="onClickMenuItem('0')">商城首页</a-menu-item>
              <a-menu-item key="1" @click="onClickMenuItem('1')">我的收藏</a-menu-item>
              <a-menu-item key="2" @click="onClickMenuItem('2')">购物车</a-menu-item>
              <a-menu-item key="3" @click="onClickMenuItem('3')">订单管理</a-menu-item>
              <a-menu-item key="4" @click="onClickMenuItem('4')">地址管理</a-menu-item>
              <a-menu-item key="5" @click="onClickMenuItem('5')">个人中心</a-menu-item>
            </a-menu>
          </div>
        </div>
      </a-layout-header>
      <a-layout-content style="min-height: 100vh; display: flex; justify-content: center">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'

export default defineComponent({
  setup () {
    const router = useRouter()
    const route = useRoute()
    const companyName = ref('骑虎网络技术有限公司')
    const activeKey = ref('0')

    // 路由路径和菜单键值的映射
    const pathKeyMap = {
      '/products': '0',
      '/favorites': '1',
      '/cart': '2',
      '/orders': '3',
      '/address': '4',
      '/profile': '5',
    }

    // 菜单键值和路由路径的映射
    const keyPathMap = {
      '0': '/products',
      '1': '/favorites',
      '2': '/cart',
      '3': '/orders',
      '4': '/address',
      '5': '/profile',
    }

    // 根据当前路由设置活动菜单项
    const setActiveKeyFromRoute = () => {
      activeKey.value = pathKeyMap[route.path] || '0'
    }

    // 监听路由变化
    watch(() => route.path, setActiveKeyFromRoute)

    // 组件挂载时设置初始活动菜单项
    onMounted(() => {
      setActiveKeyFromRoute()
    })

    const onClickMenuItem = (key) => {
      router.push(keyPathMap[key])
    }

    return {
      companyName,
      activeKey,
      onClickMenuItem,
    }
  },
})
</script>

<style scoped>
.company-logo {
  display: flex;
  align-items: center;
  height: 100%;
  min-width: 180px;
}

.company-name {
  color: #000;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  padding: 8px 14px;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.company-name:hover {
  background-color: rgba(0, 0, 0, 0.06);
}


</style>
