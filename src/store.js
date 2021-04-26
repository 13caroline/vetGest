import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
      nome: "",
      token: "",
      liToken: "", 
      tipo: "",
    },
    plugins: [createPersistedState()],
    mutations: {
      guardaToken(state, token) {
        state.liToken = token;
      },
      guardaTokenUtilizador(state, token) {
        state.token = token;
      },
      guardaNomeUtilizador(state, nome) {
        state.nome = nome;
      },
      guardaTipoUtilizador(state, tipo) {
        state.tipo = tipo;
      },
      limpaStore(state){
        state.token = "";
        state.nome = ""; 
        state.tipo = ""; 
        state.liToken = ""; 
      }
    },
    getters: {
      token: state => state.token,
      isAuthenticated: state => !!state.token,
      authStatus: state => state.status,
    },
    actions: {}
  });
