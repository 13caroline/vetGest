<template>
  <div>
    <v-app-bar absolute color="#FFFFFF" height="180" flat>
      <v-row>
        <v-col class="d-flex justify-space-around">
          <v-img
            align-center
            center
            src="../assets/CVetMinho.png"
            max-height="220"
            max-width="220"
          >
          </v-img>
        </v-col>
      </v-row>
    </v-app-bar>

    <v-card height="180" flat></v-card>

    <v-tabs
      v-model="tab"
      background-color="transparent"
      color="#737373"
      centered
    >
      <v-tab class="text-capitalize tab" v-for="item in items" :key="item">
        {{ item }}
      </v-tab>

      <v-tab-item>
        <Sobre></Sobre>
      </v-tab-item>

      <v-tab-item>
        <Registar></Registar>
      </v-tab-item>

      <v-tab-item>
        <Login></Login>
      </v-tab-item>
    </v-tabs>

    <Footer></Footer>
  </div>
</template>

<script>
import Sobre from "@/components/Sobre.vue";
import Login from "@/components/Login.vue";
import Registar from "@/components/Registar.vue"
import Footer from "@/components/Footer.vue"
export default {
  name: "mainpage",
  data() {
    return {
      tab: null,
      items: ["Sobre", "Registar", "Iniciar Sessão"],
    };
  },
  components: {
    Sobre,
    Login, 
    Registar, 
    Footer
  },
};
</script>

<style scoped>
.tab {
  font-family: "Old Standard TT";
  font-size: 20px;
}
</style>