<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12" class="my-4">
          <h3 class="font-weight-regular text-uppercase">
            <v-icon class="mr-2">fas fa-procedures</v-icon>
            Internamento
          </h3>
        </v-col>
        <v-col cols="12">
          <v-data-table
            :headers="headers"
            :items="agendamentos"
            class="elevation-1"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
          >
            <template v-slot:[`item.utente`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    color="blue"
                    v-bind="attrs"
                    v-on="on"
                    x-small
                    @click="utente(item)"
                  >
                    fas fa-book
                  </v-icon>
                </template>
                <span class="caption">Dados do utente</span>
              </v-tooltip>
              <span class="ml-1 mr-1 blue--text">{{ item.animal.nome }}</span>
              <span>({{ item.animal.especie }})</span>
            </template>

            <template v-slot:[`item.cliente`]="{ item }">
              <span class="ml-1 blue--text">{{ item.cliente }}</span>
            </template>

            <template v-slot:[`item.data`]="{ item }">
              <span>{{ format(item.data) }}</span>
            </template>

            <template v-slot:[`item.servico`]="{ item }">
              <v-chip :color="servico(item.servico)" small>
                {{ item.servico }}
              </v-chip>
            </template>

            <template v-slot:[`item.detalhes`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon v-bind="attrs" v-on="on" small @click="more(item)">
                    fas fa-arrow-right
                  </v-icon>
                </template>
                <span class="caption">Mais detalhes</span>
              </v-tooltip>
            </template>

            <template v-slot:[`item.alta`]="{ item }">
              <v-btn color="#2596be" depressed dark small @click="alta(item)">
                Criar alta
              </v-btn>
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
        </v-col>
      </v-row>
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
      cancelar: false,
      detalhes: false,
      concluir: false,
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      search: "",
      headers: [
        {
          text: "LOCALIZAÇÃO",
          align: "start",
          sortable: true,
          value: "localizacao",
        },
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "utente",
        },
        {
          text: "DATA DE INTERNAMENTO",
          value: "data",
          sortable: true,
          align: "start",
        },
        {
          text: "MOTIVO",
          value: "motivo",
          sortable: true,
          align: "start",
        },
        {
          text: "VER",
          value: "detalhes",
          sortable: false,
          align: "start",
        },
        {
          text: "DAR ALTA",
          value: "alta",
          sortable: false,
          align: "start",
        },
      ],

      agendamentos: [],
    };
  },
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
    more(item) {
      this.$router.push("/medico/internamento/detalhes/" + item.animal.id);
    },
    alta(item) {
      this.$router.push("/medico/internamento/alta/" + item.animal.id);
    },
    servico(item) {
      if (item == "Consulta") return "#B2DFDB";
      else return "#FFCCBC";
    },
    utente(item) {
      this.$router.push("/medico/utente/" + item.animal.id);
    },
  },
  created: async function () {
    this.$store.state.tipo == "Clinica";
    try {
      let response = await axios.post(
        "http://localhost:7777/medico/internamentos",
        {
          email: this.$store.state.email,
        },
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );

      this.agendamentos = response.data;
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style>
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
.body-2 .v-icon.v-icon {
  font-size: 15px;
}
</style>