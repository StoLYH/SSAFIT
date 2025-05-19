import {api} from "./index";
// import axios를 할필요없음 
// user 관련된 기능들만 모음 
const USER_API_BASE ="/api/auth";

const PostLogin = async (loginForm)=>{
    const{data}= await api.post(`${USER_API_BASE}/login`,loginForm)
    return data;
}

export{
    PostLogin
}