<template>
  <div class="extra-padding" id="page">
    <Header />
    <Cirurgias />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import Cirurgias from "@/components/Doctor/Cirurgias.vue"
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