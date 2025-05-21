import axios from "axios";
import { useAuthStore } from '@/stores/auth'
import router from '@/router'

// axios 인스턴스 생성
const api = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})

// 인터셉터 -> HTTP 응답을 받기전에 선 조치
// 응답 인터셉터 추가
// api.interceptors.response.use(
//     (response) => response,
//     (error) => {
//         if (error.response && error.response.status === 401) {
//             // 토큰이 만료되었거나 유효하지 않은 경우
//             const authStore = useAuthStore()
//             authStore.clearAuth() // 로그아웃 처리
//             router.push('/welcome/login') // 로그인 페이지로 리다이렉트
//         }
//         return Promise.reject(error)
//     }
// )
// (


api.interceptors.response.use(
    // 응답성공! (20x) response는 http응답
    (response) => {         
        return response;
    }, 
    (error) => {
        if (error.response && error.response.status === 401) {
        // 401,404,500 => 서버가 http응답을 하는경우, error.response로 http응답처리.

        const authStore = useAuthStore()    // pinina
        authStore.clearAuth();              // 
        router.push('/welcome/login');      
      }
      return Promise.reject(error)
    }
)




const api_file = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'multipart/form-data'
    }
})



// 파일 업로드용 api에도 동일한 인터셉터 적용
api_file.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            const authStore = useAuthStore()
            authStore.clearAuth()
            router.push('/welcome/login')
        }
        return Promise.reject(error)
    }
)

export { api, api_file };

