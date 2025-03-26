import { ref } from 'vue'
import { localStorageTokenName } from '@/utils/request.js'

const createGlobalState = () => {
  const isLogin = ref(false)
  const loginResult = ref({})
  const setLoginSuccessResult = (response) => {
    // console.log(response.result)
    loginResult.value = response.result
    localStorage.setItem(localStorageTokenName,response.result.token)
  }

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
    setLoginSuccessResult,
    msg: {
      success: (msg) => showMessage('success', msg),
      warning: (msg) => showMessage('warning', msg),
      error: (msg) => showMessage('error', msg),
      info: (msg) => showMessage('info', msg)
    }
  }
}

export const g_s = createGlobalState()

