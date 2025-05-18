<template>
    <div class="landing-bg">
      <div class="landing-overlay">
        <div class="landing-content">
          <div class="landing-left">
            <h1 class="gradient-title">
              Empowering Health Experts<br>to Share, Connect, and Grow
            </h1>
            <p>지식을 공유하고, 소통하며<br>전문성을 강화 시키는 건강 커뮤니티 플랫폼</p>
          </div>
          <div class="landing-right">
            <UserLogin v-if="!isRegister" @switch="isRegister = true" />
            <UserRegister v-else @switch="isRegister = false" />
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import UserLogin from '../components/UserLogin.vue'
  import UserRegister from '../components/UserRegister.vue'
  
  const isRegister = ref(false)
  </script>
  
  <style scoped>
  .landing-bg {
    min-height: 100vh;
    background: url('/landingpage2.png') center/cover no-repeat;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .landing-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(40, 40, 40, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
}
  .landing-content {
    display: flex;
    width: 100%;
    padding: 60px 40px;
    gap: 60px;
  }
  .landing-left {
    flex: 1.2;
    color: #fff;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .landing-left h1 {
    font-size: 3rem;
    font-weight: 800;
    margin-bottom: 24px;
    line-height: 1.1;
  }
  .landing-left p {
    font-size: 1.2rem;
    font-weight: 400;
    line-height: 1.6;
  }
  .landing-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  @media (max-width: 900px) {
    .landing-content { flex-direction: column; gap: 30px; padding: 30px 10px; }
    .landing-left h1 { font-size: 2rem; }
  }
  .gradient-title {
    background: linear-gradient(90deg, #fff 0%, #989898 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text; /* 표준 */
    color: transparent;
    font-size: 2rem; /* 필요시 조정 */
    font-weight: 800;
    line-height: 1.1;
  }
  </style>