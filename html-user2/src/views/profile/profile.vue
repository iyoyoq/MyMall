<template>
  <div style="padding: 24px; min-height: 360px; border-radius: 4px;">
    <a-card style="width: 800px;">
      <a-row :gutter="24">
        <a-col :span="8">
          <div style="text-align: center; padding: 24px 0;">
            <a-avatar :size="104" :src="userInfo.avatar"/>
            <div style="margin-top: 16px;">
              <a-button type="primary" @click="showEditModal">编辑资料</a-button>
            </div>
          </div>
        </a-col>
        <a-col :span="16">
          <div style="margin-bottom: 16px;">
            <div style="display: flex; margin-bottom: 16px;">
              <div style="width: 100px; text-align: right; padding-right: 12px;">昵称：</div>
              <div>{{ userInfo.nickName }}</div>
            </div>
            <div style="display: flex; margin-bottom: 16px;">
              <div style="width: 100px; text-align: right; padding-right: 12px;">手机：</div>
              <div>{{ userInfo.phone }}</div>
            </div>
            <div style="display: flex; margin-bottom: 16px;">
              <div style="width: 100px; text-align: right; padding-right: 12px;">简介：</div>
              <div>{{ userInfo.intro }}</div>
            </div>
          </div>
        </a-col>
      </a-row>
    </a-card>

    <!-- 编辑资料对话框 -->
    <a-modal v-model:visible="modalVisible" title="编辑个人资料" @ok="updateProfile" ok-text="保存" cancel-text="取消">
      <a-form :model="formState" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="昵称">
          <a-input v-model:value="formState.nickName" />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input v-model:value="formState.phone" />
        </a-form-item>
        <a-form-item label="个人简介">
          <a-textarea v-model:value="formState.intro" :auto-size="{ minRows: 2, maxRows: 5 }" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { selfDetailApi } from '@/api/auth.js'

export default {
  name: 'Profile',
  data () {
    return {
      modalVisible: false,
      userInfo: {
        id: '',
        nickName: '',
        avatar: '',
        createTime: '',
        intro: '',
        phone: '',
      },
      formState: {
        nickName: '',
        phone: '',
        intro: '',
      },
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    formatDate(dateStr) {
      if (!dateStr) return '';
      return dateStr.split('.')[0].replace('T', ' ');
    },
    async fetchData () {
      try {
        const resp = await selfDetailApi()
        const result = resp.data.result
        this.userInfo = {
          id: result.id,
          nickName: result.nickName,
          avatar: result.avatar,
          createTime: result.createTime,
          intro: result.intro,
          phone: result.phone,
        }
        // 初始化表单数据
        this.formState = {
          nickName: result.nickName,
          phone: result.phone,
          intro: result.intro,
        }
      } catch (error) {
        console.error('获取用户信息失败:', error)
      }
    },
    showEditModal() {
      // 打开编辑对话框前，确保表单数据与当前用户信息同步
      this.formState = {
        nickName: this.userInfo.nickName,
        phone: this.userInfo.phone,
        intro: this.userInfo.intro,
      }
      this.modalVisible = true
    },
    updateProfile () {
      // TODO: 处理更新个人信息的API调用
      console.log('提交的表单数据:', this.formState)
      // 模拟更新成功
      this.userInfo = {
        ...this.userInfo,
        nickName: this.formState.nickName,
        phone: this.formState.phone,
        intro: this.formState.intro,
      }
      this.modalVisible = false
    },
  },
}
</script>

<style scoped>
</style>
