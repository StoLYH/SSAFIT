<template>
  <section class="banner-section">
    <div class="banner-black">
      <slot>댓글로 칼럼이 가장 인상적이었던 운동/칼럼가?</slot>
    </div>
  </section>
</template>

<script setup>
</script>

<style scoped>
.banner-section {
  margin: 40px 0;
  padding: 0 50px;
}
.banner-black {
  background: #222;
  color: #fff;
  text-align: center;
  padding: 32px 0;
  font-size: 1.1rem;
  border-radius: 8px;
  font-weight: bold;
}
</style> 