<template>
  <div class="extra-padding" id="page">
    <Header />
    <Vacinas />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import Vacinas from "@/components/Doctor/Vacinas.vue"
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