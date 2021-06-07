<template>
  <div class="extra-padding" id="page">
    <Header />
    <EditData />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
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