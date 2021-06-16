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
    path: '/cliente/inicio',
    name: 'Página Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Page.vue')
  },
  {
    path: '/cliente/animal/:id',
    name: 'Animal Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Animal.vue')
  },
  {
    path: '/cliente/animal/editar',
    name: 'Editar Animal',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/EditData.vue')
  },
  {
    path: '/cliente/preferencias',
    name: 'Preferencias Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Preferencias.vue')
  },
  {
    path: '/cliente/prefencias/editar',
    name: 'Editar preferencias Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/EditaCliente.vue')
  },
  {
    path: '/cliente/consultas/agendar',
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
  {
    path: '/cliente/vacinas',
    name: 'Vacinas e Desparasitações Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Vacinas.vue')
  },
  {
    path: '/cliente/clinica',
    name: 'Clínica',
    component: () => import(/* webpackChunkName: "about" */ '../views/Client/Clinica.vue')
  },

  {
    path: '/cliente/animal/editar/:id',
    name: 'Editar Animal Cliente',
    component: () => import (/* webpackChunkName: "about" */ '../views/Client/EditData.vue')
  },
  //------------------------------------- Clinica -------------------------------------

  //Página inicial da clinica
  {
    path: '/clinica/inicio',
    name: 'Página Clinica',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Page.vue')
  },

  // Lista dos utentes da clinica
  {
    path: '/clinica/utentes',
    name: 'Utentes Clinica',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Pacientes.vue')
  },
  // Ir para a página de um utente
  {
    path: '/clinica/utente/:id',
    name: 'Utente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Paciente.vue')
  },
  // Lista dos clientes da clinica
  {
    path: '/clinica/clientes',
    name: 'Clientes Clinica',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Clientes.vue')
  },
  // Calendário de consultas da clínica
  {
    path: '/clinica/consultas/',
    name: 'Clinica Consultas',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Consultas.vue')
  },
  // Calendário de cirurgias da clínica
  {
    path: '/clinica/cirurgias/',
    name: 'Clinica Cirurgias',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Cirurgias.vue')
  },
  // Equipa médica da clínica
  {
    path: '/clinica/medicos/',
    name: 'Clinica Médicos',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Medicos.vue')
  },
  // Registar um utente por parte da clinica
  {
    path: '/clinica/utentes/registar',
    name: 'Registar Utente Clinica',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/RegistarPaciente.vue')
  },
  // Registar cliente por clínica
  {
    path: '/clinica/clientes/registar',
    name: 'Clinica Registar Cliente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/RegistarCliente.vue')
  },
  // Registar médico veterinário por clínica
  {
    path: '/clinica/medicos/registar',
    name: 'Clinica Registar Médico',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/RegistarVeterinario.vue')
  },
  // Editar dados de utente
  {
    path: '/clinica/editar/utente/:id',
    name: 'Clinica Editar Dados Utente',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/EditData.vue')
  },
  // Relatórios da Clínica
  {
    path: '/clinica/relatorios',
    name: 'Relatórios Clínica',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Relatorios.vue')
  },
  // Pedidos de consulta
  {
    path: '/clinica/consultas/pedidos',
    name: 'Clínica pedidos consultas',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/PedidosConsulta.vue')
  },
  // Internamento Clínica
  {
    path: '/clinica/internamento',
    name: 'Clínica Internamento',
    component: () => import(/* webpackChunkName: "about" */ '../views/Internamento/Internamento.vue')
  },
   // Internamento Clínica Detalhes
   {
    path: '/clinica/internamento/detalhes/:id',
    name: 'Clínica Internamento Detalhes',
    component: () => import(/* webpackChunkName: "about" */ '../views/Internamento/DetInternamento.vue')
  },
   // Preferências Clínica
   {
    path: '/clinica/preferencias',
    name: 'Preferências Clínica',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Preferencias.vue')
  },
  // Preferências Clínica Editar
  {
    path: '/clinica/preferencias/editar',
    name: 'Preferências Clínica Editar',
    component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/EditProfile.vue')
  },
  //------------------------------------- Médico -------------------------------------

  //Página inicial médico
  {
    path: '/medico/inicio',
    name: 'Página Inicial Médico',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Page.vue')
  },

  // Lista dos utentes do médico
  {
    path: '/medico/utentes',
    name: 'Utentes Médico',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Pacientes.vue')
  },

  // Calendário de consultas de um médico
  {
    path: '/medico/consultas/',
    name: 'Médico Consultas',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Consultas.vue')
  },

  // Calendário de cirurgias de um médico
  {
    path: '/medico/cirurgias/',
    name: 'Médico Cirurgias',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Cirurgias.vue')
  },

  // Animal Médico
  {
    path: '/medico/utente/:id',
    name: 'Animal Médico',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Animal.vue')
  },

  // Médico edita animal
  {
    path: '/medico/editar/utente/:id',
    name: 'Médico Editar Animal',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/EditData.vue')
  },
  // Internamento Médico
  {
    path: '/medico/internamento',
    name: 'Médico Internamento',
    component: () => import(/* webpackChunkName: "about" */ '../views/Internamento/Internamento.vue')
  },
  // Detalhes de internamento
  {
    path: '/medico/internamento/detalhes/:id',
    name: 'Médico Internamento Detalhes',
    component: () => import(/* webpackChunkName: "about" */ '../views/Internamento/DetInternamento.vue')
  },
  // Criação de alta
  {
    path: '/medico/internamento/alta/:id',
    name: 'Médico Internamento Alta',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Alta.vue')
  },
  // Preferências Médico
  {
    path: '/medico/preferencias',
    name: 'Preferências Médico ',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Preferencias.vue')
  },
  // Preferências Médico Editar
  {
    path: '/medico/preferencias/editar',
    name: 'Preferências Médico Editar',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/EditProfile.vue')
  },





]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})

export default router