<template>
  <div
    class="profile-card draggable"
    draggable="true"
    style="position:sticky; top:32px; z-index:2;"
  >
    <img class="profile-img" src="https://randomuser.me/api/portraits/women/44.jpg" alt="프로필" />
    <div class="profile-name">Lisa</div>
    <div class="profile-job">전문가/작가</div>
    <div class="profile-desc">안녕하세요 김작가입니다.<br>영양과 운동에 진심인 칼럼니스트입니다.<br>사내 영양관리와 운동지도를 맡아오며<br>여러분과 이야기를 이어가고자 해요!</div>
  </div>
</template>

<style scoped>
.profile-card {
  width: 240px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 16px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
  padding: 28px 20px 20px 20px;
  box-sizing: border-box;
  text-align: center;
  margin-top: 24px;
  cursor: grab;
}
.profile-card:active {
  cursor: grabbing;
}
.profile-img {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 12px;
}
.profile-name {
  font-size: 1.15rem;
  font-weight: bold;
  margin-bottom: 4px;
}
.profile-job {
  font-size: 0.98rem;
  color: #888;
  margin-bottom: 10px;
}
.profile-desc {
  font-size: 0.95rem;
  color: #444;
  line-height: 1.5;
}
</style> 