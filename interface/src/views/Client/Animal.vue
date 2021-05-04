<template>
  <div id="page">
    <Header />
    <Animal />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Animal from "@/components/Client/Animal.vue"
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Animal,
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