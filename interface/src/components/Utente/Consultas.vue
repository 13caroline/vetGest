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
              <MarcarConsulta :dados="animal"></MarcarConsulta>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="consultas"
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
                    v-if="item.estado == 'Concluída' || item.estado == 'Cancelada'"
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
              <div v-if="
                      item.estado == 'Agendada' || item.estado == 'Pendente'
                    ">
                <CancelarComDados :dados="item" @clicked="close()"></CancelarComDados>
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
  </div>
</template>


<script>
import CancelarComDados from "@/components/Dialogs/CancelarComDados.vue"
import MarcarConsulta from "@/components/Dialogs/MarcarConsulta.vue"

export default {
  data: () => ({
    animal: {},
    dialog: false, 
    dados: {},
    cancelar: {
      title: "agendamento da consulta",
      text: "o agendamento da consulta",
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
        value: "data",
      },
      {
        text: "Médico Veterinário",
        value: "medico",
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
    consultas: [
      {
        data: "05/04/2021 10:15",
        animal: "Rubi",
        medico: "Drº José Vieira",
        descricao: "Desparasitação",
        estado: "Concluída",
      },
      {
        data: "19/04/2021 15:30",
        animal: "Puscas",
        medico: "Drº José Vieira",
        descricao: "Vacinação",
        estado: "Agendada",
      },
      {
        data: "26/04/2021 14:30",
        animal: "Luffy",
        medico: "Drº José Vieira",
        descricao: "Consulta de Rotina",
        estado: "Pendente",
      },
      {
        data: "26/04/2021 15:00",
        animal: "Rubi",
        medico: "Drº José Vieira",
        descricao: "Consulta de Rotina",
        estado: "Cancelada",
      },
    ],
  }),
  components: {
    CancelarComDados, 
    MarcarConsulta
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else if (estado == "Cancelada") return "#EF9A9A";
      else if (estado == "Pendente") return "#FFE082";
      else return "#9ae5ff";
    },
    more(item) {
      console.log(item.data);
    },
     close() {
      this.dialogNova = false;
    },
  },
};
</script>

<style scoped>
.calendar {
  font-size: 16px;
}
</style>