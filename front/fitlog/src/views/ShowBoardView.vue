<template>
  <div class="show-board-container">
    <div class="show-board-header-flex">
      <div class="side-area left"><ShowBoardProfile /></div>                          <!-- 왼쪽 프로필-->
      <div class="center-area"><ShowBoardTitle :board="board" /></div>                <!-- 게시글 제목-->  
      <div class="side-area right"><ShowBoardAuthorRecommend /></div>                 <!-- 오른쪽 추천 작가-->  
    </div>


    <!-- 게시글 본문 등 추가 영역은 여기에 -->
    <div class="board-content">
      <div class="board-content" v-html="board.content"></div>
    </div>


    <!-- 버튼-->
    <div class="board-action-btns">
      <button class="edit-btn">수정하기</button>
      <button class="delete-btn">삭제하기</button>
    </div>
    <ShowBoardComment />
  </div>
</template>

<script setup>
import ShowBoardProfile from '../components/showBoardDir/ShowBoardProfile.vue'
import ShowBoardTitle from '../components/showBoardDir/ShowBoardTitle.vue'
import ShowBoardAuthorRecommend from '../components/showBoardDir/ShowBoardAuthorRecommend.vue'
import ShowBoardComment from '../components/showBoardDir/ShowBoardComment.vue'
import { useRoute } from 'vue-router'
import {ref, watch} from 'vue'
import {getoneBoard} from '@/api/board'

const route = useRoute();
const colboardId = route.params.colboardId;  // 게시판 id 가져오기  

const board = ref(null); // 게시물 정보


watch(colboardId, async () => {
  board.value = await getoneBoard(colboardId);
  console.log(board.value);
},
{ immediate: true } 
)


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
</style>
