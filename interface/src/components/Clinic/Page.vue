<template>
  <div>
    <v-container class="fill-height">
      <v-row class="w-100 mt-4 mb-0" align="center">
        <v-col cols="auto">
          <h3 class="font-weight-regular text-uppercase">
            <v-icon class="mr-2">fas fa-calendar-alt</v-icon>
            Agendamento
          </h3>
        </v-col>
        <div class="w-100 d-sm-none"></div>
        <v-col cols="auto" class="ml-auto pl-0">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2"
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
        <v-col cols="auto" class="pl-0">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2"
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
        <v-col cols="auto" class="pl-0">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2"
                small
                color="#2596be"
                v-bind="attrs"
                v-on="on"
                fab
                dark
                to="/clinica/consultas/"
              >
                <v-icon small>fas fa-scroll</v-icon>
              </v-btn>
            </template>
            <span class="caption">Agendar consulta</span>
          </v-tooltip>
        </v-col>
        <v-col cols="auto" class="pl-0">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2"
                small
                color="#2596be"
                v-bind="attrs"
                v-on="on"
                fab
                dark
                to="/clinica/cirurgias/"
              >
                <v-icon small>fas fa-band-aid</v-icon>
              </v-btn>
            </template>
            <span class="caption">Agendar cirurgia</span>
          </v-tooltip>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <v-data-table
            :headers="headers"
            :items="filteredData"
            class="elevation-1"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
            no-data-text="Não existem intervenções agendadas."
          no-results-text="Não foram encontrados resultados."
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
              <span class="ml-2 mr-1 blue--text">{{ item.utente }}</span>
              <span>({{ item.animal.especie }})</span>
            </template>

            <template v-slot:[`item.cliente`]="{ item }">
              <span class="ml-1">{{ item.cliente }}</span>
            </template>

            <template v-slot:[`item.marcacao`]="{ item }">
              <span class="ml-1">{{ format(item.marcacao) }}</span>
            </template>

            <template v-slot:[`item.tipo`]="{ item }">
              <v-chip :color="servico(item.tipo)" small>
                {{ item.tipo }}
              </v-chip>
            </template>

            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estado(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>
            <template v-slot:[`item.detalhes`]="{ item }">
              <Detalhes :dados="item"></Detalhes>

              <Admissao :dados="item" @clicked="registar"></Admissao>

              <CancelarConsulta
                v-if="item.tipo == 'Cirurgia'"
                :dialogs="cancelar"
                :dados="item"
                @clicked="registar"
              ></CancelarConsulta>
              <CancelarConsulta
                v-else
                :dialogs="cancelarC"
                :dados="item"
                @clicked="registar"
              ></CancelarConsulta>
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
      <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
        :color="color"
        :top="true"
        class="headline"
      >
        {{ text }}
      </v-snackbar>
    </v-container>
  </div>
</template>


<script>
import axios from "axios";
import store from "@/store.js";
import Detalhes from "@/components/Dialogs/Detalhes.vue";
import Admissao from "@/components/Dialogs/Admissao.vue";
import CancelarConsulta from "@/components/Dialogs/CancelarComDados.vue";
import moment from "moment";
export default {
  data() {
    return {
      dados: {},
      dialogs: {},
      cancelarC: {
        title: "o agendamento da consulta",
        text: "consulta",
      },
      cancelar: {
        title: "o agendamento da cirurgia",
        text: "cirurgia",
      },
      page: 1,
      pageCount: 0,
      itemsPerPage: 8,
      search: "",
      headers: [
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "utente",
        },
        {
          text: "MÉDICO",
          align: "start",
          sortable: true,
          value: "veterinario",
        },
        {
          text: "DATA",
          value: "marcacao",
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
          text: "SERVIÇO",
          value: "tipo",
          sortable: true,
          align: "start",
        },
        {
          text: "ESTADO",
          value: "estado",
          sortable: true,
          align: "start",
        },
        {
          text: "AÇÕES",
          value: "detalhes",
          sortable: false,
          align: "start",
        },
      ],
      agendamento: [],
      snackbar: false,
      color: "",
      text: "",
      timeout: -1,
    };
  },
  components: {
    Detalhes,
    Admissao,
    CancelarConsulta,
  },
  methods: {
    estado(item) {
      if (item == "Agendada") return "#C5E1A5";
      else return "#FFECB3";
    },
    servico(item) {
      if (item == "Consulta") return "#B2DFDB";
      else return "#FFCCBC";
    },
    utente(item) {
      this.$router.push("/clinica/utente/" + item.animal.id);
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    registar(value) {
      this.snackbar = value.snackbar;
      this.color = value.color;
      this.text = value.text;
      this.timeout = value.timeout;
      this.atualiza();
    },
    atualiza: async function () {
      this.agendamento = [];
      let response = await axios.get(
        "http://localhost:7777/clinica/consultas",
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );

      for (var i = 0; i < response.data.intervencoes.length; i++) {
        var element = response.data.intervencoes[i];
        element.utente = response.data.intervencoes[i].animal.nome;
        element.veterinario = response.data.intervencoes[i].veterinario_nome;
        element.especie = response.data.intervencoes[i].animal.especie;
        element.marcacao =
          response.data.intervencoes[i].data +
          " " +
          response.data.intervencoes[i].hora;
        this.agendamento.push(element);
      }
    },
  },
  created: async function () {
    let response = await axios.get("http://localhost:7777/clinica/consultas", {
      headers: { Authorization: "Bearer " + store.getters.token },
    });

    for (var i = 0; i < response.data.intervencoes.length; i++) {
      var element = response.data.intervencoes[i];
      element.utente = response.data.intervencoes[i].animal.nome;
      element.veterinario = response.data.intervencoes[i].veterinario_nome;
      element.especie = response.data.intervencoes[i].animal.especie;
      element.marcacao =
        response.data.intervencoes[i].data +
        " " +
        response.data.intervencoes[i].hora;
      this.agendamento.push(element);
    }
  },
  computed: {
    filteredData() {
      return this.agendamento.filter((item) => item.estado === "Agendada" || item.estado === "A decorrer");
    },
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