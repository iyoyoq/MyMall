<template>
  <div class="login-container">
    <div class="login-box">
      <h2>用户登录</h2>
      <a-form :model="formData" @submit="handleLogin">
        <a-form-item
          label="手机号"
          field="phone"
          :rules="[{ required: true, message: '请输入手机号' }]"
        >
          <a-input v-model="formData.phone" placeholder="请输入手机号" />
        </a-form-item>

        <a-form-item
          label="验证码"
          field="code"
          :rules="[{ required: true, message: '请输入验证码' }]"
        >
          <div class="code-input">
            <a-input v-model="formData.code" placeholder="请输入验证码" />
            <a-button
              :disabled="countdown > 0"
              @click="getCode"
            >
              {{ countdown > 0 ? `${countdown}秒后重试` : '获取验证码' }}
            </a-button>
          </div>
        </a-form-item>

        <a-form-item>
          <a-button type="primary" html-type="submit" long>登录</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script>
import { sendLoginCodeApi, codeLoginApi } from '@/api/login.js'
import { Message } from '@arco-design/web-vue'

export default {
  name: 'login',
  data () {
    return {
      formData: {
        phone: '',
        code: ''
      },
      countdown: 0,
      phoneCodeTimeSeconds: 120,
      timer: null
    }
  },
  methods: {
    async getCode () {
      if (!this.formData.phone) {
        Message.error('请先输入手机号')
        return
      }
      try {
        await sendLoginCodeApi(this.formData.phone)
        Message.success('验证码发送成功')
        this.startCountdown()
      } catch (error) {
        Message.error('验证码发送失败')
      }
    },

    startCountdown () {
      this.countdown = this.phoneCodeTimeSeconds
      this.timer = setInterval(() => {
        if (this.countdown > 0) {
          this.countdown--
        } else {
          clearInterval(this.timer)
        }
      }, 1000)
    },

    async handleLogin () {
      try {
        const res = await codeLoginApi(this.formData)
        Message.success('登录成功')
        this.$router.push('/products')
      } catch (error) {
        Message.error('登录失败')
      }
    }
  },
  beforeUnmount () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f2f5;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.login-box h2 {
  text-align: center;
  margin-bottom: 30px;
}

.code-input {
  display: flex;
  gap: 10px;
}

.code-input .ant-input {
  flex: 1;
}
</style>
