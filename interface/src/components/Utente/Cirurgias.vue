<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-scroll</v-icon>
                Histórico de Cirurgias
              </h3>
            </v-col>
            <v-col cols="auto">
              <MarcarCirurgia
                :dados="animal"
                @clicked="registar"
              ></MarcarCirurgia>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="filteredData"
            class="elevation-1"
            hide-default-footer
            no-data-text="Não existe histórico de cirurgias."
            no-results-text="Não foram encontrados resultados."
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
                    @click="notas(item)"
                    small
                    v-on="on"
                    v-bind="attrs"
                  >
                    mdi-plus-circle
                  </v-icon>
                </template>
                <span class="caption">Ver detalhes</span>
              </v-tooltip>
              <div v-if="item.estado == 'Agendada'">
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
      <v-dialog v-model="detalhes" width="100%" max-width="700">
        <v-card>
          <v-card-title class="headline mb-6">
            Notas Médicas
            <v-spacer></v-spacer>
            <v-btn icon small @click="detalhes = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <div v-for="(nota,index) in note" :key="index">
                <p>{{format2(nota.cirurgia.data)}} {{nota.cirurgia.hora}}</p>
                <span v-if="!nota.cirurgia.observacoes" class="font-italic">
                Sem notas médicas.
                </span>
            <span class="font-italic">
              {{nota.cirurgia.observacoes}}
            </span>
            <p>{{nota.veterinario.nome}}</p>
            <v-divider></v-divider>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import store from "@/store.js";
import MarcarCirurgia from "@/components/Dialogs/MarcarCirurgia.vue";
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";

export default {
  props: ["animal"],
  data: () => ({
    dialog: false,
    detalhes: false,
    page: 1,
    pageCount: 0,
    itemsPerPage: 8,
    dialogs: {},
    dados: {},
    cancelar: {
      title: "o agendamento da cirurgia",
      text: "cirurgia",
    },
    headers: [
      {
        text: "DATA DE AGENDAMENTO",
        align: "start",
        sortable: true,
        value: "marcacao",
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
    done: false,
    note: []
  }),
  components: {
    MarcarCirurgia,
    CancelarComDados,
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else if (estado == "Cancelada") return "#EF9A9A";
      else if (estado == "Concluída") return "#9AE5FF";
      else return "#FFECB3";
    },
    close() {
      this.dialog = false;
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
    format2(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
    notas: async function(item){
      let response = await axios.post("http://localhost:7777/clinica/cirurgia/notas",
      {
        id: item.id
      },
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      }
      );
      this.note = Array.isArray(response.data)
        ? response.data : [response.data];

      this.detalhes = true;
    },
    atualiza: async function () {
      this.cirurgias = [];
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
        this.cirurgias.push(element);
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
      this.cirurgias.push(element);
    }
  },
  computed: {
    filteredData() {
      return this.cirurgias.filter((item) => item.tipo === "Cirurgia");
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
</style>