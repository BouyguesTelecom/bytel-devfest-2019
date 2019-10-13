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
  background: linear-gradient(
    155deg,
    white 20%,
    // rgb(0, 157, 204),
      rgb(73, 139, 201),
    #{$greyBackground} 50% 100%
  );
}
</style>
