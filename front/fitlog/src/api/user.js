import {api, api_file} from "./index";
// import axios를 할필요없음 
// user 관련된 기능들만 모음 
const USER_API_BASE ="/api/user";

const PostRegist = async (registForm)=>{
    const{data}= await api_file.post(`${USER_API_BASE}`,registForm)
    return data;
}


const GetInfo = async (userId)=>{
    const{data}= await api.get(`${USER_API_BASE}/${userId}`)
    return data;
}

const GetImg = async (userId)=>{
    const{data}= await api.get(`/upload/user/${userId}`)
    return data;
}


const updateUserDetail = async (newUserDetail)=>{
    const{data}= await api.put(`${USER_API_BASE}/detail`,newUserDetail)
    return data;
}

const confirmId = async (userId) => {
    const { data } = await api.get(`${USER_API_BASE}/confirmId/${userId}`)
    return data
}

const confirmName = async (userName) => {
    const { data } = await api.get(`${USER_API_BASE}/confirmName/${userName}`)
    return data
}

export{
    PostRegist,
    confirmId,
    confirmName,
    GetInfo,
    updateUserDetail,
    GetImg
}