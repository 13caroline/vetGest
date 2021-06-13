<template>
  <v-snackbar
    v-model="show"
    :timeout="timeout"
    :color="color"
    :top="true"
    class="headline"
  >
    {{ text }}
  </v-snackbar>
</template>

<script>
export default {
  name: "Snackbar",
  created() {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "showMessage") {
        this.text = state.snackbar.text;
        this.timeout = state.snackbar.timeout;
        this.color = state.snackbar.color;
        this.show = true;
      }
    });
  },
  data() {
    return {
      show: false,
      color: "",
      text: "",
      timeout: -1,
    };
  },
};
</script>