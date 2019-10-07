// Ensemble des entrées/sorties d'api correspondants au champ 'action' du message Websocket
// Injectées en tant qu'actions dans le store
export const API_IN = {
  SET_SPEED({ commit }, message) {
    commit('setSpeed', message.value);
  }
};

export const API_OUT = {
  resetCounter({ dispatch }) {
    send(dispatch, 'RESET_COUNTER');
  },
  getSpeed({ dispatch }) {
    send(dispatch, 'GET_SPEED');
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
