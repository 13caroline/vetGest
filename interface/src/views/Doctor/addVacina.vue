<template>
  <div id="page">
    <Header />
    <RegistarPaciente />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import RegistarPaciente from "@/components/Doctor/addVacina.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    RegistarPaciente,
    Footer,
  },
 
};
</script>