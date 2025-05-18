<template>
  <div class="board-header-container">
    <div class="board-category">{{ props.board.category }}</div>
    <div class="board-title">{{ props.board.title }}</div>
    <div class="board-meta-row">
      <img class="profile-img" src="https://randomuser.me/api/portraits/men/32.jpg" alt="프로필" />
      <span class="profile-name">영양학자 김사원</span>
      <span class="board-date">2023.03.21</span>
      <span class="board-views">👁 1,308</span>
      <span class="board-likes">👍 11</span>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({ board: Object });
console.log(props.board);

</script>

<style scoped>
.board-header-container {
  max-width: 1280px;
  margin: 0 auto;
  background: #f7f8fa;
  border-radius: 16px 16px 0 0;
  padding: 36px 48px 18px 48px;
  box-sizing: border-box;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
}
.board-category {
  font-size: 1.05rem;
  color: #888;
  font-weight: 600;
  margin-bottom: 8px;
}
.board-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 18px;
  color: #222;
}
.board-meta-row {
  display: flex;
  align-items: center;
  gap: 16px;
  font-size: 1rem;
  color: #666;
}
.profile-img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 8px;
}
.profile-name {
  font-weight: 500;
  color: #222;
}
.board-date {
  color: #aaa;
}
.board-views, .board-likes {
  display: flex;
  align-items: center;
  gap: 2px;
}
</style> 