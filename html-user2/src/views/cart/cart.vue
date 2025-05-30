<template>
  <div style="padding: 10px; min-height: 360px;">
    <!-- 空购物车提示 -->
    <div
        v-if="!cartItems.length"
        class="gray-text"
        style="text-align: center;  padding: 32px 0;"
    >购物车是空的
    </div>


    <div v-else>
      <!-- 结算栏-->
      <div
          style="max-width: 900px; margin: 0 auto; background: #fff;
        padding: 0 24px 16px 24px; display: flex; justify-content: space-between; align-items: center;"
      >
        <div style="display: flex; align-items: center;">
          <a-checkbox
              v-if="!isEditMode"
              :model-value="isAllSelected"
              :indeterminate="isIndeterminate"
              @change="handleSelectAll"
          >全选
          </a-checkbox>
        </div>
        <div style="display: flex; align-items: center; gap: 24px;">
        <span style="font-size: 18px; font-weight: bold;">
          <!--总计: -->
          <span class="price-color"> ¥{{ priceShowDecimalUtil(totalPrice) }}</span>
        </span>
          <a-button type="primary" size="medium" @click="handleCheckout">
            去结算({{ totalQuantity }}件)
          </a-button>
        </div>
      </div>

      <!-- 商品列表 -->
      <div
          v-for="item in cartItems"
          :key="item.id"
          style="width: 900px; margin-bottom: 16px; border: 1px solid #e8e8e8; border-radius: 4px; padding: 10px; transition: all 0.3s;
           min-height: 70px; box-sizing: border-box;"
      >
        <div style="display: flex; align-items: center; justify-content: space-between;">
          <a-checkbox
              v-model="selectedItems"
              :value="item.id"
              style="margin-right: 16px;"
          />
          <img :src="item.mainImage" alt="商品图片"
               style="width: 50px; height: 50px; object-fit: cover;
          "/>
          <div
              style="flex: 1; text-align: left; display: flex; align-items: center; justify-content: space-between; gap: 24px;">
            <h4 style="color: #000; margin: 0; width: 150px; padding: 0 0 0 10px;">{{ item.productName }}</h4>
            <span style=" width: 100px; " class="gray-text">{{ item.skuAttrValues }}</span>
            <span style=" font-size: 16px; margin: 0; width: 100px;" class="price-color">¥{{ priceShowDecimalUtil(item.price) }}</span>
            <div style="display: flex; align-items: center; gap: 16px;">
              <span style="font-size: 14px; width: 100px;" class="gray-text">库存: {{ item.stockQuantity }}</span>
              <a-input-number
                  style="width: 150px;"
                  mode="button"
                  v-model="item.purchaseQuantity"
                  :min="1"
                  :max="1000000"
                  size="small"
                  @change="(value) => handleQuantityChange(item, value)"
              />
            </div>
          </div>
          <a-button style="margin-left: 10px;" v-if="!isEditMode" type="text" status="danger" @click="removeItem(item)">
            删除
          </a-button>
        </div>
      </div>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <div class="pagination-section">
          <a-pagination
              v-model:page-size="querySearch.pageSize"
              v-model:current="querySearch.pageNum"
              :total="total"
              show-total
              show-jumper
              @change="handlePageChange"
          />
        </div>
      </div>
    </div>


  </div>
</template>

<script>
import { cartListApi, cartRemoveApi } from '@/api/cart.js'
import { Message, Modal } from '@arco-design/web-vue'
import { priceShowDecimalUtil } from '../../utils/price.js'

export default {
  name: 'Cart',
  data () {
    return {
      isEditMode: false,
      selectedItems: [],
      cartItems: [],
      querySearch: null,
      total: 0,
    }
  },
  computed: {
    //todo 选择结算box框
    isAllSelected () {
      return this.cartItems.length === this.selectedItems.length
    },
    isIndeterminate () {
      return this.selectedItems.length > 0 && !this.isAllSelected
    },
    totalQuantity () {
      return this.cartItems.reduce((sum, item) => sum + item.purchaseQuantity, 0)
    },
    totalPrice () {
      return this.cartItems.reduce((sum, item) => sum + (item.purchaseQuantity * item.price), 0)
    },
  },
  created () {
    this.resetQuerySearch()
    this.fetchCartList()
  },
  methods: {
    priceShowDecimalUtil,
    fetchCartList () {
      cartListApi(this.querySearch).then(res => {
        const page = res.data.result.page
        this.cartItems = page.records
        this.total = page.total
      })
    },
    resetQuerySearch () {
      this.querySearch = {
        pageNum: 1,
        pageSize: 10,
      }
    },
    handlePageChange () {

    },
    toggleEditMode () {
      this.isEditMode = !this.isEditMode
      this.selectedItems = []
    },
    handleSelectAll (checked) {
      this.selectedItems = checked
          ? this.cartItems.map(item => item.id)
          : []
    },
    handleBatchDelete () {
      this.cartItems = this.cartItems.filter(
          item => !this.selectedItems.includes(item.id),
      )
      this.selectedItems = []
    },
    // +-*/ 确认框宽度400
    removeItem (item) {
      Modal.open({
        title: '提示',
        content: '确定删除？',
        okText: '确定',
        cancelText: '取消',
        width: 400,
        onOk: async () => {
          await cartRemoveApi([item.id])
          await this.fetchCartList()
          Message.success('删除成功')
        },
        onCancel: () => {
        },
      })
    },
    handleQuantityChange (item, value) {
      item.quantity = value
    },
    handleCheckout () {
      // TODO: 实现结算逻辑
      console.log('去结算', this.cartItems)
    },
  },
}
</script>
