import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import IntroView from "../views/IntroView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  
  {
    path: "/intro",
    name: "intro",
    component: IntroView,
  },
  /*
  {
    path: "/service",
    name: "service",
    component: service,
  },
  {
    path: "/serviceApplication",
    name: "serviceApplication",
    component: serviceApplication,
  },
  {
    path: "/in",
    name: "in",
    component: in,
  },
  */
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
