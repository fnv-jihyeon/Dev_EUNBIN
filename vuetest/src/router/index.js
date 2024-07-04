import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import IntroView from "@/views/IntroView.vue";
import ServiceView from "@/views/ServiceView.vue";
import CustomerSupportView from "@/views/CustomerSupportView.vue";
import InView from "@/views/InView.vue";
import ApplicationView from "@/views/ApplicationView.vue";



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
    path: '/in',
    name: 'in',
    component: InView,
  },

];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
