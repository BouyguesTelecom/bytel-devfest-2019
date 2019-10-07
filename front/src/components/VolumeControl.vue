<template>
  <v-container class="d-flex justify-end">
    {{speed}}/{{maxSpeed}}
    <div class="volume-control">
      <div class="degrade">
        <div class="blanc" :style="'height:calc('+percent+ '*500px)'"></div>
        <div class="currentBest" :style="'height:calc('+currentBest.value+ '*500px)'"></div>
      </div>
    </div>
  </v-container>
</template>

<script>
import * as moment from "moment";

export default {
  props: ["speed", "maxSpeed"],
  data: () => ({
    currentBest: {
      moment: moment(),
      value: 0.98
    }
  }),
  computed: {
    percent: function() {
      return (this.maxSpeed - this.speed) / this.maxSpeed - 0.02;
    }
  },
  watch: {
    percent: function(percent) {
      if (percent < this.currentBest.value) {
        this.currentBest = {
          moment: moment(),
          value: percent
        };
      }
      if (this.currentBest.moment.isBefore(moment().subtract(1, "seconds"))) {
        this.currentBest = {
          moment: moment(),
          value: percent
        };
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.volume-control {
  height: 500px;
  width: 50px;
  padding: 2px;
  background-color: rgb(0, 0, 0);

  .degrade {
    height: 100%;
    background: linear-gradient(red, yellow 10%, green 80%);
    width: 46px;

    > div {
      position: absolute;
      width: 46px;
      transition: height 0.15s;
    }
    .blanc {
      background: #fafafa;
    }
    .currentBest {
      background: transparent;
      border-bottom: 2px solid rgb(177, 133, 39);
    }
  }
}
</style>