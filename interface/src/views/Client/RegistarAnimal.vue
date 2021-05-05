<template>
  <div id="page">
    <Header />
    <RegistarAnimal />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import RegistarAnimal from "@/components/Client/RegistarAnimal.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    RegistarAnimal,
    Footer,
  },
 
};
</script>