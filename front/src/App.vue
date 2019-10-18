<template>
  <v-app id="full-app">
    <v-app-bar app color="transparent">
      <v-toolbar-title class="headline d-flex flex-row">
        <v-img :src="require('./assets/logo.png')" height="40" width="40" class="logo"></v-img>
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

<style lang="scss">
@import "./style.scss";

html {
  overflow: hidden;
}

.logo {
  margin-right: 20px;
}

.app-background {
  background: linear-gradient(135deg, white 17%, #498bc9, #2c2c2c 38% 100%);
}
.headline {
  span {
    font-family: "Kiona Regular";
    font-size: 40px;
    font-weight: 100;
    text-shadow: 0 -1px 1px #009dcc, -1px 1px 1px #0055a4, 1px 1.5px 1px #ea5b0f;
  }
}
</style>
