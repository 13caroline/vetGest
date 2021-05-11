<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
            <h3 class="font-weight-regular text-uppercase mt-10">
              Consultas Agendadas
            </h3>

            
              <v-row justify="end">
                <v-col cols="auto">
                  <v-btn class="mb-2 body-2" small color="#2596be" dark to="/cliente/agendar/consulta">
                    Agendar Consulta
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
                        @click="more(item)"
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
                        v-if="item.estado == 'Agendada'"
                        v-bind="attrs"
                        v-on="on"
                        small
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
            text: "Médico Veterinário",
            value: "medico",
            sortable: true,
            align: "start",
        },
        { 
            text: "Descrição", 
            value: "descricao", 
            sortable: true, 
            align: "start" 
        },
        { 
            text: "Estado", 
            value: "estado", 
            sortable: true, 
            align: "center" 
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
        medico: "Drº José Vieira",
        descricao: "Cirurgia",
        estado: "Concluída",
      },
      {
        data: "19/04/2021 15:30",
        medico: "Drº José Vieira",
        descricao: "Consulta",
        estado: "Agendada",
      },
      {
        data: "26/04/2021 14:30",
        medico: "Drº José Vieira",
        descricao: "Consulta",
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
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else return "#FFE082";
    },
    more(item) {
      console.log(item.data);
    },
  },
};
</script>