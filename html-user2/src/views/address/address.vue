<template>
  <div class="address-container">
    <div class="address-header">
      <h2>地址管理</h2>
      <a-button type="primary" @click="showModal">新增地址</a-button>
    </div>
    
    <a-list :grid="{ gutter: 16, column: 2 }" :data-source="addressList">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-card>
            <template #actions>
              <a-button type="link" @click="editAddress(item)">编辑</a-button>
              <a-button type="link" danger @click="deleteAddress(item)">删除</a-button>
            </template>
            <a-card-meta
              :title="item.name + ' ' + item.phone"
              :description="item.address"
            />
          </a-card>
        </a-list-item>
      </template>
    </a-list>

    <a-modal
      v-model:visible="modalVisible"
      :title="modalTitle"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <a-form :model="formState" :rules="rules" ref="formRef">
        <a-form-item label="收货人" name="name">
          <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item label="手机号" name="phone">
          <a-input v-model:value="formState.phone" />
        </a-form-item>
        <a-form-item label="详细地址" name="address">
          <a-textarea v-model:value="formState.address" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
export default {
  name: 'Address',
  data() {
    return {
      addressList: [],
      modalVisible: false,
      modalTitle: '新增地址',
      formState: {
        name: '',
        phone: '',
        address: ''
      },
      rules: {
        name: [{ required: true, message: '请输入收货人姓名' }],
        phone: [{ required: true, message: '请输入手机号' }],
        address: [{ required: true, message: '请输入详细地址' }]
      }
    }
  },
  methods: {
    showModal() {
      this.modalVisible = true
    },
    handleOk() {
      // 处理表单提交
    },
    handleCancel() {
      this.modalVisible = false
    },
    editAddress(item) {
      // 处理编辑地址
    },
    deleteAddress(item) {
      // 处理删除地址
    }
  }
}
</script>

<style scoped>
.address-container {
  padding: 24px;
  background: #fff;
  min-height: 360px;
  border-radius: 4px;
}
.address-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}
</style> 