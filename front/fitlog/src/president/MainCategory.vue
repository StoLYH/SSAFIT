<template>
  <div class="main-category-container">
    <FitlogHeader />
    <SearchBar />
    <CategoryBar />
    <PostList :posts="posts" />
    <Pagination :currentPage="currentPage" :totalPages="totalPages" @pageChange="handlePageChange" />
  </div>
</template>

<script setup>
import FitlogHeader from '../components/FitlogHeader.vue'
import SearchBar from '../components/SearchBar.vue'
import CategoryBar from '../components/CategoryBar.vue'
import PostList from '../components/PostList.vue'
import Pagination from '../components/Pagination.vue'

// 예시 데이터
const posts = [
  {
    id: 1,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 2,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 3,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 4,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 5,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 6,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 7,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 8,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 9,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  },
  {
    id: 10,
    img: 'https://images.unsplash.com/photo-1519864600265-abb23847ef2c',
    category: '영양/식단',
    title: '영양섭취가 중요한 식단의 중요성',
    author: '홍길동',
    date: '2024.06.01'
  }
]
const currentPage = 1
const totalPages = 5

function handlePageChange(newPage) {
  // 페이지 변경 로직
}
</script>

<style scoped>
.main-category-container {
  background: #f7f8fa;
  min-height: 100vh;
}
</style>
