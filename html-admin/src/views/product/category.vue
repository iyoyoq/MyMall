<template>
  <div class="app-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>分类管理</span>
          <el-button type="primary" @click="handleAdd">新增分类</el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="sort" label="排序" width="100" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
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
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" :max="999" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
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
import { showSuccess } from '@/utils/message'

// 模拟分类数据
const categoryData = Array.from({ length: 20 }, (_, index) => ({
  id: index + 1,
  name: `分类${index + 1}`,
  sort: index + 1,
  status: Math.random() > 0.2 ? 1 : 0,
  createTime: new Date(Date.now() - Math.floor(Math.random() * 90) * 24 * 60 * 60 * 1000).toLocaleString()
}))

const loading = ref(false)
const total = ref(0)
const tableData = ref([])

const queryParams = reactive({
  page: 1,
  pageSize: 10
})

const dialog = reactive({
  visible: false,
  title: '',
  type: 'add'
})

const form = reactive({
  id: undefined,
  name: '',
  sort: 0,
  status: 1
})

const rules = {
  name: [
    { required: true, message: '请输入分类名称', trigger: 'blur' }
  ],
  sort: [
    { required: true, message: '请输入排序号', trigger: 'blur' }
  ]
}

// 获取列表数据
const getList = async () => {
  loading.value = true
  try {
    await new Promise(resolve => setTimeout(resolve, 300))
    const start = (queryParams.page - 1) * queryParams.pageSize
    const end = start + queryParams.pageSize
    tableData.value = categoryData.slice(start, end)
    total.value = categoryData.length
  } finally {
    loading.value = false
  }
}

// 新增
const handleAdd = () => {
  dialog.type = 'add'
  dialog.title = '新增分类'
  dialog.visible = true
  form.id = undefined
  form.name = ''
  form.sort = 0
  form.status = 1
}

// 编辑
const handleEdit = (row) => {
  dialog.type = 'edit'
  dialog.title = '编辑分类'
  dialog.visible = true
  Object.assign(form, row)
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除该分类吗？', '提示', {
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