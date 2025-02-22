<template>
  <a-layout class="layout-demo">
    <a-layout-sider
        hide-trigger
        collapsible
        :collapsed="collapsed"
    >
      <div class="logo"/>
      <a-menu
          :defaultOpenKeys="['1']"
          :defaultSelectedKeys="['0']"
          :style="{ width: '100%' }"
          @menuItemClick="onClickMenuItem"
      >
        <a-menu-item key="0">
          个人中心
        </a-menu-item>
        
        <a-menu-item key="1">
          商品分类
        </a-menu-item>

        <a-menu-item key="2">
          我的收藏
        </a-menu-item>

        <a-menu-item key="3">
          购物车
        </a-menu-item>

        <a-sub-menu key="4">
          <template #title>
            <span>我的订单</span>
          </template>
          <a-menu-item key="4_1">全部订单</a-menu-item>
          <a-menu-item key="4_2">待发货</a-menu-item>
          <a-menu-item key="4_3">待收货</a-menu-item>
          <a-menu-item key="4_4">已完成</a-menu-item>
        </a-sub-menu>

        <a-menu-item key="5">
          地址管理
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
      <a-layout style="padding: 0 24px;">
        <a-breadcrumb :style="{ margin: '16px 0' }">
          <a-breadcrumb-item>Home</a-breadcrumb-item>
          <a-breadcrumb-item>List</a-breadcrumb-item>
          <a-breadcrumb-item>App</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout-content>Content</a-layout-content>
        <a-layout-footer>Footer</a-layout-footer>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
import { defineComponent, ref } from 'vue'
import { Message } from '@arco-design/web-vue'
import {
  IconCaretRight,
  IconCaretLeft,
} from '@arco-design/web-vue/es/icon'

export default defineComponent({
  components: {
    IconCaretRight,
    IconCaretLeft,
  },
  setup () {
    const collapsed = ref(false)
    const onCollapse = () => {
      collapsed.value = !collapsed.value
    }
    return {
      collapsed,
      onCollapse,
      onClickMenuItem (key) {
        Message.info({ content: `You select ${key}`, showIcon: true })
      },
    }
  },
})
</script>
<style scoped>
.layout-demo {
  height: 100vh;
  background: var(--color-fill-2);
  border: 1px solid var(--color-border);
}

.layout-demo :deep(.arco-layout-sider) .logo {
  height: 32px;
  margin: 12px 8px;
  background: rgba(255, 255, 255, 0.2);
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
  display: flex;
  flex-direction: column;
  justify-content: center;
  color: var(--color-white);
  font-size: 16px;
  font-stretch: condensed;
  text-align: center;
}
</style>
