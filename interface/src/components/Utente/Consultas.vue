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
              <MarcarConsulta :dados="animal" @clicked="registar"></MarcarConsulta>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="filteredData"
            class="elevation-1"
            hide-default-footer
            no-data-text="Não existe histórico de consultas."
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
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";
import MarcarConsulta from "@/components/Dialogs/MarcarConsulta.vue";
import axios from "axios";
import moment from "moment";
import store from "@/store.js";
export default {
  props: ["animal"],
  data: () => ({
    dialog: false,
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
        text: "Data de Marcação",
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
      this.snackbar = value.snackbar;
      this.color = value.color;
      this.text = value.text;
      this.timeout = value.timeout;
      this.atualiza();
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    atualiza: async function () {
      this.consultas = [];
      if (store.state.tipo == "Clinica") {
        let response = await axios.post(
          "http://localhost:7777/clinica/intervencao",
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
          element.marcacao =
            response.data[i].data + " " + response.data[i].hora;
          this.consultas.push(element);
        }
      }
    },
  },
  created: async function () {
    if (store.state.tipo == "Clinica") {
      let response = await axios.post(
        "http://localhost:7777/clinica/intervencao",
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