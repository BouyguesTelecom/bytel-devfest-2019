<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title class="headline text-uppercase">
        <span>Bouygues Telecom</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>

    <v-content class="app-background">
      <Accueil />
    </v-content>
  </v-app>
</template>

<script>
import Accueil from "./components/Accueil";

export default {
  name: "App",
  components: {
    Accueil
  }
};
</script>

<style lang="scss" scoped>
@import "./style.scss";

.app-background {
  background: linear-gradient(150deg, white 20%, #{$greyBackground} 40% 100%);
}
</style>
