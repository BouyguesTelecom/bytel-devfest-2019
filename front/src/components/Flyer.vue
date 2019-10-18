<template>
  <div class="flyer" :style="{transform: `translate3d(${x}px, ${y}px, 0)`}">
    <!-- <v-icon>mdi-tilde</v-icon> -->
    <v-img :src="img" :height="pixelSize" :width="pixelSize" contain></v-img>
  </div>
</template>

<script>
const images = [
  // require("../assets/firework.gif"),
  require("../assets/logo.png")
];

export default {
  props: ["size"],
  data: () => ({
    x: -100 + Math.random() * 400,
    y: 600 + Math.random() * 200,
    img: images[Math.floor(Math.random() * images.length)]
  }),
  mounted: function() {
    setInterval(this.move, 1000);
  },
  computed: {
    pixelSize: function() {
      return 10 + (90 * this.size) / 127;
    }
  },
  methods: {
    move: function() {
      this.x += Math.random() * 300;
      this.y -= Math.random() * 300;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";

.flyer {
  position: absolute;
  top: 0;
  transition: all 1s ease-in;
}
</style> 