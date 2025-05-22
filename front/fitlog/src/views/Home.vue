<template>
  <div class="main-container">
    <FitlogHeader />
    <RouterView />
    <FitlogFooter />        
  </div>
</template>

<script setup>
import FitlogHeader from '../components/FitlogHeader.vue'
import FitlogFooter from '../components/FitlogFooter.vue'



// 헤더와 푸터는 재사용한다.
</script>

<style scoped>
.main-container {
  font-family: 'Noto Sans KR', sans-serif;
  background: #f7f8fa;
  color: #222;
}
</style> 