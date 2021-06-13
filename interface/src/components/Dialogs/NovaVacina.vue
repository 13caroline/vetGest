<template>
  <v-dialog v-model="dialog" width="100%" max-width="700" persistent>
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-btn
            color="#2596be"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            dark
            fab
            class="mr-0"
            @click="dialog = true"
          >
            <v-icon small> fas fa-syringe</v-icon>
          </v-btn>
        </template>
        <span class="caption">Adicionar vacinação</span>
      </v-tooltip>
    </template>
    <v-card>
      <v-card-title class="headline mb-6"> Adicionar vacinação </v-card-title>
      <v-card-text class="black--text">
        <div>
          <p class="mb-0">Data de toma</p>
          <v-menu
            v-model="menu1"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="dateVac"
                append-icon="fas fa-calendar-alt"
                readonly
                dense
                outlined
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="dateVac"
              @input="menu1 = false"
              locale="pt PT"
              :min="new Date().toISOString().substr(0, 10)"
            ></v-date-picker>
          </v-menu>
        </div>
        <div>
          <p class="ma-0">Vacina contra</p>
          <v-select
            outlined
            color="#2596be"
            dense
            v-model="vaccontra"
            :items="contra"
            placeholder="Tipo de vacinação"
          ></v-select>
          <v-text-field
            v-if="this.vaccontra == 'Outro'"
            outlined
            dense
            color="#2596be"
            v-model="vaccontraOutro"
            placeholder="Indique outro tipo de vacinação"
          ></v-text-field>
        </div>
        <div>
          <p class="ma-0">Tratamento Utilizado</p>
          <v-text-field
            outlined
            color="#2596be"
            dense
            placeholder="Tratamento utilizado"
            v-model="tratamento"
          ></v-text-field>
        </div>
        <div>
          <p class="ma-0">Próxima dose</p>
          <v-row>
            <v-col cols="auto">
              <v-checkbox
                v-model="enabled"
                hide-details
                class="mt-1 pl-4"
                color="#2596be"
              ></v-checkbox>
            </v-col>
            <v-col>
              <v-menu
                v-model="menu"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="dateProx"
                    append-icon="fas fa-calendar-alt"
                    readonly
                    dense
                    outlined
                    :disabled="!enabled"
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="dateProx"
                  @input="menu = false"
                  locale="pt PT"
                  :min="new Date().toISOString().substr(0, 10)"
                ></v-date-picker>
              </v-menu>
            </v-col>
          </v-row>
        </div>
        <div>
          <p class="ma-0">Observações</p>
          <v-textarea
            outlined
            color="#2596be"
            rows="2"
            clearable
            clear-icon="fas fa-times-circle"
            no-resize
            v-model="observacoes"
          ></v-textarea>
        </div>
        <v-row align="end" justify="end">
          <v-col cols="auto">
            <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
          </v-col>
          <v-col cols="auto">
            <v-btn color="#2596be" small dark @click="adicionaVacina()">
              Confirmar
            </v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";
import Cancelar from "@/components/Dialogs/Cancel.vue";
import store from "@/store.js";

export default {
  props: ["animal"],
  data: () => ({
    dialog: false,
    dialogs: {},
    cancelar: {
      title: "registo de nova vacina",
      text: "o registo de uma nova vacina",
    },
    dateVac: new Date().toISOString().substr(0, 10),
    dateProx: new Date().toISOString().substr(0, 10),
    menu1: false,
    menu: false,
    vaccontra: "",
    vaccontraOutro: "",
    enabled: false,
    tratamento: "",
    observacoes: "",
    contra: [
      "Esgana",
      "Hepatite",
      "Leptospirose",
      "Parvovirose",
      "Tosse de canil",
      "Piroplasmose",
      "Outro",
    ],
  }),
  components: {
    Cancelar,
  },
  methods: {
    adicionaVacina: async function () {
      try {
        await axios.post(
            // @TODO: Validar backend
          "http://localhost:7777/medico/animal/adiciona/imunizacao",
          {
            animal: this.animal.id,
            imunizacao: {
              data_toma: this.dateVac,
              observacoes: this.observacoes,
              data: this.dateVac,
              vacina: this.vaccontraOutro
                ? this.vaccontraOutro
                : this.vaccontra,
              proxImunizacao: this.enabled ? this.dateProx : null,
              tratamento: this.tratamento,
            },
            veterinario: this.$store.state.email,
          },
          {
            headers: {
              Authorization: "Bearer " + store.getters.token.toString(),
            },
          }
        );
      } catch (e) {
        console.log(e);
      }
    },
    close() {
      this.dialog = false;
    },
  },
};
</script>