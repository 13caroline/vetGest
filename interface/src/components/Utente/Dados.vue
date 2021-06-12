<template>
  <div class="bg-color">
    <v-row align="center" class="py-3">
      <v-col>
        <h3 class="font-weight-regular text-uppercase">
          <v-icon small>fas fa-paw</v-icon>
          Informações Gerais
        </h3>
      </v-col>
      <v-col cols="auto" class="ml-auto pl-0">
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="body-2 ma-2"
              small
              color="#2596be"
              v-bind="attrs"
              v-on="on"
              fab
              dark
              @click="editar()"
            >
              <v-icon small>fas fa-pen</v-icon>
            </v-btn>
          </template>
          <span>Editar dados</span>
        </v-tooltip>
      </v-col>
    </v-row>

    <h3>Identificação</h3>
    <v-divider class="my-3"></v-divider>

    <v-row class="w-100" align="stretch">
      <v-col cols="12" md="auto">
        <div class="foto mx-auto">
          <v-img
            src="@/assets/animais/Rubi.jpg"
            aspect-ratio="1"
            class="grey lighten-2 mx-2"
            cover
          >
            <template v-slot:placeholder>
              <v-row class="fill-height ma-0" align="center" justify="center">
                <v-progress-circular
                  indeterminate
                  color="grey lighten-5"
                ></v-progress-circular>
              </v-row>
            </template>
          </v-img>
        </div>
      </v-col>
      <v-col cols="12" sm>
        <v-card class="h-100 d-flex flex-column" outlined>
          <v-list-item class="my-auto">
            <v-list-item-content>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Nome</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.nome }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Data de Nascimento</p>
                  </v-col>
                  <v-col cols="auto">
                    <p class="respos">
                      {{ format(animal.dataNascimento) }}
                    </p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Sexo</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.sexo }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos mb-0">Chip</p>
                  </v-col>
                  <v-col>
                    <p class="respos mb-0">{{ animal.chip }}</p>
                  </v-col>
                </v-row>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
      <v-col cols="12" sm>
        <v-card class="h-100 d-flex flex-column" outlined>
          <v-list-item class="my-auto">
            <v-list-item-content>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Espécie</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.especie }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Raça</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.raca }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Esterilizado</p>
                  </v-col>
                  <v-col>
                    <p v-if="animal.castracao === true" class="respos">Sim</p>
                    <p v-else class="respos">Não</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos mb-0">Dono</p>
                  </v-col>
                  <v-col>
                    <p class="respos mb-0">{{ animal.cliente_nome }}</p>
                  </v-col>
                </v-row>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>

    <h3 class="mt-6">Características</h3>
    <v-divider class="my-3"></v-divider>

    <v-row class="w-100" align="stretch">
      <v-col md="6">
        <v-card class="h-100" outlined>
          <v-list-item>
            <v-list-item-content>
              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Altura</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.altura }} (cm)</p>
                  </v-col>
                </v-row>
              </div>

              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Pelagem</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.pelagem }}</p>
                  </v-col>
                </v-row>
              </div>

              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Cor</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.cor }}</p>
                  </v-col>
                </v-row>
              </div>

              <div>
                <v-row>
                  <v-col cols="auto">
                    <p class="infos">Cauda</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.cauda }}</p>
                  </v-col>
                </v-row>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import moment from "moment";
export default {
  props: ["animal"],
  data: () => ({}),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },

    editar() {
      let route =
        this.$store.state.tipo == "Clinica"
          ? "/clinica/editar/utente/"
          : "/medico/editar/utente/";
      this.$router.push(route + this.animal.id);
    },
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

.infos {
  text-align: start;
}
.respos {
  text-align: end;
  font-weight: bold;
}

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}
.bg-color {
  background: #fafafa;
}
</style>