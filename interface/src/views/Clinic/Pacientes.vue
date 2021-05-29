<template>
  <div id="page" class="d-flex flex-column">
    <Header />
    <v-container>
      <v-row class="w-100">
           <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
             <v-icon class="mr-2">fas fa-paw</v-icon>
            Utentes
          </h3>
           <v-row justify="end">
            <v-col cols="auto">
              <v-btn
                class="mb-2 mt-6 body-2"
                small
                color="#2596be"
                dark
                to="/clinica/utentes/registar"
              >
                Registar Utente
              </v-btn>
            </v-col>
          </v-row>
        
      </v-row>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Procurar"
        outlined
        dense
        class="my-4"
        color="#2596be"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="consultas"
        :search="search"
        class="elevation-1"
        
        hide-default-footer
      >
        <template v-slot:[`item.detalhes`]="{ item }">
          <v-tooltip right>
            <template v-slot:activator="{ on, attrs }">
              <v-icon v-bind="attrs" v-on="on" small @click="more(item)">
                fas fa-plus-circle
              </v-icon>
            </template>
            <span class="caption">Mais detalhes</span>
          </v-tooltip>
        </template>
      </v-data-table>
    </v-container>

    <Footer class="mt-auto" />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
      search: '',
      headers: [
        {
          text: "UTENTE",
          align: "center",
          sortable: true,
          value: "paciente",
        },
        {
          text: "IDADE",
          value: "idade",
          sortable: true,
          align: "start",
        },
        {
          text: "CLIENTE",
          value: "cliente",
          sortable: true,
          align: "start",
        },
        {
          text: "ESPÉCIE",
          value: "especie",
          sortable: true,
          align: "start",
        },
        {
          text: "RAÇA",
          value: "raca",
          sortable: true,
          align: "start",
        },
        {
          text: "MAIS DETALHES",
          value: "detalhes",
          sortable: false,
          align: "start",
        },
      ],

      consultas: [
        {
          paciente: "Runa",
          idade: "10",
          cliente: "Carolina Cunha",
          especie: "Canídeo",
          raca: "Labrador",
        },
        {
          paciente: "Riscas",
          idade: "4",
          cliente: "João Mota",
          especie: "Felídeo",
          raca: "Gato Europeu",
        },
      ],
    };
  },
  components: {
    Header,
    Footer,
  },
  methods: {
    more(item) {
      this.$router.push("/clinica/paciente/");
      console.log(item)
    },
  },
};
</script>

<style>
#no-background-hover::before {
  background-color: transparent !important;
}
</style>