<template>
  <div class="flyer" :style="{transform: `translate3d(${x}px, ${y}px, 0)`}">
    <!-- <v-icon>mdi-tilde</v-icon> -->
    <v-img :src="img" :height="size" :width="size" aspect-ratio="1"></v-img>
  </div>
</template>

<script>
const images = [
  // require("../assets/firework.gif"),
  require("../assets/logo.png")
];

export default {
  props: ["request"],
  data: () => ({
    x: Math.random() * 200,
    y: 700 + Math.random() * 200,
    size: Math.random() * 20 + 20,
    img: images[Math.floor(Math.random() * images.length)]
  }),
  mounted: function() {
    setInterval(this.move, 1000);
  },
  methods: {
    move: function() {
      this.x += Math.random() * 100;
      this.y -= Math.random() * 100;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../style.scss";

.flyer {
  position: absolute;
  top: 0;
  width: 20px;
  height: 20px;
  transition: all 1s ease-in;
}
</style> 