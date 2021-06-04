<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-syringe</v-icon>
                Vacinas e desparasitações
              </h3>
            </v-col>
            <v-col cols="auto">
              <v-btn
                class="mb-2 mr-2 body-2"
                small
                color="#2596be"
                dark
                @click="vacinacao = true"
              >
                Adicionar Vacinação
              </v-btn>
              <v-btn
                class="mb-2 body-2"
                small
                color="#2596be"
                dark
                @click="desparasitacao = true"
              >
                Adicionar Desparasitação
              </v-btn>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="items"
            class="elevation-1"
            hide-default-footer
          >
            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estadopedido(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:[`item.confirmar`]="{ item }">
              <v-icon v-if="item.estado == 'Administrada'" small color="green">
                fas fa-check-circle
              </v-icon>
              <v-btn
                v-if="item.estado == 'Atualizada' || item.estado == 'Atrasada'"
                small
                outlined
                rounded
                color="#2596be"
                @click="openDialog(item)"
              >
                Confirmar
              </v-btn>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
      <v-dialog v-model="dialog" width="100%" max-width="700">
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
                dense
                readonly
                :value="dataPrevista"
                append-outer-icon="fas fa-calendar-alt"
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
                <v-btn color="#BDBDBD" small dark @click="dialogCancel = true">
                  Cancelar
                </v-btn>
                <v-btn
                  color="#2596be"
                  small
                  dark
                  class="ml-3"
                  @click="confirma()"
                >
                  Confirmar
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogCancel" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="justify-center cancel">
            Cancelar confirmação de desparasitação
          </v-card-title>
          <v-card-text>
            Tem a certeza que pretende cancelar a confirmação de desparasitação?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="dialogCancel = false"
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

      <v-dialog v-model="vacinacao" width="100%" max-width="700">
        <v-card>
          <v-card-title class="headline mb-6">
            Adicionar vacinação
          </v-card-title>
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
                <v-btn color="#BDBDBD" small dark @click="dialogCancel = true">
                  Cancelar
                </v-btn>
                <v-btn
                  color="#2596be"
                  small
                  dark
                  class="ml-3"
                  @click="adicionaVacina()"
                >
                  Confirmar
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogCancelVac" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="justify-center cancel">
            Cancelar vacinação
          </v-card-title>
          <v-card-text>
            Tem a certeza que pretende cancelar o registo de vacinação?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="dialogCancelVac = false"
            >
              Não
            </v-btn>
            <v-btn
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              @click="cancelarVac()"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="desparasitacao" width="100%" max-width="700">
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
                v-model="proxToma"
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
                v-model="observacoes"
              ></v-textarea>
            </div>
            <v-row align="end" justify="end">
              <v-col cols="auto">
                <v-btn color="#BDBDBD" small dark @click="dialogCancelDes = true">
                  Cancelar
                </v-btn>
                <v-btn
                  color="#2596be"
                  small
                  dark
                  class="ml-3"
                  @click="adicionaDesparasita()"
                >
                  Confirmar
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogCancelDes" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="justify-center cancel">
            Cancelar registo de desparasitação
          </v-card-title>
          <v-card-text>
            Tem a certeza que pretende cancelar o registo de desparasitação?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="dialogCancelDes = false"
            >
              Não
            </v-btn>
            <v-btn
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              @click="cancelarDes()"
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
export default {
  data: () => ({
    dialog: false,
    dialogCancel: false,
    dialogCancelVac: false,
    dialogCancelDes: false,
    vacinacao: false,
    desparasitacao: false,

    enabled: false,
    vaccontra: "",
    vaccontraOutro: "",
    date1: new Date().toISOString().substr(0, 10),
    dateProx: new Date().toISOString().substr(0, 10),
    dateToma: new Date().toISOString().substr(0, 10),
    dateVac: new Date().toISOString().substr(0, 10),
    dataPrevista: "", 
    menu: false,
    menu1: false,
    menu3: false,
    menu4: false,
    tratamento: "",
    observacoes: "",
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",

    headers: [
      {
        text: "Data Prevista",
        align: "start",
        sortable: true,
        value: "dataPrev",
      },
      {
        text: "Data de Toma",
        value: "dataToma",
        sortable: true,
        align: "start",
      },
      {
        text: "Tipo",
        value: "tipo",
        sortable: true,
        align: "start",
      },
      {
        text: "Tratamento Utilizado",
        value: "tratamento",
        sortable: true,
        align: "start",
      },
      {
        text: "Médico Veterinário",
        value: "medico",
        sortable: false,
        align: "center",
      },
      {
        text: "Estado",
        value: "estado",
        sortable: true,
        align: "center",
      },
      {
        text: "Confirmar Administração",
        value: "confirmar",
        sortable: false,
        align: "center",
      },
    ],
    items: [
      {
        dataPrev: "05/04/2021",
        dataToma: "06/04/2021",
        tipo: "Desparasitação",
        tratamento: "Bravacto",
        medico: "Drº José Vieira",
        estado: "Administrada",
      },
      {
        dataPrev: "05/05/2021",
        tipo: "Desparasitação",
        tratamento: "Bravacto",
        estado: "Atualizada",
      },
      {
        dataPrev: "05/04/2021",
        dataToma: "05/04/2021",
        tipo: "Vacina",
        tratamento: "Rabis",
        medico: "Drº José Vieira",
        estado: "Administrada",
      },
      {
        dataPrev: "05/04/2021",
        tipo: "Desparasitação",
        estado: "Atrasada",
      },
    ],
    contra: [
      "Esgana",
      "Hepatite",
      "Leptospirose",
      "Parvovirose",
      "Tosse de canil",
      "Piroplasmose",
      "Outro",
    ],
    tomas: ["1 mês", "3 meses"],
  }),
  methods: {
    estadopedido(estado) {
      if (estado == "Administrada") return "#C5E1A5";
      else if (estado == "Atrasada") return "#EF9A9A";
      else return "#FFE082";
    },
    more(item) {
      console.log(item.data);
    },
    openDialog(item) {
      this.dataPrevista = item.dataPrev;

      this.dialog = true;
    },
    cancelar() {
      this.dialogCancel = false;
      this.dialog = false;
    },
    cancelarVac() {
      this.dialogCancelVac = false;
      this.vacinacao = false;
    },
    cancelarDes() {
      this.dialogCancelDes = false;
      this.desparasitacao = false;
    },
    confirma: async function () {
      /*
      try {
          var resposta = await axios.post("http://localhost:7777/cliente/confirmaDesparasitacao", {
            idVacina: this.idVacina @TODO Adicionar idVacina
            dataToma: this.dataToma,
            tratamento: this.tratamento,
          });
          console.log(JSON.stringify(resposta.data));
          this.dialog = false;
          this.text = "Desparasitação confirmada com sucesso.";
          this.color = "success";
          this.snackbar = true;
        } catch (e) {
          console.log("erro: " + e);
          this.dialog = false;
          this.text = "Ocorreu um erro, por favor tente mais tarde!";
          this.color = "warning";
          this.snackbar = true;
        }
      */
    },
    adicionaVacina: async function () {},
    adicionaDesparasita: async function () {},
  },
  created() {
    /*
    let response = await axios.post("http://localhost:7777/cliente/getVacinas", {
      email: this.$store.state.user.email,
      animal
    });

    */
  },
};
</script>