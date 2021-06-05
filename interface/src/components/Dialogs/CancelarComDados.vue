<template>
    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
      <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-icon
            color="#E57373"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            @click="dialog = true"
          >
            mdi-calendar-remove
          </v-icon>
        </template>
        <span class="caption">Cancelar agendamento</span>
      </v-tooltip>
    </template>
      <v-card>
        <v-card-title class="cancel"> Cancelar consulta </v-card-title>
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
                <strong>21/05/2021 15:45</strong>
              </span>
            </v-col>

            <v-col class="pb-0" align="right" cols="5">
              <span class="text-uppercase">Médico</span>
            </v-col>
            <v-col class="pl-0 pb-0" cols="7">
              <span class="black--text">
                <strong>Sem preferência</strong>
              </span>
            </v-col>
          </v-row>

          <p class="mt-12">Tem a certeza que pretende cancelar a consulta?</p>
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
            @click="confirmar()"
          >
            Sim
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
  methods: {
      confirmar(){
           /*
		 try {
          var resposta = await axios.post("http://localhost:7777/cliente/cancelarConsulta", {
            estado: "Cancelada"
          });
          console.log(JSON.stringify(resposta.data));
          this.cancelar = false;
          this.text = "Desparasitação confirmada com sucesso.";
          this.color = "success";
          this.snackbar = true;
        } catch (e) {
          console.log("erro: " + e);
          this.cancelar = false;
          this.text = "Ocorreu um erro, por favor tente mais tarde!";
          this.color = "warning";
          this.snackbar = true;
        }
		*/
      },
      
  }
}
</script>