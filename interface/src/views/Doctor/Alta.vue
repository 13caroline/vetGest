<template>
  <div class="extra-padding" id="page">
    <Header />
    <Alta />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/DoctorHeader.vue";
import Alta from "@/components/Doctor/Alta.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Alta,
    Footer,
  },
 
};
</script>