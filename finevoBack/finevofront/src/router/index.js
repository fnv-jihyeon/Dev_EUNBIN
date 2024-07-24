import { createRouter, createWebHistory } from 'vue-router'
import AboutView from "@/views/AboutView.vue";
import LoginView from "@/views/LoginView.vue";
import MembershipView from "@/views/MembershipView.vue";
import AdminView from "@/views/AdminView.vue";
import MyPageView from "@/views/MyPageView.vue";
import NaverLoginView from "@/views/NaverLoginView.vue";


const routes = [
  {
    path: '/',
    name: 'home',
    component: AboutView
  },

  {
    path: '/admin',
    name: 'admin',
    component: AdminView,
  },

  {
    path: '/login',
    name: 'login',
    component: LoginView,
  },

  {
    path: '/membership',
    name: 'membership',
    component: MembershipView,
  },

  {
    path: '/mypage',
    name: 'mypage',
    component: MyPageView,
  },

  {
    path: '/naverlogin',
    name: 'naverlogin',
    component: NaverLoginView,
  },
  {
    path: '/login/oauth2/code/naver',
    redirect: '/',
    component: AboutView
  },

  {
    path: '/naverlogout',
    name: 'naverlogout',
    component: AboutView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
