<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row class="w-100">
            <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
              <v-icon class="mr-2">fas fa-scroll</v-icon>
              Marcação de Consultas
            </h3>
            <v-row justify="end">
              <v-col cols="auto">
                <v-btn
                  class="mb-2 mt-6 body-2"
                  small
                  color="#2596be"
                  dark
                  @click="dialog = true"
                >
                  Marcar nova consulta
                </v-btn>
              </v-col>
            </v-row>
          </v-row>

          <v-sheet>
            <v-toolbar flat>
              <v-btn outlined class="mr-4" color="#2596be" @click="setToday">
                Hoje
              </v-btn>
              <v-btn fab text small color="grey darken-2" @click="prev">
                <v-icon small> mdi-chevron-left </v-icon>
              </v-btn>
              <v-btn fab text small color="grey darken-2" @click="next">
                <v-icon small> mdi-chevron-right </v-icon>
              </v-btn>
              <v-toolbar-title v-if="$refs.calendar">
                {{ $refs.calendar.title }}
              </v-toolbar-title>
              <v-spacer></v-spacer>
              <v-menu bottom right>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn outlined color="#2596be" v-bind="attrs" v-on="on">
                    <span>{{ typeToLabel[type] }}</span>
                    <v-icon right> mdi-menu-down </v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item @click="type = 'day'">
                    <v-list-item-title>Dia</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'week'">
                    <v-list-item-title>Semana</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'month'">
                    <v-list-item-title>Mês</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-toolbar>
          </v-sheet>
          <v-sheet height="600">
            <v-calendar
              ref="calendar"
              v-model="focus"
              color="#2596be"
              :weekdays="weekday"
              :events="events"
              :event-color="getEventColor"
              :type="type"
              locale="pt"
              first-time="08:00"
              interval-count="12"
              @click:event="showEvent"
              @click:more="viewDay"
              @click:date="viewDay"
              @change="updateRange"
            ></v-calendar>
            <v-menu
              v-model="selectedOpen"
              :close-on-content-click="false"
              :activator="selectedElement"
              offset-x
            >
              <v-card color="grey lighten-4" min-width="350px" flat>
                <v-toolbar :color="selectedEvent.color" dark>
                  <v-btn @click="deleteEvent(selectedEvent.id)" icon>
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>
                  <v-toolbar-title
                    v-html="selectedEvent.name"
                  ></v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-text>
                  <form v-if="currentlyEditing !== selectedEvent.id">
                    {{ selectedEvent.details }}
                  </form>
                  <form v-else>
                    <textarea
                      v-model="selectedEvent.details"
                      type="text"
                      style="width: 100%"
                      :min-height="100"
                      placeholder="add note"
                    ></textarea>
                  </form>
                </v-card-text>
                <v-card-actions>
                  <v-btn text color="secondary" @click="selectedOpen = false">
                    Fechar
                  </v-btn>

                  <v-btn
                    text
                    v-if="currentlyEditing !== selectedEvent.id"
                    @click.prevent="editEvent(selectedEvent)"
                  >
                    Editar
                  </v-btn>
                  <v-btn
                    text
                    v-else
                    @click.prevent="updateEvent(selectedEvent)"
                  >
                    Guardar
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </v-sheet>

          <v-dialog v-model="dialog" width="40%">
            <v-card>
              <v-form>
                <v-card-title class="font-weight-regular text-uppercase">
                  Marcar uma nova consulta
                </v-card-title>
                <v-card-subtitle
                  >Por favor preencha o seguinte formulário</v-card-subtitle
                >

                <v-card-text>
                  <div>
                    <v-row align="center">
                      <v-col cols="10">
                        <p class="ma-0">Utente</p>
                        <v-text-field
                          color="#2596be"
                          flat
                          outlined
                          dense
                          v-model="nomeSelected"
                        ></v-text-field>
                      </v-col>

                      <v-col>
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
                    </v-row>
                  </div>

                  <div>
                    <v-col>
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
                  </div>

                  <div>
                    <v-col>
                      <p class="ma-0">Descrição</p>
                      <v-select
                        :items="filteredData"
                        dense
                        color="#2596be"
                        flat
                        outlined
                        v-model="descricao"
                        item-text ="text"
                        item-value = "text"
                      ></v-select>
                    </v-col>
                  </div>

                  <div>
                    <p class="ma-0">Data</p>
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
                  </div>

                  <div>
                    <p class="ma-0">Hora</p>
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
                  </div>

                  <div>
                    <p class="ma-0">Médico Veterinário</p>
                    <v-autocomplete
                      flat
                      color="#2596be"
                      dense
                      outlined
                      :items="medico"
                      v-model="medico"
                    ></v-autocomplete>
                  </div>

                  <v-row align="end" justify="end">
                    <v-btn color="#BDBDBD" small dark @click="cancelDialog = true"
                      >Cancelar</v-btn
                    >
                    <v-btn color="#2596be" small dark class="ml-3"
                      >Registar</v-btn
                    >
                  </v-row>
                </v-card-text>
              </v-form>
            </v-card>
          </v-dialog>

          <v-dialog v-model="dialogUtente" max-width="700">
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
              ></v-data-table>
            </v-card>
          </v-dialog>

          <v-dialog v-model="cancelDialog" persistent width="30%">
      <v-card>
        <v-card-title class="justify-center cancel">
          Cancelar Agendamento da Consulta
        </v-card-title>
        <v-card-text>
          Tem a certeza que pretende cancelar o agendamento da consulta?
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
            to="/clinica/consultas"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    focus: new Date().toISOString().substr(0, 10),
    today: new Date().toISOString().substr(0, 10),
    type: "month",
    weekday: [1, 2, 3, 4, 5, 6, 0],
    typeToLabel: {
      month: "Mês",
      week: "Semana",
      day: "Dia",
    },
    name: null,
    details: null,
    start: null,
    end: null,
    currentlyEditing: null,
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [
      {
        name: "Consulta",
        start: "2021-05-21 15:00",
        end: "2021-05-21 15:20",
        details: "Consulta de Rotina Rubi",
      },
      {
        name: "Consulta",
        start: "2021-05-05 11:00",
        end: "2021-05-05 11:30",
        details: "Consulta de Rotina Rubi",
      },
      {
        name: "Cirurgia",
        start: "2021-05-21 10:00",
        end: "2021-05-21 16:20",
        details: "Cirurgia de Rotina Rubi",
      },
    ],
    dialog: false,
    dialogUtente: false,
    cancelDialog: false,
    dataMarcacao: null,
    horaMarcacao: null,
    nomeSelected: "",
    descricao: "", 
    motivos: "", 
    date: new Date().toISOString().substr(0, 10),
    hora: new Date().getHours() + ":" + new Date().getMinutes(),
    medico: ["Drº José Vieira", "Drª Joana Ferreira"],
    motivo: [
      "Consulta anual/Vacinação",
      "Consulta extraordinária/Por doença",
      "Consulta de seguimento",
      "Procedimentos específicos",
    ],
    desc: [
      { text: "Consulta anual/Vacinação", tipo: "Consulta anual/Vacinação"},
      { text: "Vómitos/Diarreia/Recusa em comer", tipo: "Consulta extraordinária/Por doença" },
      { text: "Comportamento letárgico", tipo: "Consulta extraordinária/Por doença" },
      { text: "Alterações da marcha", tipo: "Consulta extraordinária/Por doença"},
      { text: "Problema de olhos ou ouvidos", tipo: "Consulta extraordinária/Por doença"},
      { text: "Problemas de dentes ou boca", tipo: "Consulta extraordinária/Por doença"},
      { text: "Problemas cutâneos", tipo: "Consulta extraordinária/Por doença"},
      { text: "Problemas urinários", tipo: "Consulta extraordinária/Por doença"},
      { text: "Outros", tipo: "Consulta extraordinária/Por doença"},
      { text: "Consulta de seguimento", tipo:"Consulta de seguimento"},
      { text: "Cortar unhas", tipo:"Procedimentos específicos" },
      { text: "Expressão de glândulas anais", tipo:"Procedimentos específicos" },
      { text: "Análises", tipo:"Procedimentos específicos" },
      { text: "Limpeza de ouvidos", tipo:"Procedimentos específicos" },
      { text: "Cortar o pêlo", tipo:"Procedimentos específicos" },
      { text: "Lavagem", tipo:"Procedimentos específicos" },
      { text: "Desparasitação", tipo:"Procedimentos específicos" },
      { text: "Outros", tipo:"Procedimentos específicos" },
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
    items: [
        {
          nome:"Rubi",
          cliente:"Carolina",
          raca:"Serra da Estrela",
          sexo:"Macho",
        },
        {
          nome:"Runa",
          cliente:"Carolina",
          raca:"Serra da Estrela",
          sexo:"Fêmea",
        },
      ],
    colors: ["orange"],
  }),
  mounted() {
    //this.getEvents();
    this.$refs.calendar.checkChange();
  },
  methods: {
    //get events
    getEvents() {},
    // atualizar evento
    updateEvents(ev) {
      console.log(ev);
      this.selectedOpen = false;
      this.currentlyEditing = null;
    },
    // apagar evento
    deleteEvent(ev) {
      console.log(ev);
      this.selectedOpen = false;
      this.getEvents();
    },
    // adicionar evento
    addEvent() {
      if (this.name && this.start && this.end && this.details) {
        this.getEvents();
        this.name = "";
        this.details = "";
        this.start == "";
        this.end = "";
      } else {
        //show error
      }
    },
    handleClick(row) {
        this.nomeSelected=row.nome;
        this.dialogUtente=false;

    },
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    getEventColor(event) {
      if (event.name.includes("Consulta")) return "#00d4ff";
      else return "#ffc44d";
    },
    setToday() {
      this.focus = this.today;
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    editEvent(ev) {
      this.currentlyEditing = ev.id;
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        requestAnimationFrame(() =>
          requestAnimationFrame(() => (this.selectedOpen = true))
        );
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        requestAnimationFrame(() => requestAnimationFrame(() => open()));
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange({ start, end }) {
      this.start = start;
      this.end = end;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
  },
  computed: {
      filteredData(){
        let motivo = this.motivos;
          return this.desc.filter(item => item.tipo === motivo)
      }

  }
};
</script>