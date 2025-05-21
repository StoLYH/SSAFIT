<template>
    <form class="register-form" @submit.prevent="onRegister">
      <div class="input-wrapper">
        <input class="form-input" type="text" placeholder="아이디 또는 이메일" v-model="id" required />
        <button type="button" class="check-btn" @click="checkId">중복확인</button>
      </div>
      <input class="form-input" type="password" placeholder="비밀번호" v-model="password" required />
      <input class="form-input" type="password" placeholder="비밀번호 확인" v-model="passwordConfirm" required />
      <input class="form-input" type="text" placeholder="직업" v-model="job" required />
      <div class="input-wrapper">
        <input class="form-input" type="text" placeholder="닉네임" v-model="nickname" required />
        <button type="button" class="check-btn" @click="checkNickname">중복확인</button>
      </div>
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
  import { PostRegist, confirmId, confirmName } from '@/api/user';
  import { useRouter } from 'vue-router';

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

      // file과 User 정보를 한 번에 보내기
      const formData = new FormData()
      formData.append('userId', id.value)
      formData.append('password', password.value)
      formData.append('userName', nickname.value)
      formData.append('userRole', job.value)
      if (profileImage.value) {
        formData.append('attach', profileImage.value)
      }

      const result = await PostRegist(formData)

      if (result==="success") {
        alert('회원가입이 완료되었습니다.')
        router.push('/welcome')
      }
    } catch (error) {
      console.error('회원가입 중 오류 발생:', error)
      alert('회원가입 중 오류가 발생했습니다.')
    }
  }

  const checkId = async () => {
    const res = await confirmId(id.value);
      if (res === "중복") {
        alert('이미 사용 중인 아이디입니다.');
      } else {
        alert('사용 가능한 아이디입니다.');
      }
  };

  const checkNickname = async () => {
    const res = await confirmName(nickname.value);
      if (res === "중복") {
        alert('이미 사용 중인 닉네임입니다.');
      } else {
        alert('사용 가능한 닉네임입니다.');
      }
  };

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
  .input-wrapper {
    position: relative;
    width: 100%;
    margin-bottom: 0;
  }
  .input-wrapper .form-input {
    width: 100%;
    padding-right: 100px; /* 버튼 공간 확보 */
    box-sizing: border-box;
  }
  .input-wrapper .check-btn {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);
    width: 90px;
    height: 38px;
    margin: 0;
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
