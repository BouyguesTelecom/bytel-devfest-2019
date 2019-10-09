<template>
  <v-app id="full-app">
    <v-app-bar app>
      <v-toolbar-title class="headline">
        <span>Batterie de test</span>
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
  background: linear-gradient(160deg, white 15%, #{$greyBackground} 30% 100%);
}
</style>
