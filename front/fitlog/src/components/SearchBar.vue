<template>
  <div class="search-bar">
    <select v-model="filter">
      <option value="author">작가</option>
      <option value="title">제목</option>
      <option value="content">내용물</option>
    </select>
    <input type="text" placeholder="검색어를 입력하세요" v-model="search" />
    <button>검색</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
const search = ref('')
const filter = ref('author')
</script>

<style scoped>
.search-bar {
  display: flex;
  justify-content: flex-start;
  margin: 40px 0;
  gap: 16px;
  width: 100%;
  max-width: 1500px;
  margin-left:  0px;
  margin-right: 0;
}
select {
  padding: 18px 28px;
  border: 1px solid #ddd;
  border-radius: 8px 0 0 8px;
  font-size: 22px;
  background: #fff;
}
input {
  width: 900px;
  padding: 18px;
  border: 1px solid #ddd;
  border-left: none;
  border-radius: 0;
  font-size: 22px;
}
button {
  padding: 18px 38px;
  border: none;
  background: #222;
  color: #fff;
  border-radius: 0 8px 8px 0;
  cursor: pointer;
  font-size: 22px;
}
</style> 