<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-scroll</v-icon>
                Histórico de Cirurgias
              </h3>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="cirurgias"
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

            <template v-slot:[`item.data`]="{ item }">
              <span class="ml-1">{{ format(item.data) }}</span>
            </template>

            <template v-slot:[`item.detalhes`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    class="mx-1"
                    v-if="item.estado == 'Concluída'"
                    @click="detalhes = true"
                    small
                    color="#52b9dd"
                    v-on="on"
                    v-bind="attrs"
                  >
                    mdi-plus-circle
                  </v-icon>
                  <v-icon
                    class="mx-1"
                    v-if="
                      item.estado == 'Cancelada' || item.estado == 'A decorrer'
                    "
                    @click="detalhes = true"
                    small
                    v-on="on"
                    v-bind="attrs"
                    disabled
                  >
                    mdi-plus-circle
                  </v-icon>
                </template>
                <span class="caption">Ver detalhes</span>
              </v-tooltip>
              <div v-if="item.estado == 'Agendada'">
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
      <v-dialog v-model="detalhes" width="100%" max-width="700">
        <v-card>
          <v-card-title class="headline mb-6">
            Notas Médicas
            <v-spacer></v-spacer>
            <v-btn icon small @click="dialog = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <exemplo></exemplo>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
import exemplo from "@/components/Client/exemploCirurgia.vue";
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";
import axios from "axios";
import store from "@/store.js";
import moment from "moment";

export default {
  data: () => ({
    dados: {},
    dialogs: {},
    cancelar: {
      title: "o agendamento da cirurgia",
      text: "cirurgia",
    },
    page: 1,
    pageCount: 0,
    itemsPerPage: 8,
    detalhes: false,
    headers: [
      {
        text: "DATA DE AGENDAMENTO",
        align: "start",
        sortable: true,
        value: "marcacao",
      },
      {
        text: "UTENTE",
        value: "utente",
        sortable: true,
        align: "start",
      },
      {
        text: "MÉDICO VETERINÁRIO",
        value: "veterinario_nome",
        sortable: true,
        align: "start",
      },
      {
        text: "DESCRIÇÃO",
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
    cirurgias: [],
  }),
  components: {
    exemplo,
    CancelarComDados,
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else if (estado == "Cancelada") return "#EF9A9A";
      else return "#FFECB3";
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
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
    atualiza: async function () {
      try {
        var response = await axios.post(
          "http://localhost:7777/cliente/cirurgias",
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
        this.cirurgias.push({
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
  },
  created: async function () {
    try {
      var response = await axios.post(
        "http://localhost:7777/cliente/cirurgias",
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
      this.cirurgias.push({
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