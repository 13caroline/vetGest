<template>
  <div id="page" class="d-flex flex-column extra-padding">
    <Header />
    <EditProfile />
    <Footer v-bind:marginTop="false" />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Footer from "@/components/Footer.vue";
import EditProfile from "@/components/EditProfile.vue";

export default {
  data() {
    return {};
  },
  components: {
    Header,
    Footer,
    EditProfile,
  },
};
</script>

