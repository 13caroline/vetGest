<template>
  <div>
    <v-dialog v-model="dialog" width="100%" max-width="700" persistent>
      <template v-slot:activator="{ on, attrs }">
        <v-btn v-bind="attrs" v-on="on" small outlined rounded color="#2596be">
          Confirmar
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline mb-6">
          Confirmar desparasitação
        </v-card-title>
        <v-card-text class="black--text">
          <div>
            <p class="ma-0">Data prevista</p>
            <v-text-field
              color="#2596be"
              flat
              outlined
              disabled
              dense
              readonly
              :value="dataPrev"
              append-icon="fas fa-calendar-alt"
            ></v-text-field>
          </div>
          <div>
            <p class="mb-0">Data de toma</p>
            <v-menu
              v-model="menu4"
              :close-on-content-click="false"
              :nudge-right="40"
              transition="scale-transition"
              offset-y
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="date1"
                  append-icon="fas fa-calendar-alt"
                  readonly
                  dense
                  outlined
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="date1"
                @input="menu4 = false"
                locale="pt PT"
                :min="new Date().toISOString().substr(0, 10)"
              ></v-date-picker>
            </v-menu>
          </div>
          <div>
            <p class="ma-0">Tratamento Utilizado</p>
            <v-text-field
              outlined
              color="#2596be"
              dense
              v-model="tratamento"
            ></v-text-field>
          </div>
          <v-row align="end" justify="end">
            <v-col cols="auto">
              <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
            </v-col>
            <v-col cols="auto">
              <v-btn color="#2596be" small dark @click="confirma()">
                Confirmar
              </v-btn>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from "axios";
import Cancelar from "@/components/Dialogs/Cancel.vue";
import store from "@/store.js";

export default {
  props: ["dataPrev", "id"],
  data: () => ({
    dialog: false,
    dialogs: {},
    tratamento: "",
    cancelar: {
      title: "confirmação de desparasitação",
      text: "a confirmação de desparasitação",
    },
    menu4: false,
    date1: new Date().toISOString().substr(0, 10),
  }),
  components: {
    Cancelar,
  },
  methods: {
    close() {
      this.dialog = false;
    },
    confirma: async function () {
      try {
        await axios.post(
          "http://localhost:7777/medico/animal/confirma/imunizacao",
          {
            id: this.id,
            data: this.date1,
            tratamento: this.tratamento,
          },
          {
            headers: {
              Authorization: "Bearer " + store.getters.token.toString(),
            },
          }
        );
        this.$snackbar.showMessage({
          show: true,
          color: "success",
          text: "Desparasitação confirmada com sucesso.",
          timeout: 4000,
        });
        this.dialog = false;
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
};
</script>