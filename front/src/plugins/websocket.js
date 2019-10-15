import Vue from 'vue';
import store from '../store';
import VueWebsocket from 'vue-native-websocket';

export function configureWebSocket() {
  Vue.use(VueWebsocket, 'ws://' + window.API_URL, {
    store: store,
    format: 'json',
    reconnection: true,
    reconnectionAttempts: 20,
    reconnectionDelay: 3000
  });
}
