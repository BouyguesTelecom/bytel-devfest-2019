<template>
  <div class="flyer" :style="{left: x +'px', top: y+'px'}">
    <v-img :src="img" :height="pixelSize" :width="pixelSize" contain></v-img>
  </div>
</template>

<script>
const images = [
  require("../assets/bouche.png"),
  require("../assets/eclair.png"),
  require("../assets/guitare_e.png"),
  require("../assets/guitare.png"),
  require("../assets/hand.png"),
  require("../assets/hand2.png"),
  require("../assets/heart.png"),
  // require("../assets/logo.png"),
  // require("../assets/rock_hand.png"),
  require("../assets/stars.png"),
  require("../assets/zik1.png"),
  require("../assets/zik2.png")
];

export default {
  props: ["size"],
  data: () => ({
    y: Math.random() * 700,
    x: 0,
    img: images[Math.round(Math.random() * images.length)]
  }),
  mounted: function() {
    this.x = (Math.random() * 800) % (1000 - this.y);
  },
  computed: {
    pixelSize: function() {
      return 10 + (150 * this.size) / 127;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";

.flyer {
  position: absolute;
  top: 0;
  transition: all 5s ease-in;
}
</style> 