<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '200px'" class="aside">
      <div class="logo">
        <span :style="{ visibility: isCollapse ? 'hidden' : 'visible' }">管理系统</span>
      </div>
      <el-menu
        :default-active="route.path"
        router
        :collapse="isCollapse"
        background-color="#fff"
        text-color="#303133"
        active-text-color="#ffffff"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Odometer /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>

        <el-menu-item index="/user">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>

        <el-sub-menu index="/product">
          <template #title>
            <el-icon><Goods /></el-icon>
            <span>商品管理</span>
          </template>
          <el-menu-item index="/product/list">商品管理</el-menu-item>
          <el-menu-item index="/product/category">分类管理</el-menu-item>
          <el-menu-item index="/product/comment">评论管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-icon class="toggle-icon" @click="toggleSidebar">
            <ArrowRight v-if="isCollapse" />
            <ArrowLeft v-else />
          </el-icon>
          <el-breadcrumb separator="/" class="breadcrumb">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="route.meta.title">{{ route.meta.title }}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="route.meta.subtitle">{{ route.meta.subtitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="user-info">
              {{ userStore.username }}
              <el-icon><CaretBottom /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import {
  Odometer,
  User,
  Goods,
  ChatDotRound,
  Expand,
  Fold,
  CaretBottom,
  ArrowRight,
  ArrowLeft
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)

const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value
}

const handleCommand = async (command) => {
  if (command === 'logout') {
    await userStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside {
  color: #fff;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.05);
  position: relative;
  z-index: 1;
  transition: width 0.3s ease-in-out;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #2b2f3a;
  background-color: #fff;
  transition: all 0.3s ease-in-out;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.toggle-icon {
  font-size: 20px;
  cursor: pointer;
}

.user-info {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
}

:deep(.el-menu) {
  border-right: none;
  transition: width 0.3s ease-in-out;
}

:deep(.el-menu--collapse) {
  width: 64px;
}

:deep(.el-menu-item) {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 4px 12px;
  padding-left: 8px !important;
  border-radius: 2px;
  height: 46px !important;
}

:deep(.el-menu-item:hover) {
  background-color: #dae8fe !important;
}

:deep(.el-menu-item.is-active) {
  background-color: #3b82f6 !important;
}

:deep(.el-sub-menu__title) {
  margin: 4px 12px;
  padding-left: 8px !important;
  border-radius: 2px;
  height: 46px !important;
}

:deep(.el-sub-menu__title:hover) {
  background-color: #dae8fe !important;
}

:deep(.el-menu-item .el-icon),
:deep(.el-sub-menu__title .el-icon) {
  margin-right: 8px;
  width: 24px;
  text-align: center;
  font-size: 18px;
  color: inherit;
}

:deep(.el-sub-menu__title) {
  display: flex;
  align-items: center;
  gap: 8px;
}

:deep(.el-menu--inline .el-menu-item) {
  padding-left: 28px !important;
}

:deep(.el-sub-menu.is-active > .el-sub-menu__title) {
  background-color: transparent !important;
}

:deep(.el-sub-menu.is-active > .el-sub-menu__title span),
:deep(.el-sub-menu.is-active > .el-sub-menu__title .el-icon) {
  color: #3b82f6 !important;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.breadcrumb {
  font-size: 14px;
}

:deep(.el-breadcrumb__item) {
  display: flex;
  align-items: center;
}

:deep(.el-breadcrumb__inner) {
  color: #606266;
}

:deep(.el-breadcrumb__inner.is-link:hover) {
  color: #3b82f6;
}

:deep(.el-breadcrumb__item:last-child .el-breadcrumb__inner) {
  color: #303133;
  font-weight: 500;
}
</style>
