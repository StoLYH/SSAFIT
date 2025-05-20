<template>
  <div class="register-container">

    <div class="register-header">
      <!-- Cover 이미지 등록 -->
      <label class="cover-label">
        <input type="file" accept="image/*" @change="onCoverChange" hidden />   <!-- 이미지 파일만 가능 -->
        <span class="cover-btn">메인 이미지 등록</span>
      </label>
      <div class="cover-preview">
        <img :src="coverPreview || defaultCover" alt="미리보기" />
      </div>

      <!-- 카테고리 선택 -->
      <select class="category-select" v-model="selectedCategory">
        <option disabled value="">카테고리 선택</option>
        <option v-for="cat in categories" :key="cat.value" :value="cat.value">
          {{ cat.label }}
        </option>
      </select>
    </div>

    <input class="title-input" type="text" placeholder="Title" />   <!-- 제목 입력 -->

    <div id="editorContainer" ref="editorContainer"></div>          <!-- 내용 입력  editorContainer는 Toast UI Editor의 UI와 기능 랜더링-->

    <!-- 파일 등록 + 등록 버튼, @change는 입력을 확정할 시 발생하는 이밴트 -->
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

    <!-- 첨부 파일 목록(수정,삭제가능) -->
    <div v-if="files.length" class="file-list">
      <div v-for="(file, idx) in files" :key="file.name" class="file-item">
        {{ file.name }}
        <button class="file-remove-btn" @click="removeFile(idx)">×</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Editor from '@toast-ui/editor'
import '@toast-ui/editor/dist/toastui-editor.css'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css'
import { registForm } from '@/api/board'
import {useRouter} from 'vue-router';


const router = useRouter();

// toast ui editor 설정
const editorContainer = ref(null)   // 게시글 content 랜더링 위치 설정
const editorInstance = ref(null)    // 게시글 content 객체


const files = ref([])           // 섬내일 이미지, 첨부파일 저장
const coverPreview = ref(null)
const defaultCover = '/un.png'  // 기본 이미지

onMounted(() => {
    editorInstance.value = new Editor({
      el: editorContainer.value,    // 에디어의 DOM 위치 설정
      height: '500px',
      initialEditType: 'wysiwyg',
      placeholder: '작성하세요.',
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

  // 마운트 시, 기본 이미지 파일을 files[0]에 넣음
  if (!files.value[0]) {
    fetch(defaultCover)
      .then(res => res.blob())  // 바이너리 처리
      .then(blob => {
        const file = new File([blob], 'un.png', { type: blob.type });
        files.value.unshift(file);  // 0번째에 등록
      });
  }
});

function onCoverChange(e) {   // e -> event객체
  const file = e.target.files[0]  // 이미지 파일
  if (file) {
    coverPreview.value = URL.createObjectURL(file)    // 미리보기 화면 제공

    // files[0]에 항상 커버 이미지가 오도록 함
    if (files.value.length > 0) {  
      files.value[0] = file        // 기존 0번째 mounted 될때 들어간 것은 삭제
    } 
  }
}

function handleFiles(e) {
  const selected = Array.from(e.target.files);  // 선택된 파일 배열로 만들기
  
  // cover 이미지는 files[0]에 고정, 나머지 -> 첨부파일
  const existingNames = new Set(files.value.slice(1).map((f) => f.name + f.size));
  /*
  files.value.slice(1) -> 기존 첨부파일만 가져온다
  map -> 배열의 각 요소를 "변환"해서 새 배열을 반환
  f.name + f.size -> 파일 이름과 크기를 문자열로 합친다
  Set -> 중복 제거
  */
  
  const newFiles = selected.filter((f) => !existingNames.has(f.name + f.size));

  /*
  selected -> 새로 첨부한 파일
  filter -> 조건에 맞는 요소만 남김
  !existingNames.has(f.name + f.size) -> 기존 첨부파일 중 중복 안되는 파일만 저장
  */

  files.value = [files.value[0], ...newFiles];  // 기존파일 + 새로 첨부한 파일 업데이트
  e.target.value = '';
}

function removeFile(idx) {
  // 커버 이미지는 files[0]이므로, idx==0은 삭제 불가
  if (idx === 0) return;
  files.value.splice(idx, 1);  // 해당 첨부파일 삭제
}

const categories = [
  { value: 1, label: '운동/트레이닝' },
  { value: 2, label: '재활/통증 관리' },
  { value: 3, label: '영양/식단' },
  { value: 4, label: '정신 건강/라이프스타일' },
  { value: 5, label: '의학/질환 정보' }
]
const selectedCategory = ref('')

async function registerPost() {

  // 제목,카테고리 => 미입력시 경고창
  if (selectedCategory.value == '') {
    window.alert("카테고리를 선택하세요.")
    return;
  }

  if (document.querySelector('.title-input').value == '') {
    window.alert("제목을 입력하세요."); 
    return;
  } else if (document.querySelector('.title-input').value.length < 3) {
    window.alert("제목이 너무 짧습니다.");
    return;
  } 


  const confirm = window.confirm("등록 완료 하시겠습니까?")
  if (!confirm) {
    return;
  }

  const formData = new FormData();
  formData.append('userId', 'user001'); // 하드코딩
  formData.append('category', selectedCategory.value);
  const title = document.querySelector('.title-input').value;
  const content = editorInstance.value.getHTML();
  formData.append('title', title);
  formData.append('content', content);

  // 파일 각각 attach로 추가
  files.value.forEach(file => {
    formData.append('attach', file);
  });

  try {
    const res = await registForm(formData);
    alert('등록 완료!');
    router.push("/category/1");
  } catch (err) {
    console.error('등록 실패:', err);
    alert('등록 실패!');
    router.push("/category/1");
  }
}

</script>

<style scoped>
.register-container {
  width: 100%;
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
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
.register-header {
  display: flex;
  align-items: center;
  gap: 24px;
  margin-bottom: 16px;
}
.cover-label {
  cursor: pointer;
}
.cover-btn {
  background: #222;
  color: #fff;
  border-radius: 8px;
  padding: 8px 20px;
  font-weight: bold;
  font-size: 1rem;
  display: inline-block;
}
.cover-preview img {
  width: 48px;
  height: 48px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #eee;
}
.category-select {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 8px 16px;
  font-size: 1rem;
  margin-left: auto;
}
</style>
