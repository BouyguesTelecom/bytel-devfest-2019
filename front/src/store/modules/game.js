export default {
  state: {
    speed: 0,
    maxSpeed: 0,
    requests: []
  },
  mutations: {
    setSpeed(state, value) {
      state.speed = value;
      if (value > state.maxSpeed) state.maxSpeed = value;
    },
    addRequest(state, value) {
      state.requests.push(value);
    },
    deleteRequest(state) {
      state.requests.splice(0, 1);
    }
  },
  actions: {}
};
