<template>
  <div class="conteneurs d-flex flex-row flex-wrap-reverse align-content-start">
    <div
      class="conteneur"
      v-for="n in numberWithHidden(number)"
      :key="n"
      :class="{visible: !shouldBeHidden(n), 'max-reached': n == numberWithHidden(max)}"
    >
      <v-img src="../assets/empty_container.png" :height="size" :width="size" contain class="image"></v-img>
      <div class="lambda" :style="{height: size+'px', width: size+'px', 'margin-top': -size+'px'}">
        <span
          :class="{lambdaText: n != numberWithHidden(number)}"
        >{{n == numberWithHidden(number) ? number : 'λ'}}</span>
      </div>
    </div>
  </div>
</template>
 
<script>
const nbParLigne = 11;

export default {
  props: ["number", "max"],
  computed: {
    size: () => 800 / nbParLigne
  },
  methods: {
    shouldBeHidden: n => {
      return n / nbParLigne > n % nbParLigne && n % nbParLigne != 0;
    },
    numberWithHidden: nb => {
      let n = Math.floor(nb / nbParLigne);
      if (nb > 38) n++;
      if (nb > 51) n++;
      if (nb > 60) n++;
      if (nb > 63) n++;
      //J'avoue mon échec, j'ai pas trouvé la bonne règle de calcul, je corrige les déviations
      return nb + (n * (n + 1)) / 2;
    }
  }
};
</script>

<style lang="scss" scoped>
.conteneur {
  visibility: hidden;
  &.visible {
    visibility: visible;
  }
  .image {
    filter: invert(1) //
      drop-shadow(1px 2px 6px black) //
      brightness(0.8);
  }
  .lambda {
    color: rgb(219, 219, 219);
    position: absolute;
    font-size: 30px;
    text-align: center;
    padding: 15px;
    span.lambdaText {
      font-family: monospace;
    }
  }
  &.max-reached {
    filter: contrast(100%) invert(30%) sepia(90%) saturate(10000%)
      hue-rotate(20deg);
  }
}
</style> 