export function hasValue(value) {
  // 处理 null 和 undefined
  if (value === null || value === undefined) {
    return false;
  }

  // 处理字符串
  if (typeof value === 'string') {
    return value.trim() !== '';
  }

  // 处理数字
  if (typeof value === 'number') {
    // 0 也返回 false
    return !isNaN(value) && value !== 0;
  }

  // 处理布尔值 - 如果希望布尔值 true 返回 true，false 返回 false
  if (typeof value === 'boolean') {
    return value;
  }

  // 处理对象和数组
  if (typeof value === 'object') {
    // 如果是数组，检查长度
    if (Array.isArray(value)) {
      return value.length > 0;
    }
    // 如果是对象，检查是否有属性
    return Object.keys(value).length > 0;
  }

  // 其他情况返回 true
  return true;
}
