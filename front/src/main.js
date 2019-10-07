import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import store from './store';
import { configureWebSocket } from './plugins/websocket';

Vue.config.productionTip = false;

configureWebSocket();

new Vue({
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app');
