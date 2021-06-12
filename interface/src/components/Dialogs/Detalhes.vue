<template>
  <v-dialog v-model="dialog" persistent width="100%" max-width="460">
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-icon
            color="#52b9dd"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            @click="dialog = true"
          >
            mdi-plus-circle
          </v-icon>
        </template>
        <span class="caption">Mais detalhes</span>
      </v-tooltip>
    </template>
    <v-card>
      <v-card-title class="cancel">Consulta agendada</v-card-title>
      <v-card-text>
        <v-row class="mt-2">
          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Nome do Animal</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>{{ dados.utente }}</strong>
              ({{ dados.animal.raca }})
            </span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Motivo da Consulta</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>{{ dados.descricao }}</strong>
            </span>
            <br />
            <span>{{ dados.motivo }}</span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Data</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>{{ format(dados.marcacao) }}</strong>
            </span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Médico</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>Dr.º {{dados.veterinario_nome}}</strong>
            </span>
          </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn depressed small dark color="#2596be" @click="dialog = false">
          Fechar
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import moment from "moment";
export default {
  props: ["dados"],
  data: () => ({
    dialog: false,
  }),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
  },
};
</script>