<template>
  <v-dialog v-model="dialog" width="100%" max-width="500" persistent>
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-btn
            color="#2596be"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            fab
            dark
            @click="dialog = true"
          >
            <v-icon small> fas fa-scroll</v-icon>
          </v-btn>
        </template>
        <span class="caption">Agendar consulta</span>
      </v-tooltip>
    </template>
    <v-card>
      <v-form>
        <v-card-title class="font-weight-regular text-uppercase">
          Agendar consulta
        </v-card-title>
        <v-card-subtitle
          >Por favor preencha o seguinte formulário</v-card-subtitle
        >

        <v-card-text>
          <v-row align="center">
            <v-col cols="12" class="pb-0">
              <p class="ma-0">Utente</p>
              <v-text-field
                color="#2596be"
                flat
                outlined
                readonly
                dense
                :value="dados.nome"
              ></v-text-field>
            </v-col>

            <v-col cols="12" class="py-0">
              <p class="ma-0">Motivo</p>
              <v-select
                :items="motivo"
                dense
                color="#2596be"
                flat
                outlined
                v-model="motivos"
              ></v-select>
            </v-col>

            <v-col cols="12" class="py-0">
              <p class="ma-0">Descrição</p>
              <v-select
                :items="filteredData"
                dense
                color="#2596be"
                flat
                outlined
                v-model="descricao"
                item-text="text"
                item-value="text"
              ></v-select>
            </v-col>

            <v-col cols="12" class="py-0">
              <p class="ma-0">Data</p>
            </v-col>

            <v-col cols="12" class="py-0">
              <v-menu
                v-model="menu2"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="date"
                    append-icon="fas fa-calendar-alt"
                    readonly
                    dense
                    outlined
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="date"
                  @input="menu2 = false"
                  locale="pt PT"
                  :min="new Date().toISOString().substr(0, 10)"
                ></v-date-picker>
              </v-menu>
            </v-col>

            <v-col cols="12" class="py-0">
              <p class="ma-0">Hora</p>
            </v-col>

            <v-col cols="12" class="py-0">
              <v-menu
                ref="horaMarcacao"
                v-model="horaMarcacao"
                :close-on-content-click="false"
                :nudge-right="40"
                :return-value.sync="horaMarcacao"
                transition="scale-transition"
                offset-y
                max-width="290px"
                min-width="290px"
              >
                <template v-slot:activator="{ on }">
                  <v-text-field
                    append-icon="fas fa-clock"
                    color="#2596be"
                    v-on="on"
                    outlined
                    dense
                    v-model="hora"
                  ></v-text-field>
                </template>
                <v-time-picker
                  format="24hr"
                  v-model="hora"
                  full-width
                  min="10:00"
                  max="19:45"
                  :allowed-minutes="allowedStep"
                  color="#2596be"
                ></v-time-picker>
              </v-menu>
            </v-col>
            <v-col
              cols="12"
              class="py-0"
              v-if="this.$store.state.tipo == 'Clinica'"
            >
              <p class="ma-0">Médico Veterinário</p>
            </v-col>
            <v-col
              cols="12"
              class="py-0"
              v-if="this.$store.state.tipo == 'Clinica'"
            >
              <v-autocomplete
                flat
                color="#2596be"
                dense
                outlined
                :items="medicos"
                item-text="nome"
                item-value="id"
                v-model="medico"
              ></v-autocomplete>
            </v-col>
          </v-row>

          <v-row align="end" justify="end">
            <v-col cols="auto">
              <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
            </v-col>
            <v-col cols="auto">
              <v-btn color="#2596be" small dark @click="registar()"
                >Registar</v-btn
              >
            </v-col>
          </v-row>
        </v-card-text>
      </v-form>
    </v-card>
  </v-dialog>
</template>


<script>
import Cancelar from "@/components/Dialogs/Cancel.vue";
import store from "@/store.js";
import axios from "axios";
export default {
  props: ["dados"],
  data: () => ({
    dialog: false,
    dialogs: {},
    cancelar: {
      title: "agendamento da consulta",
      text: "o agendamento da consulta",
    },
    desc: [
      { text: "Consulta anual/Vacinação", tipo: "Consulta anual/Vacinação" },
      {
        text: "Vómitos/Diarreia/Recusa em comer",
        tipo: "Consulta extraordinária/Por doença",
      },
      {
        text: "Comportamento letárgico",
        tipo: "Consulta extraordinária/Por doença",
      },
      {
        text: "Alterações da marcha",
        tipo: "Consulta extraordinária/Por doença",
      },
      {
        text: "Problema de olhos ou ouvidos",
        tipo: "Consulta extraordinária/Por doença",
      },
      {
        text: "Problemas de dentes ou boca",
        tipo: "Consulta extraordinária/Por doença",
      },
      {
        text: "Problemas cutâneos",
        tipo: "Consulta extraordinária/Por doença",
      },
      {
        text: "Problemas urinários",
        tipo: "Consulta extraordinária/Por doença",
      },
      { text: "Outros", tipo: "Consulta extraordinária/Por doença" },
      { text: "Consulta de seguimento", tipo: "Consulta de seguimento" },
      { text: "Cortar unhas", tipo: "Procedimentos específicos" },
      {
        text: "Expressão de glândulas anais",
        tipo: "Procedimentos específicos",
      },
      { text: "Análises", tipo: "Procedimentos específicos" },
      { text: "Limpeza de ouvidos", tipo: "Procedimentos específicos" },
      { text: "Cortar o pêlo", tipo: "Procedimentos específicos" },
      { text: "Lavagem", tipo: "Procedimentos específicos" },
      { text: "Desparasitação", tipo: "Procedimentos específicos" },
      { text: "Outros", tipo: "Procedimentos específicos" },
    ],
    motivo: [
      "Consulta anual/Vacinação",
      "Consulta extraordinária/Por doença",
      "Consulta de seguimento",
      "Procedimentos específicos",
    ],
    medicos: [],
    hora: "10:00",
    date: new Date().toISOString().substr(0, 10),
    menu2: false,
    descricao: "",
    motivos: "",
    medico: "",
    utente: "",
    horaMarcacao: null,
  }),
  components: {
    Cancelar,
  },
  methods: {
    close() {
      this.dialog = false;
    },
    allowedStep: (m) => m % 15 === 0,
    registar: async function () {
      let route =
        this.$store.state.tipo == "Clinica"
          ? "http://localhost:7777/clinica/intervencao/agendar"
          : "http://localhost:7777/medico/agendar/intervencao";
      await axios
        .post(
          route,
          {
            intervencao: {
              data: this.date,
              hora: this.hora,
              descricao: this.descricao,
              motivo: this.motivos,
              tipo: "Consulta",
            },
            animal: this.dados.id,
            veterinario:
              this.$store.state.tipo == "Clinica"
                ? this.medico
                : this.$store.state.email,
            cliente: this.dados.cliente_email,
          },
          { headers: { Authorization: "Bearer " + store.getters.token } }
        )
        .then((response) => {
          console.log(response);
          this.$emit("clicked", {
            text: "Consulta agendada com sucesso.",
            color: "success",
            snackbar: "true",
            timeout: 4000,
          });
          this.dialog = false;
        })
        .catch((error) => {
          if (
            error.response.data ==
            "Erro no agendamento de Consulta! Horario Indisponivel!"
          ) {
            this.$emit("clicked", {
              text: "Não é possível agendar uma consulta para o horário selecionado.",
              color: "warning",
              snackbar: "true",
              timeout: 4000,
            });
          } else {
            this.$emit("clicked", {
              text: "Ocorreu um erro no agendamento, por favor tente mais tarde!",
              color: "warning",
              snackbar: "true",
              timeout: 4000,
            });
          }
        });
    },
  },
  computed: {
    filteredData() {
      let motivo = this.motivos;
      return this.desc.filter((item) => item.tipo === motivo);
    },
  },
  created: async function () {
    if (store.state.tipo == "Clinica") {
      try {
        let response = await axios.get(
          "http://localhost:7777/clinica/medicos",
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );

        for (var i = 0; i < response.data.length; i++) {
          this.medicos.push({
            nome: response.data[i].nome,
            id: response.data[i].id,
          });
        }
      } catch (e) {
        console.log(e);
      }
    }
  },
};
</script>

<style scoped>
.calendar {
  font-size: 16px;
}
</style>