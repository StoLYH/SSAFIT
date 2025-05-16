<template>
  <FitlogHeader />
  <input class="title-input" type="text" placeholder="Title" />

  <div id="editorContainer" ref="editorContainer"></div>

  <!-- 파일 등록 + 등록 버튼 -->
  <div class="register-btn-row">
    <label class="file-upload-label">
      파일 등록하기
      <input
        type="file"
        multiple
        @change="handleFiles"
        class="file-input"
        accept=".zip,.pdf,.doc,.docx,.hwp,.jpg,.png,.jpeg,.gif,.txt,.ppt,.pptx,.xls,.xlsx"
      />
    </label>
    <button class="register-btn" @click="registerPost">등록하기</button>
  </div>

  <div v-if="files.length" class="file-list">
    <div v-for="(file, idx) in files" :key="file.name + idx" class="file-item">
      {{ file.name }}
      <button class="file-remove-btn" @click="removeFile(idx)">×</button>
    </div>
  </div>

  <FitlogFooter />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import Editor from '@toast-ui/editor'
import '@toast-ui/editor/dist/toastui-editor.css'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css'

import FitlogHeader from '../components/FitlogHeader.vue'
import FitlogFooter from '../components/FitlogFooter.vue'

const editorContainer = ref(null)
const editorInstance = ref(null)
const files = ref([])

onMounted(() => {
  editorInstance.value = new Editor({
    el: editorContainer.value,
    height: '500px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    placeholder: '여기에 게시글을 입력하세요...',
    plugins: [colorSyntax],
    toolbarItems: [
      ['heading', 'bold', 'italic', 'strike'],
      ['hr', 'quote'],
      ['ul', 'ol', 'task'],
      ['table', 'link'],
      ['code', 'codeblock'],
      ['image'],
      ['scrollSync']
    ]
  });
});

function handleFiles(e) {
  const selected = Array.from(e.target.files);
  const existingNames = new Set(files.value.map(f => f.name + f.size + f.lastModified));
  const newFiles = selected.filter(f => !existingNames.has(f.name + f.size + f.lastModified));
  files.value = [...files.value, ...newFiles];
  e.target.value = '';
}

function removeFile(idx) {
  files.value.splice(idx, 1);
}

async function registerPost() {
  const formData = new FormData();

  // 임의 지정 값 (key를 DTO와 정확히 일치)
  formData.append('userId', 'user001');
  formData.append('category', String(1)); // 숫자형이지만 문자열로 보내도 Spring이 자동 변환

  // 입력 값 추출
  const title = document.querySelector('.title-input').value;
  const content = editorInstance.value.getHTML(); // 또는 getMarkdown()

  formData.append('title', title);
  formData.append('content', content);

  // 첨부 파일 추가 (key: attach)
  files.value.forEach(file => {
    formData.append('attach', file);
  });

  try {
    const res = await axios.post('http://localhost:8080/board', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    alert('등록 완료!');
    console.log('서버 응답:', res.data);
  } catch (err) {
    console.error('등록 실패:', err);
    alert('등록 실패!');
  }
}
</script>

<style scoped>
.register-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}
.title-input {
  width: 100%;
  font-size: 2rem;
  padding: 18px 20px;
  margin-bottom: 18px;
  border: 1px solid #ddd;
  border-radius: 20px;
  box-sizing: border-box;
}
.editor-area {
  min-height: 500px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 1px 8px rgba(0,0,0,0.06);
  margin-bottom: 18px;
}
.register-btn-row {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-top: 18px;
  gap: 16px;
}
.file-upload-label {
  background: #f7f8fa;
  color: #222;
  border: 1px solid #bbb;
  border-radius: 16px;
  padding: 10px 24px;
  font-size: 1.1rem;
  cursor: pointer;
  margin-right: 8px;
  transition: background 0.2s, border 0.2s;
  display: inline-block;
}
.file-upload-label:hover {
  background: #e0e0e0;
  border-color: #888;
}
.file-input {
  display: none;
}
.file-list {
  margin-top: 10px;
  font-size: 0.98rem;
  color: #444;
}
.file-item {
  margin-bottom: 2px;
  display: flex;
  align-items: center;
  gap: 8px;
}
.file-remove-btn {
  background: #ff4d4f;
  color: #fff;
  border: none;
  border-radius: 50%;
  width: 22px;
  height: 22px;
  font-size: 16px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 6px;
  transition: background 0.2s;
}
.file-remove-btn:hover {
  background: #d9363e;
}
.register-btn {
  background: #222;
  color: #fff;
  font-size: 1.2rem;
  padding: 12px 36px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: background 0.2s;
}
.register-btn:hover {
  background: #444;
}
</style>
