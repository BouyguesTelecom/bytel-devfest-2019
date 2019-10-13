export default {
  state: {
    speed: 0,
    maxSpeed: 0,
    requests: []
  },
  mutations: {
    setStats(state, value) {
      state.speed = value.speed;
      state.maxSpeed = value.maxSpeed;
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
