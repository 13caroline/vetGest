<template>
  <div class="extra-padding" id="page">
    <Header />
    <EditData :id="$route.params.id"/>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import EditData from "@/components/Utente/EditData.vue"
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