<template>
  <v-dialog v-model="dialog" width="100%" max-width="500" persistent>
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-btn
            color="#2596be"
            class="mt-8"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            fab
            dark
            @click="dialog = true"
          >
            <v-icon small> fas fa-band-aid</v-icon>
          </v-btn>
        </template>
        <span class="caption">Marcar cirurgia</span>
      </v-tooltip>
    </template>
    <v-card>
      <v-form>
        <v-card-title class="font-weight-regular text-uppercase">
          Marcar uma nova cirurgia
        </v-card-title>
        <v-card-subtitle
          >Por favor preencha o seguinte formulário</v-card-subtitle
        >

        <v-card-text>
          <v-row align="center">
            <v-col class="pb-0">
              <p class="ma-0">Utente</p>
              <v-text-field
                color="#2596be"
                flat
                outlined
                readonly
                dense
                @click="dialogUtente = true"
                v-model="nomeSelected"
              ></v-text-field>
            </v-col>

            <v-col cols="auto" class="pb-0">
              <v-btn
                @click="dialogUtente = true"
                class="ma-0"
                fab
                depressed
                x-small
                dark
                color="#2596be"
                ><v-icon dark> fas fa-search </v-icon>
              </v-btn>
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

            <v-col cols="12" class="py-0">
              <p class="ma-0">Médico Veterinário</p>
            </v-col>
            <v-col cols="12" class="py-0">
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

    <v-dialog v-model="dialogUtente" width="100%" max-width="700">
      <v-card>
        <v-card-title>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table
          @click:row="handleClick"
          :headers="headers"
          :items="items"
          :search="search"
          no-data-text="Não existem clientes registados."
          no-results-text="Não foram encontrados resultados."
        ></v-data-table>
      </v-card>
    </v-dialog>
  </v-dialog>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
import Cancelar from "@/components/Dialogs/Cancel.vue";

export default {
  data: () => ({
    dialogs: {},
    cancelar: {
      title: "agendamento da cirurgia",
      text: "o agendamento da cirurgia",
    },
    dialog: false,
    dialogUtente: false,
    horaMarcacao: null,
    nomeSelected: "",
    descricao: "",
    motivos: "",
    medico: "",
    date: new Date().toISOString().substr(0, 10),
    menu2: false,
    hora: "10:00",
    medicos: [],
    motivo: [
      "Consulta anual/Vacinação",
      "Consulta extraordinária/Por doença",
      "Consulta de seguimento",
      "Procedimentos específicos",
    ],
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
    search: "",
    headers: [
      {
        text: "Utente",
        align: "center",
        sortable: true,
        value: "nome",
      },
      {
        text: "Cliente",
        value: "cliente",
        sortable: true,
        align: "center",
      },
      {
        text: "Raça",
        value: "raca",
        sortable: true,
        align: "center",
      },
      {
        text: "Sexo",
        value: "sexo",
        sortable: true,
        align: "center",
      },
    ],
    items: [],
  }),
  methods: {
    allowedStep: (m) => m % 15 === 0,
    handleClick(row) {
      this.nomeSelected = row.nome;
      this.id = row.id;
      this.dono = row.cliente_email;
      this.dialogUtente = false;
    },
    close() {
      this.dialog = false;
    },
    registar: async function () {
      try {
        if (store.state.tipo == "Clinica") {
          await axios.post(
            "http://localhost:7777/clinica/intervencao/agendar",
            {
              intervencao: {
                data: this.date,
                hora: this.hora,
                descricao: this.descricao,
                motivo: this.motivos,
                tipo: "Cirurgia",
              },
              animal: this.id,
              veterinario: this.medico,
              cliente: this.dono,
            },
            { headers: { Authorization: "Bearer " + store.getters.token } }
          );
          this.$emit("clicked", {
            text: "Cirurgia agendada com sucesso.",
            color: "success",
            snackbar: "true",
            timeout: 4000,
          });
          this.dialog = false;
        }
      } catch (e) {
        console.log("erro: " + e);
        this.$emit("clicked", {
          text: "Ocorreu um erro na marcação, por favor tente mais tarde!",
          color: "warning",
          snackbar: "true",
          timeout: 4000,
        });
      }
    },
  },
  computed: {
    filteredData() {
      let motivo = this.motivos;
      return this.desc.filter((item) => item.tipo === motivo);
    },
  },
  components: {
    Cancelar,
  },
  created: async function () {
    try {
      let response = await axios.get("http://localhost:7777/clinica/medicos", {
        headers: { Authorization: "Bearer " + store.getters.token },
      });

      for (var i = 0; i < response.data.length; i++) {
        this.medicos.push({
          nome: response.data[i].nome,
          id: response.data[i].id,
        });
      }
      let response2 = await axios.get("http://localhost:7777/clinica/utentes", {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      for (i = 0; i < response2.data.utentes.length; i++)
        this.items.push(response2.data.utentes[i].animal);
    } catch (e) {
      console.log(e);
    }
  },
};
</script>