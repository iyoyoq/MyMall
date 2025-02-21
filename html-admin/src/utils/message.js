import { ElMessage } from 'element-plus'

// 成功提示
export const showSuccess = (message) => {
  ElMessage({
    message,
    type: 'success',
    plain: true
  })
}

// 错误提示
export const showError = (message) => {
  ElMessage({
    message,
    type: 'error',
    plain: true
  })
}

// 警告提示
export const showWarning = (message) => {
  ElMessage({
    message,
    type: 'warning',
    plain: true
  })
}

// 普通信息提示
export const showInfo = (message) => {
  ElMessage({
    message,
    type: 'info',
    plain: true
  })
} 