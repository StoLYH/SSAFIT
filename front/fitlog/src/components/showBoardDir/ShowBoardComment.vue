<template>
  <div class="comment-section-outer">
    <div class="comment-section">
      <div class="comment-count">{{ reviews.length }}개의 댓글</div>
      <div class="comment-input-row">
        <textarea class="comment-input" placeholder="댓글을 작성하세요" v-model="textfield"></textarea>
        <button class="comment-submit-btn" @click="submitComment">댓글 작성</button>
      </div>
      <div class="comment-list">
        <CommentItem 
          v-for="review in reviews" 
          :key="review.id" 
          :review="review" 
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { registReview, getReview } from '@/api/review.js'
import { useUserStore } from '@/stores/userstore'
import CommentItem from './CommentItem.vue'

const props = defineProps({
  board: Object
})
const userStore = useUserStore();
const textfield = ref('');
const reviews = ref([])

const submitComment = async () => {
  if (!textfield.value.trim()) {
    alert('댓글 내용을 입력하세요!');
    return;
  }
  try {
    await registReview({ 
      colboardId: props.board.colboardId,
      userId: userStore.userId,
      content: textfield.value,
    });
    alert('댓글이 등록되었습니다!');
    textfield.value = '';
    await loadComments();
  } catch (error) {
    console.error(error);
    alert('댓글 등록에 실패했습니다.');
  }
};

const loadComments = async () => {
  try {
    const data = await getReview(props.board.colboardId);
    reviews.value = data;
  } catch (e) {
    console.error("리뷰 불러오기 실패:", e);
  }
};

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
</style> 