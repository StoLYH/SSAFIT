import {api} from "./index";
// import axios를 할필요없음 
// user 관련된 기능들만 모음 
const USER_API_BASE ="/api/auth";

const PostLogin = async (loginForm)=>{
    const{data}= await api.post(`${USER_API_BASE}/login`,loginForm)
    
    // 로그인 성공 시 Access Token 저장
    if (data.token) {
        sessionStorage.setItem('token', data.token);
        console.log('로그인 성공: Access Token이 저장되었습니다.');
    }
    
    return data;
}

const PostLogout = async () => {
    await api.post(`${USER_API_BASE}/logout`);
}

export{
    PostLogin,
    PostLogout
}
