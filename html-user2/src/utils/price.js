export function priceShowDecimalUtil (price) {
  if (isNaN(price)) return ''

  const formatted = (price / 100).toFixed(2)
  const [integer, decimal] = formatted.split('.')

  if (decimal === '00') {
    return integer // 没有小数部分
  } else if (decimal.endsWith('0')) {
    return `${integer}.${decimal[0]}` // 只有一位有效小数
  } else {
    return formatted // 两位有效小数
  }
}
