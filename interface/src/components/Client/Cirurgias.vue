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
                    v-if="item.estado == 'Concluída'"
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
                  :dialogs="cancelar"
                  :dados="item"
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
   <!--<v-snackbar
      v-model="snackbar"
      :timeout="timeout"
      :color="color"
      :top="true"
      class="headline"
    >
      {{ text }}
    </v-snackbar> -->
  </div>
</template>

<script>
// import axios from 'axios'
import exemplo from "@/components/Client/exemploCirurgia.vue";
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";
import axios from "axios";
import store from "@/store.js";
import moment from "moment"

export default {
  data: () => ({
        timeout: -1,
    dados: {},
    dialogs: {},
    cancelar: {
      title: "cirurgia",
    },
    detalhes: false,
    headers: [
      {
        text: "Data de Agendamento",
        align: "start",
        sortable: true,
        value: "data",
      },
      {
        text: "Utente",
        value: "paciente",
        sortable: true,
        align: "start",
      },
      {
        text: "Médico Veterinário",
        value: "medico",
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
    cirurgias: [
      /*
      {
        data: "05/04/2021 10:15",
        medico: "Drº José Vieira",
        descricao: "Cirurgia",
        estado: "Concluída",
      },
      {
        data: "19/04/2021 15:30",
        medico: "Drº José Vieira",
        descricao: "Cirurgia",
        estado: "Agendada",
      },
      {
        data: "26/04/2021 14:30",
        medico: "Drº José Vieira",
        descricao: "Cirurgia",
        estado: "Agendada",
      },
      {
        data: "26/04/2021 15:00",
        medico: "Drº José Vieira",
        descricao: "Cirurgia",
        estado: "Agendada",
      },*/
    ],
  }),
  components: {
    exemplo,
    CancelarComDados,
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      if (estado == "A decorrer") return "#FFECB3";
      if (estado == "Concluída") return "#9AE5FF";
      else return "#EF9A9A"
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
    //confirmaImunizacao: async
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
    console.log(response)
    for (var i = 0; i < response.data.length; i++) {
      this.cirurgias.push({
        data: response.data[i].data,
        paciente: response.data[i].animal.nome,
        medico: response.data[i].veterinario.nome,
        descricao: response.data[i].descricao,
        estado: response.data[i].estado,
        detalhes: response.data[i].observacoes,
      })
    }    
  },
};
</script>