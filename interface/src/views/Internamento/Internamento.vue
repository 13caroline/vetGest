<template>
  <div class="extra-padding" id="page">
    <Header v-if="this.$store.state.tipo == 'Veterinario'"/>
    <HeaderClinica v-else/>
    <Internamento v-if="this.$store.state.tipo == 'Veterinario'"/>
    <InternaClinica v-else/>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import HeaderClinica from "@/components/Headers/ClinicHeader.vue";
import Internamento from "@/components/Internamento/Internamento.vue";
import InternaClinica from "@/components/Internamento/InternaClinica.vue";
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    HeaderClinica,
    Internamento,
    InternaClinica,
    Footer,
  },
 
};
</script>