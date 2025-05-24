<template>
    <div class="question-bg">
      <div class="question-container">
        <div class="fitlog-logo-area">
          <img src="/public/qna.png" alt="Fitlog 로고" class="fitlog-logo" />
          <div class="fitlog-title">Fitlog</div>
        </div>
        <div class="main-title">운동,영양 등 건강에 관한 궁금한 점 AI에게 물어보세요</div>
        <div class="sub-title">질문에 대한 뿐만 아니라 관련된 칼럼들도 소개해줘요</div>
        
  
        <div class="question-list">
          <div
            class="question-card"
            v-for="(q, i) in questions"
            :key="i"
            @click="handleQuestionClick(q)">
            {{ q }}
          </div>
        </div>
        
        <div class="question-input-row">
          <input
            v-model="input"
            class="question-input"
            type="text"
            placeholder="운동관련 무엇이든 물어보세요!"
          />
          <button class="question-btn" @click="askQuestion">물어보기</button>
        </div>
        <br><br>

        <!-- 답변 카드: 질문 입력란 바로 아래에 위치 -->
        <div v-if="answer" class="answer-card">
   <       <div class="answer-content">
            {{ answer }}
          </div>
        </div>
        
      </div>
    </div>
    <br><br>
    <h2 >추천하는 칼럼</h2>
    <br>
    <PostList :posts="posts" />                 
    <div v-if="isLoading" class="loading-overlay">
      <div class="spinner"></div>
      <div class="loading-text">답변을 생성중입니다...</div>
    </div>
  </template>   


<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import {useUserStore} from '@/stores/userstore';
import PostList from '@/components/PostList.vue';


const userStore = useUserStore();

const input = ref('');
const answer = ref('');
const isLoading = ref(false);

onMounted(() => {
  answer.value = userStore.answer;
}) 


function handleQuestionClick(q) {
  input.value = q;
  askQuestion();
}

async function askQuestion() {
  isLoading.value = true;
  await userStore.gptCall(input.value);
  answer.value = userStore.answer;
  isLoading.value = false;
  input.value=''
}

const questions = [
  "헬스장 다닌 지 1년쯤 됐는데 몸에 눈에 띄는 변화가 없어요. 식단은 신경 안 쓴 건 맞긴 한데 운동도 주 4~5회는 했는데 왜 이럴까요? 운동 루틴을 바꿔야 할까요?",
  "제가 헬스장을 다닌지 어언 1년정도 되는데 몸의 변화는 없는 것 같고 SSAFY하면서 힘들기만해서 운동도 가기가 힘든데 어떡할까요",
  "다이어트 목적으로 운동했는데 체중은 줄어도 근육은 안 붙어요. 그냥 마른 몸이 돼버린 느낌? 헬스보다 필라테스가 더 나은 걸까요?"
];

// 정적 더미 데이터
const posts = ref([
  {
    colboardId: 1,
    title: '운동 루틴 점검하기: 변화가 없는 이유',
    content: '운동을 꾸준히 해도 변화가 없다면 루틴을 점검해보세요.',
    category: 1,
    author: '트레이너A',
    date: '2024-06-10',
    viewCnt: 123,
    likeCount: 10
  },
  {
    colboardId: 2,
    title: '식단과 회복, 그리고 멘탈 관리법',
    content: '운동 효과를 높이려면 식단과 회복, 멘탈 관리가 중요합니다.',
    category: 3,
    author: '영양사B',
    date: '2024-06-09',
    viewCnt: 98,
    likeCount: 7
  },
  {
    colboardId: 3,
    title: '꾸준함의 힘: 운동 습관 만들기',
    content: '꾸준히 운동하는 습관을 만드는 방법을 소개합니다.',
    category: 4,
    author: '코치C',
    date: '2024-06-08',
    viewCnt: 77,
    likeCount: 5
  }
]);
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
  gap: 18px;
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
  background: #8e8e89;
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
.answer-card {
  width: 100%;
  max-width: 1100px;
  margin: 40px auto 0 auto;
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  padding: 36px 32px;
  font-size: 1.1rem;
  color: #222;
  word-break: keep-all;
  display: flex;
  justify-content: center;
  align-items: center;
}
.answer-content {
  line-height: 1.7;
  font-size: 1.1rem;
  color: #222;
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
  .answer-card {
    padding: 24px 10px;
    font-size: 1rem;
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
  .answer-card {
    padding: 16px 4px;
    font-size: 0.98rem;
  }
}
h2 {
  font-weight: 700;
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
</style>
