export default {
  state: {
    speed: 0,
    maxSpeed: 0
  },
  mutations: {
    setSpeed(state, value) {
      state.speed = value;
      if (value > state.maxSpeed) state.maxSpeed = value;
    }
  },
  actions: {}
};
