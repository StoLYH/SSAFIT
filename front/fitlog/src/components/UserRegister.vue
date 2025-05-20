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
      <div v-if="previewUrl" class="image-preview">
        <img :src="previewUrl" alt="프로필 이미지 미리보기">
      </div>
      <button type="submit" class="btn btn-register">회원가입</button>
      <button type="button" class="btn btn-login" @click="goToLogin">로그인</button>
    </form>
  </template>
  
  <script setup>
  import { ref, onBeforeUnmount } from 'vue'
  import { PostRegist } from '@/api/user';
  import { useRouter } from 'vue-router';
  import { api_file } from '@/api';

  const router = useRouter();

  const goToLogin = () => {
    router.push('/login')
  }

  const id = ref('')
  const password = ref('')
  const passwordConfirm = ref('')
  const job = ref('')
  const nickname = ref('')
  const profileImage = ref(null)
  const previewUrl = ref(null)

  const onProfileImageChange = (e) => {
    const file = e.target.files[0]
    if (file) {
      profileImage.value = file
      // 이미지 미리보기 생성
      previewUrl.value = URL.createObjectURL(file)
    }
  }

  const onRegister = async () => {
    try {
      // 비밀번호 확인
      if (password.value !== passwordConfirm.value) {
        alert('비밀번호가 일치하지 않습니다.')
        return
      }

      // 프로필 이미지 업로드
      if (profileImage.value) {
        const formData = new FormData()
        formData.append('file', profileImage.value)
        formData.append('userId', id.value)

        const response = await api_file.post('/profile', formData)

        if (response.status !== 201) {
          throw new Error('프로필 이미지 업로드 실패')
        }
      }

      // 회원가입 정보 전송
      const result = await PostRegist({
        userId: id.value,
        password: password.value,
        userName: nickname.value,
        userRole: job.value
      })

      if (result) {
        alert('회원가입이 완료되었습니다.')
        router.push('/login')
      }
    } catch (error) {
      console.error('회원가입 중 오류 발생:', error)
      alert('회원가입 중 오류가 발생했습니다.')
    }
  }

  // 컴포넌트가 제거될 때 미리보기 URL 해제
  onBeforeUnmount(() => {
    if (previewUrl.value) {
      URL.revokeObjectURL(previewUrl.value)
    }
  })
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
  .image-preview {
    width: 100%;
    max-width: 200px;
    margin: 0 auto;
  }

  .image-preview img {
    width: 100%;
    height: auto;
    border-radius: 4px;
    border: 1px solid #ddd;
  }
  </style>