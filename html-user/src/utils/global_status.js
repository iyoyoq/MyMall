import { ref } from 'vue'

const createGlobalState = () => {
  const isLogin = ref(false)
  const global_msg = ref({
    type: '',
    content: '',
    show: false
  })

  const setLoginStatus = (status) => {
    isLogin.value = status
  }

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
    setLoginStatus,
    msg: {
      success: (msg) => showMessage('success', msg),
      warning: (msg) => showMessage('warning', msg),
      error: (msg) => showMessage('error', msg),
      info: (msg) => showMessage('info', msg)
    }
  }
}

export const g_s = createGlobalState()

