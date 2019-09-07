import Vue from 'vue';
import api from '../../api/api';

export default {
  state: {
    isConnected: false,
    reconnectError: false
  },
  mutations: {
    SOCKET_ONOPEN(state, event) {
      Vue.prototype.$socket = event.currentTarget;
      state.isConnected = true;
    },
    SOCKET_ONCLOSE(state) {
      state.isConnected = false;
    },
    SOCKET_ONERROR(state, event) {
      console.error(state, event);
    },
    // default handler called for all methods
    SOCKET_ONMESSAGE(state, message) {
      console.error('message sans action', message);
    },
    // mutations for reconnect methods
    SOCKET_RECONNECT(state, count) {
      console.info(state, count);
    },
    SOCKET_RECONNECT_ERROR(state) {
      state.reconnectError = true;
    }
  },
  actions: {
    sendMessage: function(context, message) {
      console.log('sending');
      Vue.prototype.$socket.send(JSON.stringify(message));
    },
    ...api
  }
};
