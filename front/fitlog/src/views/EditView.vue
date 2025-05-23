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
    <div v-if="files.length > 1" class="file-list">
      <div class="file-list-header">
        <h3>첨부 파일 목록</h3>
      </div>
      <div>
        <div v-for="(file, idx) in files.slice(1)" :key="file.name" class="file-item">
          {{ file.name }}
          <button class="file-remove-btn" @click="removeFile(idx + 1)">×</button>
        </div>
      </div>
    </div>

  </div>

</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import Editor from '@toast-ui/editor'
import '@toast-ui/editor/dist/toastui-editor.css'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css'
import { getoneBoardWithoutCnt, updateBoard, getfileInformaton, serveFile } from '@/api/board'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// toast ui editor 설정
const editorContainer = ref(null)
const editorInstance = ref(null)

const files = ref([])          // 최종적으로 서버에 제출할 파일목록
const coverPreview = ref(null) // 커버 이미지 미리보기용
const title = ref('')
const selectedCategory = ref('')
const board = ref(null)
const fileResponse = ref(null)
const existingFiles = ref([])  // 기존 파일 목록
const addedFiles = ref([])     // 새로 추가된 파일 목록 (ref)

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
    const data = await getoneBoardWithoutCnt(route.params.colboardId)
    board.value = data                        // 게시글 객체
    title.value = board.value.title           // 게시글 제목
    selectedCategory.value = board.value.category  // 게시글 카테고리

    // 파일 정보 가져오기
    const fileRes = await getfileInformaton(route.params.colboardId)  // 파일 객체(기존)
    fileResponse.value = fileRes
    existingFiles.value = fileRes      // 기존 파일 목록 저장

    // 커버 이미지 서버에서 가져와서 표시
    const coverImageUrl = `http://localhost:8080/upload/sendImg/${fileRes[0].uploadName}`
    coverPreview.value = coverImageUrl
    
    // 모든 기존 파일을 files 배열에 추가
    const loadExistingFiles = async () => {
      // 커버 이미지 로드
      const coverBlob = await fetch(coverImageUrl).then(res => res.blob())  // 파일 => js 사용하도록 2진화
      const coverFile = new File([coverBlob], fileRes[0].originalName, { type: coverBlob.type }) 
      /* 
      서버로부터 파일 (파일 객체가 아니다) => 다시 File 객체로 만들어야 브라우저 혹은 서버로 Formdata로 전송이 가능하다.
    
      new File[파일의2진데이터, 기존파일이름, 파일타입 변수명(무의미)]
      - 파일타입은 이미지일 경우 image/jpeg, image/png, image/gif 등 (브라우저가 http 요청 보낼 시 알아서 보고 채워준다)
      - coverBlob은 파일의 타입을 조회할 때 쓰는 변수이름
      */
      files.value = [coverFile]  // 커버 이미지를 files[0]에 설정

      // 나머지 기존 파일들도 로드 (1번째부터)
      for (let i = 1; i < fileRes.length; i++) {
        const fileUrl = `http://localhost:8080/upload/sendImg/${fileRes[i].uploadName}`
        try {
          const response = await fetch(fileUrl)   // 서버로부터 받은 파일
          const blob = await response.blob()     // 파일 => js 사용하도록 2진화
          const file = new File([blob], fileRes[i].originalName, { type: blob.type })
          files.value.push(file)  // 기존 파일들을 files 배열에 추가
        } catch (error) {
          console.error(`파일 로드 실패: ${fileRes[i].originalName}`, error)
        }
      }
    }

    await loadExistingFiles()  // 기존 파일들 로드

    // DOM이 마운트된 후에 에디터 초기화 -> 기존 게시물모두 로드된 이후 toast editor 로드
    await nextTick()
    if (editorContainer.value) {
      editorInstance.value = new Editor({
        el: editorContainer.value,
        height: '500px',
        initialEditType: 'wysiwyg',
        initialValue: board.value.content,    // 기존 게시글 내용 불러오기
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
  const file = e.target.files[0]  // 교체한 cover 이미지
  if (file) {
    coverPreview.value = URL.createObjectURL(file) // 이미지 미리보기
    // files[0]에 항상 커버 이미지가 오도록 함
    files.value[0] = file  // 기존 이미지 파일 교체
  }
}

function handleFiles(e) {
  const selected = Array.from(e.target.files); // 새로 선택한 파일들

  // 이미 files에 있는 파일 이름+크기 조합을 set으로 만듦 (커버 제외)
  const existingNames = new Set(files.value.slice(1).map(f => f.name + f.size));

  // 새로 선택한 파일 중 기존에 없는 것만 추가
  const uniqueNewFiles = selected.filter(f => !existingNames.has(f.name + f.size));

  // files에 커버 이미지는 그대로 두고, 새 파일만 추가
  files.value = [...files.value, ...uniqueNewFiles];
  addedFiles.value = [...addedFiles.value, ...uniqueNewFiles];

  e.target.value = '';
}

function removeFile(idx) {
  
  // files 배열에서 파일 삭제
  files.value.splice(idx, 1)
  
  // 새로 추가된 파일인 경우에만 addedFiles에서도 삭제
  const newFileIdx = addedFiles.value.findIndex(f => f === files.value[idx])
  if (newFileIdx !== -1) {
    addedFiles.value.splice(newFileIdx, 1)
  }
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
  console.log(selectedCategory.value + "dd")


  formData.append('title', title.value)
  formData.append('content', editorInstance.value.getHTML())

  // 파일 추가 (커버 이미지 + 새 파일)
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

.file-list-header {
  margin-bottom: 12px;
}

.file-item {
  margin-bottom: 4px;
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 12px;
  border-radius: 4px;
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
  transition: background 0.2s;
  margin-left: 8px;
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
