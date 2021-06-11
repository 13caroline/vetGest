<template>
  <div id="page">
    <Header />
    <Preferencias />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Preferencias from "@/components/Client/Preferencias.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Preferencias,
    Footer,
  },
 
};
</script>