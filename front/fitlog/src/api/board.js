import { api_file,  api, api_download, api_file_token, api_token} from './index'

// 메인페이지 인기 게시물 가져오기


// 가장 최근에 올라온 칼럼 3개
const getRecentColumns = async () => {
    const {data} = await api.get("board/recent");
    return data;
}

// 메인페이지
const getPopularColumns = async () => {
    const {data} = await api.get("board/popular");
    return data;
}

// 마이페이지
const getUserPopularColumns = async (userId) => {
    const {data} = await api.get("board/user/popular/" + userId);
    return data;
}


const getUserColumns = async (userId) => {
    const {data} = await api.get("board/user/" + userId);
    return data;
}



const getCategoryColumns = async (categoryNumber) => {
    const {data} = await api.get("board/category/" + categoryNumber);
    return data
}

// 토큰 추가
const registForm = async (formData) => {
    const {data} = await api_file_token.post("board", formData);
    return data;
}

// 토큰 추가 (수정)
const updateBoard = async (colboardId, formData) => {
    const response = await api_file_token.put(`board/${colboardId}`, formData)
    return response;
}

// 게시물 파일정보를 가져온다.
const getfileInformaton = async (colboardId) => {
    const {data} = await api.get(`upload/${colboardId}`);
    return data;
}

// 게시물 1개 가져오기 + 조회수 증가
const  getoneBoard = async (colboardId) => {
    const {data} = await api.get("board/" + colboardId);
    return data;
}

// 게시물 1개 가져오기 + 조회수 증가x
const  getoneBoardWithoutCnt = async (colboardId) => {
    const {data} = await api.get("board/withoutCnt/" + colboardId);
    return data;
}

//  검색하기 (writer, title 기준으로 검색하기)
const getsearch = async (query) => {
    const {data} = await api.get("board" + query);
    return data;
}

// 토큰추가(삭제)
const deleteBoard = async (colboardId) => {
    const response = await api_token.delete("board/" + colboardId);
    return response;
}

const serveFile = async (uploadName) => {
    const {data} = await api_file.get(`upload/sendImg/${uploadName}`);
    return data;
}

const clickLike = async (boardLike) => {
    const response = await api.post("board/like", boardLike);
    return response;
}

const getLike = async (colboardId) => {
    const {data} = await api.get("board/like/" + colboardId);
    return data;
}

const fileDownload = async (uploadName) => {
    try {
        const response = await api_download.get("upload/download/" + uploadName);
        
        // Content-Disposition 헤더에서 파일명 추출
        const contentDisposition = response.headers['content-disposition'];
        let fileName = uploadName;
        if (contentDisposition) {
            const fileNameMatch = contentDisposition.match(/filename="(.+)"/);
            if (fileNameMatch) {
                fileName = decodeURIComponent(fileNameMatch[1]);
            }
        }

        // Blob 객체 생성 및 다운로드
        const blob = new Blob([response.data]);
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = fileName;
        document.body.appendChild(link);
        link.click();
        
        // Cleanup
        window.URL.revokeObjectURL(url);
        document.body.removeChild(link);
        
        return true;
    } catch (error) {
        console.error('File download failed:', error);
        throw error;
    }
}

const getBestBoards = async () => {
    const {data} = await api.get("board/MonthWriterBoards");
    return data
}

const getTop3Boards = async (boardId) => {
    const {data} = await api.get("board/findTop3/" + boardId);
    return data
}

const gptCall = async (message) => {
    const {data} = await api.post("chatGPT", message);
    return data;
}

export {
    getRecentColumns,
    getPopularColumns,
    getCategoryColumns,
    registForm,
    getfileInformaton,
    getoneBoard,
    getsearch,
    deleteBoard,
    updateBoard,
    serveFile,
    clickLike,
    getLike,
    getUserColumns,
    getUserPopularColumns,
    fileDownload,
    getBestBoards,
    getoneBoardWithoutCnt,
    getTop3Boards,
    gptCall
};
