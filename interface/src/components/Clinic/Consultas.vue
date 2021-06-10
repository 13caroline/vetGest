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
                <MarcarConsultaLivre @clicked="registar"></MarcarConsultaLivre>
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      class="body-2 mt-8 ml-2"
                      small
                      color="#2596be"
                      v-bind="attrs"
                      v-on="on"
                      fab
                      dark
                      to="/clinica/consultas/pedidos"
                    >
                      <v-icon small>fas fa-book-medical</v-icon>
                    </v-btn>
                  </template>
                  <span class="caption">Pedidos de consulta</span>
                </v-tooltip>
              </v-col>
            </v-row>
          </v-row>

          <v-select
            v-model="search"
            append-icon="mdi-magnify"
            label="Médico Veterinário"
            outlined
            dense
            class="my-4"
            color="#2596be"
            :items="medico"
            item-text="nome"
            item-value="id"
            hide-details
            @change="medicoAgenda"
          ></v-select>

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
              first-time="10:00"
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
        </v-col>
      </v-row>
    </v-container>
    <v-snackbar
      v-model="snackbar"
      :timeout="timeout"
      :color="color"
      :top="true"
      class="headline"
    >
      {{ text }}
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
import moment from "moment";
import MarcarConsultaLivre from "@/components/Dialogs/MarcarConsultaLivre.vue";
export default {
  data: () => ({
    search: "",
    focus: new Date().toISOString().substr(0, 10),
    today: new Date().toISOString().substr(0, 10),
    type: "month",
    weekday: [1, 2, 3, 4, 5, 6, 0],
    typeToLabel: {
      month: "Mês",
      week: "Semana",
      day: "Dia",
    },
    medico: [],
    name: null,
    details: null,
    start: null,
    end: null,
    state:null,
    currentlyEditing: null,
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    snackbar: false,
    color: "",
    text: "",
    timeout: -1,
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
    medicoAgenda: async function(value){
      this.events = [];
      let response2 = await axios.post("http://localhost:7777/clinica/intervencoes/medico", {
        tipo: "Consulta",
        veterinario: value,
      },
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      console.log(response2)
      for (var i = 0; i < response2.data.length; i++){
        if(response2.data[i].estado != "Cancelada"){
          var marcacao = moment(response2.data[i].data + " " + response2.data[i].hora, "YYYY-MM-DD HH:mm", true).locale("pt").format("YYYY-MM-DD HH:mm");
          var final = moment(marcacao).add(15, 'minutes').locale("pt").format("YYYY-MM-DD HH:mm")
          this.events.push({
            name: response2.data[i].tipo,
            start: marcacao,
            end: final, 
            details: response2.data[i].motivo,
            state: response2.data[i].estado
          })
        }
      }
    },
    // adicionar evento
    addEvent() {
      if (this.name && this.start && this.end && this.details) {
        this.getEvents();
        this.name = "";
        this.details = "";
        this.start == "";
        this.end = "";
        this.state= "";
      } else {
        //show error
      }
    },

    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    getEventColor(event) {
      if (event.state.includes("Agendada")) return "#ACD47F";
      if (event.state.includes("A decorrer")) return "#FFDF80";
      if (event.state.includes("Pendente")) return "#FAB471"
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
    registar(value) {
      this.snackbar = value.snackbar;
      this.color = value.color;
      this.text = value.text;
      this.timeout = value.timeout;
    },
  },
  components: {
    MarcarConsultaLivre,
  },
  created: async function () {
    try {
      let response = await axios.get("http://localhost:7777/clinica/medicos", {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      for (var i = 0; i < response.data.length; i++)
        this.medico.push({nome: response.data[i].nome, id: response.data[i].id});
      let response2 = await axios.post("http://localhost:7777/clinica/intervencoes", {
        tipo: "Consulta"
      },
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      console.log(response2)
      for (i = 0; i < response2.data.length; i++){
        if(response2.data[i].estado != "Cancelada"){
          var marcacao = moment(response2.data[i].data + " " + response2.data[i].hora, "YYYY-MM-DD HH:mm", true).locale("pt").format("YYYY-MM-DD HH:mm");
          var final = moment(marcacao).add(15, 'minutes').locale("pt").format("YYYY-MM-DD HH:mm")
          this.events.push({
            name: response2.data[i].tipo,
            start: marcacao,
            end: final, 
            details: response2.data[i].motivo,
            state: response2.data[i].estado
          })
        }
      }

    } catch (e) {
      console.log(e);
    }
  },
};
</script>