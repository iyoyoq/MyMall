<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-title">
        <h2>系统登录</h2>
      </div>
      <a-form :model="loginForm" @submit="handleSubmit" class="login-form">

        <a-form-item field="userName" :rules="[{ required: true, message: '请输入用户名' }]">
          <a-input v-model="loginForm.userName" placeholder="请输入用户名">
            <template #prefix>
              <icon-user/>
            </template>
          </a-input>
        </a-form-item>
        <a-form-item field="password" :rules="[{ required: true, message: '请输入密码' }]">
          <a-input-password v-model="loginForm.password" placeholder="请输入密码">
            <template #prefix>
              <icon-lock/>
            </template>
          </a-input-password>
        </a-form-item>
        <!--<div class="login-option">-->
        <!--  <a-checkbox v-model="loginForm.rememberMe">记住密码</a-checkbox>-->
        <!--  <a-link>忘记密码？</a-link>-->
        <!--</div>-->
        <a-form-item>
          <a-button type="primary" html-type="submit" long :loading="loading">
            登录
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import { passwordLoginApi } from '@/api/auth.js'
import { localStorageTokenName } from '@/utils/request.js'

export default {
  name: 'Login',
  setup () {
    const router = useRouter()
    const loading = ref(false)
    const loginForm = ref({
      userName: '',
      password: '',
      rememberMe: false,
    })

    const handleSubmit = async () => {
      loading.value = true
      const loginData = { ...loginForm.value }
      try {
        const res = await passwordLoginApi(loginData)
        console.log(res.data.result)
        localStorage.setItem(localStorageTokenName, res.data.result.token)
        Message.success('登录成功')
        router.push('/dashboard')
      } catch (e) {
        // Message.error('登录失败，请检查用户名和密码')
      } finally {
        loading.value = false
      }
    }

    return {
      loginForm,
      loading,
      handleSubmit,
    }
  },
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  width: 400px;
  padding: 40px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
}

.login-title h2 {
  font-weight: 500;
  color: #333;
}

.login-form {
  width: 100%;
}

.login-option {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>
