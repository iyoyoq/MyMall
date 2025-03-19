import { ref } from 'vue'

const isLogin = ref(false)

const global_msg = ref({
  type: '',  // success warning info error loading
  content: '',
  show: false,
  success: (msg) => {
    const me = global_msg.value
    me.type = 'success'
    me.content = msg
    me.show = true
  },
})

/* 全局状态管理 global_status */
export const g_s = {
  _global_msg_ref: global_msg,
  // 是否登录
  isLogin_ref: isLogin,
  global_msg: {
    // 成功消息
    success: (msg) => {
      const me = global_msg.value
      me.type = 'success'
      me.content = msg
      me.show = true
    }
  }
}

