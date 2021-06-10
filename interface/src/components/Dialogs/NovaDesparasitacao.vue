<template>
  <v-dialog v-model="dialog" width="100%" max-width="700" persistent>
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">

<v-btn
                class="body-2"
                small
                v-bind="attrs"
                color="#2596be"
                v-on="{ ...on, ...diag }"
                dark
                @click="dialog = true"
              >
                Adicionar Desparasitação
                <v-icon small class="ml-4">fas fa-spider</v-icon>
              </v-btn>
        </template>
        <span class="caption">Adicionar desparasitação</span>
      </v-tooltip>
    </template>
    <v-card>
      <v-card-title class="headline mb-6">
        Adicionar desparasitação
      </v-card-title>
      <v-card-text class="black--text">
        <div>
          <p class="mb-0">Data de toma</p>
          <v-menu
            v-model="menu3"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="dateToma"
                append-icon="fas fa-calendar-alt"
                readonly
                dense
                outlined
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="dateToma"
              @input="menu3 = false"
              locale="pt PT"
            ></v-date-picker>
          </v-menu>
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
          <p class="ma-0">Próxima toma</p>
          <v-select
            outlined
            color="#2596be"
            dense
            placeholder="Próxima toma"
            v-model="proxImunizacao"
            :items="tomas"
          ></v-select>
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
            v-model="motivo"
          ></v-textarea>
        </div>
        <v-row align="end" justify="end">
          <v-col cols="auto">
            <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
          </v-col>
          <v-col cols="auto">
            <v-btn color="#2596be" small dark @click="adicionaDesparasitacao()">
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
import moment from "moment";

export default {
  props: ["dados"],
  data: () => ({
    dialog: false,
    dialogs: {},
    cancelar: {
      title: "registo de nova desparasitação",
      text: "o registo de uma nova desparasitação",
    },
    menu3: false,
    tratamento: "",
    proxImunizacao: "",
    motivo: "",
    dateToma: new Date().toISOString().substr(0, 10),
    tomas: ["1 mês", "3 meses"],
    timeskip: 0,
  }),
  components: {
    Cancelar,
  },
  methods: {
    close() {
      this.dialog = false;
    },
    adicionaDesparasitacao: async function () {
      try {
        if (store.state.tipo == "Cliente") {
          if (this.proxImunizacao == "1 mês") this.timeskip = 1;
          else this.timeskip = 3;

          await axios.post(
            "http://localhost:7777/cliente/animal/" + this.dados + "/vacinas",
            {
              imunizacao: {
                data: this.dateToma,
                observacoes: this.motivo,

                proxImunizacao: moment(this.data).add(this.timeskip, "months"),
                tratamento: this.tratamento,
              },
              email: this.$store.state.email,
            },
            {
              headers: {
                Authorization: "Bearer " + store.getters.token.toString(),
              },
            }
          );
        }
        this.dialog = false;
        this.$emit("clicked", {
          text: "Desparasitação registada com sucesso.",
          color: "success",
          snackbar: "true",
          timeout: 4000,
        });
      } catch (e) {
        console.log("erro: +" + e);
        this.$emit("clicked", {
          text: "Ocorreu um erro, por favor tente mais tarde!",
          color: "warning",
          snackbar: "true",
          timeout: 4000,
        });
      }
    },
  },
};
</script>