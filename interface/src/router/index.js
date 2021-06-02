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
      path: '/clinica/utente/',
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
      path: '/clinica/editar/utente',
      name: 'Clinica Editar Dados Utente',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/EditData.vue')
    },
    // Relatórios da Clínica
    {
      path: '/clinica/relatorios',
      name: 'Relatórios Clínica',
      component: () => import(/* webpackChunkName: "about" */ '../views/Clinic/Relatorios.vue')
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
      path: '/medico/animal/',
      name: 'Animal Médico',
      component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Animal.vue')
    },

    //Vacinas Animal
    {
      path: '/medico/vacinas/',
      name: 'Vacinas e Desparasitações Médico',
      component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/Vacinas.vue')
    },

       // Adicionar Desparasitação
       /*{
        path:  '/medico/utentes/registar',
        name: 'Registar Utentes Médico',
        component: () => import('../views/Doctor/addDesparasitacao.vue')
      },*/

      // Médico edita animal
      {
        path: '/medico/animal/editar',
        name: 'Editar Animal',
        component: () => import(/* webpackChunkName: "about" */ '../views/Doctor/EditData.vue')
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