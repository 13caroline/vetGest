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
                <v-icon class="calendar ml-4">far fa-calendar-check</v-icon>
              </v-btn>
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
                    v-if="item.estado == 'Concluída'"
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
                    v-if="
                      item.estado == 'Agendada' || item.estado == 'Pendente'
                    "
                  >
                     <CancelarComDados :dialogs="cancelar" :dados="item"></CancelarComDados>
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

       
    </v-container>
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
import CancelarComDados from '@/components/Dialogs/CancelarComDados.vue'
export default {
  data: () => ({
    dados: {},
    dialogs: {},
    cancelar: {
      title: "consulta",
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
        text: "Animal",
        align: "start",
        sortable: true,
        value: "animal",
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
    dialog: false,
  }),
  components: {
    CancelarComDados
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else if (estado == "Cancelada") return "#EF9A9A";
      else if (estado == "Pendente") return "#FFE082";
      else return "#9ae5ff";
    },
  },
 /* getConsultas: async function(){
    try{
      var resposta = await axios.get("http://localhost:7777/client/consultas"),{

      }
    }
  }*/
};
</script>

<style scoped>
.calendar {
  font-size: 16px;
}
</style>