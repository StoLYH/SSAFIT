<template>
  <div class="board-header-container">
    <div class="board-category">{{ getCategoryName(props.board.category) }}</div>
    <div class="board-title">{{ props.board.title }}</div>
    <div class="board-meta-row">
      <img class="profile-img" :src= "profileImg" alt="프로필" />
      <span class="profile-name">{{ userInfo.nickName }}</span>
      <span class="board-date">{{ props.board.createdAt }}</span>
      <span class="board-views">👁 {{ props.board.viewCnt }}</span>
      <span class="board-likes" @click="updateLike"> 👍 {{ likeCount }} </span>
    </div>
  </div>
</template>

<script setup>
import { clickLike, getLike } from '@/api/board';  
import { useUserStore } from '@/stores/userstore';
import { onMounted, ref } from 'vue';
import { GetInfo } from '@/api/user.js'
import { GetImg } from '@/api/user.js'




const props = defineProps({ board: Object });
const userStore = useUserStore();
const profileData = ref({});
const profileImg = ref('/landingpage2.png') //

const userInfo = ref({
    nickname: '당신은 누구십니까',
    role: '당신의 직업은?',
    onelineInfo: 'working out is essential to me',
  })



const likeCount = ref(0);

onMounted(async () => {
  likeCount.value = await getLike(props.board.colboardId);
  
  //유저정보가져오기
  const data = await GetInfo(props.board.userId);
    userInfo.value = {
      nickname: data.userName,
      role: data.userRoleName?.userRoleName || '직업 정보 없음',
      onelineInfo: data.userDetail?.onelineInfo || '아직 한 줄 소개가 없습니다.',
    };

    profileData.value = await GetImg(props.board.userId);
        
        if (profileData.value && profileData.value.uploadName) {
            profileImg.value = `http://localhost:8080/upload/sendImg/${profileData.value.uploadName}`;
          } else {
            console.log('프로필 이미지가 없습니다.');
            profileImg.value = '/landingpage2.png';
          }







})

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

const updateLike = async () => {
  if (!userStore.userId) {
    return;
  }
  
  try {
    const res = await clickLike({
      userId: userStore.userId,
      colboardId: props.board.colboardId
    })

    likeCount.value = await getLike(props.board.colboardId);

  } catch (error) {
    console.error(error);
  }
}





</script>

<style scoped>
.board-header-container {
  max-width: 1280px;
  margin: 0 auto;
  background: #f7f8fa;
  border-radius: 16px 16px 0 0;
  padding: 36px 48px 18px 48px;
  box-sizing: border-box;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
}
.board-category {
  font-size: 1.05rem;
  color: #888;
  font-weight: 600;
  margin-bottom: 8px;
}
.board-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 18px;
  color: #222;
}
.board-meta-row {
  display: flex;
  align-items: center;
  gap: 16px;
  font-size: 1rem;
  color: #666;
}
.profile-img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 8px;
}
.profile-name {
  font-weight: 500;
  color: #222;
}
.board-date {
  color: #aaa;
}
.board-views, .board-likes {
  display: flex;
  align-items: center;
  gap: 2px;
}
</style> 