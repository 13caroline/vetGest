<template>
  <div>
    <v-container>
      <v-row justify="space-around" class="mt-2">
        <v-col cols="auto">
          <span class="subtitle-1 head">Rubi</span>
        </v-col>
        <v-col cols="auto" class="ml-auto">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2 mb-2"
                small
                color="#2596be"
                v-bind="attrs"
                v-on="on"
                fab
                dark
                @click="internamento = true"
              >
                <v-icon small>fas fa-procedures</v-icon>
              </v-btn>
            </template>
            <span>Admitir em internamento</span>
          </v-tooltip>

          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2 ml-2 mb-2"
                small
                color="#2596be"
                v-bind="attrs"
                v-on="on"
                fab
                dark
                :to="'/medico/utente/editar/' + id"
              >
                <v-icon small>fas fa-pen</v-icon>
              </v-btn>
            </template>
            <span>Editar dados</span>
          </v-tooltip>
        </v-col>
      </v-row>
      <v-divider></v-divider>

      <v-tabs v-model="tab" color="#2596be" background-color="#fafafa">
        <v-tab v-for="item in items" :key="item.tab">
          {{ item.tab }}
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <v-tab-item>
          <Dados :animal="dados"></Dados>
        </v-tab-item>

        <v-tab-item>
          <PacienteVacinas :animal="dados"></PacienteVacinas>
        </v-tab-item>

        <v-tab-item>
          <Consultas :animal="dados"></Consultas>
        </v-tab-item>

        <v-tab-item>
          <Cirurgia :animal="dados"></Cirurgia>
        </v-tab-item>
      </v-tabs-items>

      <v-dialog v-model="internamento" width="100%" max-width="500" persistent>
        <v-card>
          <v-form>
            <v-card-title class="font-weight-regular text-uppercase">
              Admitir em internamento
            </v-card-title>
            <v-card-subtitle
              >Por favor preencha o seguinte formulário</v-card-subtitle
            >
            <v-card-text>
              <v-row align="center">
                <v-col cols="12" class="pb-0">
                  <p class="ma-0">Nota de admissão</p>
                  <v-textarea
                    color="#2596be"
                    flat
                    outlined
                    rows="5"
                    no-resize
                    v-model="nota"
                  ></v-textarea>
                </v-col>

                <v-col cols="12" class="py-0">
                  <p class="ma-0">Motivo de internamento</p>
                  <v-textarea
                    no-resize
                    dense
                    color="#2596be"
                    flat
                    outlined
                    rows="2"
                    v-model="motivo"
                  ></v-textarea>
                </v-col>
              </v-row>

              <v-row align="end" justify="end">
                <v-col cols="auto">
                  <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
                </v-col>
                <v-col cols="auto">
                  <v-btn color="#2596be" small dark>Admitir</v-btn>
                </v-col>
              </v-row>
            </v-card-text>
          </v-form>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
import PacienteVacinas from "@/components/Utente/Vacinas.vue";
import Consultas from "@/components/Utente/Consultas.vue";
import Cirurgia from "@/components/Utente/Cirurgias.vue";
import Dados from "@/components/Utente/Dados.vue";
import Cancelar from "@/components/Dialogs/Cancel.vue";
import axios from "axios";

export default {
  props: ["id"],
  data: () => ({
    tab: null,
    motivo: "",
    internamento: false,
    dados: "",
    nota: "",
    items: [
      { tab: "Informações gerais" },
      { tab: "Vacinas e Desparasitações" },
      { tab: "Consultas" },
      { tab: "Cirurgias" },
      { tab: "Histórico Clínico" },
    ],
    cancelar: {
      title: "edição de dados",
      text: "a edição dos dados",
      route: "/medico/utente",
    },
  }),
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else return "#FFE082";
    },
    more(item) {
      console.log(item.data);
    },
    close() {
      this.internamento = false;
    },
  },
  components: {
    PacienteVacinas,
    Consultas,
    Cirurgia,
    Dados,
    Cancelar,
  },
  created: async function () {
    try {
      let response = await axios.post("http://localhost:7777/clinica/utente", {
        id: this.id,
      });
      this.dados = response.data;
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style scoped>
.foto {
  width: 200px;
}

.body-2 {
  font-size: 0.8rem !important;
}

.head {
  font-size: 2.75rem !important;
}

.font-weight-bold {
  width: 40%;
  font-size: 15px;
}

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}
</style>
