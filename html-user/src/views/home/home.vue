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
      <var-switch v-model="showLoginDialog" @change="showLoginDialog = !showLoginDialog"/>
    </div>

    <div style="display: flex;justify-content: center;min-height: 100vh">
      <div style="width: 1200px">
        <router-view></router-view>
      </div>
    </div>

    <!-- 添加登录弹窗 -->
    <var-popup v-model:show="showLoginDialog" position="center">
      <login-form @login-success="handleLoginSuccess"/>
    </var-popup>
  </div>
</template>

<script>
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { setDarkTheme, setLightTheme } from '@/main.js'
import router from '@/router'
import LoginForm from '@/views/login/login.vue'
import emitter, { GlobalEvents } from '@/utils/globalEvent'

export default {
  components: {
    LoginForm
  },

  setup() {
    const drag = ref(false)
    const currentPath = ref(router.currentRoute.value.path)
    const showLoginDialog = ref(false)

    // 监听路由变化
    watch(() => router.currentRoute.value.path, (newPath) => {
      currentPath.value = newPath
    })

    // 监听登录事件
    const showLoginHandler = () => {
      showLoginDialog.value = true
    }

    onMounted(() => {
      emitter.on(GlobalEvents.SHOW_LOGIN, showLoginHandler)
    })

    onUnmounted(() => {
      emitter.off(GlobalEvents.SHOW_LOGIN, showLoginHandler)
    })

    return {
      drag,
      currentPath,
      showLoginDialog
    }
  },

  // 删除原来的 created 钩子
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
      this.showLoginDialog =  !this.showLoginDialog
      if(this.drag) {
        setLightTheme()
      } else {
        setDarkTheme()
      }
    },

    handleClick(path) {
      if (!localStorage.getItem('token') && path !== '/products') {
        this.showLoginDialog = true
        return
      }
      router.push(path)
    },

    handleLoginSuccess() {
      this.showLoginDialog = false
      // 登录成功后，可以重新尝试跳转到之前想要访问的页面
      const targetPath = router.currentRoute.value.redirectedFrom?.fullPath || '/products'
      router.push(targetPath)
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
