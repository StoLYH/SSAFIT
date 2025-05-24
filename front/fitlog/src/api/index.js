import axios from "axios";

// axios 인스턴스 생성
const api = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'

    }
})


// 게시물 수정,삭제,등록, 개인정보 수정 (4가지 HTTP요청 시에 헤더에 토큰 추가 -> 유효성 검증)
const api_token = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'

    }
})
api_token.interceptors.request.use((config)=>{
    const token = sessionStorage.getItem('token');
    if(token){
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
})


// 게시물 수정,삭제,등록, 개인정보 수정 (4가지 HTTP요청 시에 헤더에 토큰 추가 -> 유효성 검증)
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




const api_download = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    responseType: 'blob' // 중요: binary 파일 받을 준비
});




export { api, api_file, api_download, api_token };

