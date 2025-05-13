<template>
  <div class="category-bar">
    <div class="category-item strong">운동/트레이닝</div>
    <div class="category-item strong">재활/통증관리</div>
    <div class="category-item strong">재활/통증관리</div>
    <div class="category-item">건강</div>
    <div class="category-item">다이어트</div>
    <div class="category-item">QnA</div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.category-bar {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 80px;
  background: #f7f8fa;
  padding: 24px 0;
  margin-top: 0;
  margin-bottom: 32px;
  position: relative;
  z-index: 2;
  max-width: 1100px;
  left: 50%;
  transform: translateX(-50%);
}
.category-item {
  font-size: 1.08rem;
  font-weight: 700;
  color: #222;
  cursor: pointer;
  padding: 8px 18px;
  border-radius: 8px;
  transition: background 0.15s, color 0.15s;
}
.category-item.strong {
  font-weight: 900;
}
.category-item:hover {
  background: #f9c846;
  color: #fff;
}
</style> 