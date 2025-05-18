<template>
  <div>
    <MainBanner />
    <ColumnList title="요즘 인기 칼럼" :items="hotColumns" />
    <ColumnList title="최근에 올라온 칼럼" :items="recentColumns" />
    <BannerSection>댓글로 칼럼이 가장 인상적이었던 운동/칼럼가?</BannerSection>
    <AuthorList title="인기칼럼 작가" :authors="popularAuthors" />
    <AuthorList title="떠오르는 작가" :authors="risingAuthors" />
    <DividerBar />  
  </div>
</template>

<script setup>
import MainBanner from '../components/MainBanner.vue'
import ColumnList from '../components/ColumnList.vue'
import BannerSection from '../components/BannerSection.vue'
import AuthorList from '../components/AuthorList.vue'
import DividerBar from '../components/DividerBar.vue'


//연습
import { ref, onMounted } from 'vue';
import { getRecentColumns } from '@/api/board.js'

const recentColumns = ref([]);

onMounted(async () =>{
  recentColumns.value = await getRecentColumns();
  console.dir(recentColumns.value)
})





const hotColumns = [
  {
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '운동/트레이닝',
    title: '관절 체중이 들어가는 자리입니다.',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '운동/트레이닝',
    title: '관절 체중이 들어가는 자리입니다.',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '운동/트레이닝',
    title: '관절 체중이 들어가는 자리입니다.',
    author: '홍길동',
    date: '2024.06.01'
  }
]



const popularAuthors = [
  {
    name: '헬창수',
    columns: [
      '무릎보호대는 어떻게 써야할까?',
      '상체 보강 운동법 알려드립니다',
      '표준화 테스트, 먹고 계신가요?'
    ]
  },
  {
    name: '헬창수',
    columns: [
      '무릎보호대는 어떻게 써야할까?',
      '상체 보강 운동법 알려드립니다',
      '표준화 테스트, 먹고 계신가요?'
    ]
  },
  {
    name: '헬창수',
    columns: [
      '무릎보호대는 어떻게 써야할까?',
      '상체 보강 운동법 알려드립니다',
      '표준화 테스트, 먹고 계신가요?'
    ]
  }
]

const risingAuthors = [
  {
    name: '헬창수',
    columns: [
      '무릎보호대는 어떻게 써야할까?',
      '상체 보강 운동법 알려드립니다',
      '표준화 테스트, 먹고 계신가요?'
    ]
  },
  {
    name: '헬창수',
    columns: [
      '무릎보호대는 어떻게 써야할까?',
      '상체 보강 운동법 알려드립니다',
      '표준화 테스트, 먹고 계신가요?'
    ]
  },
  {
    name: '헬창수',
    columns: [
      '무릎보호대는 어떻게 써야할까?',
      '상체 보강 운동법 알려드립니다',
      '표준화 테스트, 먹고 계신가요?'
    ]
  }
]
</script>

<style scoped>
</style> 