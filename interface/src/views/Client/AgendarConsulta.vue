<template>
  <div id="page">
    <Header />
    <AgendarConsulta />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import AgendarConsulta from "@/components/Client/AgendarConsulta.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    AgendarConsulta,
    Footer,
  },
 
};
</script>