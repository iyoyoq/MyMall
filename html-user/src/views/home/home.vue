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
      <div style="width:700px;" class="nav-container">
        <var-link
          v-for="(path, index) in keyPathMap"
          :key="index"
          :class="['nav-item', { active: currentPath === path }]"
          @click="handleClick(path)"
          :href="`#${path}`"
          underline="none"
        >
          {{ navItems[index] }}
        </var-link>
      </div>
      <var-switch v-model="drag" @change="changeTheme"/>
    </div>
    <div style="display: flex;justify-content: center;min-height: 100vh">
      <div style="width: 1200px">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import { setDarkTheme, setLightTheme } from '@/main.js'
import router from '@/router'  // 添加这行导入

export default {
  setup() {
    const drag = ref(false)
    const currentPath = ref(router.currentRoute.value.path)

    watch(() => router.currentRoute.value.path, (newPath) => {
      currentPath.value = newPath
    })

    return {
      drag,
      currentPath
    }
  },

  data() {
    return {
      companyLink: 'https://www.baidu.com',
      companyName: 'MyMall 在线商城',
      navItems: ['商品列表', '我的收藏', '购物车', '我的订单', '收货地址', '个人中心'],
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
    changeTheme() {
      if(this.drag) {
        setLightTheme()
      } else {
        setDarkTheme()
      }
    },
    handleClick(path) {
      router.push(path)
    }
  }
}
</script>

<style lang="scss" scoped>
.nav-container {
  display: flex;
  gap: 20px;
  justify-content: center;
  align-items: center;

  .nav-item {
    padding: 8px 16px;
    cursor: pointer;
    color: var(--color-text);
    transition: all 0.3s;

    &:hover,
    &.active {
      color: var(--color-primary);
    }
  }
}
</style>
