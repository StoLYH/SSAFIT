<template>
    <form class="login-form" @submit.prevent="onLogin">
      <input class="form-input" type="text" placeholder="아이디 또는 이메일" v-model="id" required />
      <input class="form-input" type="password" placeholder="비밀번호" v-model="password" required />
      <div class="form-btns">
        <button type="submit" class="btn btn-login">로그인</button>
        <button type="button" class="btn btn-register" @click="goToRegister">회원가입</button>
      </div>
    </form>
  </template>
  
  <script setup>
import router from '@/router';


const goToRegister = () => {
  router.push('regist')
}
  import { ref } from 'vue'
  import { useUserStore } from '@/stores/userstore' // <-- 이게 핵심!



const userStore = useUserStore();
const id = ref('')
const password = ref('')

  const onLogin = async() => {
    const result = await userStore.login({
      userId: id.value, password: password.value
    }) 

    if (result === true) {
        router.replace('/')
    } else {
        alert(result.message || '로그인 실패!')
    }
  }

  </script>
  
  <style scoped>
  .login-form {
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
  .form-btns {
    display: flex;
    gap: 10px;
  }
  .btn {
    flex: 1;
    padding: 12px 0;
    border: none;
    border-radius: 4px;
    font-size: 1.05rem;
    font-weight: 600;
    cursor: pointer;
  }
  .btn-login {
    background: #222;
    color: #fff;
  }
  .btn-register {
    background: #f7f97a;
    color: #222;
  }
  </style>