<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-scroll</v-icon>
                Histórico de Consultas
              </h3>
            </v-col>
            <v-col cols="auto">
              <v-btn
                class="body-2"
                small
                color="#2596be"
                dark
                to="/cliente/consultas/agendar"
              >
                Agendar Consulta
                <v-icon small class="calendar ml-2"
                  >far fa-calendar-check</v-icon
                >
              </v-btn>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="consultas"
            class="elevation-1"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
          >
            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estadopedido(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:[`item.detalhes`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    v-if="item.estado == 'Concluída'"
                    @click="dialog = true"
                    small
                    color="#52b9dd"
                    v-on="on"
                    v-bind="attrs"
                  >
                    mdi-plus-circle
                  </v-icon>
                  <v-icon
                    v-if="
                      item.estado == 'Cancelada' || item.estado == 'A decorrer'
                    "
                    small
                    disabled
                    v-on="on"
                    v-bind="attrs"
                  >
                    mdi-plus-circle
                  </v-icon>
                </template>
                <span class="caption">Ver detalhes</span>
              </v-tooltip>
              <div
                v-if="item.estado == 'Agendada' || item.estado == 'Pendente'"
              >
                <CancelarComDados
                  :dialogs="cancelar"
                  :dados="item"
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
    </v-container>
  </div>
</template>

<script>
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";
import axios from "axios";
import store from "@/store.js";
export default {
  data: () => ({
    dados: {},
    dialogs: {},
    cancelar: {
      text: "consulta",
      title: "o agendamento da consulta",
    },
    done: false,
    page: 1,
    pageCount: 0,
    itemsPerPage: 8,
    headers: [
      {
        text: "Data de Agendamento",
        align: "start",
        sortable: true,
        value: "marcacao",
      },
      {
        text: "Animal",
        align: "start",
        sortable: true,
        value: "utente",
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
    dialog: false,
  }),
  components: {
    CancelarComDados,
  },
  methods: {
    atualiza: async function () {
      this.consultas = [];
      try {
        var response = await axios.post(
          "http://localhost:7777/cliente/consultas",
          {
            cliente: this.$store.state.email,
          },
          {
            headers: {
              Authorization: "Bearer " + store.getters.token.toString(),
            },
          }
        );
      } catch (e) {
        console.log("erro: +" + e);
      }
      for (var i = 0; i < response.data.length; i++) {
        this.consultas.push({
          idConsulta: response.data[i].id,
          animal: response.data[i].animal,
          marcacao: response.data[i].data + " " + response.data[i].hora,
          utente: response.data[i].animal.nome,
          veterinario_nome: response.data[i].veterinario.nome,
          descricao: response.data[i].descricao,
          estado: response.data[i].estado,
        });
      }
    },
    registar(value) {
      this.$snackbar.showMessage({
        show: true,
        color: value.color,
        text: value.text,
        timeout: value.timeout,
      });
      this.atualiza();
    },
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else if (estado == "Cancelada") return "#EF9A9A";
      else if (estado == "Pendente") return "#FFE082";
      else return "#9ae5ff";
    },
  },
  created: async function () {
    try {
      var response = await axios.post(
        "http://localhost:7777/cliente/consultas",
        {
          cliente: this.$store.state.email,
        },
        {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        }
      );
    } catch (e) {
      console.log("erro: +" + e);
    }
    for (var i = 0; i < response.data.length; i++) {
      this.consultas.push({
        idConsulta: response.data[i].id,
        animal: response.data[i].animal,
        marcacao: response.data[i].data + " " + response.data[i].hora,
        utente: response.data[i].animal.nome,
        veterinario_nome: response.data[i].veterinario.nome,
        descricao: response.data[i].descricao,
        estado: response.data[i].estado,
      });
    }
  },
};
</script>

<style>
.calendar {
  font-size: 16px;
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