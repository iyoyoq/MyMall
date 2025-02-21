import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: {}
  }),

  actions: {
    // 登录
    login(userInfo) {
      return new Promise((resolve) => {
        // 模拟登录接口
        if (userInfo.username === 'admin' && userInfo.password === '123456') {
          const token = 'mock-token'
          this.token = token
          localStorage.setItem('token', token)
          resolve(true)
        } else {
          resolve(false)
        }
      })
    },

    // 退出登录
    logout() {
      this.token = ''
      this.userInfo = {}
      localStorage.removeItem('token')
    }
  }
})
