<template>
  <div id="page" class="d-flex flex-column extra-padding">
    <Header />
    <v-container>
      <v-row class="w-100">
        <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
          <v-icon class="mr-2">fas fa-paw</v-icon>
          Utentes
        </h3>
        <v-row justify="end"> </v-row>
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
        :items="utentes"
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
import Header from "@/components/Headers/DoctorHeader.vue";
import Footer from "@/components/Footer.vue";
import axios from "axios";
import store from "@/store.js";

export default {
  data() {
    return {
      search: "",
      headers: [
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "nome",
        },
        {
          text: "IDADE",
          value: "dataNascimento",
          sortable: true,
          align: "start",
        },
        {
          text: "CLIENTE",
          value: "cliente_nome",
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

      utentes: [],
    };
  },
  components: {
    Header,
    Footer,
  },
  methods: {
    more(item) {
      this.$router.push("/medico/utente/" + item.id);
    },
  },
  created: async function () {
    try {
      let response = await axios.post(
        "http://localhost:7777/medico/utentes",
        { email: this.$store.state.email },
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );
      console.log(response);
      this.utentes = response.data.utentes.map((ut) => {
        return ut.animal;
      });
      if (typeof response.data == "object") this.agendamentos = response.data;
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style>
#no-background-hover::before {
  background-color: transparent !important;
}
</style>