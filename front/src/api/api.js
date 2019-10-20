// Ensemble des entrées/sorties d'api correspondants au champ 'action' du message Websocket
// Injectées en tant qu'actions dans le store
export const API_IN = {
  SET_STATS({ commit }, message) {
    commit('setStats', message.value);
  },
  REQUEST_START({ commit }, message) {
    commit('addRequest', message.value);
  },
  REQUEST_END({ commit }, message) {
    commit('deleteRequest', message.value);
  }
};

export const API_OUT = {
  resetStats({ dispatch }) {
    send(dispatch, 'RESET_STATS');
  },
  getStats({ dispatch }) {
    send(dispatch, 'GET_STATS');
  }
};

export default {
  ...API_IN,
  ...API_OUT
};

function send(dispatch, action, value) {
  dispatch('sendMessage', {
    action,
    value
  });
}
