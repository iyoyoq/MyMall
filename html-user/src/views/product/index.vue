<template>
  <div class="app-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>商品管理</span>
          <el-button type="primary" @click="handleAdd">新增商品</el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="price" label="价格" />
        <el-table-column prop="stock" label="库存" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        class="pagination"
        :total="total"
        v-model:current-page="queryParams.page"
        v-model:page-size="queryParams.pageSize"
        @current-change="getList"
      />
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="500px"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :precision="2" :step="0.1" />
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input-number v-model="form.stock" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialog.visible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessageBox } from 'element-plus'
import { showSuccess, showError } from '@/utils/message'
import { products, getPageData } from '@/mock/data'

const loading = ref(false)
const total = ref(0)
const tableData = ref([])

const queryParams = reactive({
  page: 1,
  pageSize: 10
})

// 修复：正确初始化 dialog 对象
const dialog = reactive({
  visible: false,
  title: '',
  type: 'add'
})

const form = reactive({
  id: undefined,
  name: '',
  price: 0,
  stock: 0,
  description: ''
})

const rules = {
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' }
  ],
  price: [
    { required: true, message: '请输入价格', trigger: 'blur' }
  ],
  stock: [
    { required: true, message: '请输入库存', trigger: 'blur' }
  ]
}

// 获取列表数据
const getList = async () => {
  loading.value = true
  try {
    await new Promise(resolve => setTimeout(resolve, 300))
    const res = getPageData(products, queryParams.page, queryParams.pageSize)
    tableData.value = res.list
    total.value = res.total
  } finally {
    loading.value = false
  }
}

// 新增
const handleAdd = () => {
  dialog.type = 'add'
  dialog.title = '新增商品'
  dialog.visible = true
  form.id = undefined
  form.name = ''
  form.price = 0
  form.stock = 0
  form.description = ''
}

// 编辑
const handleEdit = (row) => {
  dialog.type = 'edit'
  dialog.title = '编辑商品'
  dialog.visible = true
  Object.assign(form, row)
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除该商品吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    showSuccess('删除成功')
    getList()
  }).catch(() => {})
}

// 提交表单
const handleSubmit = async () => {
  showSuccess(dialog.type === 'add' ? '新增成功' : '更新成功')
  dialog.visible = false
  getList()
}

// 初始化
getList()
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>