<template>
  <div>
    <v-container>
      <v-row justify="space-around" class="mt-2">
        <v-col cols="auto" class="mx-auto mx-md-0">
          <div class="foto">
            <v-card flat>
              <v-img
                src="@/assets/animais/Rubi.jpg"
                aspect-ratio="1"
                class="grey lighten-2"
                cover
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
            </v-card>
            <v-row class="mt-5">
              <v-col class="py-0">
                <v-btn
                  class="col body-2"
                  small
                  color="#2596be"
                  dark
                  to="/cliente/animal/editar"
                >
                  Editar Dados
                </v-btn>
              </v-col>
              <v-col>
                <v-btn class="col body-2" small color="#2596be" dark>
                  Vacinas/Desparasitações
                </v-btn>
              </v-col>
            </v-row>
          </div>
        </v-col>
        <v-col cols="12" sm md="8" lg="7">
          <v-row class="animal-info">
            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Nome
                </v-col>
                <v-col class="font-weight-regular">{{ cao.nome }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Espécie
                </v-col>
                <v-col class="font-weight-regular">{{ cao.especie }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Data de nasc.
                </v-col>
                <v-col class="font-weight-regular">{{ cao.data }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Raça
                </v-col>
                <v-col class="font-weight-regular">{{ cao.raca }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Sexo
                </v-col>
                <v-col class="font-weight-regular">{{ cao.sexo }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Altura
                </v-col>
                <v-col class="font-weight-regular">{{ cao.altura }} (cm)</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Pelagem
                </v-col>
                <v-col class="font-weight-regular">{{ cao.pelagem }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Cauda
                </v-col>
                <v-col class="font-weight-regular">{{ cao.cauda }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Cor
                </v-col>
                <v-col class="font-weight-regular">{{ cao.cor }}</v-col>
              </v-row>
            </v-col>

            <v-col cols="6">
              <v-row>
                <v-col
                  cols="auto"
                  sm="12"
                  md="auto"
                  class="font-weight-bold"
                  color="grey"
                >
                  Castração
                </v-col>
                <v-col class="font-weight-regular">{{ cao.castracao }}</v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-row>
        <v-card class="mt-6">
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
        </v-card>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    cao: {
      nome: "Rubi",
      especie: "Canídeo",
      raca: "Serra da Estrela",
      sexo: "Macho",
      cor: "Castanho",
      data: "02/01/2014",
      altura: 70,
      pelagem: "Média, Lisa",
      cauda: "Comprida",
      chip: "AC14ASC7984",
      castracao: "Sim",
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
      { text: "Descrição", value: "descricao", sortable: true, align: "start" },
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
  created: {
    /*
    let response = await axios.post("http://localhost:7777/clinica/getUtentes", {
      email: this.$store.state.user.email,
    });

    */
  },
};
</script>

<style scoped>
.foto {
  width: 200px;
}

.body-2 {
  font-size: 0.8rem !important;
}

.font-weight-bold {
  width: 40%;
  font-size: 15px;
}
.font-weight-regular {
  font-size: 14px;
}

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}
</style>
