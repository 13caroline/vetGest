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
              <MarcarCirurgia :dados="animal" @clicked="registar"></MarcarCirurgia>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="filteredData"
            class="elevation-1"
            hide-default-footer
            no-data-text="Não existe histórico de cirurgias."
          no-results-text="Não foram encontrados resultados."
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
                    @click="detalhes = true"
                    small
                    v-on="on"
                    v-bind="attrs"
                  >
                    fas fa-info-circle
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
            <exemplo></exemplo>
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
import axios from "axios";
import moment from "moment";
import store from "@/store.js";
import exemplo from "@/components/Client/exemploCirurgia.vue";
import MarcarCirurgia from "@/components/Dialogs/MarcarCirurgia.vue";
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";

export default {
  props: ["animal"],
  data: () => ({
    dialog: false,
    detalhes: false,
    dialogs: {},
    dados: {},
    cancelar: {
      title: "o agendamento da cirurgia",
      text: "cirurgia",
    },
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
        text: "Descrição",
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
    cirurgias: [],
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",
  }),
  components: {
    exemplo,
    MarcarCirurgia,
    CancelarComDados,
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else if (estado == "Cancelada") return "#EF9A9A";
      else if (estado == "Pendente") return "#fccea2";
      else if (estado == "A decorrer") return "#FFECB3";
      return "#9ae5ff";
    },
    close() {
      this.dialog = false;
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
      this.cirurgias = [];
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
        this.cirurgias.push(element);
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
        this.cirurgias.push(element);
      }
     }
    },
    computed: {
      filteredData() {
        return this.cirurgias.filter((item) => item.tipo === "Cirurgia");
      },
    },
};
</script>