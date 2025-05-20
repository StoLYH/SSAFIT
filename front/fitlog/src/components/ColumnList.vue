<template>
  <section class="section">
    <h2>{{ title }}</h2>
    <div class="card-list" >
      <div class="card" v-for="(item) in items" :key="item.colboardId" @click="goToColumnDetail(item.colboardId)">
        <img :src="imageMap[item.colboardId]" alt="칼럼" />
        <div class="card-content">
          <div class="category">{{ getCategoryName(item.category) }}</div>
          <div class="title">{{ item.title }}</div>
          <div class="meta">{{ item.userId }} · {{ item.createdAt }}</div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import {ref, watch} from 'vue';
import {getfileInformaton} from '@/api/board';
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  title: String,
  items: {
    type: Array,
    default: () => []       // 부모가 전달한 데이터가 없으면 빈 배열 전달
  }
})



const imageMap = ref({}); // key(게시판 기본키) value(http요청)

// watch - { immediate: true } 조합
watch (
  () => props.items,     // 감시 값 -> 함수 리턴 값 설정
  async (newItems) => {
    const map = {};
    for (const item of newItems) {
      const fileinfo = await getfileInformaton(item.colboardId);  // 게시물에 해당하는 파일가져오기
      if (fileinfo && fileinfo.length > 0 ) {
        map[item.colboardId] = `http://localhost:8080/upload/sendImg/${fileinfo[0].uploadName}`
      } else {
        map[item.colboardId] = '/un.png';
      }
    }
    imageMap.value = map;
  }, 
  { immediate: true }   
);

const getCategoryName = (category) => {
  switch (category) {
    case 1: return "운동/트레이닝";
    case 2: return "재활/통증";
    case 3: return "영양/식단";
    case 4: return "정신 건강/라이프스타일";
    case 5: return "의학/질환";
    default: return "카테고리 없음";
  }
};

// props는 접근은 가능하지만 읽기전용이라 수정이 불가하다

const goToColumnDetail = (colboardId) => {
  router.push(`/show/${colboardId}`);
}

</script>

<style scoped>
.section {
  margin: 40px 0 0 0;
  padding: 0 50px;
}
.section h2 {
  font-size: 1.2rem;
  margin-bottom: 18px;
  font-weight: bold;
}
.card-list {
  display: flex;
  gap: 50px;
}
.card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px #0001;
  width: 340px;
  min-width: 260px;
  transition: box-shadow 0.2s;
}
.card img {
  width: 100%;
  height: 180px;
  object-fit: cover;
}
.card-content {
  padding: 16px;
}
.category {
  font-size: 0.9rem;
  color: #888;
  margin-bottom: 6px;
}
.title {
  font-size: 1.05rem;
  font-weight: bold;
  margin-bottom: 8px;
}
.meta {
  font-size: 0.85rem;
  color: #aaa;
}
@media (max-width: 900px) {
  .card-list {
    flex-direction: column;
    gap: 16px;
    align-items: center;
  }
}
</style> 