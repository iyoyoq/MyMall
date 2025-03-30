<template>
  <div style="  display: flex; ">
    <div style="margin: 0 auto ;width: 800px;">
      <!-- 地址列表 -->
      <div style="margin-bottom: 80px;">
        <!-- 首行 -->
        <div style=" display: flex;">
          <div style="margin: 20px 0 ">
            <div style=" width: 200px;">
              <var-button type="primary" block @click="handleAdd">添加地址</var-button>
            </div>
          </div>
        </div>
        <var-card v-for="item in addressList" :key="item.id" style="margin-bottom: 16px;">
          <div style="padding: 8px 0;">
            <div style="margin-bottom: 8px;">
              <span style="font-size: 16px; font-weight: bold; margin-right: 12px;">{{ item.receiverName }}</span>
              <span style="color: #666; margin-right: 12px;">{{ item.receiverPhone }}</span>
              <div v-if="item.isDefault === 1" style="display: inline-block; color: #2979ff;">默认</div>
            </div>
            <div style="color: #333; line-height: 1.5;">
              {{ item.province }}{{ item.city }}{{ item.district }}{{ item.detailAddress }}
            </div>
          </div>
          <div style="margin-top: 8px; display: flex; justify-content: flex-end; gap: 16px;">
            <var-button text type="info" @click="handleEdit(item)">编辑</var-button>
            <var-button text type="danger" @click="handleDelete(item.id)">删除</var-button>
          </div>
        </var-card>
      </div>
    </div>

    <!-- 表单弹窗 -->
    <var-popup v-model:show="showEditDialog" position="center" >
      <var-card style="width: 800px" :key="new Date()">
        <template #title>
          <div style="padding: 20px 20px 0 16px; text-align: center">
            <span v-if="editFormType === 1">添加收货地址</span>
            <span v-if="editFormType === 2">修改收货地址</span>
          </div>
        </template>
        <var-form ref="editForm">
          <var-input
              style="width: 200px"
              autocomplete="receiver_name"
              v-model="editForm.receiver_name"
              placeholder="姓名"
          />
          <var-input
              style="width: 200px"
              autocomplete="receiver_phone"
              v-model="editForm.receiver_phone"
              placeholder="手机号"
              :validate-trigger="['onBlur']"
              :rules="v => /^1[3-9]\d{9}$/.test(v) || '请输入正确的手机号'"
              type="number"
          />

          <div style="padding: 10px 0 0 0">收货地址</div>
          <div style="display:flex; justify-content: space-between">
            <div style="width: 200px">
              <!--省市区级联选择框-->
              <var-select placeholder="省级" v-model="editForm.province" @change="handleProvinceChange">
                <var-option v-for="province in areaData" :key="province.text" :label="province.text"
                            :value="province.text"/>
              </var-select>
            </div>
            <div style="width: 200px">
              <!--省市区级联选择框-->
              <var-select 
                placeholder="地市级" 
                v-model="editForm.city" 
                @change="handleCityChange" 
                :disabled="!editForm.province"
              >
                <var-option 
                  v-for="city in getCities(editForm.province)" 
                  :key="city.text" 
                  :label="city.text" 
                  :value="city.text" 
                />
              </var-select>
            </div>
            <div style="width: 200px">
              <!--省市区级联选择框-->
              <var-select 
                placeholder="县区级" 
                v-model="editForm.district" 
                :disabled="!editForm.city"
              >
                <var-option 
                  v-for="district in getDistricts(editForm.province, editForm.city)" 
                  :key="district.text" 
                  :label="district.text" 
                  :value="district.text" 
                />
              </var-select>
            </div>
          </div>

          <var-input
              autocomplete="detail_address"
              v-model="editForm.detail_address"
              placeholder="详细地址"
              textarea
              :rows="2"
          />
          <div style="margin: 20px 0 0 0">
            <var-button
                type="primary"
                block
                @click="handleSubmitEdit"
                :loading="editBtnSubmitting"
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

import { addressPageApi } from '@/api/auth.js'
import areaJson from '@varlet/ui/json/area.json'

export default {
  name: 'address',
  setup () {
    // 重置表单内容
    function resetForm () {
      this.editForm = {
        id: '',
        user_id: '',
        receiver_name: '',
        receiver_phone: '',
        province: '',
        city: '',
        district: '',
        detail_address: '',
        is_default: '',
        status: '',
      }
    }
    return {
      resetForm
    }
  },
  data () {
    return {
      addressList: [],
      showEditDialog: false,  // 表单弹窗
      editBtnSubmitting: false,
      editFormType: 1,  // 表单类型 1新增 2编辑
      editForm: null,
      areaData: areaJson,  // 确保这里正确引入 areaJson
    }
  },

  methods: {

    // 处理省级选择变化
    handleProvinceChange () {
      this.editForm.city = '' // 清空市级选择
      this.editForm.district = '' // 清空县级选择
    },
    // 处理市级选择变化
    handleCityChange () {
      this.editForm.district = '' // 清空县级选择
    },
    // 获取城市列表
    getCities (province) {
      const selectedProvince = this.areaData.find(item => item.text === province)
      return selectedProvince ? selectedProvince.children : []
    },
    // 获取区县列表
    getDistricts (province, city) {
      const selectedProvince = this.areaData.find(item => item.text === province)
      if (selectedProvince) {
        const selectedCity = selectedProvince.children.find(item => item.text === city)
        return selectedCity ? selectedCity.children : []
      }
      return []
    },
    // 表单提交
    handleSubmitEdit () {

    },
    // 获取地址列表
    async getAddressList () {
      try {
        const res = await addressPageApi({
          pageNum: 1,
          pageSize: 10,
        })
        this.addressList = res.data.result.records
      } catch (error) {
        console.error('获取地址列表失败：', error)
      }
    },

    // 编辑地址
    handleEdit (address) {
      this.showEditDialog = true
      this.editForm = { ...address } // 将选中的地址信息填入表单
    },

    // 删除地址
    handleDelete (id) {
      // TODO: 调用删除API
      console.log('删除地址：', id)
    },

    // 新增地址
    handleAdd () {
      this.showEditDialog = true
      this.resetForm() // 重置表单内容
    },
  },

  mounted () {
    this.resetForm()
    this.getAddressList()
  },
}
</script>

<style scoped>
.address-page {
  padding: 16px;
  background: #f5f5f5;
  min-height: 100vh;
  width: 100%;

}

.address-list {
  margin-bottom: 80px;
}

.address-item {
  margin-bottom: 16px;
}

.address-content {
  padding: 8px 0;
}

.user-info {
  margin-bottom: 8px;
}

.name {
  font-size: 16px;
  font-weight: bold;
  margin-right: 12px;
}

.phone {
  color: #666;
  margin-right: 12px;
}

.address-detail {
  color: #333;
  line-height: 1.5;
}

.operations {
  margin-top: 8px;
  display: flex;
  justify-content: flex-end;
  gap: 16px;
}

.add-button {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 16px;
  background: #fff;
}
</style>
