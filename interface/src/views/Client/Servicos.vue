<template>
  <div id="page">
    <Header />
    <Servicos />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Servicos from "@/components/Client/Servicos.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Servicos,
    Footer,
  },
 
};
</script>