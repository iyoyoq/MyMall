<template>
  <div class="profile-container">
    <a-row :gutter="24">
      <a-col :span="8">
        <a-card>
          <template #cover>
            <div class="avatar-wrapper">
              <a-avatar :size="104" :src="userInfo.avatar" />
            </div>
          </template>
          <a-card-meta :title="userInfo.nickname">
            <template #description>
              <div class="user-info">
                <p>用户ID：{{ userInfo.id }}</p>
                <p>注册时间：{{ userInfo.registerTime }}</p>
              </div>
            </template>
          </a-card-meta>
        </a-card>
      </a-col>
      
      <a-col :span="16">
        <a-card title="基本信息">
          <a-form
            :model="formState"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 16 }"
          >
            <a-form-item label="昵称">
              <a-input v-model:value="formState.nickname" />
            </a-form-item>
            <a-form-item label="手机号">
              <a-input v-model:value="formState.phone" />
            </a-form-item>
            <a-form-item label="邮箱">
              <a-input v-model:value="formState.email" />
            </a-form-item>
            <a-form-item :wrapper-col="{ offset: 4 }">
              <a-button type="primary" @click="updateProfile">保存修改</a-button>
            </a-form-item>
          </a-form>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  data() {
    return {
      userInfo: {
        id: '',
        nickname: '',
        avatar: '',
        registerTime: ''
      },
      formState: {
        nickname: '',
        phone: '',
        email: ''
      }
    }
  },
  methods: {
    updateProfile() {
      // 处理更新个人信息
    }
  }
}
</script>

<style scoped>
.profile-container {
  padding: 24px;
  background: #fff;
  min-height: 360px;
  border-radius: 4px;
}
.avatar-wrapper {
  text-align: center;
  padding: 24px 0;
  background: #fafafa;
}
.user-info {
  margin-top: 16px;
}
.user-info p {
  margin-bottom: 8px;
  color: rgba(0, 0, 0, 0.45);
}
</style> 