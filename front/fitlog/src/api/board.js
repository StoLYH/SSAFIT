import { api_file,  api } from './index'

// 메인페이지 인기 게시물 가져오기


// 가장 최근에 올라온 칼럼 3개
const getRecentColumns = async () => {
    const {data} = await api.get("board/recent");
    return data;
}

const getPopularColumns = async () => {
    const {data} = await api.get("board/popular");
    return data;
}

const getCategoryColumns = async (categoryNumber) => {
    const {data} = await api.get("board/category/" + categoryNumber);
    return data
}


const registForm = async (formData) => {
    const {data} = await api_file.post("board", formData);
    return data;
}

// 게시물 파일정보를 가져온다. 
const getfileInformaton = async (colboardId) => {
    const {data} = await api.get(`upload/${colboardId}`);
    return data;
}

// 게시물 1개 가져오기
const  getoneBoard = async (colboardId) => {
    const {data} = await api.get("board/" + colboardId);
    return data;
}

//  검색하기 (writer, title 기준으로 검색하기)
const getsearch = async (query) => {
    const {data} = await api.get("board" + query);
    return data;
}


export {
    getRecentColumns,
    getPopularColumns,
    getCategoryColumns,
    registForm,
    getfileInformaton,
    getoneBoard,
    getsearch
};
