<template>
  <div>
    <a-layout>
      <a-layout-header style="padding: 0 20px; ">
        <div style="padding: 0 20px; display: flex; align-items: center;  justify-content: center; ">
          <div class="company-logo">
            <a :href="companyLink" class="company-name" @click.prevent="onClickMenuItem('0')">{{ companyName }}</a>
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
      <a-layout-content style="min-height: 100vh; display: flex; justify-content: center; ">
        <div style="max-width: 1200px">
          <router-view></router-view>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
export default {
  data() {
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
      }
    }
  },

  methods: {
    setActiveKeyFromRoute() {
      this.activeKey = this.pathKeyMap[this.$route.path] || '0'
    },

    onClickMenuItem(key) {
      this.$router.push(this.keyPathMap[key])
    }
  },

  watch: {
    '$route.path': {
      handler: 'setActiveKeyFromRoute',
      immediate: true
    }
  },

  mounted() {
    this.setActiveKeyFromRoute()
  }
}
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
  text-decoration: none;
}

.company-name:hover {
  background-color: rgba(0, 0, 0, 0.06);
}


</style>
