<template>
  <div id="page">
    <Header />
    <Medicos />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Medicos from "@/components/Clinic/Medicos.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Medicos,
    Footer,
  },
 
};
</script>