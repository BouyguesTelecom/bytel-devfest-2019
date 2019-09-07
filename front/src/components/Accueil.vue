<template>
  <v-container>
    <v-layout text-center wrap>{{counter}}</v-layout>
    <v-btn @click.prevent="sendMessage()">Send</v-btn>
    <v-btn @click.self="resetCounter">Reset</v-btn>
    connexion: {{isConnected ? 'OK' : 'KO'}}
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  computed: mapState({
    counter: state => state.game.counter,
    isConnected: state => state.websocket.isConnected
  }),
  methods: {
    ...mapActions(["resetCounter"]),
    sendMessage() {
      console.log("ici");
      this.$store.dispatch("sendMessage", { action: "increment" });
    }
  }
};
</script>
