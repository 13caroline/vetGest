<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-scroll</v-icon>
                Histórico de Consultas
              </h3>
            </v-col>
            <v-col cols="auto" class="pl-0">
              <MarcarConsulta
                :dados="animal"
                @clicked="registar"
              ></MarcarConsulta>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="filteredData"
            class="elevation-1"
            hide-default-footer
            no-data-text="Não existe histórico de consultas."
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
          >
            <template v-slot:[`item.marcacao`]="{ item }">
              {{ format(item.marcacao) }}
            </template>

            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estadopedido(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:[`item.detalhes`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    v-if="
                      item.estado == 'Concluída' || item.estado == 'Cancelada'
                    "
                    @click="dialog = true"
                    small
                    v-on="on"
                    v-bind="attrs"
                  >
                    fas fa-info-circle
                  </v-icon>
                </template>
                <span class="caption">Ver detalhes</span>
              </v-tooltip>
              <div
                v-if="item.estado == 'Agendada' || item.estado == 'Pendente'"
              >
                <CancelarComDados
                  :dados="item"
                  :dialogs="cancelar"
                  @clicked="registar"
                ></CancelarComDados>
              </div>
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

      <v-dialog v-model="dialog" width="100%" max-width="700">
        <v-card>
          <v-card-title class="headline mb-6">
            Notas Médicas
            <v-spacer></v-spacer>
            <v-btn icon small @click="dialog = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <p>05/04/2021 15:45</p>
            <span class="font-italic">
              Realizada desparasitação com Bravacto ampola transdérmica sem
              complicações imediatas.
            </span>
            <p>Dr.º José Vieira</p>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-card>
  </div>
</template>


<script>
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";
import MarcarConsulta from "@/components/Dialogs/MarcarConsulta.vue";
import axios from "axios";
import moment from "moment";
import store from "@/store.js";
export default {
  props: ["animal"],
  data: () => ({
    dialog: false,

    page: 1,
    pageCount: 0,
    itemsPerPage: 8,
    dialogs: {},
    dados: {},
    cancela: {},
    cancelar: {
      title: "o agendamento da consulta",
      text: "consulta",
    },
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",
    headers: [
      {
        text: "Data de Agendamento",
        align: "start",
        sortable: true,
        value: "marcacao",
      },
      {
        text: "Médico Veterinário",
        value: "veterinario_nome",
        sortable: true,
        align: "start",
      },
      {
        text: "Motivo",
        value: "descricao",
        sortable: true,
        align: "start",
      },
      {
        text: "Estado",
        value: "estado",
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
    consultas: [],
  }),
  components: {
    CancelarComDados,
    MarcarConsulta,
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else if (estado == "Cancelada") return "#EF9A9A";
      else if (estado == "Pendente") return "#fccea2";
      else if (estado == "A decorrer") return "#FFECB3";
      return "#9ae5ff";
    },
    registar(value) {
      this.$snackbar.showMessage({
        show: true,
        color: value.color,
        text: value.text,
        timeout: 4000,
      });
      this.atualiza();
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    atualiza: async function () {
      this.consultas = [];
      let route =
        store.state.tipo == "Clinica"
          ? "http://localhost:7777/clinica/intervencao"
          : "http://localhost:7777/medico/intervencao";
      let response = await axios.post(
        route,
        {
          id: this.animal.id,
        },
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );
      for (var i = 0; i < response.data.length; i++) {
        var element = response.data[i];
        element.veterinario_nome = response.data[i].veterinario.nome;
        element.utente = response.data[i].animal.nome;
        element.marcacao = response.data[i].data + " " + response.data[i].hora;
        this.consultas.push(element);
      }
    },
  },
  created: async function () {
    let route =
      store.state.tipo == "Clinica"
        ? "http://localhost:7777/clinica/intervencao"
        : "http://localhost:7777/medico/intervencao";
    let response = await axios.post(
      route,
      {
        id: this.animal.id,
      },
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      }
    );
    for (var i = 0; i < response.data.length; i++) {
      var element = response.data[i];
      element.veterinario_nome = response.data[i].veterinario.nome;
      element.utente = response.data[i].animal.nome;
      element.marcacao = response.data[i].data + " " + response.data[i].hora;
      this.consultas.push(element);
    }
  },
  computed: {
    filteredData() {
      return this.consultas.filter((item) => item.tipo === "Consulta");
    },
  },
};
</script>

<style scoped>
.calendar {
  font-size: 16px;
}
</style>

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
</style>