<template>
  <div id="page">
    <Header />
    <PedidosConsulta />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import PedidosConsulta from "@/components/Clinic/PedidosConsulta.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    PedidosConsulta,
    Footer,
  },
 
};
</script>