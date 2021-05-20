<template>
  <div id="page" class="d-flex flex-column">
    <Header />
    <Page />
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Footer from "@/components/Footer.vue";
import Page from "@/components/Client/Page.vue"

export default {
  data() {
    return {
    }
  },
  components: {
    Header,
    Footer,
    Page
  },
};
</script>

