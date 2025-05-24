<template>
  <div class="question-bg">
    <div class="question-container">
      <div class="fitlog-logo-area">
        <img src="/public/qna.png" alt="Fitlog 로고" class="fitlog-logo" />
        <div class="fitlog-title">Fitlog</div>
      </div>
      <div class="main-title">운동,영양 등 건강에 관한 궁금한 점 AI에게 물어보세요</div>
      <div class="sub-title">질문에 대한 뿐만 아니라 관련된 칼럼들도 소개해줘요</div>
      <br>
      <br>

      <div class="question-list">
        <div
          class="question-card"
          v-for="(q, i) in questions"
          :key="i"
          @click="handleQuestionClick(q)"
        >
          {{ q }}
        </div>
      </div>
      <br>
      <br>
      <div class="question-input-row">
        <input
          v-model="input"
          class="question-input"
          type="text"
          placeholder="운동관련 무엇이든 물어보세요!"
        />
        <button class="question-btn" @click="askQuestion">물어보기</button>
      </div>
      <br>
    </div>
    <div v-if="isLoading" class="loading-overlay">
      <div class="spinner"></div>
      <div class="loading-text">답변을 생성중입니다...</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import {useUserStore} from '@/stores/userstore';

const router = useRouter();
const userStore = useUserStore();

const questions = [
  "제가 헬스장을 다닌지 어언 1년정도 되는데 몸의 변화는 없는 것 같고 싸피하면서 혼나게 힘들기만해서 운동도 가기가 힘든데 어떡할까요",
  "제가 헬스장을 다닌지 어언 1년정도 되는데 몸의 변화는 없는 것 같고 싸피하면서 혼나게 힘들기만해서 운동도 가기가 힘든데 어떡할까요",
  "제가 헬스장을 다닌지 어언 1년정도 되는데 몸의 변화는 없는 것 같고 싸피하면서 혼나게 힘들기만해서 운동도 가기가 힘든데 어떡할까요"
];

const input = ref('');
const isLoading = ref(false);

async function handleQuestionClick(q) {
  input.value = q;
  isLoading.value = true;
  await userStore.gptCall(input.value);
  isLoading.value = false;
  router.push('/ans');
}

async function askQuestion() {
  isLoading.value = true;
  await userStore.gptCall(input.value);
  isLoading.value = false;
  router.push('/ans');
}
</script>

<style scoped>
.question-bg {
  width: 100%;
  background: #181818;
  display: block;
  padding: 48px 0;
  border-radius: 32px;
}
.question-container {
  background: #181818;
  border: 2px solid #f8ce13;
  border-radius: 32px;
  padding: 48px 32px 32px 32px;
  max-width: 1150px;
  width: 100%;
  margin: 0 auto;
  box-shadow: 0 4px 32px #0008;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.fitlog-logo-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 18px;
}
.fitlog-logo {
  width: 64px;
  height: 64px;
  margin-bottom: 6px;
}
.fitlog-title {
  color: #f9c846;
  font-size: 2rem;
  font-weight: bold;
  letter-spacing: 2px;
}
.main-title {
  color: #fff;
  font-size: 1.25rem;
  font-weight: 700;
  margin-bottom: 6px;
  text-align: center;
}
.sub-title {
  color: #ccc;
  font-size: 1rem;
  margin-bottom: 32px;
  text-align: center;
}
.question-list {
  display: flex;
  gap: 40px;
  margin-bottom: 32px;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
}
.question-card {
  background: #fff;
  color: #222;
  border-radius: 16px;
  padding: 22px 18px;
  min-width: 220px;
  max-width: 260px;
  font-size: 1rem;
  font-weight: 500;
  box-shadow: 0 2px 12px #0002;
  text-align: left;
  flex: 1 1 220px;
  cursor: pointer;
  transition: background 0.18s, color 0.18s;
}
.question-card:hover {
  background: #f8ce13;
  color: #222;
}
.question-input-row {
  display: flex;
  width: 100%;
  max-width: 520px;
  margin: 0 auto;
  gap: 8px;
}
.question-input {
  flex: 1;
  border-radius: 12px;
  border: none;
  padding: 14px 18px;
  font-size: 1rem;
  background: #f3f3f3;
  color: #222;
  outline: none;
}
.question-btn {
  background: #f0f946;
  color: #222;
  border: none;
  border-radius: 12px;
  padding: 0 28px;
  font-size: 1rem;
  font-weight: 700;
  cursor: pointer;
  transition: background 0.18s;
}
.question-btn:hover {
  background: #ffe08a;
}
.loading-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0,0,0,0.4);
  z-index: 9999;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.spinner {
  border: 8px solid #f3f3f3;
  border-top: 8px solid #f8ce13;
  border-radius: 50%;
  width: 64px;
  height: 64px;
  animation: spin 1s linear infinite;
  margin-bottom: 18px;
}
@keyframes spin {
  0% { transform: rotate(0deg);}
  100% { transform: rotate(360deg);}
}
.loading-text {
  color: #fff;
  font-size: 1.2rem;
  font-weight: bold;
  text-shadow: 0 1px 8px #000;
}
@media (max-width: 900px) {
  .question-container {
    max-width: 98vw;
    padding: 24px 6vw 18px 6vw;
    border-radius: 18px;
  }
  .question-bg {
    padding: 24px 0;
    border-radius: 18px;
  }
}
@media (max-width: 700px) {
  .question-list {
    flex-direction: column;
    gap: 12px;
  }
  .question-card {
    min-width: 0;
    max-width: 100%;
    width: 100%;
    font-size: 0.98rem;
  }
  .question-input-row {
    flex-direction: column;
    gap: 8px;
    max-width: 100%;
  }
  .question-btn {
    width: 100%;
    padding: 12px 0;
    font-size: 1.05rem;
  }
}
</style>
