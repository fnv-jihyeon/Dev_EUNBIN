// router/index.js 파일
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import MembershipView from "@/views/MembershipView.vue";
import MypageView from "@/views/MypageView.vue";

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView,
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
        component: MypageView,
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;
