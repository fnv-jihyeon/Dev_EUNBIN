import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import IntroView from "@/views/IntroView.vue";
import ServiceView from "@/views/ServiceView.vue";
import CustomerSupportView from "@/views/CustomerSupportView.vue";
import AdminView from "@/views/AdminView.vue";
import ApplicationView from "@/views/ApplicationView.vue";
import LoginView from "@/views/LoginView.vue";
import InView from "@/views/InView.vue";
import MembershipView from "@/views/MembershipView.vue";
import MypageView from "@/views/MypageView.vue";



const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  
  {
    path: '/intro',
    name: 'intro',
    component: IntroView,
  },

  {
    path: '/service',
    name: 'service',
    component: ServiceView,
  },

  {
    path: '/application',
    name: 'application',
    component: ApplicationView,
  },

  {
    path: '/customersupport',
    name: 'customersupport',
    component: CustomerSupportView,
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
    path: '/in',
    name: 'in',
    component: InView,
  },

  {
    path: '/membership',
    name: 'membership',
    component: MembershipView,
  },
  {
    path: '/mypage',
    name: 'mypage',
    component: MypageView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
