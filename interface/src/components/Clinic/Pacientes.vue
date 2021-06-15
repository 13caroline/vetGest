<template>
  <div class="d-flex flex-column">
    <v-container>
      <v-row class="w-100">
        <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
          <v-icon class="mr-2">fas fa-paw</v-icon>
          Utentes
        </h3>
        <v-row justify="end">
          <v-col cols="auto">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="body-2 mt-8"
                  small
                  color="#2596be"
                  v-bind="attrs"
                  v-on="on"
                  fab
                  dark
                  to="/clinica/utentes/registar"
                >
                  <v-icon small>fas fa-paw</v-icon>
                </v-btn>
              </template>
              <span class="caption">Registar utente</span>
            </v-tooltip>
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
        :items="utentes"
        :search="search"
        class="elevation-1"
        no-data-text="Não existem utentes registados."
        no-results-text="Não foram encontrados resultados."
        hide-default-footer
        :page.sync="page"
        :items-per-page="itemsPerPage"
        @page-count="pageCount = $event"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
      >
        <template v-slot:[`item.detalhes`]="{ item }">
          <v-tooltip right>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                v-bind="attrs"
                v-on="on"
                color="#52b9dd"
                small
                @click="more(item)"
              >
                mdi-plus-circle
              </v-icon>
            </template>
            <span class="caption">Mais detalhes</span>
          </v-tooltip>
        </template>

        <template v-slot:[`item.dataNascimento`]="{ item }">
          <span class="ml-1">{{ format(item.dataNascimento) }}</span>
        </template>
      </v-data-table>
      <div class="text-center pt-2">
        <v-pagination
          v-model="page"
          :length="pageCount"
          circle
          :total-visible="7"
          color="#2596be"
          class="custom"
        ></v-pagination>
      </div>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
import moment from "moment";
export default {
  data() {
    return {
      search: "",
      page: 1,
      pageCount: 0,
      itemsPerPage: 8,
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
          align: "center",
        },
      ],
      sortBy: "nome",
      sortDesc: false,
      utentes: [],
    };
  },
  methods: {
    more(item) {
      this.$router.push("/clinica/utente/" + item.id);
    },
    format(data) {
      return moment(data).locale("pt").toNow(true); // 4 years
    },
  },
  created: async function () {
    try {
      let response = await axios.get("http://localhost:7777/clinica/utentes", {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      for (var i = 0; i < response.data.utentes.length; i++)
        this.utentes.push(response.data.utentes[i].animal);
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

.custom {
  width: auto;
  margin-left: auto;
}

.custom .v-pagination__navigation {
  height: 26px !important;
  width: 26px !important;
}

.custom .v-pagination__navigation .v-icon {
  font-size: 16px !important;
}

.custom .v-pagination__item {
  height: 26px !important;
  min-width: 26px !important;
  font-size: 0.85rem !important;
}
</style>