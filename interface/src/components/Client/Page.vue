<template>
  <div id="page" class="d-flex flex-column">
    <v-container>
      <v-row justify="center">
        <v-col cols="12" lg="4">
          <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Os meus animais
          </h3>

          <v-row justify="start">
            <v-col cols="auto">
              <v-btn
                class="body-2"
                small
                color="#2596be"
                dark
                to="/cliente/registar/animal"
              >
                <v-icon small class="mr-2">far fa-plus-square</v-icon>
                Registar Animal
              </v-btn>
            </v-col>
          </v-row>

          <v-item-group>
            <v-row>
              <v-col
                cols="auto"
                lg="6"
                class="mx-auto mx-sm-0"
                v-for="a in animal"
                :key="a.nome"
              >
                <div class="animal">
                  <img
                    src="@/assets/animais/Rubi.jpg"
                    class="w-100 grey lighten-2 rounded"
                  />
                  <!-- <v-item v-slot:default="{ toggle }">
										<v-img
											src="@/assets/animais/Rubi.jpg"
											aspect-ratio="1"
											class="grey lighten-2 rounded"
											@click="toggle"
										>
											<template v-slot:placeholder>
												<v-row align="center" justify="center">
													<v-progress-circular
														indeterminate
														color="grey lighten-5"
													></v-progress-circular>
												</v-row>
											</template>
										</v-img>
									</v-item> -->
                  <v-tooltip top>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        class="mt-2 w-100"
                        text
                        outlined
                        v-bind="attrs"
                        v-on="on"
                        small
                        id="no-background-hover"
                        to="/cliente/animal"
                      >
                        {{ a.nome }}
                      </v-btn>
                    </template>
                    <span class="caption">Ver Mais</span>
                  </v-tooltip>
                </div>
              </v-col>
            </v-row>
          </v-item-group>
        </v-col>

        <v-col lg="7" class="ml-auto mb-5">
          <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Consultas Agendadas
          </h3>

          <v-row justify="start">
            <v-col cols="auto">
              <v-btn
                class="mb-3 body-2"
                small
                color="#2596be"
                dark
                to="/cliente/agendar/consulta"
              >
                <v-icon small class="mr-2">far fa-calendar-alt</v-icon>
                Agendar Consulta
              </v-btn>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="consultas"
            class="elevation-1"
            hide-default-footer
            v-if="consultas.length"
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
                    v-if="
                      item.estado == 'Agendada' || item.estado == 'Pendente'
                    "
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
          <small v-else> <em> sem consultas agendadas </em></small>
        </v-col>
      </v-row>
    </v-container>
    <v-carousel
      class="mt-auto"
      cycle
      height="200"
      hide-delimiter-background
      :show-arrows="false"
      delimiter-icon="mdi-minus"
    >
      <v-carousel-item v-for="(slide, i) in slides" :key="i">
        <v-sheet :color="colors[i]" height="100%" style="margin-top: 12px">
          <v-row class="fill-height" align="center" justify="center">
            <v-col cols="auto" class="display-3">{{ slide }} Slide</v-col>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>

<script>
export default {
  data() {
    return {
      animal: [
        { scr: "Rubi.jpg", nome: "Rubi" },
        { scr: "Rubi.jpg", nome: "Felpudo grande" },
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
      colors: [
        "indigo",
        "warning",
        "pink darken-2",
        "red lighten-1",
        "deep-purple accent-4",
      ],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"],
    };
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
  width: 100%;
  max-width: 250px;
}
</style>
