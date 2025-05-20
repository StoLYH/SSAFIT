// src/stores/userStore.js
import { defineStore } from 'pinia'
import { PostLogin } from '@/api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    userId: sessionStorage.getItem('userId') || null,
    token: sessionStorage.getItem('token') || null
  }),
  actions: {
    async login(loginForm) {
      try {
        const data = await PostLogin(loginForm)
        this.userId = data.userId
        this.token = data.token
        sessionStorage.setItem('token', data.token)
        sessionStorage.setItem('userId', data.userId)
        return true
      } catch (error) {
        console.error('로그인 실패:', error)
        if (error.response?.status === 401) {
          return { success: false, message: '아이디 또는 비밀번호가 일치하지 않습니다.' }
        }
        return { success: false, message: '로그인 중 오류가 발생했습니다.' }
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
    }
  }
})
