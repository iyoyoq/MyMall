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
            <span>{{ userInfo.nickName|| '无'  }}</span>
          </div>
          <div class="meta-item">
            <span class="label">手机：</span>
            <span>{{ userInfo.phone || '无' }}</span>
          </div>
          <div class="meta-item">
            <span class="label">简介：</span>
            <span>{{ userInfo.intro || '无' }}</span>
          </div>
          <div class="meta-item">
            <span class="label">编号：</span>
            <span>{{ userInfo.code || '无' }}</span>
          </div>
        </div>
      </div>

      <div class="btns">
        <var-button class="btn" block @click="handleEdit">编辑资料</var-button>
        <var-button class="btn" block @click="handleLogout">退出登录</var-button>
      </div>
    </var-card>

    <!-- 添加编辑弹窗 -->
    <var-popup v-model:show="showEditDialog" position="center">
      <var-card class="edit-card">
        <template #title>
          <div class="edit-title">编辑资料</div>
        </template>

        <div class="avatar-upload">
          <var-button @click="triggerFileInput">
            更换头像
          </var-button>
          <input
              type="file"
              ref="fileInput"
              accept="image/*"
              style="display: none"
              @change="onFileChange"
          />
        </div>

        <var-form ref="editForm">
          <var-input
            v-model="editForm.nickName"
            placeholder="请输入昵称"
            label="昵称"
          />

          <var-input
            v-model="editForm.intro"
            placeholder="请输入个人简介"
            label="简介"
            type="textarea"
            :rows="3"
          />



          <div class="edit-btns">
            <var-button
              type="primary"
              block
              @click="handleSubmitEdit"
              :loading="submitting"
            >
              保存
            </var-button>
          </div>
        </var-form>
      </var-card>
    </var-popup>
  </div>
</template>

<script>
import { g_s } from '@/utils/global_status.js'
import { getSelfDetailApi, updateUserInfoApi } from '@/api/auth_user.js'

export default {
  name: 'profile',

  data () {
    return {
      userInfo: {
        phone: '',
        nickName: '',
        intro: '',
        avatar: '',
        id: '',
      },
      showAvatarPreview: false,
      showEditDialog: false,
      submitting: false,
      editForm: {
        nickName: '',
        intro: '',
        avatar: [] // 改为数组类型
      }
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
      // g_s.loginDialog.value = true
    },
    handleEdit () {
      this.editForm = {
        nickName: this.userInfo.nickName,
        intro: this.userInfo.intro || '',
        avatar: this.userInfo.avatar ? [this.userInfo.avatar] : [] // 转换为数组
      }
      this.showEditDialog = true
    },

    async handleAvatarUpload(file) {
      // TODO: 实现头像上传
      // const formData = new FormData()
      // formData.append('file', file.file)
      // const res = await uploadApi(formData)
      // this.editForm.avatar = [res.data.url] // 保持数组格式
    },

    async handleSubmitEdit() {
      try {
        this.submitting = true
        const submitData = {
          ...this.editForm,
          avatar: this.editForm.avatar[0] || '' // 提交时转回字符串
        }
        await updateUserInfoApi(submitData)
        g_s.msg.success('更新成功')
        this.showEditDialog = false
        const res = await getSelfDetailApi()
        this.userInfo = res.data.result
      } catch (error) {
        g_s.msg.error('更新失败')
      } finally {
        this.submitting = false
      }
    },
    triggerFileInput() {
      this.$refs.fileInput.click()
    },

    onFileChange(event) {
      const file = event.target.files[0]
      if (file) {
        this.handleAvatarUpload({ file })
      }
      // 清空 input 的值，这样同一个文件可以重复选择
      event.target.value = ''
    },
  }
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

.edit-card {
  width: 400px;
  padding: 20px;

  .edit-title {
    text-align: center;
    font-size: 20px;
    margin-bottom: 20px;
  }

  .edit-btns {
    margin-top: 20px;
  }
}

  .avatar-upload {
    margin-bottom: 16px;
    display: flex;
    align-items: center;

    .label {
      margin-right: 16px;
    }
  }
</style>
