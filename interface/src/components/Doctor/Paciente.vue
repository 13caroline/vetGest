<template>
  <div>
    <v-container>
      <v-row class="mt-2">
        <v-col cols="auto">
          <span class="subtitle-1 head">{{ dados.nome }}</span>
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

        <v-tab-item>
          <Historico :animal="dados"></Historico>
        </v-tab-item>
      </v-tabs-items>
    </v-container>
  </div>
</template>

<script>
import PacienteVacinas from "@/components/Utente/Vacinas.vue";
import Consultas from "@/components/Utente/Consultas.vue";
import Cirurgia from "@/components/Utente/Cirurgias.vue";
import Historico from "@/components/Utente/Historico.vue";
import Dados from "@/components/Utente/Dados.vue";
import axios from "axios";
import store from "@/store.js";

export default {
  props: ["id"],
  data: () => ({
    tab: null,
    dados: "",
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
  },
  components: {
    PacienteVacinas,
    Consultas,
    Cirurgia,
    Dados,

    Historico,
  },
  created: async function () {
    try {
      let response = await axios.post(
        "http://localhost:7777/medico/utente",
        {
          id: this.id,
        },
        { headers: { Authorization: "Bearer " + store.getters.token } }
      );
      console.log(this.dados)
      if (response) {
        this.dados = response.data;
        this.dados.image = this.dados.image
          ? "data:image/jpeg;charset=utf-8;base64," + this.dados.image
          : require("@/assets/image_placeholder.png");
      }
    } catch (e) {
      this.$snackbar.showMessage({
        show: true,
        color: "error",
        text: "Ocorreu um erro. Por favor tente mais tarde!",
        timeout: 4000,
      });
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
