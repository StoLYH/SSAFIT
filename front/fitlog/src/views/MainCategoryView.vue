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
const currentPage = ref(1);
const totalPages = ref(5);

watch(
  () => route.params.categoryNumber,    // 함수로 사용해야 변화되는 값을 추적
  async (newId) => {
    posts.value = await getCategoryColumns(newId)
  },
  { immediate: true }                   // 최초 진입 시에도 한 번 실행
)

const handleSearchEmit = (res) => {
  posts.value = res;
}

const handlePageChange = (page) => {
  currentPage.value = page;
  // TODO: 페이지 변경 시 데이터 다시 불러오기
  // 예: posts.value = await getCategoryColumns(route.params.categoryNumber, page);
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
