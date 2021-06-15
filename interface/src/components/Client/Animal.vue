<template>
  <div>
    <v-container>
      <v-row justify="space-around" class="mt-2">
        <v-col>
          <span class="subtitle-1 head">{{ animal.nome }}</span>
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
          <Dados :animal="animal"></Dados>
        </v-tab-item>

        <v-tab-item>
          <PacienteVacinas :animal="animal"></PacienteVacinas>
        </v-tab-item>

        <v-tab-item>
          <Consultas :animal="animal"></Consultas>
        </v-tab-item>

        <v-tab-item>
          <Cirurgia :animal="animal"></Cirurgia>
        </v-tab-item>

        <v-tab-item>
          <Historico :animal="animal"></Historico>
        </v-tab-item>
      </v-tabs-items>
    </v-container>
  </div>
</template>

<script>
import PacienteVacinas from "@/components/Client/Animal/Vacinas.vue";
import Consultas from "@/components/Client/Animal/Consultas.vue";
import Cirurgia from "@/components/Client/Animal/Cirurgias.vue";
import Dados from "@/components/Client/Animal/Dados.vue";
import Historico from "@/components/Client/Animal/Historico.vue";
import axios from "axios";
import store from "@/store.js";

export default {
  props: ["id"],
  data: () => ({
    tab: null,
    items: [
      { tab: "Informações gerais" },
      { tab: "Vacinas e Desparasitações" },
      { tab: "Consultas" },
      { tab: "Cirurgias" },
      { tab: "Histórico Clínico" },
    ],
    animal: {},
  }),
  methods: {
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else return "#FFE082";
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
    let response = await axios.post(
      "http://localhost:7777/cliente/animal/" + this.id,
      {
        email: this.$store.state.email,
      },
      {
        headers: {
          Authorization: "Bearer " + store.getters.token.toString(),
        },
      }
    );
    this.animal = response.data.animal;
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
.font-weight-regular {
  font-size: 14px;
}

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}
</style>