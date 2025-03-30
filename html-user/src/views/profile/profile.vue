<template>
  <div class="profile-container">
    <var-card class="profile-card">
      <div class="user-info">
        <!--头像预览组件-->
        <el-image-viewer
            v-if="showAvatarPreview"
            :url-list="[userInfo.avatar]"
            @close="showAvatarPreview = false"
        />
        <!--头像及换头像按钮-->
        <div style="display: flex; flex-direction: column; align-items: center; margin: 10px 20px">
          <div style=" width: 60px">
            <var-avatar @click="handleAvatar" :src="userInfo.avatar" hoverable class="user-avatar" size="60">
              <var-icon v-show="!userInfo.avatar" name="account-circle"/>
            </var-avatar>
          </div>
          <div style="margin: 10px 0 0 0">
            <var-button size="small" @click="triggerFileInput">
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
        </div>

        <div class="user-meta">
          <div class="meta-item">
            <span class="label">昵称：</span>
            <span>{{ userInfo.nickName}}</span>
          </div>
          <div class="meta-item">
            <span class="label">手机：</span>
            <span>{{ userInfo.phone}}</span>
          </div>
          <div class="meta-item">
            <span class="label">简介：</span>
            <span>{{ userInfo.intro}}</span>
          </div>
          <div class="meta-item">
            <span class="label">编号：</span>
            <span>{{ userInfo.code}}</span>
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
      <var-card style="width: 400px">
        <template #title>
          <div style="padding: 20px 20px 0 16px; text-align: center">编辑资料</div>
        </template>
        <var-form ref="editForm">
          <var-input
              autocomplete="nickName"
              v-model="editForm.nickName"
              placeholder="昵称"
          />
          <var-input
              style="margin-top: 20px"
              autocomplete="intro"
              v-model="editForm.intro"
              placeholder="个人简介"
              textarea
              :rows="4"
          />
          <div style="margin: 20px 0 0 0">
            <var-button
                type="primary"
                block
                @click="handleSubmitEdit"
                :loading="editUserInfoSubmitting"
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
import { getSelfDetailApi, updateUserInfoApi } from '@/api/auth.js'
import { fileUploadApi } from '@/api/file.js'
import my_help from '@/utils/my_help.js'

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
      editUserInfoSubmitting: false,
      editForm: {
        nickName: '',
        intro: '',
      },
    }
  },

  created () {
    getSelfDetailApi().then(res => {
      this.userInfo = res.data.result
    })
  },

  methods: {
    handleAvatar () {
      this.showAvatarPreview = true
    },
    handleLogout () {
      localStorage.clear()
      this.$router.replace('/products')
      g_s.isLogin.value = false
      // g_s.loginDialog.value = true
    },
    // 点击编辑按钮
    handleEdit () {
      this.editForm = {
        nickName: this.userInfo.nickName,
        intro: this.userInfo.intro || '',
      }
      this.showEditDialog = true
    },

    async handleSubmitEdit () {
      this.editUserInfoSubmitting = true
      const submitData = {
        ...this.editForm,
      }
      await updateUserInfoApi(submitData)
      g_s.msg.success('更新成功')
      this.showEditDialog = false
      my_help.refresh()
    },
    triggerFileInput () {
      this.$refs.fileInput.click()
    },

    async onFileChange (event) {
      const file = event.target.files[0]
      if (file) {
        const res = await fileUploadApi(file)
        const webUrl = res.data.result
        console.log(webUrl)
        await updateUserInfoApi({
          avatar: webUrl,
        })
        my_help.refresh()
        g_s.msg.success('头像更换成功')
      }
      // 清空 input 的值，这样同一个文件可以重复选择
      event.target.value = ''
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

.var-card__container {
  padding: 30px;
}

.edit-card {
  width: 400px;

  .edit-title {
    padding: 20px 20px 0 20px;
    text-align: center;
    font-size: 20px;
  }

  .edit-btns {
    margin-top: 20px;
  }
}


</style>
