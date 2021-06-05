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
              <MarcarCirurgia></MarcarCirurgia>
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
                    <CancelarComDados :dados="item"></CancelarComDados>
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
  </div>
</template>

<script>
// import axios from 'axios'
import exemplo from "@/components/Client/exemploCirurgia.vue";
import MarcarCirurgia from "@/components/Dialogs/MarcarCirurgia.vue";
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue";

export default {
  data: () => ({
    dialog: false,
    detalhes: false,
    dialogs: {},
    dados: {},
    cancelar: {
      title: "agendamento da cirurgia",
      text: "o agendamento da cirurgia",
    },
    headers: [
      {
        text: "Data de Marcação",
        align: "start",
        sortable: true,
        value: "data",
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
      },
    ],
  }),
  components: {
    exemplo,
    MarcarCirurgia,
    CancelarComDados,
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else return "#FFE082";
    },
    more(item) {
      console.log(item.data);
    },
    close() {
      this.dialog = false;
    },
  },
};
</script>