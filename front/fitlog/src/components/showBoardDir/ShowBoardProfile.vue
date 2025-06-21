<template>
  <div
    class="profile-card draggable"
    draggable="true"
    style="position:sticky; top:32px; z-index:2;"
    @click="goToMyPage"
  >
    <img class="profile-img" :src="profileImg"  alt="프로필" />
    <div class="profile-name">{{userInfo.nickname}}</div>
    <div class="profile-job">{{userInfo.role}}</div>
    <div class="profile-desc">{{userInfo.onelineInfo}}</div>
  </div>
</template>


<script setup>
import { defineProps } from 'vue'
import { GetImg } from '@/api/user.js'
import { GetInfo } from '@/api/user.js'
import { useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'

const router = useRouter()
const props = defineProps({
  boardUserId: String
})
const data = ref({})
const profileData = ref({});
const profileImg = ref('/landingpage2.png') //

const userInfo = ref({
    nickname: '당신은 누구십니까',
    role: '당신의 직업은?',
    onelineInfo: 'working out is essential to me',
  })

const goToMyPage = () => {
  router.push(`/mypage/${props.boardUserId}`)
}

onMounted(async () => {
  try {
    const data = await GetInfo(props.boardUserId);
    userInfo.value = {
      nickname: data.userName,
      role: data.userRoleName?.userRoleName || '직업 정보 없음',
      onelineInfo: data.userDetail?.onelineInfo || '아직 한 줄 소개가 없습니다.',
    };

    profileData.value = await GetImg(props.boardUserId);
        
        if (profileData.value && profileData.value.uploadName) {
            profileImg.value = `https://localhost:8443/upload/sendImg/${profileData.value.uploadName}`;
          } else {
            console.log('프로필 이미지가 없습니다.');
            profileImg.value = '/landingpage2.png';
          }





  } catch (e) {
    console.error("유저 정보 불러오기 실패:", e);
  }
});













</script>


<style scoped>
.profile-card {
  width: 240px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 16px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
  padding: 28px 20px 20px 20px;
  box-sizing: border-box;
  text-align: center;
  margin-top: 24px;
  cursor: grab;
}
.profile-card:active {
  cursor: grabbing;
}
.profile-img {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 12px;
}
.profile-name {
  font-size: 1.15rem;
  font-weight: bold;
  margin-bottom: 4px;
}
.profile-job {
  font-size: 0.98rem;
  color: #888;
  margin-bottom: 10px;
}
.profile-desc {
  font-size: 0.95rem;
  color: #444;
  line-height: 1.5;
}
</style> 