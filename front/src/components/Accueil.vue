<template>
  <v-container class="d-flex flex-column justify-space-between fill-height">
    <v-container>
      <v-btn @click="sendMessage()">Send</v-btn>
      <v-btn @click="resetCounter()">Reset</v-btn>
      connexion: {{isConnected ? 'OK' : 'KO'}}
    </v-container>
    <v-container>
      <volume-control :speed="speed" :maxSpeed="maxSpeed"></volume-control>
    </v-container>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import VolumeControlVue from "./VolumeControl.vue";

export default {
  components: {
    "volume-control": VolumeControlVue
  },
  computed: mapState({
    speed: state => state.game.speed,
    maxSpeed: state => state.game.maxSpeed,
    isConnected: state => state.websocket.isConnected
  }),
  watch: {
    isConnected: function(value) {
      if (value) this.getSpeed();
    }
  },
  methods: {
    ...mapActions(["resetCounter", "getSpeed"]),
    sendMessage() {
      this.$store.dispatch("sendMessage", { action: "SET_COUNTER" });
    }
  }
};
</script>
