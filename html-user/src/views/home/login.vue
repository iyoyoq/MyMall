<template>
  <div class="login-container">
    <var-card class="login-card">
      <template #title>
        <div class="login-title">登录</div>
      </template>

      <var-form ref="loginForm" style="width: 360px" >
        <var-input
            v-model="phone"
            placeholder="请输入手机号"
            :validate-trigger="['onBlur']"
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
            class="submit-btn"
            @click="handleLogin"
            native-type="submit"
        >
        登录
        </var-button>
      </var-form>
    </var-card>


  </div>
</template>

<script>
import { sendLoginCodeApi, codeLoginApi } from '@/api/auth.js'
import { g_s } from '@/utils/global_status.js'
import my_help  from '@/utils/my_help.js'

export default {
  name: 'login',

  data () {
    return {
      phone: '',
      code: '',
      countdown: 0,
      timer: null,
      submitting: false
    }
  },

  created() {
    // 监听登录弹窗状态
    //  这边直接外面控制销毁构建
    // watch(() => g_s.loginDialog.value, (newVal) => {
    //   if (!newVal) {
    //     // 弹窗关闭时清空表单
    //     this.phone = ''
    //     this.code = ''
    //     this.countdown = 0
    //     this.clearTimer()
    //   }
    // })
  },

  methods: {
    clearTimer() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
    },

    async sendCode () {
      // 表单验证
      const success = await this.$refs.loginForm.validate()
      if (!success) return

      // 发送验证码
      await sendLoginCodeApi(this.phone)
      g_s.msg.success('验证码发送成功')

      // 开始倒计时
      this.countdown = 60
      this.timer = setInterval(() => {
        this.countdown--
        if (this.countdown <= 0) {
          this.clearTimer()
        }
      }, 1000)
      // 验证码已发送

    },

    async handleLogin () {
      // 表单验证
      const success = await this.$refs.loginForm.validate()
      if (!success) return

      // 登录请求
      const res = await codeLoginApi({
        phone: this.phone,
        code: this.code,
      })
      g_s.setLoginSuccessResult(res.data)
      g_s.msg.success('登录成功')
      g_s.isLogin.value = true
      // 关闭登录弹窗
      g_s.loginDialog.value = false
      my_help.refresh()
    },
  },

  // 添加组件销毁时的处理
  beforeUnmount() {
    this.clearTimer()
  }
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
