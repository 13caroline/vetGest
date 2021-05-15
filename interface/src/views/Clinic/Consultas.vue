<template>
  <div id="page">
    <Header />
    <Consultas />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Consultas from "@/components/Clinic/Consultas.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Consultas,
    Footer,
  },
 
};
</script>