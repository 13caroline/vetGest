<template>
  <div id="page">
    <Header />
    <Internamento />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import Internamento from "@/components/Doctor/Internamento.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Internamento,
    Footer,
  },
 
};
</script>