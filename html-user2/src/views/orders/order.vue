<template>
  <div style="padding: 20px; width: 800px ">

    <!-- 商品SKU信息列表 -->
    <div style="margin: 0 0 16px 0;">
      <h3>商品信息</h3>
      <div
          v-for="item in orderSkuItems"
          :key="item.id"
          style="width: 800px;border: 1px solid #e8e8e8;
           border-radius: 4px; padding: 10px; transition: all 0.3s; min-height: 70px; box-sizing: border-box;">
        <div style="flex: 1; text-align: left; display: flex; align-items: center;
             justify-content: space-between; gap: 24px;">
          <img :src="item.mainImage" alt="商品主图"
               style="width: 50px; height: 50px; object-fit: cover;
          "/>
          <span style="margin: 0; width: 100px; padding: 0 0 0 10px;">{{ item.productName }}</span>
          <span style=" width: 100px; " class="gray-text">{{ item.skuAttrValues }}</span>
          <span>
            <span style=" font-size: 16px; margin: 0; width: 150px;" class="price-color">¥{{ item.price }}</span>
            <span style="margin: auto 30px">×{{item.quantity}}</span>
          </span>
        </div>
      </div>
    </div>


    <!-- 收货地址 -->
    <div style="margin: 0 0 16px 0;">
    <h3>收货地址</h3>
    <a-select v-model="selectedAddress" style="width: 100%" placeholder="请选择收货地址">
      <a-option v-for="addr in addressList" :key="addr.id" :value="addr.id">
        {{ addr.receiverName }} {{ addr.receiverPhone }} {{ addr.province }}{{ addr.city }}{{
          addr.district
        }}{{ addr.detailAddress }}
      </a-option>
    </a-select>
    </div>

    <!-- 订单信息 -->
    <div style="margin: 0 0 16px 0;">
      <h3 style="margin: 0 0 16px 0;">订单信息</h3>
      <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
        <span>商品总价</span>
        <span>¥{{ totalPrice }}</span>
      </div>
      <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
        <span>运费</span>
        <span>¥{{ shippingFee }}</span>
      </div>
      <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
        <span>实付金额</span>
        <span>¥{{ totalPrice + shippingFee }}</span>
      </div>
    </div>

    <!-- 备注 -->
    <div style="margin: 0 0 16px 0;">
      <h3 >订单备注</h3>
      <a-textarea v-model="remark" placeholder="请输入订单备注信息" :auto-size="{ minRows: 2, maxRows: 6 }"/>
    </div>


    <!-- 支付方式 -->
    <div style="margin: 0 0 16px 0;">
      <h3 >支付方式</h3>
      <a-radio-group>
        <a-radio value="A">积分支付</a-radio>
        <a-radio value="B">微信支付</a-radio>
        <a-radio value="C">支付宝</a-radio>
        <a-radio value="D">银行卡</a-radio>
      </a-radio-group>
    </div>

    <!-- 提交订单 -->
    <div style="text-align: center;">
      <a-button type="primary" size="large" @click="submitOrder">提交订单</a-button>
    </div>



  </div>
</template>

<script>
export default {
  name: 'createOrder',
  data () {
    return {
      selectedAddress: null,
      addressList: [],
      orderSkuItems: [
        {
          id: 1,
          mainImage: 'http://localhost:45001/mymall_files/dd480ed3-87b2-4032-ab78-fd2d0770db24/20250505224143.png',
          productName: '商品名称',
          skuAttrValues: '黑,xl',
          price: 100,
          quantity: 1
        },
      ],
      remark: '',
      shippingFee: 0,
    }
  },
  computed: {
    totalPrice () {
      return this.orderSkuItems.reduce((sum, item) => sum + (item.price * item.purchaseQuantity), 0)
    },
  },
  methods: {
    submitOrder () {
      // 提交订单逻辑
      if (!this.selectedAddress) {
        this.$message.error('请选择收货地址')
        return
      }
      // TODO: 调用创建订单API
    },
  },
  created () {
    // TODO: 获取地址列表
    // TODO: 获取订单商品信息
  },
}
</script>

<style scoped>

</style>
