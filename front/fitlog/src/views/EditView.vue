<template>
  <div v-if="board" class="edit-container">
    <div class="edit-header">

      <!-- Cover 이미지 수정 -->
      <label class="cover-label">
        <input type="file" accept="image/*" @change="onCoverChange" hidden />
        <span class="cover-btn">메인 이미지 수정</span>
      </label>

      <div class="cover-preview">
        <img :src="coverPreview || board.coverImage" alt="미리보기" />
      </div>

      <!-- 카테고리 선택 -->
      <select class="category-select" v-model="selectedCategory">
        <option disabled value="">카테고리 선택</option>
        <option v-for="cat in categories" :key="cat.value" :value="cat.value">
          {{ cat.label }}
        </option>
      </select>
    </div>

    <input class="title-input" type="text" v-model="title" placeholder="Title" />

    <div id="editorContainer" ref="editorContainer"></div>

    <!-- 파일 등록 + 수정 버튼 -->
    <div class="edit-btn-row">
      <label class="file-upload-label">
        파일 수정하기
        <input
          type="file"
          multiple
          @change="handleFiles"     
          class="file-input"
          accept=".zip,.pdf,.doc,.docx,.hwp,.jpg,.png,.jpeg,.gif,.txt,.ppt,.pptx,.xls,.xlsx"
        />
      </label>
      <button class="edit-btn" @click="updatePost">수정하기</button>
    </div>

    <!-- 첨부 파일 목록 -->
    <div v-if="files.length" class="file-list">
      <div v-for="(file, idx) in files" :key="file.name" class="file-item">
        {{ file.name }}
        <button class="file-remove-btn" @click="removeFile(idx)">×</button>
      </div>
    </div>
  </div>
  <div v-else class="loading">게시물을 불러오는 중...</div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import Editor from '@toast-ui/editor'
import '@toast-ui/editor/dist/toastui-editor.css'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css'
import { getoneBoard, updateBoard, getfileInformaton, serveFile } from '@/api/board'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// toast ui editor 설정
const editorContainer = ref(null)
const editorInstance = ref(null)

const files = ref([])
const coverPreview = ref(null)
const defaultCover = '/un.png'  // 기본 이미지
const title = ref('')
const selectedCategory = ref('')
const board = ref(null)
const fileResponse = ref(null)

const categories = [
  { value: 1, label: '운동/트레이닝' },
  { value: 2, label: '재활/통증 관리' },
  { value: 3, label: '영양/식단' },
  { value: 4, label: '정신 건강/라이프스타일' },
  { value: 5, label: '의학/질환 정보' }
]

// 게시글 데이터 로드 함수
const loadBoard = async () => {
  try {
    const response = await getoneBoard(route.params.colboardId)
    board.value = response
    title.value = board.value.title
    selectedCategory.value = board.value.category

    // 파일 정보 가져오기
    const fileRes = await getfileInformaton(route.params.colboardId)
    fileResponse.value = fileRes

    // 커버 이미지가 있는 경우 서버에서 가져와서 표시
    if (fileRes && fileRes.length > 0) {
      const coverImageUrl = `${import.meta.env.VITE_FITLOG_API_URL}/upload/sendImg/${fileRes[0].uploadName}`;
      coverPreview.value = coverImageUrl;
      
      // 파일 정보를 files 배열에 추가
      fetch(coverImageUrl)
        .then(res => res.blob())
        .then(blob => {
          const file = new File([blob], fileRes[0].originalName, { type: blob.type })
          files.value = [file]  // 커버 이미지를 files[0]에 설정
        })
    } else {
      // 커버 이미지가 없는 경우 기본 이미지 사용
      fetch(defaultCover)
        .then(res => res.blob())
        .then(blob => {
          const file = new File([blob], 'un.png', { type: blob.type })
          files.value = [file]
        })
    }

    // DOM이 마운트된 후에 에디터 초기화
    await nextTick()
    if (editorContainer.value) {
      editorInstance.value = new Editor({
        el: editorContainer.value,
        height: '500px',
        initialEditType: 'wysiwyg',
        initialValue: board.value.content,
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
      })
    }
  } catch (error) {
    console.error('게시글 로드 실패:', error)
    alert('게시글을 불러오는데 실패했습니다.')
    router.push('/')
  }
}

// 컴포넌트 마운트 시 게시글 로드
onMounted(() => {
  loadBoard()
})

function onCoverChange(e) {
  const file = e.target.files[0]
  if (file) {
    coverPreview.value = URL.createObjectURL(file)
    // files[0]에 항상 커버 이미지가 오도록 함
    if (files.value.length > 0) {
      files.value[0] = file
    } else {
      files.value = [file]
    }
  }
}

function handleFiles(e) {
  const selected = Array.from(e.target.files)
  const existingNames = new Set(files.value.slice(1).map((f) => f.name + f.size))
  const newFiles = selected.filter((f) => !existingNames.has(f.name + f.size))
  files.value = [files.value[0], ...newFiles]
  e.target.value = ''
}

function removeFile(idx) {
  if (idx === 0) return
  files.value.splice(idx, 1)
}

async function updatePost() {
  // 제목, 카테고리 검증
  if (selectedCategory.value === '') {
    alert("카테고리를 선택하세요.")
    return
  }

  if (title.value === '') {
    alert("제목을 입력하세요.")
    return
  } else if (title.value.length < 3) {
    alert("제목이 너무 짧습니다.")
    return
  }

  const confirm = window.confirm("수정을 완료하시겠습니까?")
  if (!confirm) return

  const formData = new FormData()
  formData.append('userId', board.value.userId)
  formData.append('category', selectedCategory.value)
  formData.append('title', title.value)
  formData.append('content', editorInstance.value.getHTML())

  // 파일 추가
  files.value.forEach(file => {
    formData.append('attach', file)
  })

  try {
    await updateBoard(route.params.colboardId, formData)
    alert('수정이 완료되었습니다!')
    router.push(`/show/${route.params.colboardId}`)
  } catch (err) {
    console.error('수정 실패:', err)
    alert('수정 중 오류가 발생했습니다.')
  }
}

// 컴포넌트가 제거될 때 에디터 인스턴스 정리
onBeforeUnmount(() => {
  if (editorInstance.value) {
    editorInstance.value.destroy()
  }
})
</script>

<style scoped>
.edit-container {
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

.edit-header {
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

.edit-btn-row {
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

.edit-btn {
  background: #222;
  color: #fff;
  font-size: 1.2rem;
  padding: 12px 36px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: background 0.2s;
}

.edit-btn:hover {
  background: #444;
}

.loading {
  text-align: center;
  padding: 2rem;
  font-size: 1.2rem;
  color: #666;
}
</style>
