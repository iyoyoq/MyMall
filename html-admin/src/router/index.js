import { createRouter, createWebHashHistory } from 'vue-router'
import { localStorageTokenName } from '@/utils/request.js'

export const routes = [
  {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    name: 'Login',
  },
  {
    path: '/',
    component: () => import('@/views/layout/layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/dashboard.vue'),
        name: 'Dashboard',
        meta: { title: '仪表盘', icon: 'Odometer' },

      },
      {
        path: 'user',
        name: 'User',
        meta: { title: '用户管理', icon: 'User' },
        redirect: '/user/points',
        children: [
          {
            path: 'points',
            component: () => import('@/views/user/points.vue'),
          },
          {
            path: 'user',
            component: () => import('@/views/user/user.vue'),
          },
        ],
      },
      {
        path: 'product',
        name: 'Product',
        meta: { title: '商品管理', icon: 'Goods' },
        redirect: '/product/list',
        children: [
          {
            path: 'list',
            component: () => import('@/views/product/product.vue'),
            name: 'ProductList',
            meta: { title: '商品管理', subtitle: '商品列表' },
          },
          {
            path: 'sku',
            component: () => import('@/views/product/sku.vue'),
            name: 'SKU',
            meta: { title: 'SKU', subtitle: 'SKU' },
          },
          {
            path: 'category',
            component: () => import('@/views/product/category.vue'),
            name: 'Category',
            meta: { title: '商品管理', subtitle: '分类管理' },
          },
          // {
          //   path: 'comment',
          //   component: () => import('@/views/product/comment.vue'),
          //   name: 'Comment',
          //   meta: { title: '商品管理', subtitle: '评论管理' },
          // },
        ],
      },
      {
        path: 'orders',
        redirect: '/orders/orders',
        children: [
          {
            path: 'orders',
            component: () => import('@/views/orders/orders.vue'),
          },
        ],
      },
    ],
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem(localStorageTokenName)
  if (to.path === '/login') {
    next()
  } else {
    if (!token) {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
