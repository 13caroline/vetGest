<template>
  <div id="page" class="d-flex flex-column">
    <Header />
    <v-container>
      <v-row justify="center">
        <v-col>
          <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Pacientes
          </h3>
          <!--<v-layout row class="mb-3">
            <v-tooltip top>
              <template v-slot:activator="{ on }">
                <v-btn class="ma-2" text v-on="on">
                  <v-icon left small>fas fa-paw</v-icon>
                  <span class="caption text-lowercase">Por animal</span>
                </v-btn>
              </template>
              <span>Ordenar consultas por nome de animal</span>
            </v-tooltip>

            <v-tooltip top>
              <template v-slot:activator="{ on }">
                <v-btn class="ma-2" text v-on="on">
                  <v-icon left small>fas fa-user-md</v-icon>
                  <span class="caption text-lowercase"
                    >Por médico veterinário</span
                  >
                </v-btn>
              </template>
              <span>Ordenar consultas por médico veterinário</span>
            </v-tooltip>
          </v-layout>

          <v-card
            class="mx-auto"
            height="80"
            width="2000"
            flat
            color="brown lighten-5"
            v-if="this.consultas.length === 0"
          >
            <v-card-title class="display-1 text-center justify-center">
              Não existem consultas agendadas
            </v-card-title>
          </v-card>
          <v-card
            v-else
            flat
            v-for="consulta in consultas"
            :key="consulta.data"
          >
            <v-layout row wrap :class="`pa-6 consulta ${consulta.estado}`">
              <v-flex xs6 md2>
                <div class="caption grey--text">Nome</div>
                <div class="body-2">{{ consulta.paciente }}</div>
              </v-flex>
              <v-flex xs6 sm4 md5>
                <div class="caption grey--text">Médico Veterinário</div>
                <div class="body-2">{{ consulta.medico }}</div>
              </v-flex>
              <v-flex xs6 sm4 md3>
                <div class="caption grey--text">Data</div>
                <div class="body-2">{{ consulta.data }}</div>
              </v-flex>
              <v-flex xs2 sm4 md2>
                <v-chip
                  :color="estadopedido(consulta.estado)"
                  class="black--text caption mt-4"
                  small
                  >{{ consulta.estado }}</v-chip
                >
              </v-flex>
            </v-layout>
            <v-divider></v-divider>
          </v-card>
        </v-container>-->
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
              <v-tooltip right>
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
          <v-row>
            <v-btn
              color="#2596be"
              small
              dark
              class="ml-3 mt-5"
              to="/cliente/agendar/consulta"
              >Agendar Consulta</v-btn
            >
            <v-btn
              color="#2596be"
              small
              dark
              class="ml-3 mt-5"
              to="/cliente/registar/animal"
              >Registar Animal</v-btn
            >
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <Footer v-bind:has_margin="true" class="mt-auto" />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
      animal: [
        { scr: "Rubi.jpg", nome: "Rubi" },
        { scr: "Rubi.jpg", nome: "Nikita" },
      ],
      headers: [
        {
          text: "Nome",
          align: "start",
          sortable: true,
          value: "paciente",
        },
        {
          text: "Médico Veterinário",
          value: "medico",
          sortable: true,
          align: "start",
        },
        {
          text: "Data de Marcação",
          value: "data",
          sortable: true,
          align: "start",
        },
        { 
          text: "Estado",
          value: "estado",
          sortable: true,
          align: "center" },
        {
          text: "Mais detalhes",
          value: "detalhes",
          sortable: false,
          align: "center",
        },
      ],
      consultas: [
        {
          paciente: "Runa",
          medico: "Drª Joana Ferreira",
          data: "22/08/2021 10:00",
          estado: "Agendada",
        },
        {
          paciente: "Puscas",
          medico: "Drª Joana Ferreira",
          data: "19/05/2021 16:30",
          estado: "Pendente",
        },
      ],
    };
  },
  components: {
    Header,
    Footer,
  },
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else return "#FFE082";
    },
  },
};
</script>

<style scoped>
.consulta.Agendada {
  border-left: 4px solid #c5e1a5;
}

.consulta.Pendente {
  border-left: 4px solid #ffe082;
}

#no-background-hover::before {
  background-color: transparent !important;
}

</style>
