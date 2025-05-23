import {  api } from './index'

// 메인페이지 인기 게시물 가져오기

const REVIEW_API_BASE ="/api/review";

const getReview = async (colboardId) => {
    const {data} = await api.get(`${REVIEW_API_BASE}/${colboardId}`);
    return data;
}

const registReview = async (review) => {
    const {data} = await api.post(`${REVIEW_API_BASE}`,review);
    return data;
}






// const deleteBoard = async (colboardId) => {
//     const response = await api.delete("board/" + colboardId);
//     return response;
// }

// const serveFile = async (uploadName) => {
//     const {data} = await api_file.get(`upload/sendImg/${uploadName}`);
//     return data;
// }

// const clickLike = async (boardLike) => {
//     const response = await api.post("board/like", boardLike);
//     return response;
// }

// const getLike = async (colboardId) => {
//     const {data} = await api.get("board/like/" + colboardId);
//     return data;
//}

export {
    registReview,
    getReview
   
};
