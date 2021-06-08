<template>
    <v-dialog v-model="dialog" width="100%" max-width="700" persistent>
      <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-btn
            color="#2596be"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            dark
            fab
            @click="dialog = true"
          >
            <v-icon small> fas fa-spider</v-icon>
          </v-btn>
        </template>
        <span class="caption">Adicionar desparasitação</span>
      </v-tooltip>
    </template>
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
              <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
            </v-col>
            <v-col cols="auto">
              <v-btn
                color="#2596be"
                small
                dark
                @click="adicionaDesparasita()"
              >
                Confirmar
              </v-btn>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
</template>

<script>
import axios from 'axios'
import Cancelar from "@/components/Dialogs/Cancel.vue";
import store from "@/store.js";


export default {
    props:["idAnimal"],
    

  data: () => ({
    dialog: false,
    dialogs: {},
    cancelar: {
      title: "registo de nova desparasitação",
      text: "o registo de uma nova desparasitação",
    },
    menu3: false,
    tratamento: "",
    proxToma: "",
    observacoes: "",
    dateToma: new Date().toISOString().substr(0, 10),
    tomas: ["1 mês", "3 meses"],
  }),
  components: {
    Cancelar,
  },
  methods: {
    close() {
      this.dialog = false;
    },
   adicionaDesparasita: async function() {
         try {
      if (store.state.tipo == "Clinica") {
      var response = await axios.post(
        "http://localhost:7777/cliente/animal/"+this.idAnimal+"/vacinas",
        {
          email: this.$store.state.email,
          data: this.data,
          hora: this.hora,
          motivo: this.motivo,
          proxImunizacao: this.proxImunizacao,
        },
        {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        }
      );
      console.log(response)
      }
    } catch (e) {
      console.log("erro: +" + e);
    }
    },
  },
};
</script>