<template>
  <div class="comment-section-outer">
    <div class="comment-section">
      <div class="comment-count">2개의 댓글</div>
      <div class="comment-input-row">
        <textarea class="comment-input" placeholder="댓글을 작성하세요"></textarea>
        <button class="comment-submit-btn">댓글 작성</button>
      </div>
      <div class="comment-list">
        <div class="comment-item">
          <img class="comment-profile" src="https://randomuser.me/api/portraits/men/12.jpg" alt="프로필" />
          <div class="comment-content">
            <div class="comment-meta">
              <span class="comment-nickname">건강요정</span>
              <span class="comment-date">2025-04-11</span>
            </div>
            <div class="comment-text">흥미롭네요! 다음이 더 기대되는 글인것 같습니다!다다다우다다 달려가다가다 넘어질뻔 했습니다..!!<br>다음에 기회가 된다면 한번 방문 할게요!!!</div>
          </div>
        </div>
        <div class="comment-item">
          <img class="comment-profile" src="https://randomuser.me/api/portraits/men/12.jpg" alt="프로필" />
          <div class="comment-content">
            <div class="comment-meta">
              <span class="comment-nickname">건강요정</span>
              <span class="comment-date">2025-04-11</span>
            </div>
            <div class="comment-text">흥미롭네요! 다음이 더 기대되는 글인것 같습니다!다다다우다다 달려가다가다 넘어질뻔 했습니다..!!<br>다음에 기회가 된다면 한번 방문 할게요!!!</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.comment-section-outer {
  width: 100%;
  background: #f7f8fa;
  display: flex;
  justify-content: center;
}
.comment-section {
  max-width: 1280px;
  margin: 48px auto 0 auto;
  background: #f7f8fa;
  border-radius: 16px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
  padding: 36px 32px 32px 32px;
  box-sizing: border-box;
  width: 100%;
}
.comment-count {
  font-size: 1.08rem;
  font-weight: bold;
  margin-bottom: 18px;
  color: #222;
}
.comment-input-row {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 32px;
}
.comment-input {
  width: 100%;
  min-height: 80px;
  border: 1px solid #ddd;
  border-radius: 12px;
  padding: 16px;
  font-size: 1rem;
  resize: vertical;
  box-sizing: border-box;
}
.comment-submit-btn {
  align-self: flex-end;
  background: #222;
  color: #fff;
  border: none;
  border-radius: 12px;
  padding: 8px 24px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.18s;
}
.comment-submit-btn:hover {
  background: #444;
}
.comment-list {
  display: flex;
  flex-direction: column;
  gap: 28px;
}
.comment-item {
  display: flex;
  align-items: flex-start;
  gap: 18px;
}
.comment-profile {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  object-fit: cover;
  margin-top: 2px;
}
.comment-content {
  flex: 1;
}
.comment-meta {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1rem;
  margin-bottom: 4px;
}
.comment-nickname {
  font-weight: bold;
  color: #222;
}
.comment-date {
  color: #aaa;
  font-size: 0.97rem;
}
.comment-text {
  font-size: 1.02rem;
  color: #444;
  line-height: 1.6;
}
</style> 