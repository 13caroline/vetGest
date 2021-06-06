<template>
  <div>
    <v-dialog v-model="dialog" width="100%" max-width="700" persistent>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          v-bind="attrs"
          v-on="on"
          small
          outlined
          rounded
          color="#2596be"
        >
          Confirmar
        </v-btn>
      </template>
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
              :value="dataPrev"
              append-icon="fas fa-calendar-alt"
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
              <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
            </v-col>
            <v-col cols="auto">
              <v-btn
                color="#2596be"
                small
                dark
                @click="confirma()"
              >
                Confirmar
              </v-btn>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
// import axios from 'axios'
import Cancelar from "@/components/Dialogs/Cancel.vue"

export default {
  props:["dataPrev"],
  data: () => ({
      dialog: false, 
      dialogs: {},
      cancelar: {title: "confirmação de desparasitação", text: "a confirmação de desparasitação"},
      menu4: false, 
      tratamento: "", 
      date1: new Date().toISOString().substr(0, 10),
  }),
  components: {
      Cancelar
  },
  methods: {
      close() {
      this.dialog = false;
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
  }
}
</script>