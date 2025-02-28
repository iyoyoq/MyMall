<template>
  <div>
      <a-layout>
      <a-layout-header style="padding: 0 20px; display: flex; align-items: center; ">
        <div class="company-logo">
          <div class="company-name">{{ companyName }}</div>
        </div>
        <div style="width: 100%">
          <a-menu mode="horizontal" :default-collapsed=false :default-selected-keys="['0']">
            <a-menu-item key="0">商城首页</a-menu-item>
            <a-menu-item key="1">我的收藏</a-menu-item>
            <a-menu-item key="2">购物车</a-menu-item>
            <a-menu-item key="3">订单管理</a-menu-item>
            <a-menu-item key="4">地址管理</a-menu-item>
            <a-menu-item key="5">个人中心</a-menu-item>
          </a-menu>
        </div>
      </a-layout-header>
      <a-layout style="padding: 18px 16px;">
        <a-layout-content>
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  setup () {
    const router = useRouter()
    const companyName = ref('骑虎网络技术有限公司')

    const onClickMenuItem = (key) => {
      const routeMap = {
        '0': '/',
        '1': '/favorites',
        '2': '/cart',
        '3': '/orders',
        '4': '/address',
        '5': '/profile',
      }
      router.push(routeMap[key])
    }

    return {
      companyName,
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
