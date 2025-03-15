<template>
  <var-style-provider :style-vars="styleVars">
    <var-rate v-model="state.score"/>
    <var-switch v-model="state.license"/>
    <var-button
        style="margin-top: 10px"
        type="primary"
        block
        @click="toggleTheme"
    >
      切换样式变量
    </var-button>

    <!--<var-button type="primary" block @click="toggleRootTheme">切换根节点样式变量</var-button>-->
  </var-style-provider>
</template>

<script>
import { ref, reactive } from 'vue'
import { StyleProvider } from '@varlet/ui'
export default {
  data () {
    return {}
  },
  methods: {},
  watch: {},
  setup () {
    const state = reactive({
      score: 5,
      license: true,
    })
    const successTheme = {
      '--rate-primary-color': 'var(--color-success)',
      '--button-primary-color': 'var(--color-success)',
      '--switch-handle-active-background': 'var(--color-success)',
      '--switch-track-active-background': 'var(--color-success)',
    }
    const styleVars = ref(null)

    function toggleTheme () {
      styleVars.value = styleVars.value ? null : successTheme
    }

    let rootStyleVars = null

    const darkTheme = {
      '--color-primary': 'var(--color-info)'
    }

    function toggleRootTheme() {
      rootStyleVars = rootStyleVars ? null : darkTheme
      StyleProvider(rootStyleVars)
    }
    return {
      toggleTheme,
      state,
      styleVars,
      toggleRootTheme,
    }
  },

  mounted () {
  },
}
</script>
