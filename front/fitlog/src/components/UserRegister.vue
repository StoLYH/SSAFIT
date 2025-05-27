<template>
    <form class="register-form" @submit.prevent="onRegister">
      <div class="form-group">
        <label class="form-label">아이디 또는 이메일</label>
        <div class="input-wrapper">
          <input class="form-input" type="text" v-model="id" required />
          <button type="button" class="check-btn" @click="checkId">확인</button>
        </div>
      </div>
      <div class="form-group">
        <label class="form-label">비밀번호</label>
        <div class="input-wrapper">
          <input class="form-input" type="password" v-model="password" required />
        </div>
      </div>
      <div class="form-group">
        <label class="form-label">비밀번호 확인</label>
        <div class="input-wrapper">
          <input class="form-input" type="password" v-model="passwordConfirm" required />
        </div>
      </div>
      <div class="form-group">
        <label class="form-label">닉네임</label>
        <div class="input-wrapper">
          <input class="form-input" type="text" v-model="nickname" required />
          <button type="button" class="check-btn" @click="checkNickname">확인</button>
        </div>
      </div>
      <div class="form-group">
        <label class="form-label">직업</label>
        <div class="job-radio-group">
          <label class="radio-label">
            <input type="radio" value="1" v-model="job" />
            <span class="custom-radio"></span>
            의사
          </label>
          <label class="radio-label">
            <input type="radio" value="2" v-model="job" />
            <span class="custom-radio"></span>
            헬스
          </label>
          <label class="radio-label">
            <input type="radio" value="3" v-model="job" />
            <span class="custom-radio"></span>
            의사
          </label>
          <label class="radio-label">
            <input type="radio" value="4" v-model="job" />
            <span class="custom-radio"></span>
            의사
          </label>
        </div>
      </div>
      <div class="form-group">
        <label class="profile-upload-box">
          <input type="file" accept="image/*" @change="onProfileImageChange" hidden />
          <span class="profile-box-text">프로필이미지등록</span>
        </label>
        <div v-if="previewUrl" class="image-preview">
          <img :src="previewUrl" alt="프로필 이미지 미리보기">
        </div>
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
    router.push('/welcome')
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
    width: 340px;
    padding: 28px;
    gap: 16px;
    border-radius: 12px;
    font-size: 0.97rem;
  }

  .form-group {
    gap: 5px;
  }

  .form-label {
    font-size: 1rem;
  }

  .input-wrapper {
    position: relative;
    width: 100%;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .input-wrapper .form-input {
    flex: 1 1 0%;
    width: 100%;
    min-width: 0;
    padding: 10px 12px;
    padding-right: 0;
    border: 1px solid #e0e0e0;
    border-radius: 7px;
    font-size: 0.97rem;
    background: #f9fafc;
    transition: all 0.2s;
    box-sizing: border-box;
    display: block;
  }

  .input-wrapper .check-btn {
    flex-shrink: 0;
    width: 64px;
    padding: 6px 0;
    border-radius: 8px;
    font-size: 0.95rem;
    margin-left: 4px;
  }

  .job-radio-group {
    gap: 18px;
  }

  .custom-radio {
    width: 16px;
    height: 16px;
    margin-right: 2px;
  }

  .radio-label {
    font-size: 0.97rem;
  }

  .profile-upload-box {
    width: 150px;
    height: 70px;
    border-radius: 8px;
  }

  .profile-box-text {
    font-size: 0.97rem;
  }

  .image-preview {
    width: 100%;
    max-width: 200px;
    margin: 0 auto;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .image-preview img {
    width: 100%;
    height: 200px;
    object-fit: cover;
    border-radius: 12px;
    transition: all 0.2s;
  }

  .btn {
    width: 100%;
    padding: 12px 0;
    border-radius: 8px;
    font-size: 1.05rem;
  }

  .btn-register {
    background: #6c6c6c;
    color: white;
    margin-bottom: 12px;
  }

  .btn-register:hover {
    background: #444;
  }

  .btn-login {
    background: #f5f5f5;
    color: #111;
    font-weight: 700;
  }

  .btn-login:hover {
    background: #e0e0e0;
  }

  input:focus, select:focus {
    outline: none;
    border-color: #56adff;
    box-shadow: 0 0 0 2px rgba(86, 173, 255, 0.1);
  }

  input:hover, select:hover {
    border-color: #ccc;
  }
  </style>
