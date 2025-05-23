<template>
  <div class="show-board-container" v-if="board">
    <div class="show-board-header-flex">
      <div class="side-area left"><ShowBoardProfile :board="board" /></div>           <!-- 왼쪽 프로필-->
      <div class="center-area"><ShowBoardTitle :board="board" /></div>                <!-- 게시글 제목-->  
      <div class="side-area right"><ShowBoardAuthorRecommend /></div>                 <!-- 오른쪽 추천 작가-->  
    </div>


    <!-- 게시글 본문 등 추가 영역은 여기에 -->
    <div class="board-content" v-html="board.content"></div>


    <!-- 버튼-->
    <div class="board-action-btns">
      <template v-if="userStore.userId == board.userId">
        <button class="edit-btn" @click="editfunction">수정하기</button>
        <button class="delete-btn" @click="deletefunction">삭제하기</button>
      </template>    
    </div>
    
    <div v-if="fileList.length > 1" class="file-download-list">
      <h4>첨부 파일</h4>
      <ul>
        <li v-for="file in fileList.slice(1)" :key="file.uploadName">   <!-- 0번은 섬네일 이미지라서 제외 -->
          <a
            href="#"
            @click.prevent="handleFileDownload(file)"                  
            class="file-download-link">
            {{ file.originalName }}
          </a>
        </li>
      </ul>
    </div>
  </div>
  <div v-else class="loading">로딩 중...</div>

  <ShowBoardComment />
</template>

<script setup>
import ShowBoardProfile from '../components/showBoardDir/ShowBoardProfile.vue'
import ShowBoardTitle from '../components/showBoardDir/ShowBoardTitle.vue'
import ShowBoardAuthorRecommend from '../components/showBoardDir/ShowBoardAuthorRecommend.vue'
import ShowBoardComment from '../components/showBoardDir/ShowBoardComment.vue'
import { useRoute } from 'vue-router'
import {ref, watch, onMounted} from 'vue'
import {getoneBoard} from '@/api/board'
import { useUserStore } from '@/stores/userstore'
import { deleteBoard, getfileInformaton, fileDownload } from '@/api/board'
import { useRouter } from 'vue-router'


const userStore = useUserStore();
const route = useRoute();
const router = useRouter();
const colboardId = ref(route.params.colboardId);  // ref로 변경
const board = ref(null); // 게시물 정보
const fileList = ref([])

// 게시물 데이터 로드 함수
const loadBoard = async () => {
  try {
    const response = await getoneBoard(colboardId.value);
    board.value = response;
    
    try {
      const res = await getfileInformaton(route.params.colboardId);
      fileList.value = res || [];
    } catch (fileError) {
      console.error('파일 정보 로드 실패:', fileError);
      fileList.value = [];
    }
  } catch (error) {
    console.error('게시물 로드 실패:', error);
    if (error.response) {
      if (error.response.status === 404) {
        alert('존재하지 않는 게시물입니다.');
      } else if (error.response.status === 500) {
        alert('서버 오류가 발생했습니다.');
      } 
    } 
    router.push('/');
  }
};

// route.params.colboardId가 변경될 때마다 실행
watch(() => route.params.colboardId, (newId) => {
  colboardId.value = newId;
  loadBoard();
}, { immediate: true });

const deletefunction = async () => {
  const isConfirmed = window.confirm("정말 삭제하시겠습니까?");
  if (!isConfirmed) return;

  const response = await deleteBoard(colboardId.value);
    if (response.status == 200) {
      alert('삭제가 완료되었습니다.');
      router.push('/category/1');
    } 
}

const editfunction = () => {
  router.push("/edit/" + colboardId.value);
}

const handleFileDownload = async (file) => {
  await fileDownload(file.uploadName);
}

onMounted(async () => {
  const res = await getfileInformaton(route.params.colboardId)
    fileList.value = res
})

</script>

<style scoped>
.show-board-container {
  max-width: 1600px;
  margin: 0 auto;
  padding: 20px;
}
.show-board-header-flex {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  width: 100%;
  margin: 0 auto;
  box-sizing: border-box;
  gap: 32px;
  max-width: 1600px;
}
.center-area {
  flex: 0 0 1280px;
  max-width: 1280px;
}
.side-area {
  flex: 1 1 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 0;
}
.left {
  align-items: flex-end;
}
.right {
  align-items: flex-start;
}
@media (max-width: 1400px) {
  .show-board-header-flex {
    gap: 12px;
  }
  .side-area {
    display: none;
  }
}
.board-action-btns {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  max-width: 1280px;
  margin: 32px auto 0 auto;
  padding-right: 8px;
}
.edit-btn,
.delete-btn {
  padding: 8px 22px;
  border-radius: 20px;
  border: 1px solid #bbb;
  background: #fff;
  color: #222;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.18s, color 0.18s, border 0.18s;
}
.edit-btn:hover {
  background: #f9c846;
  color: #fff;
  border-color: #f9c846;
}
.delete-btn {
  border-color: #ff4d4f;
  color: #ff4d4f;
}
.delete-btn:hover {
  background: #ff4d4f;
  color: #fff;
}
.loading {
  text-align: center;
  padding: 2rem;
  font-size: 1.2rem;
  color: #666;
}
.file-download-list {
  margin: 2rem 0 1rem 0;
}
.file-download-list ul {
  padding-left: 0;
  list-style: none;
}
.file-download-list li {
  margin-bottom: 0.5rem;
}
.file-download-list a {
  color: #1976d2;
  text-decoration: underline;
  font-weight: 500;
}
.board-content {
  margin-top: -170px;         /* 기존에 margin-top이 있다면 0으로 */
  padding-top: 0;        /* 기존에 padding-top이 있다면 0으로 */
  /* 필요하다면 아래도 추가 */
  margin-bottom: 24px;   /* 아래쪽 여백만 살짝 */
}
.file-download-link {
    color: #007bff;
    text-decoration: none;
    cursor: pointer;
}

.file-download-link:hover {
    text-decoration: underline;
}
</style>
