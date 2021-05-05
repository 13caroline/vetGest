<template>
  <div id="page">
    <Header />
    <EditData />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import EditData from "@/components/Client/EditData.vue"
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