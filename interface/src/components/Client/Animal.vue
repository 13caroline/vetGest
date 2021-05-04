<template>
  <div>
    <v-container fluid>
      <v-card
        height="60%"
        class="mx-auto mt-2"
        width="90%"
        flat
        color="#fafafa"
      >
        <v-card-text class="ml-10">
          <v-row>
            <v-col cols="3">
              <v-card height="200" width="200" flat>
                <v-img
                  src="@/assets/animais/Rubi.jpg"
                  aspect-ratio="1"
                  class="grey lighten-2 ml-n3"
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
                <v-btn class="mt-5 body-2" small color="#2596be" dark to="/cliente/animal/editar"
                  >Editar Dados</v-btn
                >
              </v-row>
              <v-row>
                <v-btn class="mt-2 body-2" small color="#2596be" dark outlined
                  >Vacinas e Desparasitações</v-btn
                >
              </v-row>
            </v-col>

            <v-col cols="4">
              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Nome</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.nome }}</span>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Espécie</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.especie }}</span>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Raça</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.raca }}</span>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Sexo</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.sexo }}</span>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Altura</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.altura }} (cm)</span>
                </v-col>
              </v-row>
            </v-col>

            <v-col cols="5">
              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">
                    Data de Nascimento
                  </p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.data }}</span>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Pelagem</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.pelagem }}</span>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Cauda</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.cauda }}</span>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Cor</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.cor }}</span>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="4">
                  <p class="font-weight-bold" color="grey">Castração</p>
                </v-col>
                <v-col>
                  <span class="font-weight-regular">{{ cao.castracao }}</span>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row>
            <v-card width="85%">
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
                  <v-icon v-if="item.estado == 'Concluída'" @click="more(item)" small>
                    fas fa-info-circle
                  </v-icon>
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
                    <span class = "caption">Cancelar marcação</span>
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
