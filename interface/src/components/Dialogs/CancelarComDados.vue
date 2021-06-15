<template>
  <v-dialog v-model="dialog" persistent width="100%" max-width="460">
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-icon
            v-if="dados.estado == 'A decorrer'"
            disabled
            color="#E57373"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            @click="dialog = true"
          >
            mdi-calendar-remove
          </v-icon>
          <v-icon
            v-else
            color="#E57373"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            @click="dialog = true"
          >
            mdi-calendar-remove
          </v-icon>
        </template>
        <span class="caption">Cancelar agendamento</span>
      </v-tooltip>
    </template>
    <v-card>
      <v-card-title class="cancel"> Cancelar {{ dialogs.title }} </v-card-title>
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
            <span class="text-uppercase">Motivo da {{ dialogs.text }}</span>
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
              <strong>Dr.º {{ dados.veterinario_nome }}</strong>
            </span>
          </v-col>
        </v-row>

        <p class="mt-12">
          Tem a certeza que pretende cancelar {{ dialogs.title }}?
        </p>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          depressed
          large
          width="50%"
          dark
          color="#BDBDBD"
          @click="dialog = false"
        >
          Não
        </v-btn>
        <v-btn
          depressed
          large
          dark
          color="#2596be"
          width="50%"
          @click="confirmar()"
        >
          Sim
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
  props: ["dados", "dialogs"],
  data: () => ({
    dialog: false,
  }),
  methods: {
    confirmar: async function () {
      try {
        if (store.state.tipo == "Clinica") {
          await axios.post(
            "http://localhost:7777/clinica/intervencao/alterar",
            {
              id: this.dados.id,
              estado: "Cancelada",
            },
            {
              headers: { Authorization: "Bearer " + store.getters.token },
            }
          );
        }
        if (store.state.tipo == "Cliente") {
          await axios.post(
            "http://localhost:7777/cliente/animal/cancelar/" +
              this.dados.idConsulta,
            {
              email: this.$store.state.email,
              animal: this.dados.animal.id,
            },
            {
              headers: { Authorization: "Bearer " + store.getters.token },
            }
          );
        }
        if (store.state.tipo == "Veterinario") {
          await axios.post(
            "http://localhost:7777/medico/intervencao/alterar",
            {
              id: this.dados.id,
              estado: "Cancelada",
              observacoes:""
            },
            {
              headers: { Authorization: "Bearer " + store.getters.token },
            }
          );
        }
        this.dialog = false;
        if (this.dialogs.text == "consulta")
          this.$emit("clicked", {
            text: "Consulta cancelada com sucesso.",
            color: "success",
            snackbar: "true",
            timeout: 4000,
          });
        if (this.dialogs.text == "cirurgia")
          this.$emit("clicked", {
            text: "Cirurgia cancelada com sucesso.",
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
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
  },
};
</script>