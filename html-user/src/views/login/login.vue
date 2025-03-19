<template>
  <div class="login-container">
    <var-card class="login-card">
      <template #title>
        <div class="login-title">登录</div>
      </template>

      <var-form ref="loginForm" style="width: 360px">
        <var-input
            v-model="phone"
            placeholder="请输入手机号"
            :rules="v => /^1[3-9]\d{9}$/.test(v) || '请输入正确的手机号'"
            type="number"
        />

        <div class="code-container">
          <var-input
              v-model="code"
              placeholder="请输入验证码"
              class="code-input"
          />
          <var-button
              class="send-code-btn"
              type="primary"
              :disabled="countdown > 0"
              @click="sendCode"
          >
            {{ countdown > 0 ? `${countdown}秒后重试` : '发送验证码' }}
          </var-button>
        </div>

        <var-button
            block
            type="primary"
            class="login-btn"
            @click="handleLogin"
        >
          登录
        </var-button>
      </var-form>
    </var-card>


  </div>
</template>

<script>
import { sendLoginCodeApi, codeLoginApi } from '@/api/login'

export default {
  name: 'login',

  data () {
    return {
      phone: '',
      code: '',
      countdown: 0,
    }
  },

  methods: {
    async sendCode () {
      try {
        // 表单验证
        const success = await this.$refs.loginForm.validate()
        if (!success) return

        // 发送验证码
        await sendLoginCodeApi(this.phone)

        // 开始倒计时
        this.countdown = 120
        const timer = setInterval(() => {
          this.countdown--
          if (this.countdown <= 0) {
            clearInterval(timer)
          }
        }, 1000)
        // 验证码已发送
      } catch (error) {
        this.$toast.error('发送验证码失败')
      }
    },

    async handleLogin () {
      try {
        // 表单验证
        const success = await this.$refs.loginForm.validate()
        if (!success) return

        // 登录请求
        const res = await codeLoginApi({
          phone: this.phone,
          code: this.code,
        })
        msg_success('登录成功')
        // 这里可以存储token等登录信息
        // 跳转到首页
        this.$router.push('/')
      } catch (error) {
        this.$toast.error('登录失败')
      }
    },
  },
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-card {
}

.login-title {
  padding: 40px 0 0 0;
  text-align: center;
  font-size: 24px;
  color: var(--color-primary);
}

.code-container {
  display: flex;
  gap: 10px;
  margin: 20px 0;
}

.code-input {
  flex: 1;
}

.send-code-btn {
  width: 120px;
}

.login-btn {
  margin-top: 20px;
}


</style>
