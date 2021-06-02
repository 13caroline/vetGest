<template>
  <div id="page">
    <Header />
    <Animal :id="$route.params.id"/>
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