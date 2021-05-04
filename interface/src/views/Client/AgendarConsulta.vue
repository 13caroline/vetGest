<template>
  <div id="page">
    <Header />
    <AgendarConsulta />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import AgendarConsulta from "@/components/Client/AgendarConsulta.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    AgendarConsulta,
    Footer,
  },
 
};
</script>

<style scoped>
#page {
  background: #fafafa;
  height: 100%;
  background-position: center;
  background-repeat: repeat;
  background-size: contain;
  position: relative;
}


</style>