<template>
  <div class="extra-padding" id="page">
    <Header />
    <DetInternamento />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import DetInternamento from "@/components/Internamento/DetInternamento.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    DetInternamento,
    Footer,
  },
 
};
</script>