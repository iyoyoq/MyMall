import { ref } from 'vue'

const createGlobalState = () => {
  const isLogin = ref(false)
  const global_msg = ref({
    type: '',
    content: '',
    show: false
  })
  // 添加登录弹窗控制
  const loginDialog = ref(false)


  const showMessage = (type, content) => {
    global_msg.value = {
      type,
      content,
      show: true
    }
  }

  return {
    isLogin: isLogin,
    _global_msg: global_msg,
    loginDialog,
    msg: {
      success: (msg) => showMessage('success', msg),
      warning: (msg) => showMessage('warning', msg),
      error: (msg) => showMessage('error', msg),
      info: (msg) => showMessage('info', msg)
    }
  }
}

export const g_s = createGlobalState()

