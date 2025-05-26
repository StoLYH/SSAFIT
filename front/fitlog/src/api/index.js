import axios from "axios";

// 비적용
const api = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'
    },
    validateStatus: ()=> true
})

// 토큰처리 적용
const api_token = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'
    },
    validateStatus: ()=> true
})
api_token.interceptors.request.use((config)=>{
    const token = sessionStorage.getItem('token');
    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
})

// 비적용
const api_file = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'multipart/form-data'
    }
})
api_file.interceptors.request.use((config)=>{
    const token = sessionStorage.getItem('token');
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
    }
})
api_file_token.interceptors.request.use((config)=>{
    const token = sessionStorage.getItem('token');
    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
})

// 비적용
const api_download = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    responseType: 'blob' // 중요: binary 파일 받을 준비
});




export { api, api_file, api_download,api_token, api_file_token };

