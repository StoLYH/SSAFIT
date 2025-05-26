<template>
  <div class="post-item" @click="goToShow">
    <img
      :src="fileinfo && fileinfo[0] ? `http://localhost:8080/upload/sendImg/${fileinfo[0].uploadName}` : '/un.png'"
      alt="썸네일"/>
                                          <!-- 기본 이미지 어떻게 처리할지 고민...... -->
    <div class="post-info">
      <div class="title">{{ post.title }}</div>
      <div class="summary">{{ removehtml }} </div>
      <div class="meta-row">
        <span class="category">{{ getCategoryName(post.category) }}</span>
        <span class="author">{{ post.author }}</span>
        <span class="date">{{ post.date }}</span>
        <span class="views">👁 {{ post.viewCnt }}</span>
        <span class="likes">👍 {{ likeCount }}</span>
        <span class="comments">💬 {{reviewCount.length}}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import {watch, ref} from 'vue';
import {getfileInformaton, getLike} from '@/api/board';
import { useRouter } from 'vue-router';
import {getReview} from '@/api/review';


const props =defineProps({ post: Object })    // 각 게시판의 dto가 넘어온다

const fileinfo = ref(null);
const removehtml = ref("");
const router = useRouter();
const likeCount = ref(0);
const reviewCount = ref(0);

function goToShow() {
  router.push('/show/' + props.post.colboardId);
}

function getCategoryName(num) {
  switch (Number(num)) {
    case 1: return "운동/트레이닝";
    case 2: return "재활/통증";
    case 3: return "영양/식단";
    case 4: return "정신 건강/라이프스타일";
    case 5: return "의학/질환";
    default: return "카테고리 없음";
  }
}

watch(
  () => props.post.colboardId,  // 처음과 + 특정 카테고리의 게시물이 바뀔 때 마다 호출
  async (newId) => {
    fileinfo.value = await getfileInformaton(newId);  // 게시판 id를 이용해서 해당 게시판의 파일정보 가져온다
    removehtml.value = props.post.content.replace(/<[^>]*>?/g, '').slice(0, 100);
    likeCount.value = await getLike(newId);
    reviewCount.value = await getReview(newId);

  },
  { immediate: true } // 처음 렌더링 될 때도 실행
)

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
  font-size: 25px;
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