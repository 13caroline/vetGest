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
              <div class="text-uppercase font-weight-bold">Rubi</div>
              <span class="text-uppercase body-2 text--secondary">8 ANOS</span>
            </v-col>
          </v-row>
        </v-card>
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
            <v-card-text v-if="notas.length== 0">
              <em>Não existem notas de internamento</em>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col class="py-0" cols="12" md="6">
          <v-card class="h-100">
            <v-card-text class="pb-0">
              <div>
                <p class="ma-0">Motivo de internamento</p>
                <v-textarea
                  color="#2596be"
                  v-model="motivo"
                  outlined
                  rows="1"
                  flat
                  no-resize
                >
                </v-textarea>
              </div>

              <div>
                <p class="ma-0">História médica prévia</p>
                <v-textarea
                  color="#2596be"
                  v-model="historia_medica"
                  outlined
                  rows="3"
                  flat
                  no-resize
                >
                </v-textarea>
              </div>

              <div>
                <p class="ma-0">Evolução no internamento</p>
                <v-textarea
                  color="#2596be"
                  v-model="evolucao"
                  outlined
                  rows="3"
                  flat
                  no-resize
                >
                </v-textarea>
              </div>

              <div>
                <p class="ma-0">Proposta terapêutica</p>
                <v-textarea
                  color="#2596be"
                  v-model="proposta_terapeutica"
                  outlined
                  rows="3"
                  flat
                  no-resize
                >
                </v-textarea>
              </div>

              <div>
                <p class="ma-0">Orientação</p>
                <v-textarea
                  color="#2596be"
                  v-model="orientacao"
                  outlined
                  rows="1"
                  flat
                  no-resize
                >
                </v-textarea>
              </div>
            </v-card-text>
            <v-card-actions class="pt-0">
              <v-btn
                depressed
                dark
                color="#2596be"
                @click="cancelDialog = true"
                class="mr-2"
              >
                Confirmar alta
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>

      <v-dialog v-model="cancelDialog" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="justify-center cancel">
            Confirmar alta
          </v-card-title>
          <v-card-text>
            Tem a certeza que pretende confirmar a alta de Rubi?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="cancelDialog = false"
            >
              Não
            </v-btn>
            <v-btn
              @click="darAlta()"
              depressed
              large
              dark
              color="#2596be"
              width="50%"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
import moment from "moment";
export default {
  props: ["id"],
  data: () => ({
    cancelDialog: false,
    animal: {},
    notas: [],
    motivo: "",
    historia_medica: "",
    evolucao: "",
    proposta_terapeutica: "",
    orientacao: "",
  }),
  methods: {
    age(data) {
      return moment(data).locale("pt").toNow(true);
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    darAlta: async function () {
      try {
        let response = await axios.post(
          "http://localhost:7777/medico/internamento/alta/confirma",
          {
            alta: {
              data: moment().format(),
              motivo: this.motivo,
              historia_medica: this.historia_medica,
              evolucao: this.evolucao,
              proposta_terapeutica: this.proposta_terapeutica,
              orientacao: this.orientacao,
            },
            email: this.$store.state.email,
            animal: this.id,
          },
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );
        if (response) {
          this.$router.push("/medico/internamento");
           this.$snackbar.showMessage({
          show: true,
          color: "success",
          text: "Nota de alta registada com sucesso.",
          timeout: 4000,
        });
        }
      } catch (e) {
        this.$snackbar.showMessage({
          show: true,
          color: "error",
          text: "Ocorreu um erro. Por favor tente mais tarde!",
          timeout: 4000,
        });
      }
    },
  },
  created: async function () {
    let route = "http://localhost:7777/medico/internamento/detalhes";
    try {
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
    } catch (e) {
      this.$snackbar.showMessage({
        show: true,
        color: "error",
        text: "Ocorreu um erro. Por favor tente mais tarde!",
        timeout: 4000,
      });
    }
  },
};
</script>

<style scoped>
.details {
  text-align: left;
}

.custom-heigth {
  height: 715px;
  overflow-y: auto;
}
</style>