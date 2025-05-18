<template>
  <PostItem v-for="post in posts" :key="post.id" :post="post" />
</template>

<script setup>
import PostItem from './PostItem.vue'

const props = defineProps({ posts: Array })
console.dir(props.posts);
console.log("안녕!");

// 특정 카테고리의 게시판의 게시물 리스트를 가져온다. -> for문 넘기기

</script>

.post-list {
  margin: 32px auto;
  max-width: 100%;
  padding: 0 80px;
  background: #f7f8fa;
  border-radius: 24px;
  box-shadow: 0 2px 16px rgba(0,0,0,0.06);
}<style scoped>

</style> 