<template>
  <div>
    <v-card flat color="#fafafa">
      <h3 class="pt-6 mb-3">Identificação</h3>
      <v-divider></v-divider>
      <v-row class="animal-info w-100" align="start">
        <v-col cols="12" md="8">
          <v-row>
            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Nome</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ cao.nome }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Espécie</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ cao.especie }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Dono</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ cao.dono }}
              </div>
            </v-col>
            <div class="d-none d-sm-block w-100"></div>
            <v-col cols62 sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Sexo</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ cao.sexo }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Raça</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ cao.raca }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">
                Esterilizado
              </div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ cao.castracao }}
              </div>
            </v-col>
            <div class="d-none d-sm-block w-100"></div>

            <v-col cols="6" sm="4">
              <div>
                <div class="font-weight-bold mx-2 mt-2" color="grey">Chip</div>
                <div class="font-weight-regular mx-2 mb-2">
                  {{ cao.chip }}
                </div>
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">
                Data de Nascimento
              </div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ cao.data }}
              </div>
            </v-col>
          </v-row>
        </v-col>
        <v-col>
          <div class="font-weight-bold mx-2 mt-2" color="grey">Fotografia</div>
          <div class="foto">
            <v-card flat>
              <v-img
                src="@/assets/animais/Rubi.jpg"
                aspect-ratio="1"
                class="grey lighten-2 mx-2"
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
          </div>
        </v-col>
      </v-row>

      <h3 class="mt-6 mb-3">Características</h3>
      <v-divider></v-divider>
      <v-row class="animal-info">
        <v-col cols="6" sm="3">
          <div class="font-weight-bold mx-2 mt-2" color="grey">Altura</div>
          <div class="font-weight-regular mx-2 mb-2">{{ cao.altura }} (cm)</div>
        </v-col>

        <v-col cols="6" sm="3">
          <div class="font-weight-bold mx-2 mt-2" color="grey">Pelagem</div>
          <div class="font-weight-regular mx-2 mb-2">
            {{ cao.pelagem }}
          </div>
        </v-col>

        <v-col cols="6" sm="3">
          <div class="font-weight-bold mx-2 mt-2" color="grey">Cor</div>
          <div class="font-weight-regular mx-2 mb-2">
            {{ cao.cor }}
          </div>
        </v-col>

        <v-col cols="6" sm="3">
          <div class="font-weight-bold mx-2 mt-2" color="grey">Cauda</div>
          <div class="font-weight-regular mx-2 mb-2">
            {{ cao.cauda }}
          </div>
        </v-col>
      </v-row>
    </v-card>
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
      dono: "Carolina Cunha",
    },
  }),
  created() {
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

.head {
  font-size: 2.75rem !important;
}

.font-weight-bold {
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