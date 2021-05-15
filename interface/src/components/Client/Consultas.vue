<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row justify="center">
            <v-col cols="auto">
              <v-btn
                class="mb-2 mt-6 body-2"
                small
                color="#2596be"
                dark
                to="/cliente/agendar/consulta"
              >
                Agendar Consulta
              </v-btn>
            </v-col>
          </v-row>

          <h3 class="font-weight-regular text-uppercase mt-8 mb-4">
            <v-icon small>fas fa-scroll</v-icon>
            Histórico de Consultas
          </h3>

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
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    v-if="
                      item.estado == 'Agendada' || item.estado == 'Pendente'
                    "
                    v-bind="attrs"
                    v-on="on"
                    small
                    @click="cancelar = true"
                  >
                    fas fa-calendar-times
                  </v-icon>
                </template>
                <span class="caption">Cancelar marcação</span>
              </v-tooltip>
            </template>
          </v-data-table>
        </v-col>
      </v-row>

      <v-dialog v-model="dialog" width="50%">
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
            <span class="font-italic"
              >Realizada desparasitação com Bravacto ampola transdérmica sem
              complicações imediatas.
            </span>
            <p>Dr.º José Vieira</p>
          </v-card-text>
        </v-card>
      </v-dialog>

      <v-dialog v-model="cancelar" persistent width="30%">
        <v-card>
          <v-card-title class="justify-center cancel">
            Cancelar consulta
          </v-card-title>
          <v-card-text>

            <span class="text-uppercase">Nome do Animal</span
            ><span class="black--text">
              <strong>Rubi</strong> (Serra da Estrela)
            </span>

            <div>
              <span class="text-uppercase">Motivo da Consulta</span>
              <span class="black--text">
                <strong>Vómito/Diarreia/Recusa a comer</strong></span
              >
              <span> Consulta extraordinária/Por doença </span>
            </div>

            <div>
              <span class="text-uppercase">Data</span>
              <span class="black--text">
                <strong>21/05/2021 15:45</strong></span
              >
            </div>

            <div>
              <span class="text-uppercase">Médico</span>
              <span class="black--text"> <strong>Sem preferência</strong></span>
            </div>

            <p class="mt-4">Tem a certeza que pretende cancelar a consulta? </p>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="cancelar = false"
            >
              Não
            </v-btn>
            <v-btn
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              to="/cliente/consultas"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
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
    cancelar: false,
  }),
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
  },
};
</script>