<template>
  <div class="profile-container">
    <var-card class="profile-card">
      <div class="user-info">
        <var-avatar :src="avatar" class="user-avatar" size="60">
          <var-icon v-if="!avatar" name="account-circle"/>
        </var-avatar>
        <div class="user-meta">
          <div class="meta-item">
            <span class="label">昵称：</span>
            <span>{{ nickname || `用户${phone.slice(-4)}` }}</span>
          </div>
          <div class="meta-item">
            <span class="label">手机：</span>
            <span>{{ phone }}</span>
          </div>
          <div class="meta-item">
            <span class="label">简介：</span>
            <span>{{ intro || '暂无简介' }}</span>
          </div>
          <div class="meta-item">
            <span class="label">编号：</span>
            <span>{{ code || '未设置' }}</span>
          </div>
        </div>
      </div>

      <div class="btns">
        <var-button class="btn" block @click="handleEdit">编辑资料</var-button>
        <var-button class="btn" block @click="handleLogout">退出登录</var-button>
      </div>
    </var-card>
  </div>
</template>

<script>
export default {
  name: 'profile',

  data() {
    return {
      phone: localStorage.getItem('phone') || '',
      nickname: localStorage.getItem('nickname') || '',
      intro: localStorage.getItem('intro') || '',
      avatar: localStorage.getItem('avatar') || '',
      code: localStorage.getItem('code') || ''
    }
  },

  methods: {
    handleLogout() {
      localStorage.clear()
      this.$router.replace('/products')
    },
    handleEdit(){

    }
  }
}
</script>

<style lang="scss" scoped>
.profile-container {
  display: flex;
  padding: 16px;
  justify-content: center;
  .profile-card {
    width: 800px;
    .user-info {
      padding: 20px;
      display: flex;
      align-items: flex-start;

      .user-avatar {
        margin-right: 16px;
      }

      .user-meta {
        flex: 1;
        .meta-item {
          margin-bottom: 8px;
          font-size: 14px;

          .label {
            margin-right: 8px;
          }
        }
      }
    }

    .btns {
      margin: 20px;
      display: flex;

      .btn{
        width: 200px;
      }
    }
  }
}
</style>
