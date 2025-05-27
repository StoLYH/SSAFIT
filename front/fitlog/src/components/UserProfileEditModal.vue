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
  emit('save', { ...userInfo, attach: file.value })
  emit('close')
}
function close() {
  emit('close')
}
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 32px;
  border-radius: 16px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.modal-content h3 {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 24px;
  color: #333;
  text-align: center;
}

.profile-img-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 12px;
}

.profile-img {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.profile-img-area input[type="file"] {
  background: #fff;
  padding: 8px 16px;
  border-radius: 8px;
  border: 1px solid #ddd;
  cursor: pointer;
  font-size: 0.9rem;
  color: #666;
  transition: all 0.2s;
}

.profile-img-area input[type="file"]:hover {
  background: #f0f0f0;
  border-color: #ccc;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #444;
  font-size: 0.95rem;
}

input, select, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s;
  background: #fff;
}

input:focus, select:focus, textarea:focus {
  outline: none;
  border-color: #3b5998;
  box-shadow: 0 0 0 2px rgba(59, 89, 152, 0.1);
}

select {
  appearance: none;
  background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right 12px center;
  background-size: 16px;
  padding-right: 40px;
}

.modal-actions {
  margin-top: 32px;
  display: flex;
  gap: 12px;
  justify-content: flex-end;
}

.modal-actions button {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.modal-actions button:first-child {
  background: #3b5998;
  color: white;
}

.modal-actions button:last-child {
  background: #f0f0f0;
  color: #666;
}

.modal-actions button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.modal-actions button:first-child:hover {
  background: #344e86;
}

.modal-actions button:last-child:hover {
  background: #e0e0e0;
}
</style>