<template>
  <div class="main-category-container">
    <SearchBar @searchEmit="handleSearchEmit" />
    <CategoryBar />
    <div class="register-btn-wrapper">
      <RegisterButton />
    </div>
    <PostList :posts="posts" />
    <Pagination :currentPage="currentPage" :totalPages="totalPages" @pageChange="handlePageChange" />
  </div>
</template>

<script setup>
import SearchBar from '@/components/SearchBar.vue'
import CategoryBar from '@/components/CategoryBar.vue'
import PostList from '@/components/PostList.vue'
import Pagination from '@/components/Pagination.vue'
import RegisterButton from '@/components/RegisterButton.vue'
import { getCategoryColumns } from '../api/board'
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute();
const posts = ref([]);
const totalPosts = ref([]);
const currentPage = ref(1);
const totalPages = ref(99);

watch(
  () => route.params.categoryNumber,    // 함수로 사용해야 변화되는 값을 추적
  async (newId) => {
    totalPosts.value = await getCategoryColumns(newId)
    console.log(totalPosts.value.length);
    
    posts.value = totalPosts.value.slice(0, 10); 
    totalPages.value = Math.floor(posts.value.length / 10) + 1;
    
  },
  { immediate: true }                   // 최초 진입 시에도 한 번 실행
)

const handleSearchEmit = (res) => {
  posts.value = res;
}

const handlePageChange = async (page) => {
  currentPage.value = page;
  
  posts.value = totalPosts.value.slice((page - 1) * 10, page * 10);
}

</script>

<style scoped>
.main-category-container {
  background: #f7f8fa;
  min-height: 100vh;
}
.register-btn-wrapper {
  display: flex;
  justify-content: flex-end;
  margin: 16px 0;
}
</style>
