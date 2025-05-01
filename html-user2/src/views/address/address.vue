<template>
  <div style="padding: 24px; min-height: 360px; border-radius: 4px; width: 800px;">
    <div style="display: flex; justify-content: flex-start; margin-bottom: 24px; width: 100%">
      <a-button type="primary" @click="showModal">新增地址</a-button>
    </div>
    <div style="display: flex; flex-direction: column; gap: 16px;">
      <div v-for="item in addressList" :key="item.id">
        <a-card style="width: 700px;">
          <div style="display: flex; justify-content: space-between; align-items: flex-start;">
            <div>
              <div style="font-size: 16px; font-weight: bold; margin-bottom: 8px;">
                <a-tag color="blue" v-if="item.isDefault === 1" style="margin: 0 0 3px 0;">默认</a-tag>
                {{ item.receiverName }}
                <span style="margin-left: 8px;">{{ item.receiverPhone }}</span>

              </div>
              <div >
                {{ item.province || '' }}{{ item.city || '' }}{{ item.district || '' }}
              </div>
              <div style="margin-top: 4px;">
                {{ item.detailAddress || '' }}
              </div>
            </div>
            <div style="display: flex; gap: 8px;">
              <a-button type="text" @click="editAddress(item)">编辑</a-button>
              <a-button type="text" status="danger" @click="confirmDelete(item)">删除</a-button>
              <a-button type="text" @click="setDefault(item)" v-if="item.isDefault === 0">设为默认</a-button>
              <a-button type="text" @click="cancelDefault(item)" v-else>取消默认</a-button>
            </div>
          </div>
        </a-card>
      </div>
    </div>
    <a-modal
        v-model:visible="modalVisible"
        :title="modalTitle"
        @ok="handleOk"
        @cancel="handleCancel"
    >
      <a-form :model="formState" :rules="rules" ref="formRef">
        <a-form-item label="收货人" name="receiverName">
          <a-input v-model="formState.receiverName" placeholder="请输入收货人姓名"/>
        </a-form-item>
        <a-form-item label="手机号" name="receiverPhone">
          <a-input v-model="formState.receiverPhone" placeholder="请输入手机号"/>
        </a-form-item>
        <a-form-item label="所在地区" name="area">
          <a-cascader
              v-model="selectedArea"
              :options="areaOptions"
              placeholder="请选择省/市/区"
              expand-trigger="hover"
              path-mode
              @change="handleAreaChange"
          />
        </a-form-item>
        <a-form-item label="详细地址" name="detailAddress">
          <a-textarea v-model="formState.detailAddress" placeholder="请输入详细地址"/>
        </a-form-item>
        <!--<a-form-item name="isDefault">-->
        <!--  <a-checkbox v-model="formState.isDefault">设为默认地址</a-checkbox>-->
        <!--</a-form-item>-->
      </a-form>
    </a-modal>

    <!-- 删除确认模态框 -->
    <a-modal
        v-model:visible="deleteModalVisible"
        title="确认删除"
        @ok="handleDelete"
        @cancel="cancelDelete"
        width="400px"
    >
      <p>确定要删除这个地址吗？</p>
    </a-modal>
  </div>
</template>

<script>
import { addressApi } from '@/api/address.js'
import { Message } from '@arco-design/web-vue'
import areaData from '@/utils/area.json'

export default {
  name: 'Address',
  data () {
    return {
      addressList: [],
      modalVisible: false,
      deleteModalVisible: false,
      modalTitle: '新增地址',
      currentId: null,
      formState: {},
      selectedArea: [], // 存储级联选择器选中的值
      areaOptions: [], // 地区数据
      rules: {
        receiverName: [{ required: true, message: '请输入收货人姓名' }],
        receiverPhone: [{ required: true, message: '请输入手机号' }],
        area: [{ required: true, message: '请选择所在地区' }],
        detailAddress: [{ required: true, message: '请输入详细地址' }],
      },
      itemToDelete: null,
    }
  },
  created () {
    this.fetchAddressList()
    this.initAreaOptions()
  },
  methods: {
    // 初始化地区数据
    initAreaOptions () {
      // 将原始数据转换为级联选择器需要的格式
      this.areaOptions = areaData.map(province => {
        return {
          value: province.text,
          label: province.text,
          children: province.children ? province.children.map(city => {
            return {
              value: city.text,
              label: city.text,
              children: city.children ? city.children.map(district => {
                return {
                  value: district.text,
                  label: district.text,
                }
              }) : [],
            }
          }) : [],
        }
      })
      // console.log(this.areaOptions)
    },
    // 处理地区选择变化
    handleAreaChange (value, selectedOptions) {
      // console.log(value)
      if (value && value.length > 0) {
        this.formState.province = value[0] || ''
        this.formState.city = value[1] || ''
        this.formState.district = value[2] || ''
      } else {
        this.formState.province = ''
        this.formState.city = ''
        this.formState.district = ''
      }
    },
    async fetchAddressList () {
      const res = await addressApi.list({
        pageNum: 1,
        pageSize: 100,
      })
      // console.log(res.data.result.records)
      this.addressList = res.data.result.records
    },
    showModal () {
      this.modalTitle = '新增地址'
      this.currentId = null
      this.formState = {
        receiverName: '',
        receiverPhone: '',
        province: '',
        city: '',
        district: '',
        detailAddress: '',
      }
      this.selectedArea = []
      this.modalVisible = true
    },
    editAddress (item) {
      this.modalTitle = '编辑地址'
      this.currentId = item.id
      this.formState = item
      // 一次性设置级联选择器的值
      this.selectedArea = [item.province, item.city, item.district].filter(Boolean)
      this.modalVisible = true
    },

    async handleOk () {
      try {
        const formData = {
          ...this.formState,
          isDefault: this.formState.isDefault ? 1 : 0,
        }
        console.log(formData)
        if (this.currentId) {
          // 编辑
          await addressApi.update({
            id: this.currentId,
            ...formData,
          })
          Message.success('修改成功')
        } else {
          // 新增
          await addressApi.save(formData)
          Message.success('添加成功')
        }

        this.modalVisible = false
        this.fetchAddressList()
      } catch (error) {
        // console.error('保存地址失败:', error)
        // Message.error('操作失败: ' + (error.message || '未知错误'))
      }
    },
    handleCancel () {
      this.modalVisible = false
    },
    confirmDelete (item) {
      this.itemToDelete = item
      this.deleteModalVisible = true
    },
    async handleDelete () {
      try {
        await addressApi.remove([this.itemToDelete.id])
        Message.success('删除成功')
        this.fetchAddressList()
      } catch (error) {
        console.error('删除地址失败:', error)
        Message.error('删除失败: ' + (error.message || '未知错误'))
      } finally {
        this.deleteModalVisible = false
        this.itemToDelete = null
      }
    },
    cancelDelete () {
      this.deleteModalVisible = false
      this.itemToDelete = null
    },
    async setDefault (item) {
      await addressApi.setDefault(item.id)
      Message.success('设置成功')
      this.fetchAddressList()
    },
    async cancelDefault(item){
      await addressApi.cancelDefault(item)
      Message.success('取消成功')
      this.fetchAddressList()
    }
  },
}
</script>
