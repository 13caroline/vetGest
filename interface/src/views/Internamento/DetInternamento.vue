<template>
  <div class="extra-padding" id="page">
    <Header v-if="this.$store.state.tipo == 'Veterinario'"/>
    <HeaderClinica v-else/>
    <DetInternamento :id="$route.params.id"/>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import HeaderClinica from "@/components/Headers/ClinicHeader.vue";
import DetInternamento from "@/components/Internamento/DetInternamento.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    HeaderClinica,
    DetInternamento,
    Footer,
  },
 
};
</script>