import Vue from 'vue'
import VueRouter from 'vue-router'
//import store from '../store'

Vue.use(VueRouter)

const routes = [
  //------------------------------------- Cliente -------------------------------------
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
  {
    path: '/cliente/consultas',
    name: 'Consultas Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Consultas.vue')
  },
  {
    path: '/cliente/cirurgias',
    name: 'Cirurgias Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Cirurgias.vue')
  },

    //------------------------------------- Clinica -------------------------------------

      //Página Inicial da Clinica
    {
      path: '/clinica/home',
      name: 'Página Clinica',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Page.vue')
    },

    // Lista dos Pacientes de uma clinica
    {
      path: '/clinica/pacientes',
      name: 'Pacientes Clinica',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Pacientes.vue')
    },

    // Registar um paciente por parte da clinica
    {
      path: '/clinica/registar/paciente',
      name: 'Registar Paciente Clinica',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/RegistarPaciente.vue')
    },

    // Ir para a página de um paciente
    {
      path: '/clinica/paciente/',
      name: 'Paciente',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Paciente.vue')
    },

     // Lista dos Clientes de uma clinica
     {
      path: '/clinica/clientes',
      name: 'Clientes Clinica',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Clientes.vue')
    },



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