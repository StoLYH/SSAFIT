<template>
    <div class="mypage-container">
      <!-- 프로필 영역 -->
      <div class="profile-section">
        <img class="profile-img" :src="profileImg" alt="프로필" />
        <div class="profile-info">
          <h1>{{ userInfo.nickname }}</h1>
          <div class="profile-role">{{ userInfo.role }}</div>
          <div class="profile-desc">{{ userInfo.desc }}</div>
          <ul class="profile-extra">
            <li v-for="(item, idx) in userInfo.extra" :key="idx">{{ item }}</li>
          </ul>
        </div>
        <button class="edit-btn">정보수정</button>
      </div>
  
      <!-- 인기 칼럼 -->
      <section>
        <h2>{{ userInfo.nickname }}의 인기 칼럼</h2>
        <div v-if="popularColumns.length === 0" class="no-data">
          아직 등록된 게시물이 없습니다.
        </div>
        <ColumnList v-else :items="popularColumns" />
      </section>
  
      <!-- 전체 칼럼 -->
      <section>
        <h2>{{ userInfo.nickname }}의 전체 칼럼</h2>
        <div v-if="posts.length === 0" class="no-data">
          아직 등록된 게시물이 없습니다.
        </div>
        <PostList v-else :posts="posts" />
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import ColumnList from '@/components/ColumnList.vue'
import PostList from '@/components/PostList.vue';
import { useUserStore } from '@/stores/userstore';
import { getUserColumns } from '@/api/board.js'
import { getUserPopularColumns } from '@/api/board.js'
import { GetInfo } from '@/api/user'

  const data = ref({});
  const posts = ref([]);
  const store = useUserStore();
  
  const profileImg = ref('/landingpage2.png') // 기본 프로필 이미지
  const userInfo = ref({
    nickname: '당신은 누구십니까',
    role: '당신의 직업은?',
    desc: 'working out is essential to me',
    extra: [
      'winning bodybuilding contest',
      'working in H1 Fitness',
      'healthman youtube channel'
    ]
  })
  
  // 예시 데이터, 실제로는 API에서 받아와야 함
  const popularColumns = ref([])
  
  onMounted(async () => {
    if (store.userId) {
      try {
        data.value = await GetInfo(store.userId);
        userInfo.value = {
          nickname: data.value.userName,
          role: data.value.userRoleName.userRoleName 
        }
      } catch (error) {
        console.error('Error fetching user info:', error);
      }

      // 게시물 데이터 가져오기
      const columns = await getUserColumns(store.userId);
      posts.value = columns || []; // null이나 undefined면 빈 배열로

      const popular = await getUserPopularColumns(store.userId);
      popularColumns.value = popular || []; // null이나 undefined면 빈 배열로
    }
  })  





  </script>
  
  <style scoped>
  .mypage-container {
    max-width: 1100px;
    margin: 0 auto;
    padding: 32px 0;
  }
  .profile-section {
    display: flex;
    align-items: center;
    gap: 32px;
    margin-bottom: 40px;
  }
  .profile-img {
    width: 180px;
    height: 220px;
    object-fit: cover;
    border-radius: 16px;
    border: 1px solid #ddd;
  }
  .profile-info {
    flex: 1;
  }
  .profile-info h1 {
    font-size: 2.2rem;
    font-weight: bold;
    margin-bottom: 8px;
  }
  .profile-role {
    font-size: 1.1rem;
    color: #888;
    margin-bottom: 8px;
  }
  .profile-desc {
    font-size: 1.05rem;
    margin-bottom: 8px;
  }
  .profile-extra {
    list-style: none;
    padding: 0;
    margin: 0;
    color: #666;
    font-size: 0.98rem;
  }
  .edit-btn {
    background: #222;
    color: #fff;
    border: none;
    border-radius: 8px;
    padding: 10px 24px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    margin-left: auto;
  }
  section {
    margin-bottom: 36px;
  }
  h2 {
    font-size: 1.3rem;
    font-weight: bold;
    margin-bottom: 18px;
  }
  .no-data {
    text-align: center;
    padding: 2rem;
    color: #666;
    font-size: 1.1rem;
  }
  </style>