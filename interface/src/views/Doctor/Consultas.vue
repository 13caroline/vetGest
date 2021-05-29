<template>
  <div id="page">
    <Header />
    <Consultas />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import Consultas from "@/components/Doctor/Consultas.vue"
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