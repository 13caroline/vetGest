<template>
  <div class="extra-padding" id="page">
    <Header />
    <RegistarCliente />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import RegistarCliente from "@/components/Clinic/RegistarCliente.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    RegistarCliente,
    Footer,
  },
 
};
</script>