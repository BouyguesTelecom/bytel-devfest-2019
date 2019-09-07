// Ensemble des entrées/sorties d'api correspondants au champ 'action' du message Websocket
// Injectées en tant qu'actions dans le store
export const API_IN = {
  setCounter({ commit }, message) {
    commit('setCounter', message.value);
  }
};

export const API_OUT = {
  resetCounter({ dispatch }) {
    console.trace('reset');
    dispatch('sendMessage', {
      action: 'resetCounter'
    });
  }
};

export default {
  ...API_IN,
  ...API_OUT
};
