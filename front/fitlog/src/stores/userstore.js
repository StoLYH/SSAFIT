// src/stores/userStore.js
import { defineStore } from 'pinia'
import { PostLogin } from '@/api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    userId: null,
    token: null
  }),
  actions: {
    async login(loginForm) {
      try {
        const data = await PostLogin(loginForm)

        // 로그인 성공 시 userId와 token 저장
        this.userId = data.userId
        this.token = data.token

        // 토큰 세션스토리지에도 저장
        sessionStorage.setItem('token', data.token)
        return true
      } catch (error) {
        console.error('로그인 실패:', error)
        return false
      }
    },
    setUser(userId, token) {
      this.userId = userId
      this.token = token
    },
    clearUser() {
      this.userId = null
      this.token = null
      sessionStorage.removeItem('token')
      sessionStorage.removeItem('userId')
    }
  }
},)
