<template>
  <div class="app-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="handleAdd">新增用户</el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="email" label="邮箱" />
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
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
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
import { users, getPageData } from '@/mock/data'

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
  type: 'add' // 或 'edit'
})

const form = reactive({
  id: undefined,
  username: '',
  email: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ]
}

// 获取列表数据
const getList = async () => {
  loading.value = true
  try {
    // 模拟API调用延迟
    await new Promise(resolve => setTimeout(resolve, 300))
    const res = getPageData(users, queryParams.page, queryParams.pageSize)
    tableData.value = res.list
    total.value = res.total
  } finally {
    loading.value = false
  }
}

// 新增
const handleAdd = () => {
  dialog.type = 'add'
  dialog.title = '新增用户'
  dialog.visible = true
  form.id = undefined
  form.username = ''
  form.email = ''
}

// 编辑
const handleEdit = (row) => {
  dialog.type = 'edit'
  dialog.title = '编辑用户'
  dialog.visible = true
  Object.assign(form, row)
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除该用户吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    // 调用删除API
    // await deleteUser(row.id)
    showSuccess('删除成功')
    getList()
  }).catch(() => {})
}

// 提交表单
const handleSubmit = async () => {
  // 调用新增或编辑API
  // const api = dialog.type === 'add' ? addUser : updateUser
  // await api(form)
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