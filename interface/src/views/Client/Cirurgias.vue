<template>
  <div id="page">
    <Header />
    <Cirurgias />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Cirurgias from "@/components/Client/Cirurgias.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Cirurgias,
    Footer,
  },
 
};
</script>