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
          :key="item.id.toString()"
          style="width: 900px; margin-bottom: 16px; border: 1px solid #e8e8e8; border-radius: 4px; padding: 10px; transition: all 0.3s;
           min-height: 70px; box-sizing: border-box;"
      >
        <div style="display: flex; align-items: center; justify-content: space-between;">
          <a-checkbox
              :default-checked="isItemSelected(item.id)"
              style="margin-right: 16px;"
              @change="(checked) => handleItemSelect(item.id, checked)"
          />
          <img :src="item.mainImage" alt="商品图片"
               style="width: 50px; height: 50px; object-fit: cover;"/>
          <div
              style="flex: 1; text-align: left; display: flex; align-items: center; justify-content: space-between; gap: 24px;">
            <h4 style="color: #000; margin: 0; width: 150px; padding: 0 0 0 10px;">{{ item.productName }}</h4>
            <span style=" width: 100px; " class="gray-text">{{ item.skuAttrValues }}</span>
            <span style=" font-size: 16px; margin: 0; width: 100px;" class="price-color">¥{{
                priceShowDecimalUtil(item.price)
              }}</span>
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
          <a-button style="margin-left: 10px;" type="text" status="danger" @click="removeItem(item)">
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
import { priceShowDecimalUtil } from '@/utils/price.js'
import { createOrderApi } from '@/api/order.js'
import router from '@/router/index.js'

export default {
  name: 'Cart',
  data () {
    return {
      cartItems: [],  // 该页全部项
      querySearch: null,
      total: 0,
      selectedMap: {}, // 新增，存储 skuId: 数量
    }
  },
  computed: {
    totalQuantity () {
      let total = 0
      for (const item in this.selectedMap) {
        // const quantity = this.selectedMap[skuId]
        // total += quantity
        total++   // 这边使用sku数目
      }
      return total
    },
    totalPrice () {
      let total = 0
      for (const skuId in this.selectedMap) {
        // console.log('skuId', skuId)
        const thisItem = this.selectedMap[skuId]
        // console.log('thisItem', thisItem)
        total += thisItem.purchaseQuantity * thisItem.price
      }
      return total
    },
    isItemSelected () {
      return (id) => {
        return this.selectedMap.hasOwnProperty(id)
      }
    }
    ,
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
        // console.log('cartItems', page.records)
        this.total = page.total
      })
    },
    resetQuerySearch () {
      this.querySearch = {
        pageNum: 1,
        pageSize: 10,
      }
    },
    handlePageChange (page) {
      // console.log(page)
      this.querySearch.pageNum = page
      this.fetchCartList()
    },
    // 监听单个复选框变化
    handleItemSelect (id, checked) {

      const item = this.cartItems.find(i => i.id === id)
      if (checked) { // 添加
        // console.log(item.purchaseQuantity)
        this.selectedMap[id] = item
        // console.log('添加后的selectedItems', this.selectedMap)
      } else { // 移除
        delete this.selectedMap[id]
        // console.log('移除后的selectedItems', this.selectedMap)
      }
      // console.log('this.selectedMap', this.selectedMap)
    },
    // 监听数量变化时同步 map
    handleQuantityChange (item, value) {
      item.purchaseQuantity = value
      if (this.selectedMap[item.id] !== undefined) {
        this.selectedMap[item.id] = item
      }
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
    async handleCheckout () {
      // console.log('去结算', this.selectedMap)
      const params = {
        skuIdAndQuantity: {},
      }
      const rmSkuIdList = []
      for (const skuId in this.selectedMap) {
        rmSkuIdList.push(skuId)
        params.skuIdAndQuantity[skuId] = this.selectedMap[skuId].purchaseQuantity
      }
      // 创建订单
      const resp = await createOrderApi(params)
      await cartRemoveApi(rmSkuIdList)
      // 清除选中的购物车商品
      const orderId = resp.data.result
      this.$router.push({
        path: '/order',
        query: {
          orderId: orderId,
        },
      })
    },
  },
}
</script>
