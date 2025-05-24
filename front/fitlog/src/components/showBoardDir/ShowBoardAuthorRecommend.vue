<template>
  <div
    class="author-recommend-card draggable"
    draggable="true"
    style="position:sticky; top:32px; z-index:2;">
    <div class="recommend-title">작가의 다른 콘텐츠</div>
    <ul class="recommend-list">
      <li v-for="(board) in top3Boards" :key="board.colboardId">
        {{ categoryMap[board.category] }}<br>
        <span class="recommend-link" @click="goToBoard(board.colboardId)">{{ board.title }}</span>
      </li>
    </ul>
  </div>

</template>

<script setup>
import { getTop3Boards } from '@/api/board';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const categoryMap = {
  1: '운동/트레이닝',
  2: '재활/통증 관리',
  3: '영양/식단',
  4: '정신 건강/라이프스타일',
  5: '의학/질환 정보'
};

const props = defineProps({
  boardId: Number
})

const top3Boards = ref([]);

onMounted(async () => {
  top3Boards.value = await getTop3Boards(props.boardId);
})

const goToBoard = (colboardId) => {
  router.push("/show/" + colboardId);
};

</script>

<style scoped>
.author-recommend-card {
  width: 240px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 16px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
  padding: 24px 20px 20px 20px;
  box-sizing: border-box;
  margin-top: 24px;
  cursor: grab;
}
.author-recommend-card:active {
  cursor: grabbing;
}
.recommend-title {
  font-size: 1.08rem;
  font-weight: bold;
  margin-bottom: 14px;
  color: #222;
}
.recommend-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.recommend-list li {
  font-size: 0.98rem;
  color: #444;
  margin-bottom: 12px;
  line-height: 1.4;
}
.recommend-link {
  color: #4a90e2;
  font-weight: 500;
  cursor: pointer;
  display: block;
  margin-top: 2px;
}
</style> 