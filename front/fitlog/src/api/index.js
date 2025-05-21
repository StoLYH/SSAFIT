import axios from "axios";

// axios 인스턴스 생성
const api = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})

const api_file = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers: {
        'Content-Type': 'multipart/form-data'
    }
})

export { api, api_file };

