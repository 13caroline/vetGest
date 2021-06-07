<template>
  <div class="extra-padding" id="page">
    <Header />
    <Animal />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import Animal from "@/components/Doctor/Paciente.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Animal,
    Footer,
  },
 
};
</script>