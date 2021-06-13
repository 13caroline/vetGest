<template>
  <v-app>
    <v-main>
      <router-view></router-view>
      <Snackbar />
    </v-main>
  </v-app>
</template>

<script>
import Snackbar from './components/Snackbar.vue';

  export default {
    name : 'App', 
    components: {Snackbar},
    data: () => ({
    //
  }),
  }
</script> 