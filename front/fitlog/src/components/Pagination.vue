<template>
  <div class="pagination">
    <button :disabled="currentPage === 1" @click="changePage(currentPage - 1)">&lt;</button>
    
    
    <button
      v-for="page in totalPages"
      :key="page"
      :class="{ active: page === currentPage }"
      @click="changePage(page)">{{ page }}</button>


    <button :disabled="currentPage === totalPages" @click="changePage(currentPage + 1)">&gt;</button>
  </div>
</template>

<script setup>
const props = defineProps({
  currentPage: Number,
  totalPages: Number
})
const emit = defineEmits(['pageChange'])

function changePage(page) {
  if (page >= 1 && page <= props.totalPages) emit('pageChange', page)
}
</script>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin: 32px 0;
}
button {
  border: none;
  background: #fff;
  color: #222;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 18px;
  min-width: 40px;
  transition: background 0.2s, color 0.2s;
}
button.active {
  background: #222;
  color: #fff;
}
button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style> 