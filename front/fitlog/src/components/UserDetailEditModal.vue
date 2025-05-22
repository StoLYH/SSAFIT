<template>
    <div class="modal-backdrop" @click.self="close">
      <div class="modal-content">
        <h3>{{ field === 'onelineInfo' ? '한 줄 소개 수정' : '경력 수정' }}</h3>
        <textarea v-model="inputValue" rows="4" />
        <div class="modal-actions">
          <button @click="save">저장</button>
          <button @click="close">취소</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, watch } from 'vue'
  const props = defineProps({
    modelValue: String,
    field: String
  })
  const emit = defineEmits(['update:modelValue', 'close', 'save'])
  
  const inputValue = ref(props.modelValue)
  watch(() => props.modelValue, v => inputValue.value = v)
  
  function save() {
    emit('save', { field: props.field, value: inputValue.value })
    emit('close')
  }
  function close() {
    emit('close')
  }
  </script>
  
  <style scoped>
  .modal-backdrop {
    position: fixed; left: 0; top: 0; width: 100vw; height: 100vh;
    background: rgba(0,0,0,0.3); display: flex; align-items: center; justify-content: center; z-index: 1000;
  }
  .modal-content {
    background: #fff; border-radius: 12px; padding: 32px; min-width: 320px;
    box-shadow: 0 2px 16px rgba(0,0,0,0.15);
  }
  .modal-actions { margin-top: 16px; display: flex; gap: 12px; justify-content: flex-end; }
  textarea { width: 100%; border-radius: 6px; border: 1px solid #ccc; padding: 8px; }
  </style>