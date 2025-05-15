<template>
  <div
      style="padding:30px;background:#fff;"
  >
    <div
        style="display:flex;gap:40px;"
    >
      <!--左半部分-->
      <div style="width:400px; height: 400px; ">
        <a-image
            width="400"
            height="400"
            :src="product.mainImage"
            alt="主图"
            style="width:400px;height:100%;object-fit:contain;border:1px solid #eee;background:#fafafa;"
        />
        <div
            v-if="imageList.length > 0"
            style="display:flex;gap:8px;margin-top:10px;"
        >
          <img
              v-for="(img, idx) in imageList"
              :key="idx"
              :src="img"
              @click="product.mainImage = img"
              :class="{ active: img === product.mainImage }"
              :style="`
              width:60px;
              height:60px;
              object-fit:cover;
              border:2px solid ${img === product.mainImage ? 'rgb(var(--primary-6))' : '#eee'};
              cursor:pointer;
            `"
          />
        </div>
      </div>

      <!--右半部分-->
      <div style="flex:1;">
        <h2 style="font-size:24px;font-weight:bold;margin-bottom:12px;">
          {{ product.name }}
        </h2>
        <div style="margin-bottom:16px; " class="gray-text">
          {{ product.description }}
        </div>
        <div>
          <span
              style="color:var(--mymall-price-color);font-size:28px;font-weight:bold;margin-bottom:16px;display:inline-block;">
            ￥{{ selectedSku ? (selectedSku.price / 100).toFixed(2) : (product.startingPrice / 100).toFixed(2) }}
          </span>
          <span style="margin-left: 10px" class="gray-text">
            已售{{ product.salesCount }}
          </span>
        </div>
        <div v-if="skuAttrNames.length">
          <div
              v-for="(attr, idx) in skuAttrNames"
              :key="attr"
              class="attr-bottom"
          >
            <span class="gray-text attr-right">{{ attr }}</span>
            <div style="display:inline-block;">
              <span
                  v-for="(value, vIdx) in getAttrValues(idx)"
                  :key="value"
                  :class="['sku-value', { selected: selectedAttr[idx] === value, disabled: isSkuDisabled(idx, value) }]"
                  @click="selectAttr(idx, value)"
                  :style="`
                  display:inline-block;
                  padding:4px 14px;
                  margin-right:8px;
                  margin-bottom:4px;
                  border:1px solid ${selectedAttr[idx] === value ? 'rgb(var(--primary-6))' : isSkuDisabled(idx, value) ? '#eee' : '#ddd'};
                  border-radius:3px;
                  cursor:${isSkuDisabled(idx, value) ? 'not-allowed' : 'pointer'};
                  background:${selectedAttr[idx] === value ? 'rgb(var(--primary-1))' : isSkuDisabled(idx, value) ? '#f5f5f5' : '#fafafa'};
                  color:${selectedAttr[idx] === value ? 'rgb(var(--primary-6))' : isSkuDisabled(idx, value) ? '#bbb' : 'inherit'};
                  transition:border-color 0.2s;
                `"
              >
                {{ value }}
              </span>
            </div>
          </div>
        </div>
        <div class="attr-bottom " style="display:flex; align-items: center; ">
          <div class="gray-text attr-right">数量</div>
          <div class="attr-right">
            <a-input-number size="small" v-model="purchaseQuantity" style="width: 120px" :min="1" :default-value="1"
                            mode="button"/>
          </div>
          <div class="gray-text"><span>库存</span>{{ selectedSku ? selectedSku.stockQuantity : '-' }}</div>
        </div>
        <div style="margin-bottom:16px; display: flex">
          <div>
            <!--加入购物车-->
            <a-button :loading="loadingAddCart" @click="addCart" type="outline"
                      :disabled="!selectedSku || selectedSku.stockQuantity === 0">
              加入购物车
            </a-button>
          </div>
          <div style="width: 150px; margin: 0 0 0 10px">
            <a-button long type="primary" :disabled="!selectedSku || selectedSku.stockQuantity === 0">立即购买
            </a-button>
          </div>

          <!--收藏按钮-->
          <div style=" margin: 0 0 0 10px;">
            <a-button @click="clickFavorite(product.userIsFavorite)" type="dashed"
                      :status="product.userIsFavorite ? 'warning':'primary'">
              <template #icon>
                <icon-star-fill/>
              </template>
            </a-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { addFavoriteApi, favoriteCancelApi, productApi } from '@/api/product.js'
import { Message } from '@arco-design/web-vue'
import { cartAddApi } from '@/api/cart.js'

export default {
  name: 'product',
  data () {
    return {
      productId: this.$route.query.productId,
      product: {},
      imageList: [],
      skuAttrNames: [],
      skuList: [],
      selectedAttr: [],
      selectedSku: null,
      purchaseQuantity: 1,
      loadingAddCart: false,
    }
  },
  created () {
    this.fetchDetail()
  },
  methods: {
    addCart () {
      console.log('选中的sku', this.selectedSku)
      console.log('选择数量', this.purchaseQuantity)
      this.loadingAddCart = true
      cartAddApi({
        skuId: this.selectedSku.id,
        purchaseQuantity: this.purchaseQuantity,
        skuCode: this.selectedSku.code,
      }).then((resp) => {
        Message.success('添加至购物车成功')
      }).catch((error) => {
        Message.error('添加至购物车失败')
      }).finally(() => {
        this.loadingAddCart = false
      })
    },
    fetchDetail () {
      productApi(this.productId).then((resp) => {
        const data = resp.data.result
        // console.log('detail', data)
        this.product = data
        this.imageList = data.images ? data.images.split(',') : []
        this.skuAttrNames = data.skuAttrNames ? data.skuAttrNames.split(',') : []
        this.skuList = data.sku && data.sku.skuList ? data.sku.skuList : []
        this.selectedAttr = Array(this.skuAttrNames.length).fill(null)
        this.selectedSku = null
      })
    },
    getAttrValues (attrIdx) {
      // 获取某个属性下所有可选值
      const values = new Set()
      this.skuList.forEach(sku => {
        const val = sku.skuAttrValueList[attrIdx]
        if (val) values.add(val)
      })
      return Array.from(values)
    },
    selectAttr (attrIdx, value) {
      if (this.isSkuDisabled(attrIdx, value)) return
      this.selectedAttr[attrIdx] = value
      this.updateSelectedSku()
    },
    updateSelectedSku () {
      // 选中的属性值组合，查找sku
      if (this.selectedAttr.every(v => v)) {
        this.selectedSku = this.skuList.find(sku =>
            sku.skuAttrValueList.every((val, idx) => val === this.selectedAttr[idx]),
        )
      } else {
        this.selectedSku = null
      }
    },
    isSkuDisabled (attrIdx, value) {
      // 判断某个属性值是否可选（无库存则禁用）
      const testAttr = [...this.selectedAttr]
      testAttr[attrIdx] = value
      // 只要有一个sku匹配且有库存，则可选
      return !this.skuList.some(sku =>
          sku.skuAttrValueList.every((val, idx) => {
            if (testAttr[idx]) return testAttr[idx] === val
            return true
          }) && sku.stockQuantity > 0,
      )
    },
    clickFavorite (currentIsFavorite) {

      if (!currentIsFavorite) {
        addFavoriteApi({
          productId: this.productId,
        }).then(resp => {
          this.product.userIsFavorite = true
          Message.success('收藏成功')
        })
      } else {
        favoriteCancelApi(this.productId).then(resp => {
          this.product.userIsFavorite = false
          Message.success('取消收藏成功')
        })
      }

    },
  },
}
</script>
<style scoped>
.attr-bottom {
  margin-bottom: 16px;
}

.attr-right {
  margin-right: 8px;
}
</style>
