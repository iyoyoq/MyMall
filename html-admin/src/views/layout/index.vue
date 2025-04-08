<template>
  <div style="display: flex;">
    <div class="menu">
      <a-menu
          :style="{ width: '200px', height: '100%' }"
          :default-open-keys="['0']"
          :default-selected-keys="['0_1']"
          show-collapse-button
      >
        <a-menu-item key="0_0_0">后台总览</a-menu-item>
        <a-sub-menu key="0">
          <template #title>商品管理</template>
          <a-menu-item key="0_0">商品管理</a-menu-item>
          <a-menu-item key="0_1">商品评论</a-menu-item>
          <a-menu-item key="0_2">商品分类</a-menu-item>
        </a-sub-menu>
        <a-menu-item key="1_1">用户管理</a-menu-item>
        <a-sub-menu key="2">
          <template #title>Navigation 3</template>
          <a-menu-item-group title="Menu Group 1">
            <a-menu-item key="2_0">Menu 1</a-menu-item>
            <a-menu-item key="2_1">Menu 2</a-menu-item>
          </a-menu-item-group>
          <a-menu-item-group title="Menu Group 2">
            <a-menu-item key="2_2">Menu 3</a-menu-item>
            <a-menu-item key="2_3">Menu 4</a-menu-item>
          </a-menu-item-group>
        </a-sub-menu>
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


export default {
  components: {},
}
</script>
<style scoped>
.menu {
  box-sizing: border-box;
  max-width: 200px;
  height: 100vh;
  padding: 50px 4px 0 4px;
  background-color: var(--color-bg-1)
}
</style>
