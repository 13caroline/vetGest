<template>
  <div id="page" class="d-flex flex-column">
    <Header />
    <v-container>
      <v-row class="w-100">

           <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Próximas consultas/cirurgias
          </h3>
        <v-col cols="auto" class="ml-auto">
          <v-btn elevation="2" to="/clinica/registar/paciente">
            Registar Paciente
          </v-btn>
        </v-col>
      </v-row>
      <v-data-table
        :headers="headers"
        :items="consultas"
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
      headers: [
        {
          text: "Paciente",
          align: "center",
          sortable: true,
          value: "paciente",
        },
        {
          text: "Idade",
          value: "idade",
          sortable: true,
          align: "center",
        },
        {
          text: "Cliente",
          value: "cliente",
          sortable: true,
          align: "center",
        },
        {
          text: "Espécie",
          value: "especie",
          sortable: true,
          align: "center",
        },
        {
          text: "Raça",
          value: "raca",
          sortable: true,
          align: "center",
        },
        {
          text: "Mais detalhes",
          value: "detalhes",
          sortable: false,
          align: "center",
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
      console.log(item);
    },
  },
};
</script>

<style>
#no-background-hover::before {
  background-color: transparent !important;
}
</style>