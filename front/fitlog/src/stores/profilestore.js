// src/stores/profilestore.js
// 마이페이지 전용 피니아 스토어 

import { defineStore } from 'pinia'
import { GetInfo } from '@/api/user.js'



export const useProfileStore = defineStore('profile', {
  state: () => ({
    userId: null,
  user: {}
  }),
  actions: { 
    async GetProfileInfo(userId) {
      try {
        this.user = await GetInfo(userId)

        // 로그인 성공 시 userId를 저장하기 
        this.userId = this.user.userId

  
        
        return true
      } catch (error) {
        console.error('마이페이지 불러오기 실패:', error)
        return false
      }
    }
  }
},)
