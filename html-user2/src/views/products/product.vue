<template>
  <div class="product-detail-container">
    <div class="product-main">
      <div class="product-images">
        <img :src="product.mainImage" alt="主图" class="main-image"/>
        <div class="image-list" v-if="imageList.length > 0">
          <img
              v-for="(img, idx) in imageList"
              :key="idx"
              :src="img"
              class="sub-image"
              @click="product.mainImage = img"
              :class="{ active: img === product.mainImage }"
          />
        </div>
      </div>
      <div class="product-info">
        <h2 class="product-title">{{ product.name }}</h2>
        <div class="product-desc">{{ product.description }}</div>
        <div class="product-price">
          <span>￥{{
              selectedSku ? (selectedSku.price / 100).toFixed(2) : (product.startingPrice / 100).toFixed(2)
            }}</span>
        </div>
        <div class="product-sku" v-if="skuAttrNames.length">
          <div v-for="(attr, idx) in skuAttrNames" :key="attr" class="sku-attr">
            <span>{{ attr }}：</span>
            <div class="sku-attr-values">
              <span
                  v-for="(value, vIdx) in getAttrValues(idx)"
                  :key="value"
                  :class="['sku-value', { selected: selectedAttr[idx] === value, disabled: isSkuDisabled(idx, value) }]"
                  @click="selectAttr(idx, value)"
              >{{ value }}</span>
            </div>
          </div>
        </div>
        <div class="product-stock">
          库存：{{ selectedSku ? selectedSku.stockQuantity : '-' }}
        </div>
        <div class="product-actions">
          <a-button type="primary" :disabled="!selectedSku || selectedSku.stockQuantity === 0">立即购买</a-button>
        </div>
      </div>
    </div>
    <div class="product-detail-desc">
      <h3>商品详情</h3>
      <div>{{ product.description }}</div>
    </div>
  </div>
</template>
<script>
import { productApi } from '@/api/product.js'

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
    }
  },
  created () {
    this.fetchDetail()
  },
  methods: {
    fetchDetail () {
      productApi(this.productId).then((resp) => {
        const data = resp.data.result
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
      this.$set(this.selectedAttr, attrIdx, value)
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
  },
}
</script>

<style scoped>
.product-detail-container {
  padding: 30px;
  background: #fff;
}

.product-main {
  display: flex;
  gap: 40px;
}

.product-images {
  width: 400px;
}

.main-image {
  width: 100%;
  height: 400px;
  object-fit: contain;
  border: 1px solid #eee;
  background: #fafafa;
}

.image-list {
  display: flex;
  gap: 8px;
  margin-top: 10px;
}

.sub-image {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border: 2px solid #eee;
  cursor: pointer;
}

.sub-image.active {
  border-color: rgb(var(--primary-6));
}

.product-info {
  flex: 1;
}

.product-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 12px;
}

.product-desc {
  color: #666;
  margin-bottom: 16px;
}

.product-price {
  color: var(--mymall-price-color);
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 16px;
}

.product-sku {
  margin-bottom: 16px;
}

.sku-attr {
  margin-bottom: 8px;
}

.sku-attr-values {
  display: inline-block;
}

.sku-value {
  display: inline-block;
  padding: 4px 14px;
  margin-right: 8px;
  margin-bottom: 4px;
  border: 1px solid #ddd;
  border-radius: 3px;
  cursor: pointer;
  background: #fafafa;
  transition: border-color 0.2s;
}

.sku-value.selected {
  border-color: #409eff;
  background: #e6f7ff;
}

.sku-value.disabled {
  color: #bbb;
  border-color: #eee;
  cursor: not-allowed;
  background: #f5f5f5;
}

.product-stock {
  margin-bottom: 16px;
  color: #888;
}

.product-actions {
  margin-bottom: 16px;
}

.product-detail-desc {
  margin-top: 40px;
  padding: 20px;
  background: #fafafa;
  border-radius: 6px;
}
</style>
