import { createRouter, createWebHashHistory } from 'vue-router'

export const routes = [
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    name: 'Login',
  },
  {
    path: '/',
    component: () => import('@/views/layout/index.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        name: 'Dashboard',
        meta: { title: '仪表盘', icon: 'Odometer' },
      },
      {
        path: 'user',
        component: () => import('@/views/user/index.vue'),
        name: 'User',
        meta: { title: '用户管理', icon: 'User' },
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
            path: 'category',
            component: () => import('@/views/product/category.vue'),
            name: 'Category',
            meta: { title: '商品管理', subtitle: '分类管理' },
          },
          {
            path: 'comment',
            component: () => import('@/views/product/comment.vue'),
            name: 'Comment',
            meta: { title: '商品管理', subtitle: '评论管理' },
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
  const token = localStorage.getItem('token')
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
