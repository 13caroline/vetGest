import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import store from './store';
import '@fortawesome/fontawesome-free/css/all.css';
//import * as VueGoogleMaps from 'vue2-google-maps'
//import VueGeolocation from 'vue-browser-geolocation'
//import 'material-design-icons-iconfont/dist/material-design-icons.css'
import snackbarPlugin from './plugins/snackbar';


import('./styles/general.scss');

var moment = require('moment');
Vue.use(snackbarPlugin, { store })
Vue.config.productionTip = false;



new Vue({
  router,
  store,
  vuetify,
  iconfont: 'fa',
  moment: moment,
  render: h => h(App)
}).$mount('#app')
