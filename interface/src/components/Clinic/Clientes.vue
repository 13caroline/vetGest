<template>
  <div id="page">
    <v-container>
      <v-row class="w-100">
        <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
          <v-icon class="mr-2">fas fa-user</v-icon>
          Clientes
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
                  to="/clinica/clientes/registar"
                >
                  <v-icon small>fas fa-user-plus</v-icon>
                </v-btn>
              </template>
              <span class="caption">Registar cliente</span>
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
        :items="clientes"
        :search="search"
        class="elevation-1"
        hide-default-footer
        no-data-text="Não existem clientes registados."
        no-results-text="Não foram encontrados resultados."
        :page.sync="page"
        :items-per-page="itemsPerPage"
        @page-count="pageCount = $event"
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
      <div class="text-center pt-2">
        <v-pagination
          v-model="page"
          :length="pageCount"
          circle
          :total-visible="4"
          color="#2596be"
          class="custom"
        ></v-pagination>
      </div>

      <v-dialog v-model="dialog" width="100%" max-width="460">
        <v-card>
          <v-card-title class="mb-6 grey--text">
            Identificação pessoal
            

            <v-spacer></v-spacer>
            <v-btn icon small @click="dialog = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <v-row align="center">
              <v-col cols="auto">
                <v-img
                  max-height="100"
                  max-width="100"
                  src="@/assets/medico.png"
                >
                </v-img>
              </v-col>
              <v-col>
                <span
                  class="font-weight-bold headline indication text-uppercase"
                >
                  {{ this.nome }}
                </span>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Telemóvel:</span>
              </v-col>

              <v-col>
                <div class="information font-weight-light">
                  {{ this.contacto }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Email:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.email }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Morada:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.morada }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Concelho:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.concelho }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold"
                  >Número de contribuinte:</span
                >
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.nif }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold"
                  >Utentes associados:</span
                >
              </v-col>

              <v-col align="right" class="pr-0">
                <div v-for="utente in utentes" :key="utente.id">
                  <v-btn
                    class="font-weight-light text-decoration-underline"
                    color="#616161"
                    text
                    depresseds
                    @click="toUtente(utente.id)"
                    >{{ utente.nome }}</v-btn
                  >
                </div>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
    <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
        :color="color"
        :top="true"
        class="headline"
      >
        {{ text }}
      </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store.js";

export default {
  data() {
    return {
      dialog: false,
      page: 1,
      pageCount: 0,
      itemsPerPage: 8,
      search: "",
      headers: [
        {
          text: "NOME COMPLETO",
          align: "start",
          sortable: true,
          value: "nome",
        },
        {
          text: "NÚMERO TELEFONE",
          value: "contacto",
          sortable: true,
          align: "start",
        },
        {
          text: "EMAIL",
          value: "email",
          sortable: true,
          align: "start",
        },
        {
          text: "MORADA",
          value: "morada",
          sortable: false,
          align: "start",
        },
        {
          text: "MAIS DETALHES",
          value: "detalhes",
          sortable: false,
          align: "center",
        },
      ],
      clientes: [],
      nome: "",
      contacto: "",
      email: "",
      morada: "",
      concelho: "",
      nif: "",
      utentes: "",
      snackbar: false, 
      text: "", 
      timeout: -1, 
      color: "", 
    };
  },
  methods: {
    more(item) {
      this.nome = item.nome;
      this.contacto = item.contacto;
      this.email = item.email;
      this.morada = item.morada;
      this.concelho = item.concelho;
      this.nif = item.nif;
      this.utentes = item.animais;
      this.dialog = true;
    },
    toUtente(value) {
      this.$router.push("/clinica/utente/" + value);
    },
  },
  created: async function () {
    try {
      let response = await axios.get("http://localhost:7777/clinica/clientes", {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      this.clientes = response.data;
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

.information {
  text-align: right;
  color: #616161;
}

.indication {
  color: #424242;
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