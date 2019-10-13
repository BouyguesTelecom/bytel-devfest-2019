<template>
  <div class="d-flex fill-height app-content">
    <div class="isConnected" :class="{ok: isConnected}">•</div>

    <div v-show="showReady" class="encouragements align-self-center justify-lg-space-between">
      <div class="a-vos-marques d-flex flex-row">À vos marques...</div>
      <div class="prets d-flex justify-lg-space-between align-center">
        <v-img src="../assets/drummer.png" height="200" width="200" contain></v-img>Prêts?
      </div>
      <span class="battez align-self-center">Battez !</span>
    </div>

    <volume-control :speed="speed" :maxSpeed="maxSpeed" class="volume-control"></volume-control>

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
  data: () => ({
    showReady: true
  }),
  computed: mapState({
    speed: state => state.game.speed,
    maxSpeed: state => state.game.maxSpeed,
    requests: state => state.game.requests,
    isConnected: state => state.websocket.isConnected
  }),
  watch: {
    isConnected: function(value) {
      if (value) this.getStats();
    },
    speed: function(value) {
      if (this.showReady && value !== 0) {
        this.showReady = false;
      }
    }
  },
  methods: {
    ...mapActions(["resetCounter", "getStats"])
  },
  mounted: function() {
    setInterval(() => {
      if (this.speed == 0) {
        this.showReady = true;
      }
    }, 30000);
  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";
@import "../fonts.scss";

.app-content {
  padding: 20px;
}
.isConnected {
  position: absolute;
  top: -10px;
  right: 10px;
  font-size: 50px;
  color: red;
  &.ok {
    color: green;
  }
}

.volume-control {
  position: absolute;
  bottom: 20px;
  right: 20px;
}

.encouragements {
  $height: 500px;
  $width: 800px;

  position: absolute;
  display: flex;
  flex-direction: column;

  width: $width;
  height: $height;
  top: 50%;
  margin-top: calc(-#{$height} / 2);
  left: 50%;
  margin-left: calc(-#{$width} / 2);

  color: yellowgreen;
  // font-weight: 500;
  font-size: 50px;
  background-color: rgba(0, 0, 0, 0.2);
  padding: 20px 40px;
  border-radius: 20px;
  border: lighten($greyBackground, 20%) 1px solid;

  font-weight: normal;
  font-style: normal;
  font-family: "Road Rage", Arial;

  .a-vos-marques {
  }
  .battez {
    font-size: 70px;
  }
}
</style>