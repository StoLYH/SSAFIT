<template>
  <div class="comment-section-outer">
    <div class="comment-section">
      <div class="comment-count">2개의 댓글</div>
      <div class="comment-input-row">
        <textarea class="comment-input" placeholder="댓글을 작성하세요" v-model="textfield"></textarea>
        <button class="comment-submit-btn" @click="submitComment">댓글 작성</button>
      </div>





      <div class="comment-list">
        <div class="comment-item">
          <img class="comment-profile" src="https://randomuser.me/api/portraits/men/12.jpg" alt="프로필" />
          <div class="comment-content">
            <div class="comment-meta">
              <span class="comment-nickname">건강요정</span>
              <span class="comment-date">2025-04-11</span>
            </div>
            <div class="comment-text">흥미롭네요! 다음이 더 기대되는 글인것 같습니다!다다다우다다 달려가다가다 넘어질뻔 했습니다..!!<br>다음에 기회가 된다면 한번 방문 할게요!!!</div>
          </div>
        </div>
        <div class="comment-item">
          <img class="comment-profile" src="https://randomuser.me/api/portraits/men/12.jpg" alt="프로필" />
          <div class="comment-content">
            <div class="comment-meta">
              <span class="comment-nickname">건강요정</span>
              <span class="comment-date">2025-04-11</span>
            </div>
            <div class="comment-text">흥미롭네요! 다음이 더 기대되는 글인것 같습니다!다다다우다다 달려가다가다 넘어질뻔 했습니다..!!<br>다음에 기회가 된다면 한번 방문 할게요!!!</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { useUserStore } from '@/stores/userstore';
import { watch, ref } from 'vue';
import { registReview } from '@/api/review.js'
import { getReview } from '@/api/review.js'

import { defineProps } from 'vue'




const props = defineProps({
  board: Object
})
const userStore = useUserStore();
const textfield = ref('');
const reviews = ref([]) //리뷰들 받는 곳 


//리뷰 등록하기 
const submitComment = async () => {
  if (!textfield.value.trim()) {
    alert('댓글 내용을 입력하세요!');
    return;
  }

  




  try {
    // 예시: API 호출로 댓글 등록 (api/review.js의 registReview 함수 사용 가정)
    await registReview({ 
      colboardId: props.board.colboardId,
      userId: userStore.userId,
      content: textfield.value,
     });
    alert('댓글이 등록되었습니다!');
    textfield.value = ''; // 입력창 초기화

    // 댓글 목록 다시 불러오기 (필요 시)
    // await loadComments();
  } catch (error) {
    console.error(error);
    alert('댓글 등록에 실패했습니다.');
  }
};


//게시물 리뷰 가져오기 


const loadComments = async () => {
  try {
    const data = await getReview(props.board.colboardId);
    reviews.value = data;
    console.dir(data);
  } catch (e) {
    console.error("리뷰 불러오기 실패:", e);
  }
};

// board가 들어올 때까지 감시
watch(() => props.board, (newBoard) => {
  if (newBoard && newBoard.colboardId) {
    loadComments();
  }
}, { immediate: true });




</script>







<style scoped>
.comment-section-outer {
  width: 100%;
  background: #f7f8fa;
  display: flex;
  justify-content: center;
}
.comment-section {
  max-width: 1280px;
  margin: 48px auto 0 auto;
  background: #f7f8fa;
  border-radius: 16px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
  padding: 36px 32px 32px 32px;
  box-sizing: border-box;
  width: 100%;
}
.comment-count {
  font-size: 1.08rem;
  font-weight: bold;
  margin-bottom: 18px;
  color: #222;
}
.comment-input-row {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 32px;
}
.comment-input {
  width: 100%;
  min-height: 80px;
  border: 1px solid #ddd;
  border-radius: 12px;
  padding: 16px;
  font-size: 1rem;
  resize: vertical;
  box-sizing: border-box;
}
.comment-submit-btn {
  align-self: flex-end;
  background: #222;
  color: #fff;
  border: none;
  border-radius: 12px;
  padding: 8px 24px;
  font-size: 1rem;
  cursor: pointer;
}
.comment-submit-btn:hover {
  background: #444;
}
.comment-list {
  display: flex;
  flex-direction: column;
  gap: 28px;
}
.comment-item {
  display: flex;
  align-items: flex-start;
  gap: 18px;
}
.comment-profile {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  object-fit: cover;
  margin-top: 2px;
}
.comment-content {
  flex: 1;
}
.comment-meta {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1rem;
  margin-bottom: 4px;
}
.comment-nickname {
  font-weight: bold;
  color: #222;
}
.comment-date {
  color: #aaa;
  font-size: 0.97rem;
}
.comment-text {
  font-size: 1.02rem;
  color: #444;
  line-height: 1.6;
}
</style> 