<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row class="w-100">
            <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
              <v-icon class="mr-2">fas fa-scroll</v-icon>
              Agendamento de Consultas
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
              max-width="500px"
            >
              <v-card color="grey lighten-4" max-width="500px" flat>
                <v-card-text>
                  <v-row>
                    <v-col class="pb-0" align="right" cols="5">
                      <span class="text-uppercase">Nome do Animal</span>
                    </v-col>
                    <v-col class="pl-0 pb-0" cols="7">
                      <span class="black--text">
                        <strong>{{ selectedEvent.utente }}</strong>
                        ({{ selectedEvent.raca }})
                      </span>
                    </v-col>

                    <v-col class="pb-0" align="right" cols="5">
                      <span class="text-uppercase">Motivo da consulta</span>
                    </v-col>
                    <v-col class="pl-0 pb-0" cols="7">
                      <span class="black--text">
                        <strong>{{ selectedEvent.details }}</strong>
                      </span>
                      <br />
                      <span>{{ selectedEvent.desc }}</span>
                    </v-col>

                    <v-col class="pb-0" align="right" cols="5">
                      <span class="text-uppercase">Data</span>
                    </v-col>
                    <v-col class="pl-0 pb-0" cols="7">
                      <span class="black--text">
                        <strong
                          >{{ format(selectedEvent.start) }} -
                          {{ format(selectedEvent.end) }}</strong
                        >
                      </span>
                    </v-col>

                    <v-col class="pb-0" align="right" cols="5">
                      <span class="text-uppercase">Médico</span>
                    </v-col>
                    <v-col class="pl-0 pb-0" cols="7">
                      <span class="black--text">
                        <strong>Dr.º {{ selectedEvent.vet }}</strong>
                      </span>
                    </v-col>

                    <v-col class="pb-0" align="right" cols="5">
                      <span class="text-uppercase">estado</span>
                    </v-col>
                    <v-col class="pl-0 pb-0" cols="7">
                      <v-chip :color="colors" small>
                        {{ selectedEvent.state }}
                      </v-chip>
                    </v-col>
                  </v-row>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-tooltip top v-if="selectedEvent.state == 'Pendente'">
                    <template v-slot:activator="{ on, attrs }">
                      <v-icon
                        color="#28a428"
                        v-bind="attrs"
                        v-on="{ on }"
                        @click="confirmar('Agendada')"
                      >
                        clipboard-check
                      </v-icon>
                    </template>
                    <span class="caption">Confirmar consulta</span>
                  </v-tooltip>
                  <v-tooltip top v-if="selectedEvent.state == 'Agendada'">
                    <template v-slot:activator="{ on, attrs }">
                      <v-icon
                        color="#66BB6A"
                        v-bind="attrs"
                        v-on="{ on }"
                        @click="confirmar('A decorrer')"
                      >
                        mdi-calendar-check
                      </v-icon>
                    </template>
                    <span class="caption">Admitir utente</span>
                  </v-tooltip>

                  <v-tooltip
                    top
                    v-if="
                      selectedEvent.state == 'Agendada' ||
                      selectedEvent.state == 'Pendente'
                    "
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-icon
                        color="#E57373"
                        v-bind="attrs"
                        v-on="{ on }"
                        @click="confirmar('Cancelada')"
                      >
                        mdi-calendar-remove
                      </v-icon>
                    </template>
                    <span class="caption">Cancelar agendamento</span>
                  </v-tooltip>
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
    state: null,
    colors: null,
    vet: null,
    desc: null,
    currentlyEditing: null,
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
  }),
  mounted() {
    //this.getEvents();
    this.$refs.calendar.checkChange();
  },
  methods: {
    //get events
    getEvents() {},
    medicoAgenda: async function (value) {
      this.events = [];
      let response2 = await axios.post(
        "http://localhost:7777/clinica/intervencoes/medico",
        {
          tipo: "Consulta",
          veterinario: value,
        },
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );
      for (var i = 0; i < response2.data.length; i++) {
        if (response2.data[i].estado != "Cancelada") {
          var marcacao = moment(
            response2.data[i].data + " " + response2.data[i].hora,
            "YYYY-MM-DD HH:mm",
            true
          )
            .locale("pt")
            .format("YYYY-MM-DD HH:mm");
          var final = moment(marcacao)
            .add(15, "minutes")
            .locale("pt")
            .format("YYYY-MM-DD HH:mm");
          this.events.push({
            name: response2.data[i].tipo,
            start: marcacao,
            end: final,
            details: response2.data[i].motivo,
            state: response2.data[i].estado,
            vet: response2.data[i].veterinario.nome,
            desc: response2.data[i].descricao,
            utente: response2.data[i].animal.nome,
            raca: response2.data[i].animal.raca,
          });
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
        this.state = "";
        this.vet = "";
        this.desc = "";
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
      if (event.state.includes("Pendente")) return "#FAB471";
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
        this.colors = this.getEventColor(event);
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
      this.atualiza();
      this.$snackbar.showMessage({
        show: true,
        color: value.color,
        text: value.text,
        timeout: 4000,
      });
    },
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    confirmar: async function (estado) {
      try {
        if (store.state.tipo == "Clinica") {
          await axios.post(
            "http://localhost:7777/clinica/intervencao/alterar",
            {
              id: this.selectedEvent.id,
              estado: estado,
            },
            {
              headers: { Authorization: "Bearer " + store.getters.token },
            }
          );
        }
        this.selectedOpen = false;
        this.atualiza();
        let text = "";
        if (estado == "A decorrer") {
          text = "Utente admitido com sucesso.";
        } else {
          text = "Consulta cancelada com sucesso.";
        }

        this.$snackbar.showMessage({
          show: true,
          color: "success",
          text: text,
          timeout: 4000,
        });
      } catch (e) {
        this.selectedOpen = false;
        this.$snackbar.showMessage({
          show: true,
          color: "warning",
          text: "Ocorreu um erro, por favor tente mais tarde!",
          timeout: 4000,
        });
      }
    },
    atualiza: async function () {
      try {
        this.events = [];
        let response2 = await axios.post(
          "http://localhost:7777/clinica/intervencoes",
          {
            tipo: "Consulta",
          },
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );
        for (var i = 0; i < response2.data.length; i++) {
          if (response2.data[i].estado != "Cancelada") {
            var marcacao = moment(
              response2.data[i].data + " " + response2.data[i].hora,
              "YYYY-MM-DD HH:mm",
              true
            )
              .locale("pt")
              .format("YYYY-MM-DD HH:mm");
            var final = moment(marcacao)
              .add(15, "minutes")
              .locale("pt")
              .format("YYYY-MM-DD HH:mm");
            this.events.push({
              id: response2.data[i].id,
              name: response2.data[i].tipo,
              start: marcacao,
              end: final,
              details: response2.data[i].motivo,
              state: response2.data[i].estado,
              vet: response2.data[i].veterinario.nome,
              desc: response2.data[i].descricao,
              utente: response2.data[i].animal.nome,
              raca: response2.data[i].animal.raca,
            });
          }
        }
      } catch (e) {
        console.log(e);
      }
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
        this.medico.push({
          nome: response.data[i].nome,
          id: response.data[i].id,
        });
      let response2 = await axios.post(
        "http://localhost:7777/clinica/intervencoes",
        {
          tipo: "Consulta",
        },
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );
      for (i = 0; i < response2.data.length; i++) {
        if (response2.data[i].estado != "Cancelada") {
          var marcacao = moment(
            response2.data[i].data + " " + response2.data[i].hora,
            "YYYY-MM-DD HH:mm",
            true
          )
            .locale("pt")
            .format("YYYY-MM-DD HH:mm");
          var final = moment(marcacao)
            .add(15, "minutes")
            .locale("pt")
            .format("YYYY-MM-DD HH:mm");
          this.events.push({
            id: response2.data[i].id,
            name: response2.data[i].tipo,
            start: marcacao,
            end: final,
            details: response2.data[i].motivo,
            state: response2.data[i].estado,
            vet: response2.data[i].veterinario.nome,
            desc: response2.data[i].descricao,
            utente: response2.data[i].animal.nome,
            raca: response2.data[i].animal.raca,
          });
        }
      }
    } catch (e) {
      console.log(e);
    }
  },
};
</script>