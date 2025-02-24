<template>
  <div class="favorites-container">
    <div class="header">
      <span>
        共<span class="highlight">{{ favoritesList.length }}</span>件商品
      </span>
      <a-button
        size="small"
        type="primary"
        style="margin-left: 10px"
        @click="toggleEditMode"
      >
        {{ isEditMode ? '完成' : '编辑' }}
      </a-button>
    </div>
    <div v-if="!favoritesList.length" class="empty-tip">暂无收藏</div>
    <div v-else class="favorites-list">
      <div class="batch-actions" v-if="isEditMode">
        <a-checkbox
          :model-value="isAllSelected"
          :indeterminate="isIndeterminate"
          @change="handleSelectAll"
        >全选</a-checkbox>
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
      <div v-for="item in favoritesList" :key="item.id" class="favorite-item">
        <div class="favorite-content">
          <a-checkbox
            v-if="isEditMode"
            v-model="selectedItems"
            :value="item.id"
            class="item-checkbox"
          />
          <img :src="item.image" alt="商品图片" class="product-image" />
          <div class="product-info">
            <h4>{{ item.title }}</h4>
            <p class="price">¥{{ item.price }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Favorites',
  data() {
    return {
      isEditMode: false,
      selectedItems: [],
      favoritesList: [
        {
          id: 1,
          title: '2024新款连衣裙',
          price: 199.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        },
        {
          id: 2,
          title: '时尚休闲运动鞋',
          price: 299.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        },
        {
          id: 3,
          title: '韩版时尚背包',
          price: 159.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        },
        {
          id: 4,
          title: '纯棉休闲T恤',
          price: 89.00,
          image: 'https://img1.baidu.com/it/u=3234918589,2701109302&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667'
        }
      ]
    }
  },
  computed: {
    isAllSelected() {
      return this.favoritesList.length === this.selectedItems.length
    },
    isIndeterminate() {
      return this.selectedItems.length > 0 && !this.isAllSelected
    }
  },
  methods: {
    toggleEditMode() {
      this.isEditMode = !this.isEditMode
      this.selectedItems = []
    },
    handleSelectAll(checked) {
      this.selectedItems = checked
        ? this.favoritesList.map(item => item.id)
        : []
    },
    handleBatchDelete() {
      // TODO: 调用接口删除选中的收藏项
      this.favoritesList = this.favoritesList.filter(
        item => !this.selectedItems.includes(item.id)
      )
      this.selectedItems = []
    }
  }
}
</script>

<style scoped>
.favorites-container {
  padding: 25px;
  min-height: 360px;
}

.header {
  display: flex;
  justify-content: start;
  align-items: center;
  margin-bottom: 16px;
}

.header span {
  color: #333;
}

.highlight {
  color: var(--color-primary) !important;
}


.empty-tip {
  text-align: center;
  color: var(--color-primary-hover);
  padding: 32px 0;
}

.favorite-item {
  width: 800px;
  margin-bottom: 16px;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 10px;
  transition: all 0.3s;
  height: 70px;
}

.favorite-item:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.batch-actions {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}

.item-checkbox {
  margin-right: 16px;
}

.favorite-content {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.product-image {
  width: 70px;
  height: 70px;
  object-fit: cover;
  margin-right: 24px;
}

.product-info {
  flex: 1;
  text-align: left;
}

.product-info h4 {
  color: #000;
  margin: 0;
}

.price {
  color: var(--color-primary-6);
  font-size: 16px;
  margin-top: 8px;
  text-align: left;
}

.favorites-container h2 {
  color: #000;
  margin-top: 0px;
  margin-left: 10px;
  text-align: left;
}
</style>
