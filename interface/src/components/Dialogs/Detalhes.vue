<template>
  <v-dialog v-model="dialog" persistent width="100%" max-width="460">
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-icon
            color="grey"
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
              <strong>Rubi</strong>
              (Serra da Estrela)
            </span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Motivo da Consulta</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>Vómito/Diarreia/Recusa a comer</strong>
            </span>
            <br />
            <span>Consulta extraordinária/Por doença</span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Data</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>19/04/2021 15:30</strong>
            </span>
          </v-col>

          <v-col class="pb-0" align="right" cols="5">
            <span class="text-uppercase">Médico</span>
          </v-col>
          <v-col class="pl-0 pb-0" cols="7">
            <span class="black--text">
              <strong>Dr. José Vieira</strong>
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
export default {
    props: ['dados'],
  data: () => ({
    dialog: false,
  }),
};
</script>