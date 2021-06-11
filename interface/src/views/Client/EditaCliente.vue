<template>
  <div id="page">
    <Header />
    <EditarCliente />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import EditarCliente from "@/components/Client/editarCliente.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    EditarCliente,
    Footer,
  },
 
};
</script>