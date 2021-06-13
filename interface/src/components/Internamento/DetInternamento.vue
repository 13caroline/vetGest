<template>
  <div>
    <v-container class="fill-height">
      <v-row class="w-100">
        <v-card flat height="100" width="190" outlined class="ma-4">
          <v-row>
            <v-col>
              <v-img
                src="@/assets/animais/Rubi.jpg"
                aspect-ratio="1"
                class="grey lighten-2 ma-2 rounded"
                cover
                min-height="80"
                min-width="80"
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
            </v-col>
            <v-col class="details my-auto">
              <div class="text-uppercase font-weight-bold">
                {{ animal.nome }}
              </div>
              <span class="text-uppercase body-2 text--secondary">
                {{ age(animal.dataNascimento) }}</span
              >
            </v-col>
          </v-row>
        </v-card>
      </v-row>

      <v-row class="w-100">
        <v-col cols="12" md="6">
          <v-card class="h-100 custom">
            <v-card-text
              class="py-0"
              v-for="(nota, index) in notas"
              :key="index"
            >
              <v-timeline align-top dense>
                <v-timeline-item color="#2596be" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>{{ nota.data }}</strong>
                    </v-col>
                    <v-col>
                      <span>{{ nota.descricao }}</span>
                    </v-col>
                  </v-row>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
            <v-card-text v-if="!notas">
              <em>Não existem notas de internamento</em>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col v-if="this.$store.state.tipo == 'Veterinario'">
          <v-card class="h-100">
            <v-card-text class="pb-0">
              <v-textarea
                color="#2596be"
                outlined
                rows="20"
                flat
                no-resize
                v-model="descricao"
              >
              </v-textarea>
            </v-card-text>
            <v-card-actions class="pt-0"
              ><v-btn
                depressed
                dark
                color="#2596be"
                class="ml-auto mr-2"
                @click="adicionarNota()"
              >
                Gravar
              </v-btn></v-card-actions
            >
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
import moment from "moment";
export default {
  props: ["id"],
  data() {
    return {
      descricao: "",
      animal: {},
      notas: [],
    };
  },
  created: async function () {
    let route =
      this.$store.state.tipo == "Clinica"
        ? "http://localhost:7777/clinica/internamento/detalhes"
        : "http://localhost:7777/medico/internamento/detalhes";
    let response = await axios.post(
      route,
      {
        id: this.id,
      },
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      }
    );
    this.animal = response.data.animal;
    this.notas = Array.isArray(response.data.notas)
      ? response.data.notas
      : [response.data.notas];
  },
  methods: {
    age(data) {
      return moment(data).locale("pt").toNow(true); // 4 years
    },
    adicionarNota: async function () {
      let response = await axios.post(
        "http://localhost:7777/medico/internamento/detalhes/adiciona",
        {
          notaInternamento: {
            descricao: this.descricao,
          },
          email: this.$store.state.email,
          animal: this.id,
        },
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );
      if (response) {
        this.created();
      }
    },
  },
};
</script>


<style scoped>
.details {
  text-align: left;
}

.custom {
  max-height: calc(100vh - (132px + 177px));
  overflow-y: auto;
}
</style>