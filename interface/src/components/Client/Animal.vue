<template>
  <div>
    <v-container>
      <v-card height="60%" class="mx-auto mt-2" flat color="#fafafa">
        <v-card-text>
          <v-row>
            <v-col cols="3">
              <v-card height="200" width="200" flat>
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
              <v-row>
                <v-col>
                  <v-btn
                    class="col mt-5 body-2"
                    small
                    color="#2596be"
                    dark
                    to="/cliente/animal/editar"
                    >Editar Dados</v-btn
                  >
                </v-col>
                <v-col>
                  <v-btn class="col body-2" small color="#2596be" dark
                    >Vacinas/Desparasitações</v-btn
                  >
                </v-col>
              </v-row>
            </v-col>

            <v-col cols="3">
              <v-row class="mt-1">
                <p class="font-weight-bold" color="grey">Nome</p>
                <p class="font-weight-regular ml-5">{{ cao.nome }}</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Espécie</p>
                <p class="font-weight-regular ml-5">{{ cao.especie }}</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Raça</p>
                <p class="font-weight-regular ml-5">{{ cao.raca }}</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Sexo</p>
                <p class="font-weight-regular ml-5">{{ cao.sexo }}</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Altura</p>
                <p class="font-weight-regular ml-5">{{ cao.altura }} (cm)</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Data de Nascimento</p>
                <p class="font-weight-regular ml-5">{{ cao.data }}</p>
              </v-row>
            </v-col>

            <v-col>
              <v-row>
                <p class="font-weight-bold" color="grey">Pelagem</p>
                <p class="font-weight-regular ml-5">{{ cao.pelagem }}</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Cauda</p>
                <p class="font-weight-regular ml-5">{{ cao.cauda }}</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Cor</p>
                <p class="font-weight-regular ml-5">{{ cao.cor }}</p>
              </v-row>

              <v-row>
                <p class="font-weight-bold" color="grey">Castração</p>
                <p class="font-weight-regular ml-5">{{ cao.castracao }}</p>
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
        </v-card-text>
      </v-card>
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
};
</script>
