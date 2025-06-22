<template>
  <header class="header">
    <div class="left-area">
      <div class="logo-area">
        <img src="/fitlog.png" @click="goToHome" alt="Fitlog 로고" class="fitlog-img" />
      </div>
      <nav class="main-nav">
        <ul>
          <li class="main-menu-item"><span>핏로그 칼럼</span></li>
          <li class="main-menu-item"><span>핏로그 디베이트</span></li>
          <li class="main-menu-item"><span @click="goToQuestion">핏로그 QnA</span></li>
        </ul>
      </nav>
    </div>
    <div class="right-menu">
      <span class="search-icon">
        <svg width="22" height="22" viewBox="0 0 22 22" fill="none" xmlns="http://www.w3.org/2000/svg">
          <circle cx="10" cy="10" r="7" stroke="#222" stroke-width="2"/>
          <line x1="15.4142" y1="15" x2="20" y2="19.5858" stroke="#222" stroke-width="2" stroke-linecap="round"/>
        </svg>
      </span>
      <span class="right-link">광고 상품</span>
      <RouterLink class="right-link" :to="`/mypage/${userId}`">마이페이지</RouterLink>
      <template v-if="!userStore.userId">
        <span class="right-link" @click="goToLogin">로그인</span>
        <button class="signup-btn" @click="goToRegister">회원가입</button>
      </template>
      <template v-else>
        <span class="right-link">{{ userId }}님</span>
        <button class="signup-btn" @click="handleLogout">로그아웃</button>
      </template>
    </div>
  </header>
  <div class="border-line"></div>
  <div class="full-border-line"></div>
</template>

<script setup>
import router from '@/router';
import { useUserStore } from '@/stores/userstore';

const userStore = useUserStore();
const userId = userStore.userId;

const goToLogin = ()=>{
  router.push('/welcome/login')}

const goToRegister = () => {
  router.push('/welcome/regist')
}

const goToHome = () => {
  router.push("/");
}


const goToQuestion = () => {
  router.push('/question');
}

const handleLogout = async () => {
  console.log('Logout clicked');
  try {
    // 서버 로그아웃 API 호출
    await userStore.logout();
    console.log('Logout successful');
    router.push('/');
  } catch (error) {
    console.error('Logout failed:', error);
    // 에러가 발생해도 로컬 상태는 정리
    userStore.clearUser();
    router.push('/');
  }
}
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding: 18px 40px 12px 40px;
  background: #f7f8fa;
  border-bottom: 1px solid #eee;
}
.left-area {
  display: flex;
  align-items: center;
  gap: 32px;
}
.logo-area {
  display: flex;
  align-items: center;
  gap: 8px;
}
.fitlog-img {
  height: 80px;
  vertical-align: middle;
}
.fitlog-title {
  font-size: 1.3rem;
  font-weight: bold;
}
.main-nav ul {
  display: flex;
  gap: 32px;
  list-style: none;
  align-items: center;
  margin: 0;
  padding: 0;
}
.main-menu-item span {
  font-size: 1.15rem;
  font-weight: 800;
  letter-spacing: -0.5px;
  color: #111;
  transition: color 0.2s;
}
.main-menu-item span:hover {
  color: #f9c846;
}
.right-menu {
  display: flex;
  align-items: center;
  gap: 18px;
  margin-left: auto;
}
.search-icon {
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: filter 0.2s;
}
.search-icon:hover svg {
  filter: drop-shadow(0 0 2px #f9c846);
}
.right-link {
  font-size: 0.98rem;
  color: #222;
  cursor: pointer;
}
.signup-btn {
  background: #22313f;
  color: #fff;
  border: none;
  border-radius: 20px;
  padding: 8px 20px;
  font-weight: bold;
  cursor: pointer;
  font-size: 0.98rem;
}
.border-line {
  width: 100%;
  height: 4px;
  background: #f3f4f6;
  border: none;
  margin: 0;
}
.full-border-line {
  width: 100vw;
  height: 2px;
  background: #d1d5db;
  border: none;
  margin: 0 0 0 calc(-50vw + 50%);
  box-shadow: 0 2px 8px #0001;
  margin-bottom: 20px;
}
</style> 