import { createApp } from 'vue'
import router from './router'
import App from './App.vue'
import { StyleProvider, Themes } from '@varlet/ui'
import { MyGlobalTheme1, MyGlobalTheme2 } from '@/styles/global_theme.js'

const app = createApp(App)

app.use(router)

app.mount('#app')

// 主题设置
// StyleProvider( MyGlobalTheme1.light)
const setLightTheme = () => StyleProvider(MyGlobalTheme1.light)
const setDarkTheme = () => StyleProvider(Themes.dark)
setLightTheme()
export {
  setLightTheme,
  setDarkTheme,
}
// 自定义请进入 MyGlobalTheme1.dark
// https://varletjs.org/#/zh-CN/themes
// 主题名称   主题包
// Material Design2亮色   Themes.light
// Material Design2暗色    Themes.dark
// Material Design3亮色  Themes.md3Light
// Material Design3暗色  Themes.md3Dark
