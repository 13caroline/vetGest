<template>
  <div>
    <v-container class="fill-height">
      <v-row justify="center">
        <v-col cols="12" md="4">
          <v-card class="mt-8" flat color="transparent">
            <v-card-title class="justify-center"
              >Agendar Consulta Online</v-card-title
            >
            <v-card-text class="justify"
              >Por favor indique os seus dados e do seu animal de estimação bem
              como a sua preferência para a marcação da consulta. Entraremos em
              contacto consigo no prazo máximo de 24h para confirmação da
              consulta.</v-card-text
            >
          </v-card>
        </v-col>

        <v-col>
          <v-card class="mt-10" flat color="transparent">
            <div>
              <p class="ma-0">Nome</p>
              <v-autocomplete
                flat
                color="#2596be"
                dense
                outlined
                :items="animais"
              ></v-autocomplete>
            </div>

            <div>
              <p class="ma-0">Motivo</p>
              <v-textarea
                outlined
                color="#2596be"
                rows="2"
                clearable
                clear-icon="fas fa-times-circle"
                no-resize
              ></v-textarea>
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
                :return-value.sync="dataFim"
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
                v-model="medicoVet"
              ></v-autocomplete>
            </div>

            <v-row align="end" justify="end">
              <v-col cols="auto">
                <v-btn color="#BDBDBD" small dark @click="dialog = true"
                  >Cancelar</v-btn
                >
                <v-btn color="#2596be" small dark class="ml-3">Agendar</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog v-model="dialog" persistent width="30%">
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
            to="/cliente/home"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
//import moment from 'moment';
export default {
  data: () => ({
    dialog: false,
    dataMarcacao: null,
    horaMarcacao: null,
    date: new Date().toISOString().substr(0, 10),
    hora: new Date().getHours() + ":" + new Date().getMinutes(),
    animais: ["Rubi", "Puscas", "Nikita", "Rudi"],
    medico: ["Sem Preferência", "Drº José Vieira", "Drª Joana Ferreira"],
    medicoVet: 'Sem Preferência',
  }),
};
</script>

<style scoped>
.justify {
  text-align: center;
  text-justify: inter-character;
}
</style>
