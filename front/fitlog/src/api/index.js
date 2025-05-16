// axios 객체를 해당 Js파일에서 생성할 것 
import axios from "axios"

const api = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers:{
        "Content-Type": "application/json"
    },
})

export default api;