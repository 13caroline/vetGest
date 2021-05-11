import Vue from 'vue'
import VueRouter from 'vue-router'
//import store from '../store'

Vue.use(VueRouter)

const routes = [
  // Cliente
  {
    path: '/',
    name: 'Home',
    component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
  },
  {
    path: '/cliente/home',
    name: 'Página Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Page.vue')
  },
  {
    path: '/cliente/animal',
    name: 'Animal Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Animal.vue')
  },
  {
    path: '/cliente/animal/editar',
    name: 'Editar Animal',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/EditData.vue')
  },
  {
    path: '/cliente/agendar/consulta',
    name: 'Agendar Consulta',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/AgendarConsulta.vue')
  },
  {
    path: '/cliente/registar/animal',
    name: 'Registar Animal',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/RegistarAnimal.vue')
  },

    //Clinica
    {
      path: '/clinica/home',
      name: 'Página Clinica',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Page.vue')
    },
    {
      path: '/clinica/pacientes',
      name: 'Pacientes Clinica',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Pacientes.vue')

    }


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes, 
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})

export default router