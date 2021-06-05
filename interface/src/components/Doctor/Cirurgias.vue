<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row class="w-100">
            <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
              <v-icon class="mr-2">fas fa-band-aid</v-icon>
              Marcação de Cirurgias
            </h3>
            <v-row justify="end">
               <v-col cols="auto">
                <MarcarCirurgiaLivre></MarcarCirurgiaLivre>
              </v-col>
            </v-row>
          </v-row>

          <v-sheet class="mt-4">
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
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import MarcarCirurgiaLivre from "@/components/Dialogs/MarcarCirurgiaLivre.vue"

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
    medico: ["Drº José Vieira", "Drª Joana Ferreira"],
    search: "",
    colors: ["orange"],
  }),
  mounted() {
    //this.getEvents();
    this.$refs.calendar.checkChange();
  },
  components:{
    MarcarCirurgiaLivre
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
};
</script>