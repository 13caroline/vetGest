<template>
  <div>
    <v-container class="fill-height">
      <v-row class="row my-4 mx-0">
        <v-col cols="auto" class="custom-card">
          <v-row>
            <v-col cols="auto">
              <v-img
                :src="animal.image"
                aspect-ratio="1"
                class="grey lighten-2 ma-2 rounded"
                cover
                min-height="80"
                min-width="80"
                width="80"
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
        </v-col>
      </v-row>

      <v-row class="w-100">
        <v-col class="py-0">
          <v-card class="h-100 custom-heigth">
            <v-card-text
              class="py-0"
              v-for="(nota, index) in notas"
              :key="index"
            >
              <v-timeline align-top dense>
                <v-timeline-item color="#2596be" small>
                  <v-row class="pt-1">
                    <v-col cols="3" class="px-0">
                      <strong>{{ format(nota.data) }}</strong>
                    </v-col>
                    <v-col>
                      <span>{{ nota.descricao }}</span>
                    </v-col>
                  </v-row>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
            <v-card-text v-if="notas.length == 0">
              <em>Não existem notas de internamento</em>
            </v-card-text>
          </v-card>
        </v-col>
        <v-col
          class="py-0"
          cols="12"
          md="6"
          v-if="this.$store.state.tipo == 'Veterinario'"
        >
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
  methods: {
    age(data) {
      return moment(data).locale("pt").toNow(true); // 4 years
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    loadData: async function () {
      this.notas = [];
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
      if (response.data.notas) {
        this.notas = Array.isArray(response.data.notas)
          ? response.data.notas
          : [response.data.notas];
      }
      this.animal.image = this.animal.image
          ? "data:image/jpeg;charset=utf-8;base64," + this.animal.image
          : require("@/assets/image_placeholder.png");
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
        this.loadData();
        this.descricao = "";
      }
    },
  },
  created() {
    this.loadData();
  },
};
</script>


<style scoped>
.details {
  text-align: left;
}

.custom-heigth {
  height: 660px;
  overflow-y: auto;
}
.custom-card {
  background: white;
  border: thin solid rgba(0, 0, 0, 0.12);
  border-radius: 4px;
}
</style>