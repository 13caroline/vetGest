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
                {{ animal.nome }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Espécie</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ animal.especie }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Dono</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ animal.cliente_nome }}
              </div>
            </v-col>
            <div class="d-none d-sm-block w-100"></div>
            <v-col cols62 sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Sexo</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ animal.sexo }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">Raça</div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ animal.raca }}
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">
                Esterilizado
              </div>
              <div v-if="animal.castracao === true" class="font-weight-regular mx-2 mb-2">
                Sim 
              </div>
              <div v-else class="font-weight-regular mx-2 mb-2">
                Não 
              </div>
            </v-col>
            <div class="d-none d-sm-block w-100"></div>

            <v-col cols="6" sm="4">
              <div>
                <div class="font-weight-bold mx-2 mt-2" color="grey">Chip</div>
                <div class="font-weight-regular mx-2 mb-2">
                  {{ animal.chip }}
                </div>
              </div>
            </v-col>

            <v-col cols="6" sm="4">
              <div class="font-weight-bold mx-2 mt-2" color="grey">
                Data de Nascimento
              </div>
              <div class="font-weight-regular mx-2 mb-2">
                {{ format(animal.dataNascimento) }}
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
          <div class="font-weight-regular mx-2 mb-2">{{ animal.altura }} (cm)</div>
        </v-col>

        <v-col cols="6" sm="3">
          <div class="font-weight-bold mx-2 mt-2" color="grey">Pelagem</div>
          <div class="font-weight-regular mx-2 mb-2">
            {{ animal.pelagem }}
          </div>
        </v-col>

        <v-col cols="6" sm="3">
          <div class="font-weight-bold mx-2 mt-2" color="grey">Cor</div>
          <div class="font-weight-regular mx-2 mb-2">
            {{ animal.cor }}
          </div>
        </v-col>

        <v-col cols="6" sm="3">
          <div class="font-weight-bold mx-2 mt-2" color="grey">Cauda</div>
          <div class="font-weight-regular mx-2 mb-2">
            {{ animal.cauda }}
          </div>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import moment from "moment"
export default {
  props:["animal"],
  data: () => ({
  }),
  methods:{
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
  }
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