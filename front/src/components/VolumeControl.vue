<template>
  <div class="d-flex justify-end">
    <div class="volume-control">
      <div class="graduation graduation-verticale">
        <div class="degrade">
          <div class="background" :style="speedToHeight(speed)"></div>
          <div class="currentBest" :style="speedToHeight(currentBest.value)"></div>
          <div class="separateur"></div>
        </div>
      </div>
      <div class="graduation graduation-horizontale"></div>
      <div class="graduation graduation-horizontale seconde-graduation"></div>
      <div class="graduation graduation-horizontale graduation-droite"></div>
      <div class="graduation graduation-horizontale graduation-droite seconde-graduation"></div>
      <div class="ordonnee" :class="{max: maxSpeed === speed && maxSpeed > 0}">{{maxSpeed}}</div>
      <div class="ordonnee middle">{{halfMaxSpeed}}</div>
      <div class="ordonnee min">0</div>
    </div>
  </div>
</template>

<script>
import * as moment from "moment";

export default {
  props: ["speed", "maxSpeed"],
  data: () => ({
    currentBest: {
      moment: moment(),
      value: 0
    }
  }),
  computed: {
    halfMaxSpeed: function() {
      return Math.round(this.maxSpeed / 2);
    }
  },
  watch: {
    speed: function(speed) {
      if (
        speed > this.currentBest.value ||
        this.currentBest.moment.isBefore(moment().subtract(1, "seconds"))
      ) {
        this.resetCurrentBest();
      }
    }
  },
  methods: {
    speedToHeight: function(speed) {
      const percentage = this.maxSpeed === 0 ? 1 : 1 - speed / this.maxSpeed;
      return "height:calc(" + percentage + "* 500px - 16px)";
    },
    resetCurrentBest: function() {
      this.currentBest = {
        moment: moment(),
        value: this.speed
      };
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";

$darkenGreyBackground: darken($greyBackground, 10%);
$height: 500px;
$width: 60px;
$padding: 4px;

.volume-control {
  height: $height;
  width: $width;
  background-color: $greyBackground;
  margin-right: 50px;

  .graduation {
    position: absolute;
    border-color: lighten($greyBackground, 10%);
    border-style: solid;
    background-color: transparent;

    &.graduation-verticale {
      padding: 0 $padding;
      height: calc(#{$height} - 2 *#{$padding});
      width: calc(#{$width} + 4px);
      border-width: 0 2px;
    }
    &.graduation-horizontale {
      height: calc((#{$height} - 2 *#{$padding}) / 2 + 1px);
      width: 4px;
      margin-left: -4px;
      border-width: 2px 0;
      border-style: solid;
    }
    &.seconde-graduation {
      margin-top: calc((#{$height} - 2 *#{$padding}) / 2 - 1px);
    }
    &.graduation-droite {
      margin-left: calc(#{$width} + 4px);
    }
  }

  .ordonnee {
    position: absolute;
    color: lighten($greyBackground, 60%);
    margin-left: -30px;
    margin-top: -10px;
    text-align: right;

    &.middle {
      margin-top: calc((#{$height} - 2 *#{$padding}) / 2 - 11px);
    }
    &.min {
      margin-top: calc(#{$height} - 2 *#{$padding} - 12px);
      margin-left: -20px;
    }
    &.max {
      color: red;
      font-weight: bold;
      font-size: 35px;
      margin-top: -25px;
      margin-left: -50px;
      transition: all 0.2s;
    }
  }

  .degrade {
    height: calc(#{$height} - 2 *#{$padding});
    background: linear-gradient(red, yellow 10%, green 80%);
    width: calc(#{$width} - 2 *#{$padding});

    > div {
      position: absolute;
      width: calc(#{$width} - 2 *#{$padding});
      transition: height 0.15s;
    }
    .background {
      background: $darkenGreyBackground;
    }
    .currentBest {
      background: transparent;
      border-bottom: 2px solid rgb(177, 133, 39);
    }
    .separateur {
      height: calc(#{$height} - 2 * #{$padding});
      width: calc((#{$width} - #{$padding}) / 2);
      border-right: 4px solid $darkenGreyBackground;
    }
  }
}
</style>