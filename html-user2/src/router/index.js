import { createRouter, createWebHashHistory } from 'vue-router'

/* 示例跳转
router.push({
  path: '/product/sku',
  query: {
    productId: record.id,
  },
})

productId: this.$route.query.productId,
*/
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
        meta: { title: '首页' },
      },

      {
        path: 'favorites',
        component: () => import('@/views/favorites/favorites.vue'),
        name: 'favorites',
        meta: { title: '我的收藏' },
      },
      {
        path: 'cart',
        component: () => import('@/views/cart/cart.vue'),
        name: 'cart',
        meta: { title: '购物车' },
      },
      {
        path: 'orders',
        component: () => import('@/views/orders/orders.vue'),
        name: 'orders',
        meta: { title: '订单管理' },
      },
      {
        path: 'address',
        component: () => import('@/views/address/address.vue'),
        name: 'address',
        meta: { title: '地址管理' },
      },
      {
        path: 'profile',
        component: () => import('@/views/profile/profile.vue'),
        name: 'profile',
        meta: { title: '个人中心' },
      },
      {
        path: 'product',
        component: () => import('@/views/products/product.vue'),
        name: 'product',
        meta: { title: '个人中心' },
      },

    ],
  },
  {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    name: 'login',
    meta: { title: '登录' },
  },

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

// 每次路由跳转后滚动到顶部
router.afterEach(() => {
  window.scrollTo(0, 0)
})

export default router
