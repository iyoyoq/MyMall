<template>
  <div style="display: flex; ">
    <div style="width: 1200px; margin: 0 auto;">
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
        <div class="theme-switch">
          <var-button
              round
              text
              class="theme-button"
              @click="changeTheme"
          >
            <var-icon :name="moon ? 'weather-night':'white-balance-sunny'  "/>
          </var-button>
        </div>
        <var-button v-if="!isLogin" type="primary" @click="loginDialog = true" size="mini">登录</var-button>
      </div>

      <div style="display: flex;justify-content: center;min-height: 100vh">
        <div style="width: 1200px">
          <router-view></router-view>
        </div>
      </div>

      <var-snackbar v-model:type="pageMsg.type" v-model:show="pageMsg.show">{{ pageMsg.content }}</var-snackbar>

      <!-- 添加登录弹窗 -->
      <var-popup v-model:show="loginDialog" position="center">
        <login-form :key="loginDialog"/>
      </var-popup>
    </div>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import { setDarkTheme, setLightTheme } from '@/main.js'
import router from '@/router'
import LoginForm from '@/views/home/login.vue'
import { g_s } from '@/utils/global_status.js'

export default {
  components: {
    LoginForm,
  },

  setup () {
    const moon = ref(false)
    const currentPath = ref(router.currentRoute.value.path)

    // 监听路由变化
    watch(() => router.currentRoute.value.path, (newPath) => {
      currentPath.value = newPath
    })

    return {
      moon,
      currentPath,
      pageMsg: g_s._global_msg,
      loginDialog: g_s.loginDialog,  // 使用全局状态
    }
  },

  // 删除原来的 created 钩子
  data () {
    return {
      isLogin: g_s.isLogin,
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
      },
    }
  },

  methods: {
    changeTheme () {
      if (this.moon) {
        setLightTheme()
      } else {
        setDarkTheme()
      }
      this.moon = !this.moon
    },

    handleClick (path) {
      // if (path !== '/products' && login_status.value === false) {
      //   this.showLoginDialog = true
      //   return
      // }
      router.push(path)
    },

    // handleLoginSuccess() {
    // 登录成功后，可以重新尝试跳转到之前想要访问的页面
    // const targetPath = router.currentRoute.value.redirectedFrom?.fullPath || '/products'
    // router.push(targetPath)
    // }
  },
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

.theme-switch {
  margin: 0 10px;

  .theme-button {
    padding: 8px;
    transition: all 0.3s;

    :deep(.var-icon) {
      font-size: 20px;
      color: var(--color-text);
    }

    &:hover {
      background: var(--color-primary-light);

      :deep(.var-icon) {
        color: var(--color-primary);
      }
    }
  }
}
</style>
