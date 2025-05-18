<template>
  <div
    class="author-recommend-card draggable"
    draggable="true"
    style="position:sticky; top:32px; z-index:2;"
  >
    <div class="recommend-title">작가의 다른 콘텐츠</div>
    <ul class="recommend-list">
      <li>영양/운동법<br><span class="recommend-link">운동과 영양관리</span></li>
      <li>영양/칼럼<br><span class="recommend-link">영양의 중요성</span></li>
      <li>영양/칼럼<br><span class="recommend-link">추천식단asdfasdfasdfasdf</span></li>
    </ul>
  </div>
</template>

<style scoped>
.author-recommend-card {
  width: 240px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 16px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.04);
  padding: 24px 20px 20px 20px;
  box-sizing: border-box;
  margin-top: 24px;
  cursor: grab;
}
.author-recommend-card:active {
  cursor: grabbing;
}
.recommend-title {
  font-size: 1.08rem;
  font-weight: bold;
  margin-bottom: 14px;
  color: #222;
}
.recommend-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.recommend-list li {
  font-size: 0.98rem;
  color: #444;
  margin-bottom: 12px;
  line-height: 1.4;
}
.recommend-link {
  color: #4a90e2;
  font-weight: 500;
  cursor: pointer;
  display: block;
  margin-top: 2px;
}
</style> 