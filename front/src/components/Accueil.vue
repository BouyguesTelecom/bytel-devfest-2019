<template>
  <div class="d-flex fill-height app-content">
    <div class="isConnected" :class="{ok: isConnected}">•</div>

    <!-- <div class="mean-gauge align-self-center justify-lg-space-between">
      <mean-speed-control
        :speed="meanSpeed"
        :maxSpeed="maxMeanSpeed"
        :maxSpeedSession="maxMeanSpeedSession"
      />
    </div>-->

    <div
      v-show="!isSessionActive"
      class="encouragements align-self-center justify-lg-space-between"
    >
      <div class="a-vos-marques d-flex flex-row justify-start">
        À vos baguettes...
        <v-img src="../assets/baguettes.png" height="100" width="100" contain class="orange-img"></v-img>
      </div>
      <div class="prets d-flex justify-end align-center">Prêts?</div>
      <v-img src="../assets/drummer.png" height="200" width="200" contain class="drummer"></v-img>
      <span class="battez align-self-center justify-end">Battez !</span>
    </div>

    <volume-control
      :speed="speed"
      :maxSpeed="maxSpeedTotal"
      :maxSpeedSession="maxSpeedSession"
      title="Vitesse"
      class="speed-control"
    ></volume-control>
    <volume-control
      :speed="meanSpeed"
      :maxSpeed="maxMeanSpeed"
      :maxSpeedSession="maxMeanSpeedSession"
      :isDecimal="true"
      title="Endurance"
      class="mean-speed-control"
    ></volume-control>

    <flyer :size="request.size" v-for="request in requests" :key="request.id"></flyer>

    <lambda-counter :number="nombreLambdasMax" style="opacity: 0.2" class="lambda-counter"></lambda-counter>
    <lambda-counter :number="nombreLambdasSession" :max="nombreLambdasMax" class="lambda-counter"></lambda-counter>
    <v-btn class="reset-button" text icon @click="resetStats">
      <v-icon>mdi-refresh</v-icon>
    </v-btn>
  </div>
</template>
 
<script>
import { mapState, mapActions } from "vuex";
import VolumeControlVue from "./VolumeControl.vue";
import FlyerVue from "./Flyer.vue";
import LambdaCounterVue from "./LambdaCounter.vue";

export default {
  components: {
    "volume-control": VolumeControlVue,
    flyer: FlyerVue,
    lambdaCounter: LambdaCounterVue
  },
  computed: mapState({
    speed: state => state.game.speed,
    maxSpeedSession: state => state.game.maxSpeedSession,
    maxSpeedTotal: state => state.game.maxSpeedTotal,
    meanSpeed: state => state.game.meanSpeed,
    maxMeanSpeed: state => state.game.maxMeanSpeed,
    maxMeanSpeedSession: state => state.game.maxMeanSpeedSession,
    isSessionActive: state => state.game.isSessionActive,
    nombreLambdas: state => state.game.nombreLambdas,
    nombreLambdasMax: state => state.game.nombreLambdasMax,
    nombreLambdasSession: state => state.game.nombreLambdasSession,
    requests: state => state.game.requests,
    isConnected: state => state.websocket.isConnected
  }),
  watch: {
    isConnected: function(value) {
      if (value) this.getStats();
    }
  },
  methods: {
    ...mapActions(["resetStats", "getStats"])
  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";

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

.speed-control {
  position: absolute;
  bottom: 40px;
  right: 85px;
}
.mean-speed-control {
  position: absolute;
  bottom: 40px;
  right: 400px;
}

.encouragements {
  $height: 500px;
  $width: 800px;

  position: absolute;
  display: flex;
  flex-direction: column;
  z-index: 1000;

  width: $width;
  height: $height;
  top: 35%;
  margin-top: calc(-#{$height} / 2);
  left: 35%;
  margin-left: calc(-#{$width} / 2);

  color: rgb(189, 28, 28);
  // font-weight: 500;
  font-size: 60px;
  background-color: rgba(0, 0, 0, 0.2);
  padding: 20px 40px;
  border-radius: 20px;
  border: lighten($greyBackground, 20%) 1px solid;

  font-weight: normal;
  font-style: normal;

  .orange-img {
    filter: contrast(100%) //
      invert(30%) //
      sepia(90%) //
      saturate(10000%) //
      hue-rotate(20deg) //
      drop-shadow(2px 4px 6px black);
  }
  .drummer {
    position: absolute;
    top: calc(50% - 100px);
    left: calc(50% - 100px);

    filter: contrast(100%) //
      sepia(50%) //
      saturate(10000%) //
      hue-rotate(20deg) //
      drop-shadow(2px 4px 6px black);
  }
  .battez {
    font-size: 70px;
  }
}
.lambda-counter {
  position: absolute;
  width: 800px;
  bottom: 40px;
  left: 350px;
}
.reset-button {
  position: absolute;
  top: 0;
  right: 0;
  margin-top: -50px;
  z-index: 100;
}
</style>