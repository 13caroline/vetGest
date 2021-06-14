<template>
  <div class="extra-padding" id="page">
    <Header />
    <Pacientes />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Pacientes from "@/components/Clinic/Pacientes.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Pacientes,
    Footer,
  },
 
};
</script>

<style>
.custom {
  width: auto;
  margin-left: auto;
}

.custom .v-pagination__navigation {
  height: 26px !important;
  width: 26px !important;
}

.custom .v-pagination__navigation .v-icon {
  font-size: 16px !important;
}

.custom .v-pagination__item {
  height: 26px !important;
  min-width: 26px !important;
  font-size: 0.85rem !important;
}
</style>