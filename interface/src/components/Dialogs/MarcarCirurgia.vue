<template>
    <v-dialog v-model="dialog" width="100%" max-width="500">
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
            Marcar cirurgia
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
                  value="Rubi"
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
                  ref="dataMarcacao"
                  v-model="dataMarcacao"
                  :close-on-content-click="true"
                  :nudge-right="40"
                  :return-value.sync="dataMarcacao"
                  transition="scale-transition"
                  offset-y
                  max-width="290px"
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      append-icon="fas fa-calendar-day"
                      outlined
                      color="#2596be"
                      v-on="on"
                      v-bind="attrs"
                      v-model="date"
                      dense
                      readonly
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    full-width
                    color="#2596be"
                    :min="new Date().toISOString().substr(0, 10)"
                    v-model="date"
                    locale="pt-PT"
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
                    color="#2596be"
                  ></v-time-picker>
                </v-menu>
              </v-col>

              <!--<v-col cols="12" class="py-0">
                  <p class="ma-0">Médico Veterinário</p>
                </v-col>
                <v-col cols="12" class="py-0">
                  <v-autocomplete
                    flat
                    color="#2596be"
                    dense
                    outlined
                    :items="medico"
                    v-model="medico"
                  ></v-autocomplete>
                </v-col>-->
              <!-- v-if tipo == clinica -->
            </v-row>

            <v-row align="end" justify="end">
              <v-col cols="auto">
                <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
              </v-col>
              <v-col cols="auto">
                <v-btn color="#2596be" small dark>Registar</v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-form>
      </v-card>
    </v-dialog>
</template>

<script>
// import axios from 'axios'
import Cancelar from "@/components/Dialogs/Cancel.vue";

export default {
  data: () => ({
    dialog: false,
    dialogs: {},
    cancelar: {
      title: "agendamento da cirurgia",
      text: "o agendamento da cirurgia",
    },
    motivos: "",
    descricao: "",
    dataMarcacao: false,
    horaMarcacao: null,
    date: new Date().toISOString().substr(0, 10),
    hora: new Date().getHours() + ":" + new Date().getMinutes(),
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
  }),
  components: {
    Cancelar,
  },
  methods: {
    close() {
      this.dialog = false;
    },
    
  },
  computed: {
    filteredData() {
      let motivo = this.motivos;
      return this.desc.filter((item) => item.tipo === motivo);
    },
  },
};
</script>