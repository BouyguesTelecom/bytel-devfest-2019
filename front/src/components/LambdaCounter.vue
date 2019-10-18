<template>
  <div class="conteneurs d-flex flex-row flex-wrap-reverse align-content-start">
    <div
      class="conteneur"
      v-for="n in numberWithHidden"
      :key="n"
      :class="{hidden: shouldBeHidden(n)}"
    >
      <v-img src="../assets/container.png" :height="size" :width="size" contain class="image"></v-img>
      <div
        class="numero"
        :style="{height: size+'px', width: size+'px', 'margin-top': -size-40+'px'}"
      >{{number}}</div>
    </div>
  </div>
</template>
 
<script>
const nbParLigne = 11;

export default {
  props: ["number"],
  computed: {
    numberWithHidden: function() {
      let n = Math.round(this.number / nbParLigne);
      return this.number + (n * (n + 1)) / 2;
    },
    size: () => 800 / nbParLigne
  },
  methods: {
    shouldBeHidden: n => {
      return n / nbParLigne > n % nbParLigne && n % nbParLigne != 0;
    }
  }
};
</script>

<style lang="scss" scoped>
.hidden {
  visibility: hidden;
}
.conteneur {
  .image {
    filter: invert(1) //
      drop-shadow(1px 2px 6px black) //
      brightness(0.8);
  }
  .numero {
    position: absolute;
    font-size: 30px;
    color: red;
    text-align: center;
    visibility: hidden;
  }
  &:last-child .numero {
    visibility: visible;
  }
}
</style> 