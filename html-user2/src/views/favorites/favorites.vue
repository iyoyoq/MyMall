<template>
  <div v-if="!loading">
    <div style="padding:25px;min-height:360px;">
      <div v-if="favoritesList.length"
           style="display:flex;justify-content:start;align-items:center;margin-bottom:16px;">
        <span class="gray-text-deeper">
          共
          <span>{{ total }}</span>
          件商品
        </span>
        <a-button
            size="small"
            type="primary"
            style="margin-left:10px;"
            @click="toggleEditMode"
        >
          {{ isEditMode ? '完成' : '编辑' }}
        </a-button>
      </div>
      <div v-if="!favoritesList.length" style="text-align:center;padding:32px 0; " class="gray-text">
        暂无收藏
      </div>
      <div v-else style="overflow-x:auto;">
        <div v-if="isEditMode" style="display:flex;align-items:center;gap:16px;margin-bottom:16px;">
          <a-checkbox
              :model-value="isAllSelected"
              :indeterminate="isIndeterminate"
              @change="handleSelectAll"

          ><span class="gray-text-deeper"> 全选</span>
          </a-checkbox>
          <a-button
              type="primary"
              status="danger"
              @click="handleBatchDelete"
              :disabled="!selectedItems.length"
              size="small"
          >
            取消收藏({{ selectedItems.length }})
          </a-button>
        </div>
        <a-checkbox-group v-if="isEditMode" v-model="selectedItems">
          <div
              v-for="item in favoritesList"
              :key="item.id"
              style="width:800px;margin-bottom:16px;border:1px solid #e8e8e8;border-radius:4px;padding:10px;transition:all 0.3s;height:70px;box-shadow:0 2px 8px rgba(0,0,0,0.09);"
          >
            <div style="display:flex;align-items:center;justify-content:flex-start;">
              <a-checkbox
                  :value="item.id"
                  style="margin-right:16px;"
              />
              <img :src="item.product.mainImage" alt="商品图片"
                   style="width:70px;height:70px;object-fit:cover;margin-right:24px;"/>
              <div style="flex:1;text-align:left;">
                <h4 style="color:#000;margin:0;">{{ item.product.name }}</h4>
                <p style="color:var(--mymall-price-color);font-size:16px;margin-top:8px;text-align:left;">
                  ¥{{ priceShowDecimalUtil(item.product.startingPrice) }}
                </p>
              </div>
            </div>
          </div>
        </a-checkbox-group>
        <template v-else>
          <div
              v-for="item in favoritesList"
              :key="item.id"
              style="width:800px;margin-bottom:16px;border:1px solid #e8e8e8;border-radius:4px;padding:10px;transition:all 0.3s;height:70px;box-shadow:0 2px 8px rgba(0,0,0,0.09);cursor:pointer;"
              @click="goProductDetail(item.product)"
          >
            <div style="display:flex;align-items:center;justify-content:flex-start;">
              <img :src="item.product.mainImage" alt="商品图片"
                   style="width:70px;height:70px;object-fit:cover;margin-right:24px;"/>
              <div style="flex:1;text-align:left;">
                <h4 style="color:#000;margin:0;">{{ item.product.name }}</h4>
                <p style="color:var(--mymall-price-color);font-size:16px;margin-top:8px;text-align:left;">
                  ¥{{ priceShowDecimalUtil(item.product.startingPrice) }}
                </p>
              </div>
            </div>
          </div>
        </template>


        <!-- 分页 -->
        <div class="pagination-wrapper">
          <div class="pagination-section">
            <a-pagination
                v-model:page-size="queryParams.pageSize"
                v-model:current="queryParams.pageNum"
                :total="total"
                show-total
                show-jumper
                @change="handlePageChange"
            />
          </div>
        </div>

      </div>
    </div>




  </div>
</template>

<script>
import { favoriteBatchCancelApi, listFavoriteApi } from '@/api/product.js'
import { Message } from '@arco-design/web-vue'
import router from '@/router/index.js'
import { priceShowDecimalUtil } from '@/utils/price.js'

export default {
  name: 'Favorites',
  data () {
    return {
      isEditMode: false,
      selectedItems: [],
      favoritesList: [],
      queryParams: null,
      loading: true,
      total: 0,
    }
  },
  created () {
    this.resetQueryParams()
    this.fetchList()
  },
  computed: {
    isAllSelected () {
      return this.favoritesList.length > 0 && this.favoritesList.length === this.selectedItems.length
    },
    isIndeterminate () {
      return this.selectedItems.length > 0 && !this.isAllSelected
    },
  },
  methods: {
    priceShowDecimalUtil,
    handlePageChange (page) {
      // console.log(page)
      this.queryParams.pageNum = page
      this.fetchList()
    },
    resetQueryParams () {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
      }
    },
    async fetchList () {
      const resp = await listFavoriteApi(this.queryParams)
      const result = resp.data.result
      // console.log(result)
      this.total = result.total
      // 适配接口返回结构，并将 id 转为字符串
      this.favoritesList = (result.records || []).map(item => ({
        id: String(item.id),
        product: item.product,
      }))
      this.loading = false
    },
    toggleEditMode () {
      this.isEditMode = !this.isEditMode
      this.selectedItems = []
    },
    handleSelectAll (checked) {
      this.selectedItems = checked
          ? this.favoritesList.map(item => item.id)
          : []
    },
    async handleBatchDelete () {
      // console.log('选中的idList', this.selectedItems)
      await favoriteBatchCancelApi(this.selectedItems)
      Message.success('取消收藏成功')
      this.fetchList()
      this.selectedItems = []
    },
    goProductDetail (product) {
      router.push({
        path: '/product',
        query: {
          productId: product.id,
        },
      })
    },
  },
}
</script>
