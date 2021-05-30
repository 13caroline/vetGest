<template>
  <div>
    <v-container>
      <v-row justify="space-around" class="mt-2">
        <v-col cols="auto">
          <span class="subtitle-1 head">Rubi</span>
        </v-col>
        <v-col cols="auto" class="ml-auto">
          <v-btn icon depressed to="/clinica/editar/utente">
            <v-icon color="#2596be">fas fa-pen</v-icon>
          </v-btn>
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
          <Dados></Dados>
        </v-tab-item>

        <v-tab-item>
          <PacienteVacinas></PacienteVacinas>
        </v-tab-item>

        <v-tab-item>
          <Consultas></Consultas>
        </v-tab-item>

        <v-tab-item>
          <Cirurgia></Cirurgia>
        </v-tab-item>
      </v-tabs-items>
    </v-container>
  </div>
</template>

<script>
import PacienteVacinas from "@/components/Clinic/Paciente/Vacinas.vue"
import Consultas from "@/components/Clinic/Paciente/Consultas.vue"
import Cirurgia from "@/components/Clinic/Paciente/Cirurgias.vue"
import Dados from "@/components/Clinic/Paciente/Dados.vue"

export default {
  data: () => ({
    tab: null,    
    items: [
      { tab: "Informações gerais" },
      { tab: "Vacinas e Desparasitações" },
      { tab: "Consultas" },
      { tab: "Cirurgias" },
      { tab: "Histórico Clínico" },
    ],
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
  components:{
    PacienteVacinas, 
    Consultas,
    Cirurgia,
    Dados
  },
  created() {
    /*
    let response = await axios.post("http://localhost:7777/clinica/getUtentes", {
      email: this.$store.state.user.email,
    });

    */
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
