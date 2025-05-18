<<<<<<< HEAD
// axios 객체를 해당 Js파일에서 생성할 것 
import axios from "axios"

const api = axios.create({
    baseURL: import.meta.env.VITE_FITLOG_API_URL,
    headers:{
        "Content-Type": "application/json"
    },
})

export default api;
=======
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
>>>>>>> 501e7ac2458b9fbd4584d2dcb52ace5e67d8d2ff
