<template>
  <a-layout class="layout-demo">
    <a-layout-sider
        hide-trigger
        collapsible
        :collapsed="collapsed"
    >
      <div class="logo">
        <span class="company-name" v-show="!collapsed">{{ companyName }}</span>
      </div>
      <a-menu
          :selectedKeys="[activeKey]"
          :style="{ width: '100%' }"
          @menuItemClick="onClickMenuItem"
      >
        <a-menu-item key="0">
          <template #icon><IconApps /></template>
          商品首页
        </a-menu-item>

        <a-menu-item key="1">
          <template #icon><IconStar /></template>
          我的收藏
        </a-menu-item>

        <a-menu-item key="2">
          <template #icon><ShoppingCartIcon /></template>
          购物车
        </a-menu-item>

        <a-menu-item key="3">
          <template #icon><IconFile /></template>
          订单管理
        </a-menu-item>

        <a-menu-item key="4">
          <template #icon><IconLocation /></template>
          地址管理
        </a-menu-item>

        <a-menu-item key="5">
          <template #icon><IconUser /></template>
          个人中心
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="padding-left: 20px;">
        <a-button shape="round" @click="onCollapse">
          <IconCaretRight v-if="collapsed"/>
          <IconCaretLeft v-else/>
        </a-button>
      </a-layout-header>
      <a-layout style="padding: 18px 16px;">
        <!--<a-breadcrumb :style="{ margin: '16px 0' }">-->
        <!--  &lt;!&ndash; 移除这三行 &ndash;&gt;-->
        <!--   <a-breadcrumb-item>Home</a-breadcrumb-item> -->
        <!--   <a-breadcrumb-item>List</a-breadcrumb-item> -->
        <!--   <a-breadcrumb-item>App</a-breadcrumb-item> -->
        <!--</a-breadcrumb>-->
        <a-layout-content>
          <router-view></router-view>
        </a-layout-content>
        <!--<a-layout-footer>Footer</a-layout-footer>-->
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
import { defineComponent, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import {
  IconCaretRight,
  IconCaretLeft,
  IconUser,
  IconApps,
  IconStar,
  IconFile,
  IconLocation,
} from '@arco-design/web-vue/es/icon'
import ShoppingCartIcon from '@/components/icons/ShoppingCartIcon.vue'

export default defineComponent({
  components: {
    IconCaretRight,
    IconCaretLeft,
    IconUser,
    IconApps,
    IconStar,
    IconFile,
    IconLocation,
    ShoppingCartIcon,
  },
  setup () {
    const router = useRouter()
    const route = useRoute()
    const collapsed = ref(false)
    const companyName = ref('骑虎网络技术有限公司')
    const activeKey = ref('0')

    // 路径和菜单键值的映射
    const pathKeyMap = {
      '/': '0',
      '/favorites': '1',
      '/cart': '2',
      '/orders': '3',
      '/address': '4',
      '/profile': '5'
    }

    // 根据路径设置活动菜单项
    const setActiveKey = (path) => {
      activeKey.value = pathKeyMap[path] || '0'
    }

    // 监听路由变化
    watch(
      () => route.path,
      (newPath) => {
        setActiveKey(newPath)
      },
      { immediate: true }
    )

    const onCollapse = () => {
      collapsed.value = !collapsed.value
    }

    const onClickMenuItem = (key) => {
      const routeMap = {
        '0': '/',
        '1': '/favorites',
        '2': '/cart',
        '3': '/orders',
        '4': '/address',
        '5': '/profile'
      }
      router.push(routeMap[key])
    }

    return {
      collapsed,
      companyName,
      activeKey,
      onCollapse,
      onClickMenuItem,
    }
  },
})
</script>
<style scoped>
/* 删除 :root 相关的样式定义 */

.layout-demo :deep(.arco-menu-item.arco-menu-selected) {
  background-color: var(--color-primary-light-1);
  color: var(--color-primary-6);
}

.layout-demo :deep(.arco-menu-item:hover) {
  background-color: var(--color-primary-light-1);
  color: var(--color-primary-6);
}

.layout-demo {
  height: 100vh;
  background: var(--color-fill-2);
  border: 0;
}

.layout-demo :deep(.arco-layout-sider) .logo {
  height: 32px;
  margin: 12px 8px;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.layout-demo :deep(.arco-layout-sider-light) .logo {
  background: var(--color-fill-2);
}


.layout-demo :deep(.arco-layout-header) {
  height: 64px;
  line-height: 64px;
  background: var(--color-bg-3);
}

.layout-demo :deep(.arco-layout-footer) {
  height: 48px;
  color: var(--color-text-2);
  font-weight: 400;
  font-size: 14px;
  line-height: 48px;
}

.layout-demo :deep(.arco-layout-content) {
  color: var(--color-text-2);
  font-weight: 400;
  font-size: 14px;
  background: var(--color-bg-3);
}

.layout-demo :deep(.arco-layout-footer),
.layout-demo :deep(.arco-layout-content) {
  color: var(--color-white);
  font-size: 16px;
  font-stretch: condensed;
  text-align: center;
}

.company-name {
  color: var(--color-text-1);
  font-size: 16px;
  font-weight: bold;
  white-space: nowrap;
  transition: opacity 0.2s;
}
</style>
