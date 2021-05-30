<template>
  <div id="page">
    <Header />
    <EditData />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import EditData from "@/components/Clinic/Paciente/EditData.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    EditData,
    Footer,
  },
 
};
</script>