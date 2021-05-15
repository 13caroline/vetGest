<template>
  <div id="page">
    <Header />
    <Vacinas />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Vacinas from "@/components/Client/Vacinas.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Vacinas,
    Footer,
  },
 
};
</script>