<template>
  <PostItem v-for="post in posts" :key="post.id" :post="post" />
</template>

<script setup>
import PostItem from './PostItem.vue'
defineProps({ posts: Array })
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