<template>
  <div id="page" class="d-flex flex-column extra-padding">
    <Header />
    <Preferencias />
    <Footer v-bind:marginTop="false" />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Footer from "@/components/Footer.vue";
import Preferencias from "@/components/Preferencias.vue";

export default {
  data() {
    return {};
  },
  components: {
    Header,
    Footer,
    Preferencias,
  },
};
</script>

