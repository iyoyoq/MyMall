<template>
  <div class="container">
    <div class="header">
      <a-row>
        <a-col :span="12">
          <a-button type="primary" @click="openAddModal(null)">
            <template #icon>
              <icon-plus/>
            </template>
            添加分类
          </a-button>
        </a-col>
        <a-col :span="12" style="text-align: right">
          <a-input-search
              v-model="searchKeyword"
              placeholder="请输入分类名称"
              style="width: 300px"
              @search="handleSearch"
          />
        </a-col>
      </a-row>
    </div>

    <a-table
        :loading="loading"
        :data="tableData"
        :pagination="false"
        row-key="id"
        :expand-all-rows="true"
    >
      <template #columns>
        <a-table-column title="分类名称" data-index="name"/>
        <a-table-column title="状态" data-index="status">
          <template #cell="{ record }">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '上架' : '下架' }}
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
              <a-button type="text" size="small" @click="openAddModal(null, record.id)">
                <icon-plus/>
                添加子分类
              </a-button>
              <a-popconfirm
                  content="确定要删除该分类吗？"
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

    <!-- 新增/编辑分类弹窗 -->
    <a-modal
        v-model:visible="modalVisible"
        :title="modalTitle"
        @cancel="resetForm"
        @before-ok="handleSubmit"
    >
      <a-form :model="form" ref="formRef" :rules="rules" label-align="right">
        <a-form-item field="parentId" label="父级分类">
          <a-tree-select
              v-model="form.parentId"
              :data="categoryTreeData"
              placeholder="请选择父级分类"
              allow-clear
          />
        </a-form-item>
        <a-form-item field="name" label="分类名称" required>
          <a-input v-model="form.name" placeholder="请输入分类名称"/>
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
} from '@arco-design/web-vue/es/icon'
import { productCategoryCreateApi, productCategoryListApi, productCategoryUpdateApi } from '@/api/product.js'

export default {
  name: 'ProductCategory',
  components: {
    IconPlus,
    IconEdit,
    IconDelete,
  },
  data () {
    return {
      loading: false,
      tableData: [],
      searchKeyword: '',
      modalVisible: false,
      modalTitle: '添加分类',
      form: {
        id: null,
        parentId: null,
        name: '',
        status: 1,
      },
      rules: {
        name: [
          { required: true, message: '请输入分类名称' },
        ],
      },
    }
  },
  computed: {
    categoryTreeData () {
      const treeData = this.tableData.map(item => ({
        key: item.id.toString(), // 将 id 转换为字符串
        title: item.name,
        children: item.children?.map(child => ({
          key: child.id.toString(), // 将 id 转换为字符串
          title: child.name,
        })) || [],
      }))
      return [{ key: '-1', title: '顶级分类', children: treeData }] // 顶级分类的 key 也使用字符串
    },
  },
  created () {
    this.fetchCategoryList()
  },
  methods: {
    // 获取分类列表
    async fetchCategoryList () {
      this.loading = true
      try {
        const res = await productCategoryListApi()
        // console.log(res.data.result)
        this.tableData = res.data.result
      } finally {
        this.loading = false
      }
    },

    // 将平铺数据转换为树形结构
    handleTreeData (data) {
      const result = []
      const map = {}

      data.forEach(item => {
        map[item.id] = { ...item, children: [] }
      })

      data.forEach(item => {
        const parent = map[item.parentId]
        if (parent) {
          parent.children.push(map[item.id])
        } else {
          result.push(map[item.id])
        }
      })

      return result
    },

    // 打开新增/编辑弹窗
    openAddModal (record, parentId = '-1') {
      this.resetForm()

      if (record) {
        // 编辑模式
        this.modalTitle = '编辑分类'
        Object.keys(this.form).forEach(key => {
          if (key in record) {
            // 确保 parentId 是字符串类型
            this.form[key] = key === 'parentId' ? record[key].toString() : record[key]
          }
        })
      } else {
        // 新增模式
        this.modalTitle = '添加分类'
        this.form.parentId = parentId.toString()
      }

      this.modalVisible = true
    },

    // 重置表单
    resetForm () {
      Object.keys(this.form).forEach(key => {
        this.form[key] = key === 'status' ? 1 : null
      })
      this.$refs.formRef?.clearValidate()
    },

    // 提交表单
    async handleSubmit (done) {
      this.$refs.formRef?.validate(async (errors) => {
        if (errors) {
          done(false)
          return
        }
        const isEdit = !!this.form.id
        try {
          if (isEdit) {
            await productCategoryUpdateApi(this.form)
          } else {
            await productCategoryCreateApi(this.form)
          }
          Message.success(`${isEdit ? '更新' : '添加'}分类成功`)
          this.fetchCategoryList()
          done()
        } catch (error) {
          Message.error(`${isEdit ? '更新' : '添加'}分类失败`)
          done(false)
        }
      })
    },

    // 删除分类
    async handleDelete (record) {
      try {
        await productCategoryUpdateApi({
          id: record.id,
          status: -1
        })
        // 模拟API调用
        Message.success('删除分类成功')
        this.fetchCategoryList()
      } catch (error) {
        Message.error('删除分类失败')
      }
    },

    // 搜索
    handleSearch () {
      this.fetchCategoryList()
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
.container {
  margin: 20px 0 0 0;
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  width: 1200px;
}

.header {
  margin-bottom: 20px;
}
</style>
