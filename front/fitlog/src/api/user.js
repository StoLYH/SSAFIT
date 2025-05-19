import {api} from "./index";
// import axios를 할필요없음 
// user 관련된 기능들만 모음 
const USER_API_BASE ="/api/user";

const PostRegist = async (registForm)=>{
    const{data}= await api.post(`${USER_API_BASE}`,registForm)
    return data;
}

export{
    PostRegist
}