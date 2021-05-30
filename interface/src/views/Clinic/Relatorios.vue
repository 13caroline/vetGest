<template>
  <div id="page">
    <Header />
    <Relatorios />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Relatorios from "@/components/Clinic/Relatorios.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Relatorios,
    Footer,
  },
 
};
</script>