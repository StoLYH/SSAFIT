<template>
  <div class="post-item">
    <img src="/un.png" alt="썸네일" />
    <div class="post-info">
      <div class="title">{{ post.title }}</div>
      <div class="summary">첫 줄에는 글의 요약 내용 일부가 표시되는 부분입니다<br>첫 줄에는 글의 요약 내용 일부가 표시되는 부분입니다<br>첫 줄에는 글의 요약 내용 일부가 표시되는 부분입니다</div>
      <div class="meta-row">
        <span class="category">{{ post.category }}</span>
        <span class="author">{{ post.author }}</span>
        <span class="date">{{ post.date }}</span>
        <span class="views">👁 1,308</span>
        <span class="likes">👍 11</span>
        <span class="comments">💬 2</span>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({ post: Object })
</script>

<style scoped>
.post-item {
  display: flex;
  background: #f7f8fa;
  border-radius: 16px;
  margin-bottom: 32px;
  padding: 40px 48px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.06);
  max-width: 100%;
  min-height: 200px;
  align-items: center;
  margin-left: 0px;
}
img {
  width: 180px;
  height: 130px;
  object-fit: cover;
  border-radius: 12px;
  margin-right: 40px;
  background: #eee;
}
.post-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 12px;
}
.summary {
  font-size: 16px;
  color: #555;
  margin-bottom: 16px;
  line-height: 1.6;
}
.meta-row {
  display: flex;
  align-items: center;
  gap: 18px;
  font-size: 15px;
  color: #888;
}
.category {
  font-weight: 500;
  color: #4a90e2;
}
.author {
  font-weight: 400;
}
.date {
  color: #aaa;
}
.views, .likes, .comments {
  display: flex;
  align-items: center;
  gap: 2px;
}
</style> 