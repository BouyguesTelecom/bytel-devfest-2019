<template>
  <div>
      <VueSvgGauge
        :start-angle="-90"
        :end-angle="90"
        :value="speed"
        :min="0"
        :max="maxSpeed"
        baseColor="#131313"
        :separatorThickness="0"
        :gauge-color="[{ offset: 0, color: 'green'}, {offset: 50, color:'yellow'}, { offset: 100, color: 'red'}]"
        :scale-interval="0"
        class="gauge"
      >
        <div class="gauge-value">
          {{ speed }}
        </div>
      </VueSvgGauge>
  </div>
</template>

<script>
import VueSvgGauge from './VueGauge'

export default {
  components:{
    VueSvgGauge
  },
  props: ["speed", "maxSpeed", "maxSpeedSession"],
  methods: {

  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";

$height: 150px;
$width: 300px;
$fontSize: 30px;

.gauge-value {
  text-align: center;
  color: lighten($greyBackground, 60%);
  margin-top:65px;
  font-size:22px;
}
.gauge {
  width: $width;
  height: $height;
}
</style>