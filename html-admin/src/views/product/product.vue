<template>
  <div class="container">
    <div class="header">
      <a-row>
        <a-col :span="12">
          <a-button type="primary" @click="openAddModal(null)">
            <template #icon>
              <icon-plus/>
            </template>
            添加商品
          </a-button>
        </a-col>
        <a-col :span="12" style="text-align: right">
          <a-space>
            <product-category-selector
                style="width: 250px"
                v-model="productListApiParam.categoryId"
                @change="handleSearch"
            />
            <a-select
                v-model="productListApiParam.status"
                placeholder="请选择状态"
                style="width: 120px"
                allow-clear
                @change="handleSearch"
            >
              <a-option :value="1">上架</a-option>
              <a-option :value="0">下架</a-option>
              <!--<a-option :value="-1">已删除</a-option>-->
            </a-select>
            <a-input-search
                v-model="productListApiParam.name"
                placeholder="请输入商品名称"
                style="width: 200px"
                @search="handleSearch"
            />
          </a-space>
        </a-col>
      </a-row>
    </div>

    <a-table
        :loading="loading"
        :data="tableData"
        :pagination="pagination"
        @page-change="onPageChange"
    >
      <template #columns>
        <a-table-column title="商品名称" data-index="name">
          <template #cell="{ record }">
            <div class="product-info">
              <a-image
                  :src="record.mainImage"
                  :preview-visible="false"
                  width="50"
                  height="50"
                  fit="cover"
              />
              <span class="product-name">{{ record.name }}</span>
            </div>
          </template>
        </a-table-column>
        <a-table-column title="分类" data-index="categoryName"/>
        <a-table-column title="状态" data-index="status">
          <template #cell="{ record }">
            <a-tag :color="getStatusColor(record.status)">
              {{ getStatusText(record.status) }}
            </a-tag>
          </template>
        </a-table-column>
        <a-table-column title="创建时间" data-index="createTime">
          <template #cell="{ record }">
            {{ formatDate(record.createTime) }}
          </template>
        </a-table-column>
        <a-table-column title="操作" :width="200">
          <template #cell="{ record }">
            <a-space>
              <a-button type="text" size="small" @click="openAddModal(record)">
                <icon-edit/>
                编辑
              </a-button>
              <a-button
                  type="text"
                  size="small"
                  :status="record.status === 1 ? 'warning' : 'success'"
                  @click="handleStatusChange(record)"
              >
                <icon-swap/>
                {{ record.status === 1 ? '下架' : '上架' }}
              </a-button>
              <a-popconfirm
                  content="确定要删除该商品吗？"
                  @ok="handleDelete(record)"
              >
                <a-button type="text" status="danger" size="small">
                  <icon-delete/>
                  删除
                </a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>

    <!-- 新增/编辑商品弹窗 -->
    <a-modal
        v-model:visible="modalVisible"
        :title="modalTitle"
        @cancel="resetForm"
        @before-ok="handleSubmit"
    >
      <a-form :model="form" ref="formRef" :rules="rules" label-align="right">
        <a-form-item field="name" label="商品名称" required>
          <a-input v-model="form.name" placeholder="请输入商品名称"/>
        </a-form-item>
        <a-form-item field="categoryId" label="商品分类" required>
          <product-category-selector v-model="form.categoryId"/>
        </a-form-item>
        <a-form-item field="mainImage" label="主图" required>
          <image-uploader v-model="form.mainImage"/>
        </a-form-item>
        <a-form-item field="images" label="商品图片">
          <image-uploader v-model="form.images"/>
        </a-form-item>
        <a-form-item field="description" label="商品描述">
          <a-textarea
              v-model="form.description"
              placeholder="请输入商品描述"
              :auto-size="{ minRows: 3, maxRows: 5 }"
          />
        </a-form-item>
        <a-form-item field="status" label="状态">
          <a-radio-group v-model="form.status">
            <a-radio :value="1">上架</a-radio>
            <a-radio :value="0">下架</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { Message } from '@arco-design/web-vue'
import {
  IconPlus,
  IconEdit,
  IconDelete,
  IconSwap,
} from '@arco-design/web-vue/es/icon'
import { productListApi, productSaveApi, productUpdateApi } from '@/api/product.js'
import ProductCategorySelector from './component/ProductCategorySelector.vue'
import ImageUploader from './component/ImageUploader.vue'

export default {
  name: 'ProductList',
  components: {
    IconPlus,
    IconEdit,
    IconDelete,
    IconSwap,
    ProductCategorySelector,
    ImageUploader,
  },
  data () {
    return {
      loading: false,
      tableData: [],
      pagination: {
        total: 0,
        current: 1,
        pageSize: 10,
      },
      modalVisible: false,
      modalTitle: '添加商品',
      rules: {
        name: [{ required: true, message: '请输入商品名称' }],
        categoryId: [{ required: true, message: '请选择商品分类' }],
        mainImage: [{ required: true, message: '请上传商品主图' }],
      },
      productListApiParam: {
        pageNum: 1,
        pageSize: 10,
        name: null,  // 商品名称
        categoryId: null,  // 分类 id
        status: null,
      },
      form: null,
    }
  },
  created () {
    this.resetForm()
    this.fetchProductList()
  },
  methods: {
    // 重置表单
    resetForm () {
      this.form = {
        id: null,
        name: '',
        categoryId: null,
        description: '',
        mainImage: '',
        images: [],
        status: 1,
      }
      this.$refs.formRef?.clearValidate()
    },

    // 获取商品列表
    async fetchProductList () {
      this.loading = true
      try {
        const resp = await productListApi(this.productListApiParam)
        // console.log(resp)
        this.tableData = resp.data.result.records
        this.pagination.total = 0
      } finally {
        this.loading = false
      }
    },

    // 搜索
    handleSearch () {
      this.pagination.current = 1
      this.fetchProductList()
    },

    // 翻页
    onPageChange (current) {
      this.pagination.current = current
      this.fetchProductList()
    },

    // 打开新增/编辑弹窗
    openAddModal (record) {
      this.resetForm()
      if (record) {
        this.modalTitle = '编辑商品'
        Object.keys(this.form).forEach(key => {
          if (key in record) {
            this.form[key] = record[key]
          }
        })
        // 处理图片列表
        if (record.images) {
          this.form.images = record.images ? record.images.split(',').filter(Boolean) : [] // 过滤空值
        }
        // console.log(20250420180020, this.form)
      } else {
        this.modalTitle = '添加商品'
      }
      this.modalVisible = true
    },

    // 提交表单
    async handleSubmit (done) {
      this.$refs.formRef?.validate(async (errors) => {
        if (errors) {
          done(false)
          return
        }
        const isEdit = !!this.form.id
        const formToSubmit = {
          ...this.form,
          images: Array.isArray(this.form.images) ? this.form.images.filter(Boolean).join(',') : '', // 确保是数组且过滤空值
        }
        try {
          console.log(20250420180130, formToSubmit)
          const resp = isEdit ? await productUpdateApi(formToSubmit) : await productSaveApi(formToSubmit)
          Message.success(`${isEdit ? '更新' : '添加'}商品成功`)
          this.fetchProductList()
          done()
        } catch (error) {
          Message.error(`${isEdit ? '更新' : '添加'}商品失败`)
          done(false)
        }
      })
    },

    // 修改商品状态
    async handleStatusChange (record) {
      try {
        await productUpdateApi({
          id: record.id,
          status: record.status === 0 ? 1 : 0,
        })
        Message.success('修改状态成功')
        this.fetchProductList()
      } catch (error) {
        Message.error('修改状态失败')
      }
    },

    // 删除商品
    async handleDelete (record) {
      try {
        // TODO: 调用删除接口
        Message.success('删除商品成功')
        this.fetchProductList()
      } catch (error) {
        Message.error('删除商品失败')
      }
    },

    // 获取状态文本
    getStatusText (status) {
      const statusMap = {
        1: '上架',
        0: '下架',
        '-1': '已删除',
      }
      return statusMap[status] || '未知'
    },

    // 获取状态颜色
    getStatusColor (status) {
      const colorMap = {
        1: 'green',
        0: 'orange',
        '-1': 'red',
      }
      return colorMap[status] || 'gray'
    },

    // 格式化日期
    formatDate (date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    },
  },
}
</script>

<style scoped>

.header {
  margin-bottom: 20px;
}

.product-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.product-name {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
