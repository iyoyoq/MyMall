<template>
  <div style="display: flex;" class="bg-color-gray">
    <div class="menu" >
      <a-menu
          :style="{ width: '192px', height: '100%' }"
          show-collapse-button
          v-model:selected-keys="selectedKeys"
          :default-open-keys="defaultParentMenu"
          @menu-item-click="clickMenu"
      >
        <a-menu-item key="/dashboard">后台总览</a-menu-item>
        <a-sub-menu key="/product">
          <template #title>商品管理</template>
          <a-menu-item key="/product/list">商品管理</a-menu-item>
          <!--<a-menu-item key="/product/comment">商品评论</a-menu-item>-->
          <a-menu-item key="/product/category">商品分类</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="/user">
          <template #title>用户管理</template>
          <a-menu-item key="/user/points">积分管理</a-menu-item>
          <!--<a-menu-item key="/product/category">用户管理</a-menu-item>-->
        </a-sub-menu>
        <a-sub-menu key="/orders">
          <template #title>订单管理</template>
          <a-menu-item key="/orders/orders">订单总览</a-menu-item>
          <!--<a-menu-item key="/product/category">用户管理</a-menu-item>-->
        </a-sub-menu>
        <!--<a-sub-menu key="2">-->
        <!--  <template #title>Navigation 3</template>-->
        <!--  <a-menu-item-group title="Menu Group 1">-->
        <!--    <a-menu-item key="2_0">Menu 1</a-menu-item>-->
        <!--    <a-menu-item key="2_1">Menu 2</a-menu-item>-->
        <!--  </a-menu-item-group>-->
        <!--  <a-menu-item-group title="Menu Group 2">-->
        <!--    <a-menu-item key="2_2">Menu 3</a-menu-item>-->
        <!--    <a-menu-item key="2_3">Menu 4</a-menu-item>-->
        <!--  </a-menu-item-group>-->
        <!--</a-sub-menu>-->
      </a-menu>
    </div>

    <div style="width: 100%;display: flex; justify-content: center">
      <div>
        <router-view/>
      </div>
    </div>
  </div>
</template>
<script>
import router from '@/router/index.js'
import { ref, watch } from 'vue'

export default {
  components: {},
  setup () {
    const currentPath = ref(router.currentRoute.value.path)
    const selectedKeys = ref([currentPath.value])
    const defaultParentMenu = ref([])
    const parts = currentPath.value.split('/').filter(part => part !== '')
    defaultParentMenu.value = parts.length === 2 ? ['/' + parts[0]] : []
    // 监听路由变化
    watch(() => router.currentRoute.value.path, (newPath) => {
      currentPath.value = newPath
      selectedKeys.value = [newPath]
    })
    const clickMenu = (key) => {
      router.push(key)
    }
    return {
      currentPath,  // 当前路径
      defaultParentMenu,  // 展开的父菜单
      selectedKeys, //选择的 菜单
      clickMenu,  // 点击菜单
    }
  },
}
</script>
<style scoped>
.menu {
  box-sizing: border-box;
  max-width: 200px;
  height: 100vh;
  padding: 20px 4px 0 4px;
  background-color:  var(--color-menu-light-bg);
}
</style>
