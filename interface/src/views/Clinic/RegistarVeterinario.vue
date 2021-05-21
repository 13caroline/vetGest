<template>
  <div id="page">
    <Header />
    <RegistarVeterinario />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import RegistarVeterinario from "@/components/Clinic/RegistarVeterinario.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    RegistarVeterinario,
    Footer,
  },
 
};
</script>