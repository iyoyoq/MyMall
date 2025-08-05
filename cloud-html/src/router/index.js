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
      // 下面为 admin端
      {
        path: 'admin',
        redirect: '/admin/dashboard',
        meta: { title: '商家后台' },
        children: [
          {
            path: 'dashboard',
            component: () => import('@/views/admin/dashboard/dashboard.vue'),
            name: 'dashboard',
            meta: { title: '首页' },
          },
          {
            path: 'product',
            component: () => import('@/views/admin/product/product.vue'),
            meta: { title: '商品管理' },
          },

          {
            path: 'dashboard',
            component: () => import('@/views/admin/dashboard/dashboard.vue'),
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
                component: () => import('@/views/admin/user/points.vue'),
              },
              {
                path: 'user',
                component: () => import('@/views/admin/user/user.vue'),
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
                component: () => import('@/views/admin/product/product.vue'),
                name: 'ProductList',
                meta: { title: '商品管理', subtitle: '商品列表' },
              },
              {
                path: 'sku',
                component: () => import('@/views/admin/product/sku.vue'),
                name: 'SKU',
                meta: { title: 'SKU', subtitle: 'SKU' },
              },
              {
                path: 'category',
                component: () => import('@/views/admin/product/category.vue'),
                name: 'Category',
                meta: { title: '商品管理', subtitle: '分类管理' },
              },
            ],
          },
          {
            path: 'orders',
            redirect: '/orders/orders',
            children: [
              {
                path: 'orders',
                component: () => import('@/views/admin/orders/orders.vue'),
              },
            ],
          },

        ],
      },

      // 下面为 user端
      {
        path: 'products',
        component: () => import('@/views/user/products/products.vue'),
        name: 'products',
        meta: { title: '首页' },
      },
      {
        path: 'favorites',
        component: () => import('@/views/user/favorites/favorites.vue'),
        name: 'favorites',
        meta: { title: '我的收藏' },
      },
      {
        path: 'cart',
        component: () => import('@/views/user/cart/cart.vue'),
        name: 'cart',
        meta: { title: '购物车' },
      },
      {
        path: 'orders',
        component: () => import('@/views/user/orders/orders.vue'),
        name: 'orders',
        meta: { title: '订单管理' },
      },
      {
        path: 'address',
        component: () => import('@/views/user/address/address.vue'),
        name: 'address',
        meta: { title: '地址管理' },
      },
      {
        path: 'profile',
        component: () => import('@/views/user/profile/profile.vue'),
        name: 'profile',
        meta: { title: '个人中心' },
      },
      {
        path: 'product',
        component: () => import('@/views/user/products/product.vue'),
        name: 'product',
        meta: { title: '个人中心' },
      },
      {
        path: 'order',
        component: () => import('@/views/user/orders/order.vue'),
        name: 'order',
        meta: { title: '订单' },
      },
    ],
  },
  {
    path: '/login',
    component: () => import('@/views/user/login/login.vue'),
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
