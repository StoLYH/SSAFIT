import DebatePageView from '@/views/DebatePageView.vue'
import MainCategoryView from '@/views/MainCategoryView.vue'
import MainPageView from '@/views/MainPageView.vue'
import RegisterView from '@/views/RegisterView.vue'
import ShowBoardView from '@/views/ShowBoardView.vue'
import LandingPage from '@/views/LandingPage.vue'
import Home from '@/views/Home.vue'
import UserRegister from '@/components/UserRegister.vue'
import UserLogin from '@/components/UserLogin.vue'
import MyPageView from '@/views/MyPageView.vue'
import EditView from '@/views/EditView.vue'
import QuestionView from '@/views/QuestionView.vue'
import AnsView from '@/views/AnsView.vue'

import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',              // 부모경로
      name: 'home',
      component: Home,        // 헤더,푸터 재사용 가능한 목록 
      children: [
        {
          path: '',          
          name: 'main',
          component: MainPageView,
        },
        {
          path: 'category/:categoryNumber',   //   부모경로 + category
          name: 'category',
          component: MainCategoryView,
        },
        {
          path: 'register',
          name: 'register',
          component: RegisterView,
        },
        {
          path: 'edit/:colboardId',
          name: 'edit',
          component: EditView,
        },
        {
          path: 'show/:colboardId',
          name: 'show',
          component: ShowBoardView,
        },
        {
          path: 'debate',
          name: 'debate',
          component: DebatePageView,
        },
        {
          path: 'mypage/:userId',   //   부모경로 + category
          name: 'mypage',
          component: MyPageView,
        },
        {
          path: 'question',   
          name: 'question',
          component: QuestionView,
        },
        {
          path: 'ans',   
          name: 'ans',
          component: AnsView,
        }
      ]
    },
    {
      path: '/welcome',
      name: 'landingpage',
      component: LandingPage,
      redirect: { name: 'login' }, 
      children: [
        {
          path: 'login',
          name: 'login',
          component: UserLogin,
        },
        {
          path: 'regist',
          name: 'regist',
          component: UserRegister,
        }
      ]
    } ]
});

export default router
