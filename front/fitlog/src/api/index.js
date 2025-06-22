import axios from "axios";
import { useUserStore } from '@/stores/userstore';

// 비적용
const api = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true,
    validateStatus: ()=> true
})

// 토큰처리 적용
const api_token = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true,
    validateStatus: ()=> true
})

// 요청 인터셉터 - Access Token 추가
api_token.interceptors.request.use((config)=>{
    const userStore = useUserStore();
    const token = userStore.token;
    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
})

// 응답 인터셉터 - 토큰 재발급 처리
api_token.interceptors.response.use(
    (response) => {
        // 새로운 Access Token이 헤더에 있으면 저장
        const newToken = response.headers['new-access-token'];
        if (newToken) {
            const userStore = useUserStore();
            userStore.updateToken(newToken);
            console.log('새로운 Access Token이 저장되었습니다.');
        }
        return response;
    },
    async (error) => {
        const originalRequest = error.config;
        
        // 401 에러이고 토큰 만료로 인한 재발급인 경우
        if (error.response?.status === 401 && 
            error.response?.headers['token-expired'] === 'true' && 
            !originalRequest._retry) {
            
            originalRequest._retry = true;
            
            // 새로운 토큰을 헤더에서 가져와서 store에 저장
            const newToken = error.response.headers['new-access-token'];
            if (newToken) {
                const userStore = useUserStore();
                userStore.updateToken(newToken);
                console.log('토큰이 재발급되어 저장되었습니다.');
                
                // 새로운 토큰으로 원래 요청 재시도
                originalRequest.headers.Authorization = `Bearer ${newToken}`;
                return api_token(originalRequest);
            }
        }
        
        return Promise.reject(error);
    }
);

// 비적용
const api_file = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'multipart/form-data'
    },
    withCredentials: true
})
api_file.interceptors.request.use((config)=>{
    const userStore = useUserStore();
    const token = userStore.token;
    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
})

// 토큰처리 적용
const api_file_token = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'multipart/form-data'
    },
    withCredentials: true
})

// 요청 인터셉터 - Access Token 추가
api_file_token.interceptors.request.use((config)=>{
    const userStore = useUserStore();
    const token = userStore.token;
    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
})

// 응답 인터셉터 - 토큰 재발급 처리
api_file_token.interceptors.response.use(
    // 정상
    (response) => {
        // 새로운 Access Token이 헤더에 있으면 저장
        const newToken = response.headers['new-access-token'];
        if (newToken) {
            const userStore = useUserStore();
            userStore.updateToken(newToken);
            console.log('새로운 Access Token이 저장되었습니다.');
        }
        return response;
    },
    async (error) => {  // 토큰 만료
        const originalRequest = error.config;
        
        // 401 에러이고 토큰 만료로 인한 재발급인 경우
        if (error.response?.status === 401 && 
            error.response?.headers['token-expired'] === 'true' && 
            !originalRequest._retry) {
            
            originalRequest._retry = true;
            
            // 새로운 토큰을 헤더에서 가져와서 store에 저장
            const newToken = error.response.headers['new-access-token'];
            if (newToken) {
                const userStore = useUserStore();
                userStore.updateToken(newToken);
                console.log('토큰이 재발급되어 저장되었습니다.');
                
                // 새로운 토큰으로 원래 요청 재시도
                originalRequest.headers.Authorization = `Bearer ${newToken}`;
                return api_file_token(originalRequest);
            }
        }
        
        return Promise.reject(error);
    }
);

// 비적용
const api_download = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    responseType: 'blob', // 중요: binary 파일 받을 준비
    withCredentials: true
});

export { api, api_file, api_download, api_token, api_file_token };

