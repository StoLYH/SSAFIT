import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia' // 👈 추가

const app = createApp(App)
const pinia = createPinia()
app.use(router)
app.use(pinia)
app.mount('#app')
