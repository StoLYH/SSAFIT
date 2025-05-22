import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import { useUserStore } from '@/stores/userstore'

const app = createApp(App)

const pinia = createPinia()                       // 먼저 pinia 생성
pinia.use(piniaPluginPersistedstate)             // 그 다음에 plugin 연결

app.use(router)
app.use(pinia)

// === Pinia 상태 복원 ===
const userStore = useUserStore()
const userId = sessionStorage.getItem('userId')
const token = sessionStorage.getItem('token')
if (userId && token) {
  userStore.setUser(userId, token)
}

app.mount('#app')
