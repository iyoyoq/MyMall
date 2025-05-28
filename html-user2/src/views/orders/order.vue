<template>
  <div style="padding: 20px; width: 800px ">

    <!-- 商品SKU信息列表 -->
    <div style="margin: 0 0 16px 0;">
      <h3>商品信息</h3>
      <div
          v-for="item in orderDetailList"
          :key="item.orderDetail.id"
          style="width: 800px;border: 1px solid #e8e8e8;
           border-radius: 4px; padding: 10px; transition: all 0.3s; min-height: 70px; box-sizing: border-box;">
        <div style="flex: 1; text-align: left; display: flex; align-items: center;
             justify-content: space-between; gap: 24px;">
          <img :src="item.sku.mainImage" alt="商品主图"
               style="width: 50px; height: 50px; object-fit: cover;
          "/>
          <span style="margin: 0; width: 100px; padding: 0 0 0 10px;">{{ item.sku.productName }}</span>
          <span style=" width: 100px; " class="gray-text">{{ item.sku.skuAttrValues }}</span>
          <span>
            <span style=" font-size: 16px; margin: 0; width: 150px;" class="price-color">¥{{
                item.orderDetail.price
              }}</span>
            <span style="margin: auto 30px">×{{ item.orderDetail.quantity }}</span>
          </span>
        </div>
      </div>
    </div>


    <!-- 收货地址 -->
    <div style="margin: 0 0 16px 0;">
      <h3>收货地址</h3>
      <a-select v-model="selectedAddress" style="width: 100%" placeholder="请选择收货地址">
        <a-option v-for="(addr, index) in addressList" :value="addr.id.toString()" :key="addr.id">
          {{ addr.receiverName }} {{ addr.receiverPhone }}
          {{ addr.province }}{{ addr.city }}{{ addr.district }}{{ addr.detailAddress }}
        </a-option>
      </a-select>
    </div>


    <!-- 订单信息 -->
    <div style="margin: 0 0 16px 0;">
      <h3>订单信息</h3>
      <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
        <span>商品总价</span>
        <span>¥{{ order.totalAmount - order.totalShippingFee }}</span>
      </div>
      <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
        <span>运费</span>
        <span>¥{{ order.totalShippingFee }}</span>
      </div>
      <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
        <span>实付金额</span>
        <span>¥{{ order.totalAmount }}</span>
      </div>
    </div>

    <!-- 备注 -->
    <div style="margin: 0 0 16px 0;">
      <h3>订单备注</h3>
      <a-textarea v-model="remark" placeholder="请输入订单备注信息" :auto-size="{ minRows: 2, maxRows: 6 }"/>
    </div>


    <!-- 支付方式 -->
    <div style="margin: 0 0 16px 0;">
      <h3>支付方式</h3>
      <a-radio-group v-model="payMethod">
        <a-radio value="10">积分支付</a-radio>
        <a-radio value="20" disabled>微信支付</a-radio>
        <a-radio value="40" disabled>支付宝</a-radio>
        <a-radio value="30" disabled>银行卡</a-radio>
      </a-radio-group>
    </div>

    <!-- 提交订单 -->
    <div style="text-align: center;">
      <a-button type="primary" size="large" @click="submitOrder" style="min-width: 150px">
        <div>
          <span>去支付</span>
          <span style="font-size: 12px; ">
          ({{ payDdlCountdown }})
        </span>
        </div>
      </a-button>
    </div>


  </div>
</template>

<script>
import { detailOrderApi, goPayApi, userPointsApi } from '@/api/order.js'
import { addressApi } from '@/api/address.js'
import { hasValue } from '@/utils/check.js'
import { getCountdown } from '@/utils/datetime.js'

export default {
  name: 'createOrder',
  data () {
    return {
      orderId: null,
      // 地址信息
      selectedAddress: '',
      addressList: [],
      // 商品信息
      orderDetailList: [],  // orderDetail sku
      // 订单主信息
      order: {},
      remark: '',
      shippingFee: 0,
      payDdlCountdown: null,
      payMethod: '10',
    }
  },
  async mounted () {
    this.orderId = this.$route.query.orderId
    await this.fetchOrderDetail()
    await this.fetchAddressList()
    // 初始更新倒计时
    this.updatePayDdl()
    // 设置定时器，每秒更新一次
    this.timer = setInterval(() => {
      this.updatePayDdl()
    }, 1000)
  },
  beforeDestroy () {
    // 组件销毁前清除定时器
    if (this.timer) {
      clearInterval(this.timer)
    }
  },
  computed: {},
  methods: {
    async fetchAddressList () {
      const resp = await addressApi.list()
      this.addressList = resp.data.result.records
      // console.log('this.addressList', this.addressList)
    },

    async fetchOrderDetail () {
      const resp = await detailOrderApi(this.orderId)
      const result = resp.data.result
      console.log(result)
      if (hasValue(result?.address?.id)) {
        this.selectedAddress = result.address.id.toString()
      }
      this.order = result.order
      this.orderDetailList = result.orderDetailList

    },
    updatePayDdl () {
      this.payDdlCountdown = getCountdown(this.order.payDdl)
      // console.log('this.payDdlCountdown', this.payDdlCountdown)
    },
    async submitOrder () {
      // 提交订单逻辑
      if (!this.selectedAddress) {
        this.$message.error('请选择收货地址')
        return
      }
      //TODO 目前仅支持积分支付
      const goPayApiData = {
        id: this.orderId,
        addressId: this.selectedAddress,
        userRemark: this.remark,
        payMethod:  this.payMethod,
      }
      const userPointsApiResp = await userPointsApi()
      const result = userPointsApiResp.data.result
      console.log('result', result)
      // console.log('goPayApiData', goPayApiData)
      const resp = await goPayApi(goPayApiData)

    },
  },

}
</script>

<style scoped>

</style>
