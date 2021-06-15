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
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            class="elevation-1"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
            no-data-text="Não existe histórico de consultas."
            no-results-text="Não foram encontrados resultados."
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
                    small
                    color="#52b9dd"
                    v-on="on"
                    @click="notas(item)"
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
              :total-visible="7"
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
            <v-btn icon small @click="closeDialog()">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <p>{{ format2(nota.data) }} {{ nota.hora }}</p>
            <span v-if="!nota.observacoes" class="font-italic">
              Sem notas médicas.
            </span>
            <span class="font-italic">
              {{ nota.observacoes }}
            </span>
            <p>{{ nota.veterinario_nome }}</p>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";
import axios from "axios";
import moment from "moment";
import store from "@/store.js";
export default {
  data: () => ({
    dados: {},
    dialogs: {},
    sortBy: "marcacao",
    sortDesc: true,
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
        text: "DATA DE AGENDAMENTO",
        align: "start",
        sortable: true,
        value: "marcacao",
      },
      {
        text: "ANIMAL",
        align: "start",
        sortable: true,
        value: "utente",
      },
      {
        text: "MÉDICO VETERINÁRIO",
        value: "veterinario_nome",
        sortable: true,
        align: "start",
      },
      {
        text: "MOTIVO",
        value: "descricao",
        sortable: true,
        align: "start",
      },
      {
        text: "ESTADO",
        value: "estado",
        sortable: true,
        align: "center",
      },
      {
        text: "AÇÕES",
        value: "detalhes",
        sortable: false,
        align: "center",
      },
    ],
    consultas: [],
    dialog: false,
    nota: {},
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
    closeDialog() {
      this.dialog = false;
    },
    notas(item) {
      this.dialog = true;
      this.nota = item;
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
      else if (estado == "Pendente") return "#FCCEA2";
      else if (estado == "Concluída") return "#9AE5FF";
      else return "#FFECB3";
    },
    format2(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
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