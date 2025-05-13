<template>
  <footer class="footer">
    <div class="footer-top">
      <span class="footer-link">서비스 전체보기</span>
      <div class="footer-sns">
        <span class="sns-icon">F</span>
        <span class="sns-icon">B</span>
        <span class="sns-icon">N</span>
      </div>
    </div>
    <div class="footer-divider"></div>
    <div class="footer-content">
      <div>
        <b>고객 문의</b><br>
        전화하기 010-0000-0000<br>
        이메일 fitlog@naver.com<br>
        <span class="footer-desc">10:00~18:00 주말/공휴일 제외</span>
      </div>
      <div>
        <b>FIT LOG</b><br>
        핏로그 소개<br>
        핏로그 크루 소개
      </div>
      <div>
        <b>기타 문의</b>
      </div>
    </div>
    <div class="footer-bottom">
      <div class="footer-info">
        <span>핏로그 | 대표이사 : 홍길동 | 서울특별시 강남구 테헤란로 211 5층 핏로그</span><br>
        <span>사업자등록번호 : 210-00-57103 | 통신판매업신고 : 제2024-서울강남-0377</span><br>
        <span>제휴·광고 | 문의하기 | 핏로그정책 | 개인정보처리방침 | 이용약관 | 청소년보호정책 | 고객센터</span><br>
        <span>© 2025 FITLOG</span>
      </div>
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
.footer {
  background: #fff;
  margin-top: 60px;
  padding: 0 0 0 0;
  font-size: 0.97rem;
  color: #222;
}
.footer-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1150px;
  margin: 0 auto;
  padding: 32px 0 8px 0;
  background: #fff;
}
.footer-link {
  color: #888;
  font-size: 1.05rem;
  cursor: pointer;
}
.footer-sns {
  display: flex;
  gap: 12px;
}
.sns-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 28px;
  height: 28px;
  background: #fff;
  border-radius: 50%;
  color: #888;
  font-weight: bold;
  font-size: 1.1rem;
  border: 1px solid #e0e0e0;
  cursor: pointer;
  transition: background 0.15s, color 0.15s;
}
.sns-icon:hover {
  background: #f9c846;
  color: #fff;
}
.footer-divider {
  width: 100%;
  max-width: 1150px;
  height: 1px;
  background: #e0e0e0;
  margin: 0 auto 24px auto;
  background: #fff;
}
.footer-content {
  display: flex;
  justify-content: space-between;
  max-width: 1150px;
  margin: 0 auto;
  padding-bottom: 24px;
  font-size: 1.01rem;
  background: #fff;
}
.footer-content > div {
  flex: 1;
  min-width: 180px;
}
.footer-desc {
  color: #aaa;
  font-size: 0.95rem;
}
.footer-bottom {
  background: #fff;
  padding: 18px 0 32px 0;
  text-align: center;
  color: #aaa;
  font-size: 0.93rem;
  border-top: 1px solid #e0e0e0;
}
.footer-info {
  max-width: 1150px;
  margin: 0 auto;
  text-align: left;
  color: #aaa;
  font-size: 0.93rem;
  line-height: 1.7;
  background: #fff;
}
@media (max-width: 900px) {
  .footer-content {
    flex-direction: column;
    gap: 16px;
    align-items: center;
  }
  .footer-top, .footer-divider, .footer-info {
    max-width: 98vw;
  }
}
</style> 