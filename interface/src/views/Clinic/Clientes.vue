<template>
  <div class="extra-padding" id="page">
    <Header />
    <Clientes />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Clientes from "@/components/Clinic/Clientes.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Clientes,
    Footer,
  },
 
};
</script>