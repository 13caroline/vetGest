<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-scroll</v-icon>
                Histórico de Cirurgias
              </h3>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="cirurgias"
            class="elevation-1"
            hide-default-footer
          >
            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estadopedido(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:[`item.detalhes`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    class="mx-1"
                    v-if="item.estado == 'Concluída'"
                    @click="detalhes = true"
                    small
                    v-on="on"
                    v-bind="attrs"
                  >
                    fas fa-info-circle
                  </v-icon>
                </template>
                <span class="caption">Ver detalhes</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    class="mx-1"
                    v-if="item.estado == 'Agendada'"
                    v-bind="attrs"
                    v-on="on"
                    small
                    @click="cancelar = true"
                  >
                    fas fa-calendar-times
                  </v-icon>
                </template>
                <span class="caption">Cancelar cirurgia</span>
              </v-tooltip>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
      <v-dialog v-model="detalhes" width="100%" max-width="700">
        <v-card>
          <v-card-title class="headline mb-6">
            Notas Médicas
            <v-spacer></v-spacer>
            <v-btn icon small @click="dialog = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <exemplo></exemplo>
          </v-card-text>
        </v-card>
      </v-dialog>
      <v-dialog v-model="cancelar" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="cancel"> Cancelar cirurgia </v-card-title>
          <v-card-text>
            <v-row class="mt-2">
              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Nome do Animal</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Rubi</strong>
                  (Serra da Estrela)
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Motivo da Cirurgia</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Castração</strong>
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Data</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>19/04/2021 15:30</strong>
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Médico</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Drº José Vieira</strong>
                </span>
              </v-col>
            </v-row>

            <p class="mt-12">Tem a certeza que pretende cancelar a cirurgia?</p>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="cancelar = false"
            >
              Não
            </v-btn>
            <v-btn
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              @click="cancelar()"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialog" width="100%" max-width="500">
        <v-card>
          <v-form>
            <v-card-title class="font-weight-regular text-uppercase">
              Agendar cirurgia
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
                    v-model="utente"
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
                      color="#2596be"
                      min="10:00"
                      max="19:45"
                      :allowed-minutes="allowedStep"
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
                    :items="medico"
                    v-model="medico"
                  ></v-autocomplete>
                </v-col>
              </v-row>

              <v-row align="end" justify="end">
                <v-col cols="auto">
                  <v-btn color="#BDBDBD" small dark @click="cancelDialog = true"
                    >Cancelar</v-btn
                  >
                </v-col>
                <v-col cols="auto">
                  <v-btn color="#2596be" small dark class="ml-3"
                    >Registar</v-btn
                  >
                </v-col>
              </v-row>
            </v-card-text>
          </v-form>
        </v-card>
      </v-dialog>

      <v-dialog v-model="cancelDialog" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="justify-center cancel">
            Cancelar agendamento da cirurgia
          </v-card-title>
          <v-card-text>
            Tem a certeza que pretende cancelar o agendamento da cirurgia?
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
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              @click="cancelDialog = false"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </div>
</template>

<script>
// import axios from 'axios'
import exemplo from "@/components/Client/exemploCirurgia.vue";
import axios from "axios";
import store from "@/store.js";
export default {
  data: () => ({
    cancelDialog: false,
    dialog: false,
    detalhes: false,
    cancelar: false,
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
    headers: [
      {
        text: "Data de Agendamento",
        align: "start",
        sortable: true,
        value: "data",
      },
      {
        text: "Médico Veterinário",
        value: "medico",
        sortable: true,
        align: "start",
      },
      {
        text: "Descrição",
        value: "descricao",
        sortable: true,
        align: "start",
      },
      {
        text: "Estado",
        value: "estado",
        sortable: true,
        align: "center",
      },
      {
        text: "Mais detalhes",
        value: "detalhes",
        sortable: false,
        align: "center",
      },
    ],
    cirurgias: [],
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
    exemplo,
  },
  methods: {
    allowedStep: (m) => m % 15 === 0,
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else return "#FFE082";
    },
    more(item) {
      console.log(item.data);
    },
    /* cancelar: async function () {
      
		 try {
          var resposta = await axios.post("http://localhost:7777/cliente/cancelarCirurgia", {
            estado: "Cancelada"
          });
          console.log(JSON.stringify(resposta.data));
          this.cancelar = false;
          this.text = "Desparasitação confirmada com sucesso.";
          this.color = "success";
          this.snackbar = true;
        } catch (e) {
          console.log("erro: " + e);
          this.cancelar = false;
          this.text = "Ocorreu um erro, por favor tente mais tarde!";
          this.color = "warning";
          this.snackbar = true;
        }
		
    },*/
  },
  created: async function () {
    try {
      var response = await axios.post(
        "http://localhost:7777/cliente/cirurgias",
        {
          cliente: this.$store.state.email,
        },
        {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        }
      );
    } catch (e) {
      console.log("erro: +" + e);
    }

    for (var i = 0; i < response.data.length; i++) {
      this.cirurgias.push({
        data: response.data[i].data,
        animal: response.data[i].animal.nome,
        medico: response.data[i].veterinario.nome,
        descricao: response.data[i].descricao,
        estado: response.data[i].estado,
      });
    }
  },
  computed: {
    filteredData() {
      let motivo = this.motivos;
      return this.desc.filter((item) => item.tipo === motivo);
    },
  },
};
</script>