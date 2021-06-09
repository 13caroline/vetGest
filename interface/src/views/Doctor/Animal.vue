<template>
  <div class="extra-padding" id="page">
    <Header />
    <Paciente :id="$route.params.id"/>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import Paciente from "@/components/Doctor/Paciente.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Paciente,
    Footer,
  },
 
};
</script>