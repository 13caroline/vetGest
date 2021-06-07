<template>
  <div class="extra-padding" id="page">
    <Header />
    <Paciente />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Paciente from "@/components/Clinic/Paciente.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Paciente,
    Footer,
  },
 
};
</script>