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

<style scoped>
#page {
  background: #fafafa;
  /*background-image: url('../assets/4.jpg'); */
  height: 100%;
  background-position: center;
  background-repeat: repeat;
  background-size: contain;
  position: relative;
}


</style>