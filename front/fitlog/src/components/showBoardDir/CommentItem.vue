<template>
  <div class="comment-item">
    <img class="comment-profile" :src="profileImg" alt="프로필" />
    <div class="comment-content">
      <div class="comment-meta">
        <span class="comment-nickname">{{ userInfo.nickname }}</span>
        <span class="comment-date">{{ review.createAt }}</span>
      </div>
      <div class="comment-text">{{ review.content }}</div>
    </div>
  </div>
</template>

<script setup>
import { GetImg } from '@/api/user.js'
import { GetInfo } from '@/api/user.js'
import { ref, watch } from 'vue'



const props = defineProps({
  review: {
    type: Object,
    required: true
  }
})


const profileData = ref({});
const profileImg = ref('/landingpage2.png') //

const userInfo = ref({
    nickname: '알 수 없음',
    role: '당신의 직업은?',
  })


  
        
  const loadUserInfo = async () => {
  try {
    const data = await GetInfo(props.review.userId);
    userInfo.value ={
      nickname : data.userName,
      role : data.userRoleName,

    }
    profileData.value = await GetImg(props.review.userId);

    if (profileData.value && profileData.value.uploadName) {
            profileImg.value = `https://localhost:8443/upload/sendImg/${profileData.value.uploadName}`;
          } else {
            console.log('프로필 이미지가 없습니다.');
            profileImg.value = '/landingpage2.png';
          }

  } catch (e) {
    console.error("사용자 불러오기 실패:", e);
  }
};

watch(() => props.review, (newReview) => {
  if (newReview && newReview.userId) {
    loadUserInfo();
  }
}, { immediate: true });      
  








</script>

<style scoped>
.comment-item {
  display: flex;
  align-items: flex-start;
  gap: 18px;
}
.comment-profile {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  object-fit: cover;
  margin-top: 2px;
}
.comment-content {
  flex: 1;
}
.comment-meta {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1rem;
  margin-bottom: 4px;
}
.comment-nickname {
  font-weight: bold;
  color: #222;
}
.comment-date {
  color: #aaa;
  font-size: 0.97rem;
}
.comment-text {
  font-size: 1.02rem;
  color: #444;
  line-height: 1.6;
}
</style> 