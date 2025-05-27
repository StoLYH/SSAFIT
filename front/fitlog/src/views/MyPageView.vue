<template>
    <div class="mypage-container">
      <!-- 프로필 영역 -->
      <div class="profile-section">
        <img class="profile-img" :src="profileImg" alt="프로필" />
        <div class="profile-info">
          <h1>{{ userInfo.nickname }}</h1>
          <div class="profile-role">{{ userInfo.role }}</div>
          <div class="profile-onelineInfo" @click="profileStore.user.editable && openEdit('onelineInfo')">{{ userInfo.onelineInfo }}</div>
          <div class="profile-exper" @click="profileStore.user.editable && openEdit('exper')">{{ userInfo.exper }}</div>
          <UserDetailEditModal
    v-if="showModal"
    :modelValue="editValue"
    :field="editField"
    @close="showModal = false"
    @save="handleSave"
  />

          
        </div>
        <button class="edit-btn" @click="openProfileEdit">정보수정</button>
      </div>
      <UserProfileEditModal
        v-if="showEditModal"
        :modelValue="userEditInfo"
        @save="handleProfileSave"
        @close="showEditModal = false"
      />

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
  import router from '@/router';

  import { ref, onMounted } from 'vue'
  import ColumnList from '@/components/ColumnList.vue'
import PostList from '@/components/PostList.vue';
import { getUserColumns } from '@/api/board.js'
import { getUserPopularColumns } from '@/api/board.js'
import UserDetailEditModal from '@/components/UserDetailEditModal.vue'
import { updateUserDetail } from '@/api/user.js'
import { GetImg } from '@/api/user.js'
import { useRoute } from 'vue-router';
import { useProfileStore } from '@/stores/profilestore.js';
import UserProfileEditModal from '@/components/UserProfileEditModal.vue'
import { updateUser } from '@/api/user.js'

  const route = useRoute()
  const posts = ref([]);
  const profileData = ref({});
  const profileImg = ref('/landingpage2.png') // 기본 프로필 이미지
  const userInfo = ref({
    nickname: '당신은 누구십니까',
    role: '당신의 직업은?',
    onelineInfo: 'working out is essential to me',
    exper: 'winning bodybuilding contest, working in H1 Fitness, healthman youtube channel'
  })
  const profileStore = useProfileStore();

  const popularColumns = ref([])
  
  onMounted(async () => {
    const userId = route.params.userId
    try {
      const success = await profileStore.GetProfileInfo(userId)
      if (success) {
        const userData = profileStore.user
        userInfo.value = {
          nickname: userData.userName,
          role: userData.userRoleName?.userRoleName,
          onelineInfo: userData.userDetail?.onelineInfo || '아직 한 줄 소개가 없습니다.',
          exper: userData.userDetail?.exper || '아직 경력 정보가 없습니다.'
        }

        profileData.value = await GetImg(userId);
        
        if (profileData.value && profileData.value.uploadName) {
          profileImg.value = `http://localhost:8080/upload/sendImg/${profileData.value.uploadName}`;
        } else {
          console.log('프로필 이미지가 없습니다.');
          profileImg.value = '/landingpage2.png';
        }
      } else {
        console.error('프로필 정보를 불러오는데 실패했습니다.')
      }
    } catch (error) {
      console.error('사용자 정보 로딩 실패:', error);
    }

    // 칼럼 리스트 - 실패해도 에러 아님 (빈 배열은 정상)
    posts.value = (await getUserColumns(userId).catch(() => [])) ?? []
    popularColumns.value = (await getUserPopularColumns(userId).catch(() => [])) ?? []
  })  
  const showModal = ref(false)
const editField = ref('')
const editValue = ref('')

function openEdit(field) {
  editField.value = field
  editValue.value = userInfo.value[field]
  showModal.value = true
}
async function handleSave({ field, value }) {
  const newUserDetail = {
    userId: profileStore.userId,
    onelineInfo: field === 'onelineInfo' ? value : userInfo.value.onelineInfo,
      exper: field === 'exper' ? value : userInfo.value.exper
  } 
  // TODO: API로 저장 후 userInfo 갱신
  try {
      await updateUserDetail(newUserDetail)
      userInfo.value[field] = value
    } catch (e) {
      alert('저장에 실패했습니다.')
    }
}

const showEditModal = ref(false)
const userEditInfo = ref({})

function openProfileEdit() {
  if(profileStore.user.editable){
  userEditInfo.value = {
    userName: userInfo.value.nickname,
    userRole: profileStore.user.userRole,
    exper: userInfo.value.exper,
    onelineInfo: userInfo.value.onelineInfo,
    attach : profileImg.value
  }
  showEditModal.value = true
}
}

async function handleProfileSave(updatedInfo) {
  const formData = new FormData()
  formData.append('userName', updatedInfo.userName)
  formData.append('userRole', updatedInfo.userRole)
  formData.append('exper', updatedInfo.exper)
  formData.append('onelineInfo', updatedInfo.onelineInfo)
  if (updatedInfo.attach) {
    formData.append('attach', updatedInfo.attach)
  }
  await updateUser(formData, profileStore.userId)
  // 저장 후 최신 정보 다시 불러오기
  await profileStore.GetProfileInfo(profileStore.userId)
  const userData = profileStore.user
  userInfo.value = {
    nickname: userData.userName,
    role: userData.userRole, // 숫자!
    onelineInfo: userData.userDetail?.onelineInfo || '아직 한 줄 소개가 없습니다.',
    exper: userData.userDetail?.exper || '아직 경력 정보가 없습니다.'
  }
  profileData.value = await GetImg(profileStore.userId)
  if (profileData.value && profileData.value.uploadName) {
    profileImg.value = `http://localhost:8080/upload/sendImg/${profileData.value.uploadName}`
  }
  showEditModal.value = false
  location.reload()


}

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
  .profile-onelineInfo {
    font-size: 1.05rem;
    margin-bottom: 8px;
  }
  .profile-exper {
    list-style: none;
    padding: 0;
    margin: 0;
    color: #666;
    font-size: 0.98rem;
  }

  .profile-onelineInfo,
.profile-exper {
  font-size: 1.05rem;
  margin-bottom: 8px;
  cursor: pointer; /* 손가락 커서 */
}

.profile-onelineInfo:hover,
.profile-exper:hover {
  background: #f0f4ff;   /* 원하는 배경색으로 변경 */
  color: #1a237e;        /* 원하는 글자색으로 변경 */
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