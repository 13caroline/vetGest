<template>
  <v-dialog v-model="dialog" persistent width="100%" max-width="460">
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-icon
            v-if="dados.estado == 'A decorrer'"
            color="#66BB6A"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            disabled
            @click="dialog = true"
          >
            mdi-calendar-check
          </v-icon>
          <v-icon
            v-else
            color="#66BB6A"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            @click="dialog = true"
          >
            mdi-calendar-check
          </v-icon>
        </template>
        <span class="caption">Admitir utente</span>
      </v-tooltip>
    </template>
    <v-card>
      <v-card-title class="cancel">Admitir utente</v-card-title>
      <v-card-text>
        <v-row class="mt-2">
          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Nome do Animal</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>{{ dados.utente }}</strong>
              ({{ dados.animal.raca }})
            </span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Motivo da Consulta</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>{{ dados.descricao }}</strong>
            </span>
            <br />
            <span>{{ dados.motivo }}</span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Data</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>{{ format(dados.marcacao) }}</strong>
            </span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Médico</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>Dr.º {{dados.veterinario_nome}}</strong>
            </span>
          </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <v-btn
          depressed
          large
          width="50%"
          dark
          color="#BDBDBD"
          @click="dialog = false"
        >
          Cancelar
        </v-btn>
        <v-btn
          depressed
          large
          dark
          color="#2596be"
          width="50%"
          @click="confirmar()"
        >
          Admitir
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import moment from "moment";
import axios from "axios";
import store from "@/store.js";

export default {
  props: ["dados"],
  data: () => ({
    dialog: false,
  }),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    confirmar: async function () {
      try {
        if (store.state.tipo == "Clinica") {
          await axios.post(
            "http://localhost:7777/clinica/intervencao/alterar",
            {
              id: this.dados.id,
              estado: "A decorrer",
            },
            {
              headers: { Authorization: "Bearer " + store.getters.token },
            }
          );
        }
        this.dialog = false;
        if (this.dados.tipo == "Consulta")
          this.$emit("clicked", {
            text: "Consulta admitida com sucesso.",
            color: "success",
            snackbar: "true",
            timeout: 4000,
          });
        if (this.dados.tipo == "Cirurgia")
          this.$emit("clicked", {
            text: "Cirurgia admitida com sucesso.",
            color: "success",
            snackbar: "true",
            timeout: 4000,
          });
      } catch (e) {
        console.log("erro: " + e);
        this.dialog = false;
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