<template>
  <div class="d-flex flex-column justify-space-between fill-height app-content">
    <div class="logo">
      <v-img :src="require('../assets/logo.png')" height="100" width="100"></v-img>
    </div>
    <div>
      <volume-control :speed="speed" :maxSpeed="maxSpeed"></volume-control>
    </div>
    <flyer :request="request" v-for="request in requests" :key="request"></flyer>
  </div>
</template>
 
<script>
import { mapState, mapActions } from "vuex";
import VolumeControlVue from "./VolumeControl.vue";
import FlyerVue from "./Flyer.vue";

export default {
  components: {
    "volume-control": VolumeControlVue,
    flyer: FlyerVue
  },
  computed: mapState({
    speed: state => state.game.speed,
    maxSpeed: state => state.game.maxSpeed,
    requests: state => state.game.requests,
    isConnected: state => state.websocket.isConnected
  }),
  watch: {
    isConnected: function(value) {
      if (value) this.getStats();
    }
  },
  methods: {
    ...mapActions(["resetCounter", "getStats"])
  }
};
</script>

<style lang="scss" scoped>
.app-content {
  padding: 20px;
}
</style>