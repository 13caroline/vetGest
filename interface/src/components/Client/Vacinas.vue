<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
            
          <h3 class="font-weight-regular text-uppercase mt-10 mb-4">
              <v-icon small>fas fa-syringe</v-icon>
            Vacinas e desparasitações
          </h3>

          <v-data-table
            :headers="headers"
            :items="items"
            class="elevation-1"
            hide-default-footer
          >
            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estadopedido(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:[`item.confirmar`]="{ item }">

                  <v-icon
                    v-if="item.estado == 'Administrada'"
                    v-bind="attrs"
                    v-on="on"
                    small
                    color="green"
                  >
                    fas fa-check-circle
                  </v-icon>
                  <v-btn
                    v-if="item.estado == 'Atualizada' || item.estado == 'Atrasada'"
                    v-bind="attrs"
                    v-on="on"
                    small
                    outlined
                    rounded
                    color="#2596be"
                  >
                    Confirmar 
                  </v-btn>
       
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
        text: "Data Prevista",
        align: "start",
        sortable: true,
        value: "dataPrev",
      },
      {
        text: "Data de Toma",
        value: "dataToma",
        sortable: true,
        align: "start",
      },
      {
        text: "Tipo",
        value: "tipo",
        sortable: true,
        align: "start",
      },
      {
        text: "Tratamento Utilizado",
        value: "tratamento",
        sortable: true,
        align: "start",
      },
      {
        text: "Médico Veterinário",
        value: "medico",
        sortable: false,
        align: "center",
      },
      {
        text: "Estado",
        value: "estado",
        sortable: true,
        align: "center",
      },
      {
        text: "Confirmar Administração",
        value: "confirmar",
        sortable: false,
        align: "center",
      },
    ],
    items: [
      {
        dataPrev: "05/04/2021",
        dataToma: "06/04/2021",
        tipo: "Desparasitação",
        tratamento: "Bravacto",
        medico: "Drº José Vieira",
        estado: "Administrada",
      },
      {
        dataPrev: "05/05/2021",
        tipo: "Desparasitação",
        tratamento: "Bravacto",
        estado: "Atualizada",
      },
      {
        dataPrev: "05/04/2021",
        dataToma: "05/04/2021",
        tipo: "Vacina",
        tratamento: "Rabis",
        medico: "Drº José Vieira",
        estado: "Administrada",
      },
      {
        dataPrev: "05/04/2021",
        tipo: "Desparasitação",
        estado: "Atrasada",
      },
    ],
  }),
  methods: {
    estadopedido(estado) {
       if (estado == "Administrada") return "#C5E1A5";
      else if (estado == "Atrasada") return "#EF9A9A";
      else return "#FFE082";
    },
    more(item) {
      console.log(item.data);
    },
  },
};
</script>