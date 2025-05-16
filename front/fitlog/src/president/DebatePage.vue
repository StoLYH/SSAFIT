<template>
    <div class="main-container">
      <FitlogHeader />
      <MainBanner />
      <ColumnList title="오픈 토론장" :items="hotColumns" />
      <BannerSection>댓글로 칼럼이 가장 인상적이었던 운동/칼럼가?</BannerSection>
      <ColumnList title="HOT 토론장" :items="recentColumns" />
      <FitlogFooter />
    </div>
  </template>
  
  <script setup>
  import FitlogHeader from '../components/FitlogHeader.vue'
  import MainBanner from '../components/MainBanner.vue'
  import ColumnList from '../components/ColumnList.vue'
  import BannerSection from '../components/BannerSection.vue'
  import FitlogFooter from '../components/FitlogFooter.vue'
  
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
  const recentColumns = [
    {
      img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
      category: '재활/통증 관리',
      title: '관절 체중이 들어가는 자리입니다.',
      author: '홍길동',
      date: '2024.06.01'
    },
    {
      img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
      category: '재활/통증 관리',
      title: '관절 체중이 들어가는 자리입니다.',
      author: '홍길동',
      date: '2024.06.01'
    },
    {
      img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
      category: '재활/통증 관리',
      title: '관절 체중이 들어가는 자리입니다.',
      author: '홍길동',
      date: '2024.06.01'
    }
  ]
  </script>
  
  <style scoped>
  .main-container {
    font-family: 'Noto Sans KR', sans-serif;
    background: #f7f8fa;
    color: #222;
  }
  </style> 