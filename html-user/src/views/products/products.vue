<template>
  <div class="products-container">
    <!-- 搜索框 -->
    <div class="product-header">
      <div class="search-box">
        <var-form>
          <var-input
              v-model="searchKeyword"
              placeholder="搜索商品"
              clearable
          >
            <template #append-icon>
              <var-icon name="magnify"/>
            </template>
          </var-input>
        </var-form>
      </div>

      <!-- 分类选择 -->
      <div class="category-tabs">
        <var-tabs v-model:active="activeCategory">
          <var-tab>全部</var-tab>
          <var-tab v-for="category in categories" :key="category.id">
            {{ category.name }}
          </var-tab>
        </var-tabs>
      </div>
    </div>

    <div class="product-content">
      <!-- 商品列表 -->
      <div class="products-grid">
        <var-card
            v-for="product in displayedProducts"
            :key="product.id"
            class="product-card"
        >
          <img :src="product.image" class="product-image"/>
          <div class="product-title">{{ product.title }}</div>
          <div class="product-price">¥{{ product.price }}</div>
        </var-card>
      </div>

      <!-- 分页 -->
      <var-pagination
          v-model:current="currentPage"
          :total="total"
          :size="pageSize"
          @change="handlePageChange"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'Products',

  data () {
    return {
      searchKeyword: '',
      activeCategory: 0, // 修改为数字类型
      currentPage: 1,
      total: 100,
      pageSize: 24,
      products: [],
      displayedProducts: [
        // 示例数据
        {
          id: 1,
          title: 'HUAWEI Mate X5',
          price: 3599.00,
          image: 'path/to/image',
        },
        // ... 其他商品数据
      ],
      categories: [
        { id: 1, name: '手机数码' },
        { id: 2, name: '电脑办公' },
        { id: 3, name: '家用电器' },
        { id: 4, name: '服装鞋包' },
        { id: 5, name: '食品生鲜' },
      ],
    }
  },

  methods: {
    handlePageChange (page) {
      this.currentPage = page
      // TODO: 加载对应页码的商品数据
    },
  },

  mounted () {
    // TODO: 初始化时加载商品数据
  },
}
</script>




<style lang="scss" scoped>
.products-container {
  padding: 16px;

  .product-header {
    display: flex;
    .search-box {
      margin-bottom: 16px;
      width: 200px;
    }

    .category-tabs {
      margin: 8px 0 16px 0;
      width: 500px;
    }
  }

  .product-content {
    .products-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 16px;
      margin-bottom: 24px;

      .product-card {
        cursor: pointer;

        .product-image {
          width: 100%;
          height: 200px;
          object-fit: cover;
        }

        .product-title {
          margin: 8px 0;
          font-size: 14px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }

        .product-price {
          color: #f44336;
          font-size: 16px;
          font-weight: bold;
        }
      }
    }
  }
}
</style>

