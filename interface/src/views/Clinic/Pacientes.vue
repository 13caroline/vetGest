<template>
  <div id="page">
    <Header />
    <Pacientes />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Pacientes from "@/components/Clinic/Pacientes.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Pacientes,
    Footer,
  },
 
};
</script>