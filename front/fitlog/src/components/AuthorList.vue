<template>
 
  <section class="section">
  <h2>이달의 작가 </h2>
   <div class="author-list">
      <div class="author-card" v-for="(writer, idx) in writers" :key="idx">
        <div class="author-name">{{ writer }}</div>
        <div class="author-subtitle">"작가의 인사말"</div>
        <ul class="column-list">
          <li v-for="(board) in getAuthorBoards(idx)" :key="board.colboardId" @click="goToBoard(board.colboardId)" class="clickable">
            <img :src="imageUrls[board.colboardId] || '/un.png'" alt="썸네일" class="square-img" />
            <span class="col-title">{{ board.title }}</span>
          </li>
        </ul>
        <div class="more-link">해당 작가의 글 더보기 &gt;</div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRouter } from 'vue-router'
import { getfileInformaton } from '@/api/board'

const router = useRouter()

const props = defineProps({
  writer: Array,
  AuthorsBoard1: Array,
  AuthorsBoard2: Array,
  AuthorsBoard3: Array,
})

const writers = computed(() => props.writer)

const imageUrls = ref({}) // { [colboardId]: url }

const getAuthorBoards = (authorIndex) => {
  switch(authorIndex) {
    case 0:
      return props.AuthorsBoard1
    case 1:
      return props.AuthorsBoard2
    case 2:
      return props.AuthorsBoard3
    default:
      return []
  }
}

const goToBoard = (colboardId) => {
  router.push(`/show/${colboardId}`)
}

watch(
  () => [props.AuthorsBoard1, props.AuthorsBoard2, props.AuthorsBoard3],
  (newBoards) => {  
    newBoards.forEach(async (boards) => {
      for (const board of boards) {
          const fileList = await getfileInformaton(board.colboardId)
          if (fileList && fileList[0] && fileList[0].uploadName) {
            imageUrls.value[board.colboardId] = `http://localhost:8080/upload/sendImg/${fileList[0].uploadName}`
          } else {
            imageUrls.value[board.colboardId] = '/un.png'
          }
        }
    })
  },
  { deep: true ,    // props.AuthorsBoard(1,2,3) 감시하는 배열의 내부 객체까지 변화를 감지한다 (for문)
  immediate: true}  // onMouted시에도 감시
)

</script>

<style scoped>
.section {
  margin: 40px 0 0 0;
  padding: 0 50px;
}
.section h2 {
  font-size: 1.7rem;
  margin-bottom: 18px;
  font-weight: bold;
}
.author-list {
  display: flex;
  gap: 48px;
}
.author-card {
  background: #fff;
  border-radius: 8px;
  padding: 40px 36px 24px 36px;
  width: 380px;
  min-width: 280px;
  box-shadow: 0 2px 8px #0001;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.author-name {
  font-weight: 900;
  font-size: 1.1rem;
  margin-bottom: 4px;
}
.author-subtitle {
  font-size: 1.05rem;
  color: #222;
  margin-bottom: 18px;
}
.column-list {
  list-style: none;
  padding: 0;
  margin: 0 0 18px 0;
}
.column-list li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.square-img {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  margin-right: 12px;
  flex-shrink: 0;
  object-fit: cover;
}
.col-title {
  font-size: 1rem;
  color: #222;
}
.more-link {
  margin-top: 8px;
  font-size: 0.95rem;
  color: #888;
  text-align: right;
  cursor: pointer;
}
.more-link:hover {
  color: #f9c846;
}
.clickable {
  cursor: pointer;
  transition: background-color 0.2s;
}
.clickable:hover {
  background-color: #f5f5f5;
  border-radius: 8px;
}
@media (max-width: 900px) {
  .author-list {
    flex-direction: column;
    gap: 16px;
    align-items: center;
  }
}
</style> 