<template>
  <div class="profile-container">
    <var-card class="profile-card">
      <div class="user-info">
        <el-image-viewer
            v-if="showAvatarPreview"
            :url-list="[userInfo.avatar]"
            @close="showAvatarPreview = false"
        />
        <var-avatar @click="handleAvatar" :src="userInfo.avatar" hoverable class="user-avatar" size="60">
          <var-icon v-show="!userInfo.avatar" name="account-circle"/>
        </var-avatar>
        <div class="user-meta">
          <div class="meta-item">
            <span class="label">昵称：</span>
            <span>{{ userInfo.nickName }}</span>
          </div>
          <div class="meta-item">
            <span class="label">手机：</span>
            <span>{{ userInfo.phone }}</span>
          </div>
          <div class="meta-item">
            <span class="label">简介：</span>
            <span>{{ userInfo.intro || '暂无简介' }}</span>
          </div>
          <div class="meta-item">
            <span class="label">编号：</span>
            <span>{{ userInfo.code || '未设置' }}</span>
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
import { g_s } from '@/utils/global_status.js'
import { getSelfDetailApi } from '@/api/auth_user.js'

export default {
  name: 'profile',

  data () {
    return {
      userInfo: {
        phone: '',
        nickname: '',
        intro: '',
        avatar: '',
        id: '',
      },
      showAvatarPreview: false
    }
  },

  created () {
    getSelfDetailApi().then(res => {
      this.userInfo = res.data.result
    })
  },

  methods: {
    handleAvatar(){
      this.showAvatarPreview = true
    },
    handleLogout () {
      localStorage.clear()
      this.$router.replace('/products')
      g_s.isLogin.value =false
      g_s.loginDialog.value = true
    },
    handleEdit () {
      // TODO: 实现编辑功能
    },
  },
}
</script>

<style lang="scss" scoped>
.profile-container {
  display: flex;
  padding: 16px;

  .profile-card {
    width: 800px;
    margin: 0 auto;

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

      .btn {
        width: 200px;
      }
    }
  }
}
</style>
