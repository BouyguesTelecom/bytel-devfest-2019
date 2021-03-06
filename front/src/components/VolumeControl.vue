<template>
  <div class="d-flex flex-column">
    <div
      class="control-title"
      :class="{max: maxSpeedSession === maxSpeed && maxSpeedSession > 0}"
    >{{title}}</div>
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
      <div
        class="ordonnee"
        :class="{max: maxSpeed === speed && maxSpeed > 0, decimal: isDecimal}"
      >{{maxSpeed}}</div>
      <div class="ordonnee middle">{{halfMaxSpeed}}</div>
      <div class="ordonnee min">0</div>
      <div
        class="ordonnee right maxSpeedSession"
        :class="{max: maxSpeed === maxSpeedSession && maxSpeed > 0}"
        :style="speedToMargin(maxSpeedSession)"
      >{{maxSpeedSession}}</div>
    </div>
  </div>
</template>

<script>
import * as moment from "moment";

export default {
  props: ["speed", "maxSpeed", "maxSpeedSession", "isDecimal", "title"],
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
  mounted: function() {
    setInterval(() => {
      if (
        this.currentBest.moment.isBefore(moment().subtract(1, "seconds")) &&
        this.speed !== this.currentBest.value
      ) {
        this.resetCurrentBest(this.speed);
      }
    }, 200);
  },
  watch: {
    speed: function(newValue) {
      if (newValue > this.currentBest.value) {
        this.resetCurrentBest(newValue);
      }
    }
  },
  methods: {
    speedToHeight: function(speed) {
      const percentage = this.maxSpeed === 0 ? 1 : 1 - speed / this.maxSpeed;
      return "height:calc(" + percentage + " * 684px)";
    },
    speedToMargin: function(speed) {
      const percentage = this.maxSpeed === 0 ? 1 : 1 - speed / this.maxSpeed;
      return "margin-top:calc(689px * " + percentage + " - 11px)";
    },
    resetCurrentBest: function(newValue) {
      this.currentBest = {
        moment: moment(),
        value: newValue
      };
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";

$darkenGreyBackground: darken($greyBackground, 10%);
$height: 700px;
$width: 80px;
$padding: 4px;

.volume-control {
  height: $height;
  width: $width;
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
    margin-left: -40px;
    margin-top: -17px;
    text-align: right;
    font-size: 30px;

    &.decimal {
      margin-left: -60px;
    }

    &.middle {
      margin-top: calc((#{$height} - 2 *#{$padding}) / 2 - 19px);
    }
    &.min {
      margin-top: calc(#{$height} - 2 *#{$padding} - 22px);
      margin-left: -24px;
    }
    &.max {
      color: #e00d0d;
      font-weight: bold;
      font-size: 35px;
      margin-top: -25px !important;
      margin-left: -50px;
      transition: all 0.2s;
      &.decimal {
        margin-left: -70px;
      }
    }
    &.right {
      right: 0;
      margin-left: 0;

      &.maxSpeedSession {
        padding: 2px 5px;
        border-bottom: 2px solid #721717;
        border-radius: 20px;
        width: 30px;
        text-align: center;
        &.max {
          width: 50px;
          margin-right: -20px;
        }
      }
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

.control-title {
  margin-bottom: 50px;
  width: 200px;
  text-align: center;
  margin-left: -58px;
  color: lighten($greyBackground, 60%);
  font-size: 40px;
  &.max {
    color: #e00d0d;
    font-weight: bold;
    transition: all 0.2s;
    font-size: 50px;
  }
}
</style>