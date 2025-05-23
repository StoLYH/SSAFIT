<template>
  <div>
    <MainBanner />
    <ColumnList title="요즘 인기 칼럼" :items="hotColumns" /> 
    <ColumnList title="최근에 올라온 칼럼" :items="recentColumns" />
    <BannerSection>댓글로 칼럼이 가장 인상적이었던 운동/칼럼가?</BannerSection>
    <AuthorList title="인기칼럼 작가" :writer="popularAuthors" :AuthorsBoard1="AuthorsBoard1" 
    :AuthorsBoard2="AuthorsBoard2" :AuthorsBoard3="AuthorsBoard3" />
    <DividerBar />  
  </div>
</template>

<script setup>
import MainBanner from '../components/MainBanner.vue'
import ColumnList from '../components/ColumnList.vue'
import BannerSection from '../components/BannerSection.vue'
import AuthorList from '../components/AuthorList.vue'
import DividerBar from '../components/DividerBar.vue'

import { ref, onMounted } from 'vue';
import { getPopularColumns, getRecentColumns, getBestBoards, getoneBoardWithoutCnt } from '@/api/board.js'

const recentColumns = ref([]);
const hotColumns = ref([]);
const popularAuthors = ref([]);
const risingAuthors = ref([]);

const AuthorsBoard1 = ref([]);
const AuthorsBoard2 = ref([]);
const AuthorsBoard3 = ref([]);

onMounted(async () => {
  hotColumns.value = await getPopularColumns();
  recentColumns.value = await getRecentColumns();

  const bestBoardNumStr = await getBestBoards();
  for (let i = 0; i < 3; i++) {
    const board = await getoneBoardWithoutCnt(bestBoardNumStr[i][0]);
    popularAuthors.value.push(board.userId);
  }
  console.log(popularAuthors.value);  // 작가이름

  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      const board = await getoneBoardWithoutCnt(bestBoardNumStr[i][j]);
      if (i == 0) {
        AuthorsBoard1.value.push(board);
      } else if (i == 1) {
        AuthorsBoard2.value.push(board);
      } else {
        AuthorsBoard3.value.push(board);
      }
    }
  }

  // 떠오르는 작가 데이터도 설정
  risingAuthors.value = [...popularAuthors.value]; // 임시로 같은 데이터 사용
})
</script>

<style scoped>
</style> 