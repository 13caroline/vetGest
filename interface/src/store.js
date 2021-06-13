import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        email: "",
        token: "",
        tipo: "",
        snackbar: {
            text: "",
            color: "",
            timeout: "",
        },
    },
    plugins: [createPersistedState()],
    mutations: {
        guardaTokenUtilizador(state, token) {
            state.token = token;
        },
        guardaEmailUtilizador(state, email) {
            state.email = email;
        },
        guardaTipoUtilizador(state, tipo) {
            state.tipo = tipo;
        },
        limpaStore(state) {
            state.token = "";
            state.email = "";
            state.tipo = "";
        },
        showMessage (state, payload) {
            state.snackbar.text = payload.text;
            state.snackbar.color = payload.color;
            state.snackbar.timeout = payload.timeout;
        },
    },
    getters: {
        token: state => state.token,
        isAuthenticated: state => !!state.token,
        authStatus: state => state.status,
    },
    actions: {}
});
