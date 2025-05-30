<template>
  <div style="padding: 24px; min-height: 360px; border-radius: 4px;">
    <a-card style="width: 800px;">
      <div style="display:flex; justify-content: left; margin: 10px 0">
        <!--左-->
        <div>
          <div style="text-align: center; padding: 0; margin: 0 0 0 30px">
            <a-avatar :size="60" :image-url="userInfo.avatar"/>
            <div style="margin-top: 16px; ">
              <a-button type="primary" size="mini" @click="showEditModal">编辑资料</a-button>
            </div>
          </div>
        </div>
        <!--右-->
        <div>
          <div style="margin: 8px 0;">
            <div class="user-info-item">
              <div class="label-container">编号：</div>
              <div class="value-container">{{ userInfo.code }}</div>
            </div>

            <div class="user-info-item">
              <div class="label-container">昵称：</div>
              <div class="value-container">{{ userInfo.nickName }}</div>
            </div>

            <div class="user-info-item">
              <div class="label-container">手机：</div>
              <div class="value-container">{{ userInfo.phone }}</div>
            </div>

            <div class="user-info-item">
              <div class="label-container">简介：</div>
              <div class="value-container">{{ userInfo.intro }}</div>
            </div>
          </div>
        </div>
      </div>
    </a-card>

    <!-- 编辑资料对话框 -->
    <a-modal v-model:visible="modalVisible" title="编辑个人资料" @ok="updateProfile" ok-text="保存" cancel-text="取消">
      <a-form :model="formState" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="头像">
          <image-uploader style="margin: 0 0 0 4px" v-model="formState.avatar" />
        </a-form-item>
        <a-form-item label="昵称">
          <a-input v-model="formState.nickName"/>
        </a-form-item>
        <a-form-item label="手机">
          <a-input v-model="formState.phone"/>
        </a-form-item>
        <a-form-item label="简介">
          <a-textarea v-model="formState.intro" :auto-size="{ minRows: 2, maxRows: 5 }"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { selfDetailApi, updateSelfDetailApi } from '@/api/auth.js'
import ImageUploader from '@/views/component/ImageUploader.vue'

export default {
  name: 'Profile',
  components: { ImageUploader },
  data () {
    return {
      modalVisible: false,
      userInfo: {},
      formState: {},
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    async fetchData () {
      const resp = await selfDetailApi()
      const result = resp.data.result
      this.userInfo = result
      // 初始化表单数据
      this.formState = {
        nickName: result.nickName,
        phone: result.phone ? String(result.phone) : '',
        intro: result.intro,
      }
    },
    showEditModal () {
      // 打开编辑对话框前，确保表单数据与当前用户信息同步
      this.formState = {
        ...this.userInfo,
        phone: this.userInfo.phone ? String(this.userInfo.phone) : ''
      }
      this.modalVisible = true
    },
    async updateProfile () {
      console.log('提交的表单数据:', this.formState)
      const resp = await updateSelfDetailApi( this.formState)
      this.userInfo = resp.data.result
      this.fetchData()
      this.modalVisible = false
    },
  },
}
</script>

<style scoped>
.user-info-item {
  display: flex;
  margin-bottom: 16px;
}
.label-container {
  width: 100px; /* 统一设置标签宽度 */
  text-align: right;
  padding-right: 12px;
  flex-shrink: 0; /* 防止标签宽度被压缩 */
}
.value-container {
  flex-grow: 1; /* 内容区域自动填充剩余空间 */
  word-break: break-all; /* 防止长内容溢出 */
}
</style>
