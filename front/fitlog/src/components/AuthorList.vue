<template>
  <section class="section">
    <h2>{{ title }}</h2>
    <div class="author-list">
      <div class="author-card" v-for="(author, idx) in authors" :key="idx">
        <div class="author-name">{{ author.name }}</div>
        <div class="author-subtitle">“{{ author.subtitle }}”</div>
        <ul class="column-list">
          <li v-for="(col, i) in author.columns" :key="i">
            <img src="/un.png" alt="썸네일" class="square-img" />
            <span class="col-title">{{ col }}</span>
          </li>
        </ul>
        <div class="more-link">해당 작가의 글 더보기 &gt;</div>
      </div>
    </div>
  </section>
</template>

<script setup>
const props = defineProps({
  title: String,
  authors: {
    type: Array,
    default: () => []
  }
})





</script>

<style scoped>
.section {
  margin: 40px 0 0 0;
  padding: 0 50px;
}
.section h2 {
  font-size: 1.7rem;
  margin-bottom: 18px;
  font-weight: bold;
}
.author-list {
  display: flex;
  gap: 48px;
}
.author-card {
  background: #fff;
  border-radius: 8px;
  padding: 40px 36px 24px 36px;
  width: 380px;
  min-width: 280px;
  box-shadow: 0 2px 8px #0001;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.author-name {
  font-weight: 900;
  font-size: 1.1rem;
  margin-bottom: 4px;
}
.author-subtitle {
  font-size: 1.05rem;
  color: #222;
  margin-bottom: 18px;
}
.column-list {
  list-style: none;
  padding: 0;
  margin: 0 0 18px 0;
}
.column-list li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.square-img {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  margin-right: 12px;
  flex-shrink: 0;
  object-fit: cover;
}
.col-title {
  font-size: 1rem;
  color: #222;
}
.more-link {
  margin-top: 8px;
  font-size: 0.95rem;
  color: #888;
  text-align: right;
  cursor: pointer;
}
.more-link:hover {
  color: #f9c846;
}
@media (max-width: 900px) {
  .author-list {
    flex-direction: column;
    gap: 16px;
    align-items: center;
  }
}
</style> 