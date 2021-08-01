import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home'
import Dashboard from '../views/Dashboard'
import MyExams from '../views/MyExams'
import MyExercise from '../views/MyExercises'
import CreateExercise from '../views/CreateTrain'
import CreateExam from '../views/CreateExam'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
  },
  {
    path: '/exercises',
    name: 'Exercises',
    component: MyExercise,
  },
  {
    path: '/exercises/create',
    name: 'CreateExercise',
    component: CreateExercise,
  },
  {
    path: '/exams',
    name: 'Exams',
    component: MyExams,
  },
  {
    path: '/exams/create',
    name: 'CreateExam',
    component: CreateExam,
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
