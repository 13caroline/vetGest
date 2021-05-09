<template>
  <div id="page" class="d-flex flex-column">
    <Header />
    <v-container>
      <v-row justify="center">
        <v-col cols="12" lg="4">
          <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Os meus animais
          </h3>
          <v-item-group>
            <v-row>
              <v-col
                class="d-flex child-flex animal"
                cols="5"
                v-for="a in animal"
                :key="a.nome"
              >
                <v-card flat tile color="#fafafa">
                  <v-item v-slot:default="{ toggle }">
                    <v-img
                      src="@/assets/animais/Rubi.jpg"
                      aspect-ratio="1"
                      class="grey lighten-2"
                      @click="toggle"
                    >
                      <template v-slot:placeholder>
                        <v-row
                          class="fill-height ma-0"
                          align="center"
                          justify="center"
                        >
                          <v-progress-circular
                            indeterminate
                            color="grey lighten-5"
                          ></v-progress-circular>
                        </v-row>
                      </template>
                    </v-img>
                  </v-item>
                  <v-row justify="center">
                    <div>
                      <p class="text-uppercase font-weight-regular mt-3 ml-5">
                        {{ a.nome }}
                      </p>
                      <v-btn
                        class="mt-n5 ml-n1"
                        text
                        outlined
                        small
                        id="no-background-hover"
                        to="/cliente/animal"
                      >
                        Ver mais
                      </v-btn>
                    </div>
                  </v-row>
                </v-card>
              </v-col>
            </v-row>
          </v-item-group>
        </v-col>

        <v-col>
          <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Consultas Agendadas
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
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
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
        { text: "Estado", value: "estado", sortable: true, align: "center" },
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

.animal {
  max-width: 250px;
}
</style>
