<template>
  <div class="modal-backdrop" @click.self="close">
    <div class="modal-content">
      <h3>프로필 정보 수정</h3>
      <div class="profile-img-area">
        <img :src="imgPreview || userInfo.profileImg || '/default-profile.png'" class="profile-img" />
        <input type="file" @change="onFileChange" accept="image/*" />
      </div>
      <div class="form-group">
        <label>이름</label>
        <input v-model="userInfo.userName" />
      </div>
      <div class="form-group">
        <label>역할</label>
        <select v-model.number="userInfo.userRole">
          <option value="1">의사</option>
          <option value="2">헬스트레이너</option>
          <option value="3">물리치료사</option>
          <option value="4">영양사</option>

        </select>
      </div> 
      <div class="form-group">
        <label>한 줄 소개</label>
        <textarea v-model="userInfo.onelineInfo" rows="2" />
      </div>
      <div class="form-group">
        <label>경력</label>
        <input v-model="userInfo.exper" />
      </div>
      <div class="modal-actions">
        <button @click="save">저장</button>
        <button @click="close">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
const props = defineProps({
  modelValue: Object // { userName, userRole, expr, onelineInfo, profileImg }
})
const emit = defineEmits(['update:modelValue', 'close', 'save'])

const userInfo = reactive({ ...props.modelValue })
const imgPreview = ref(null)
const file = ref(null)

watch(() => props.modelValue, v => Object.assign(userInfo, v))

function onFileChange(e) {
  const f = e.target.files[0]
  if (f) {
    file.value = f
    imgPreview.value = URL.createObjectURL(f)
  }
}

function save() {
  // emit('save', { ...userInfo, file: file.value }) // 파일 포함
  emit('save', { ...userInfo, profileImgFile: file.value })
  emit('close')
}
function close() {
  emit('close')
}
</script>

<style scoped>
.modal-backdrop { /* ...기존 스타일... */ }
.modal-content { /* ...기존 스타일... */ }
.profile-img-area { display: flex; align-items: center; gap: 16px; margin-bottom: 16px; }
.profile-img { width: 64px; height: 64px; border-radius: 50%; object-fit: cover; }
.form-group { margin-bottom: 12px; }
input, select, textarea { width: 100%; border-radius: 6px; border: 1px solid #ccc; padding: 8px; }
.modal-actions { margin-top: 16px; display: flex; gap: 12px; justify-content: flex-end; }
</style>