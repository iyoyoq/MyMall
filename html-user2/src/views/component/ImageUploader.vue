<template>
  <div>
    <a-upload
        list-type="picture-card"
        :limit="imageLimit "
        :file-list="fileList"
        :custom-request="handleUpload"
        image-preview
        @change="changeFileList"
    >
    </a-upload>
  </div>
</template>

<script>
import { Message } from '@arco-design/web-vue'
import { fileUploadApi } from '@/api/file.js'

export default {
  name: 'ImageUploader',
  props: {
    modelValue: {
      type: [String, Array],
      default: '',
    },
    maxNum: {
      type: Number,
      default: 5,
    },
  },
  emits: ['update:modelValue'],
  computed: {
    imageLimit () {
      return Array.isArray(this.modelValue) ? this.maxNum : 1
    },
    fileList: {
      get () {
        if (this.modelValue == null || this.modelValue.length === 0) return []
        if (this.imageLimit > 1) {
          return this.modelValue.map((url, index) => ({
            url,
            name: `图片${index + 1}`,
          }))
        }
        return [
          {
            url: this.modelValue,
            name: '图片',
          }]
      },
      set (newValue) {
        if (this.imageLimit > 1) {
          this.$emit('update:modelValue', newValue.map(file => file.url))
        } else {
          this.$emit('update:modelValue', newValue[0]?.url || '')
        }
      },
    },
  },
  created () {
    // console.log('abc', this.imageLimit)
  },
  methods: {
    async handleUpload (option) {
      const { onError, onSuccess, fileItem } = option
      try {
        const resp = await fileUploadApi(fileItem.file)
        const url = resp.data.result

        if (this.imageLimit > 1) {
          const newFileList = [...this.fileList]
          newFileList.at(-1).url = url
          this.fileList = newFileList
          // console.log(this.fileList)
        } else {
          this.fileList = [{ url, name: '图片' }]
        }
        onSuccess()
      } catch (e) {
        Message.error('图片上传失败')
        onError()
      }
    },
    changeFileList (list) {
      this.fileList = list
      // console.log('changeFileList', list)
      // console.log(20250421124016, this.fileList)
    },
  },
}
</script>
