import { createRouter, createWebHashHistory } from 'vue-router'

export const routes = [
  {
    path: '/',
    component: () => import('@/views/home/home.vue'),
    name: 'home',
    redirect: '/products',
    children: [
      {
        path: 'products',
        component: () => import('@/views/products/products.vue'),
        name: 'products',
        meta: { title: '首页' }
      },
      {
        path: 'favorites',
        component: () => import('@/views/favorites/favorites.vue'),
        name: 'favorites',
        meta: { title: '我的收藏' }
      },
      {
        path: 'cart',
        component: () => import('@/views/cart/cart.vue'),
        name: 'cart',
        meta: { title: '购物车' }
      },
      {
        path: 'orders',
        component: () => import('@/views/orders/orders.vue'),
        name: 'orders',
        meta: { title: '订单管理' }
      },
      {
        path: 'address',
        component: () => import('@/views/address/address.vue'),
        name: 'address',
        meta: { title: '地址管理' }
      },
      {
        path: 'profile',
        component: () => import('@/views/profile/profile.vue'),
        name: 'profile',
        meta: { title: '个人中心' }
      }
    ]
  },
  /*

  {
    path: '/',
    component: () => import('@/views/layout/home.vue'),
    redirect: '/dashboard',
    children: [

      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/home.vue'),
        name: 'Dashboard',
        meta: { title: '仪表盘', icon: 'Odometer' },
      },
      {
        path: 'user',
        component: () => import('@/views/user/home.vue'),
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
            component: () => import('@/views/product/home.vue'),
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

  */

]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

// 路由守卫
/*
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

 */

export default router
