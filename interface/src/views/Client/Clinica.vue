<template>
  <div id="page">
    <Header />
    <Clinica />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Clinica from "@/components/Client/Clinica.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Clinica,
    Footer,
  },
 
};
</script>