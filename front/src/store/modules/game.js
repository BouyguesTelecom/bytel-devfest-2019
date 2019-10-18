import uuidv1 from 'uuid';

export default {
  state: {
    speed: 0,
    maxSpeedTotal: 0,
    maxSpeedSession: 0,
    isSessionActive: false,
    nombreLambdas: 0,
    nombreLambdasMax: 0,
    nombreLambdasSession: 0,
    requests: []
  },
  mutations: {
    setStats(state, value) {
      state.speed = value.speed;
      state.maxSpeedTotal = value.maxSpeed;
      state.maxSpeedSession = value.session.maxSpeed;
      state.isSessionActive = value.session.active;
      state.nombreLambdasMax = value.maxLambdasUtilisees;
      state.nombreLambdas = value.lambdasUtilisees;
      state.nombreLambdasSession = value.session.lambdasUtilisees;
    },
    addRequest(state, value) {
      state.requests.push({
        id: uuidv1(),
        size: value
      });
    },
    deleteRequest(state) {
      state.requests.splice(0, 1);
    }
  },
  actions: {}
};
