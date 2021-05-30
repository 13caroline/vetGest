<template>
  <div id="page" class="d-flex flex-column">
    <Header />
    <Page />
    <Footer v-bind:marginTop="false" />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Footer from "@/components/Footer.vue";
import Page from "@/components/Clinic/Page.vue";

export default {
  data() {
    return {};
  },
  components: {
    Header,
    Footer,
    Page,
  },
};
</script>

