<template>
<template v-if="!userStore.userId">
</template>
<template v-if="userStore.userId">
  <button class="register-btn" @click="LoginClick">등록하기</button>
</template>
</template>

<script setup>
import {ref} from 'vue';
import { useRouter} from 'vue-router';
import { useUserStore } from '@/stores/userstore';



const router = useRouter();
const login = ref(true);
const userStore = useUserStore();


function LoginClick() {
  console.log("등록함");
  if (login.value == true) {
    router.push('/register');
  }
  
}   


</script>

<style scoped>
.register-btn {
  background: #222;
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 12px 32px;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.2s;
  margin-top: 16px;
}
.register-btn:hover {
  background: #444;
}
</style> 