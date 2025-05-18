<<<<<<< HEAD
<script setup>
import DebatePage from './president/DebatePage.vue';
import MainPage from './president/MainPage.vue'
import MainCategory from './president/MainCategory.vue';
import register from './president/register.vue';
import LandingPage from './president/LandingPage.vue';
</script>

<template>
  <LandingPage></LandingPage>
  <MainPage></MainPage>
=======
<template>
  <RouterView />
>>>>>>> 501e7ac2458b9fbd4584d2dcb52ace5e67d8d2ff
</template>

<script setup>
import { RouterView } from 'vue-router'
</script>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
