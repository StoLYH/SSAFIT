<template>
  <section class="main-banner">
    <img src="/banner.png" alt="banner" />
  </section>
  <CategoryBar />
</template>

<script setup>
import CategoryBar from './CategoryBar.vue'
</script>

<style scoped>
.main-banner {
  position: relative;
  width: 100%;
  overflow: hidden;
  margin-bottom: 32px;
}
.main-banner img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: top;
  filter: brightness(0.7);
}
.logo-color {
  color: #f9c846;
  font-weight: bold;
}
</style> 