<template>
  <div>
    <v-container>
      <v-row class="w-100">
        <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
          <v-icon class="mr-2">fas fa-user-md</v-icon>
          Médicos Veterinários
        </h3>
        <v-row justify="end">
          <v-col cols="auto">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="body-2 mt-8"
                  small
                  color="#2596be"
                  v-bind="attrs"
                  v-on="on"
                  fab
                  dark
                  to="/clinica/medicos/registar"
                >
                  <v-icon small>fas fa-user-md</v-icon>
                </v-btn>
              </template>
              <span class="caption">Registar médico veterinário</span>
            </v-tooltip>
          </v-col>
        </v-row>
      </v-row>
      <v-row>
        <v-col
          v-for="n in medicos"
          :key="n.id"
          class="d-flex child-flex"
          cols="6"
          sm="4"
          md="3"
          xl="2"
        >
          <div>
            <v-hover v-slot:default="{ hover }">
              <v-img
                src="@/assets/sobre.png"
                aspect-ratio="1"
                @click="openInfo(n)"
                style="border-radius: 100%"
              >
                <v-expand-transition>
                  <div
                    v-bind:class="{ hover: hover }"
                    class="
                      d-flex
                      transition-fast-in-fast-out
                      black
                      v-card--reveal
                      white--text
                      hover-div
                    "
                  >
                    <v-btn
                      text
                      dark
                      depressed
                      class="text-capitalize"
                      @click="openInfo(n)"
                      >Detalhes</v-btn
                    >
                  </div>
                </v-expand-transition>
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
            </v-hover>
            <p align="center" class="mt-2 w-100">{{ n.nome }}</p>
          </div>
        </v-col>
      </v-row>

      <v-dialog v-model="dialog" width="100%" max-width="460">
        <v-card>
          <v-card-title class="mb-6 grey--text">
            Identificação pessoal
            <v-tooltip right>
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon small class="ml-2">
                  <v-icon v-bind="attrs" v-on="on" small>fas fa-pen</v-icon>
                </v-btn>
              </template>
              <span class="caption">Editar dados</span>
            </v-tooltip>

            <v-spacer></v-spacer>
            <v-btn icon small @click="dialog = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <v-row align="center">
              <v-col cols="auto">
                <v-img
                  max-height="100"
                  max-width="100"
                  src="@/assets/exemplo.png"
                >
                </v-img>
              </v-col>
              <v-col>
                <span class="font-weight-bold headline indication text-uppercase">
                  {{ this.nome }}
                </span>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Telemóvel:</span>
              </v-col>

              <v-col>
                <div class="information font-weight-light">
                  {{ this.contacto }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Email:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.email }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Morada:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.morada }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold"
                  >Número de contribuinte:</span
                >
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.nif }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">IBAN</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.iban }}
                </div>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
export default {
  data: () => ({
    dialog: false,
    nome: "",
    morada: "",
    nif: "",
    email: "",
    contacto: "",
    iban: "",
    medicos: [],
  }),
  methods: {
    openInfo(n) {
      this.nome = n.nome;
      this.morada = n.morada;
      this.nif = n.nif;
      this.email = n.email;
      this.contacto = n.contacto;
      this.iban = n.iban;

      this.dialog = true;
    },
  },
  created: async function () {
    try {
      let response = await axios.get("http://localhost:7777/clinica/medicos", {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      this.medicos = response.data;
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.5;
  position: absolute;
  width: 100%;
}

.information {
  text-align: right;
  color: #616161;
}

.indication {
  color: #424242;
}

.hover-div {
  height: 100%;
  border-radius: 100%;
  opacity: 0;
}

.hover-div.hover {
  opacity: 0.7;
}
</style>