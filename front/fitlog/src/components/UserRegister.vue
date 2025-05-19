<template>
    <form class="register-form" @submit.prevent="onRegister">
      <input class="form-input" type="text" placeholder="아이디 또는 이메일" v-model="id" required />
      <input class="form-input" type="password" placeholder="비밀번호" v-model="password" required />
      <input class="form-input" type="password" placeholder="비밀번호 확인" v-model="passwordConfirm" required />
      <input class="form-input" type="text" placeholder="직업" v-model="job" required />
      <input class="form-input" type="text" placeholder="닉네임" v-model="nickname" required />
      <label class="profile-upload">
        <input type="file" accept="image/*" @change="onProfileImageChange" hidden />
        <span class="profile-btn">프로필 이미지 등록</span>
      </label>
      <button type="submit" class="btn btn-register">회원가입</button>
      <button type="button" class="btn btn-login" @click="goToLogin">로그인</button>
    </form>
  </template>
  
  <script setup>
  import { ref } from 'vue'

  const goToLogin = () => {
  router.push('/login')
}



  const id = ref('')
  const password = ref('')
  const passwordConfirm = ref('')
  const job = ref('')
  const nickname = ref('')
  const onProfileImageChange = (e) => {
    // 프로필 이미지 업로드 로직
  }
  const onRegister = () => {
    // 회원가입 로직
  }
  </script>
  
  <style scoped>
  .register-form {
    width: 350px;
    display: flex;
    flex-direction: column;
    gap: 18px;
  }
  .form-input {
    width: 100%;
    padding: 13px 15px;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    background: #fff;
    margin-bottom: 0;
  }
  .profile-upload {
    display: flex;
    align-items: center;
    margin-bottom: 0;
  }
  .profile-btn {
    background: #3b5998;
    color: #fff;
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 0.98rem;
  }
  .btn {
    width: 100%;
    padding: 12px 0;
    border: none;
    border-radius: 4px;
    font-size: 1.05rem;
    font-weight: 600;
    cursor: pointer;
    margin-bottom: 0;
  }
  .btn-register {
    background: #f7f97a;
    color: #222;
    margin-bottom: 0;
  }
  .btn-login {
    background: #222;
    color: #fff;
    margin-bottom: 0;
  }
  </style>