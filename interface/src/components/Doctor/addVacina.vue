<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row class="w-100">
            <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
              <v-icon class="mr-2">fas fa-paw</v-icon>
              Adicionar Vacina
            </h3>
          </v-row>
          <div class="indication">
            <span class="body-2"
              >Por favor preencha o seguinte formulário
            </span>
          </div>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-row class="mt-2">
              <v-col sm="6" md="4" cols="12" class="py-0" >
                <p class="ma-0">Estado</p>
                <v-select
                  color="#2596be"
                  class="font-weight-regular"
                  outlined
                  dense
                  label="Por favor selecione"
                  v-model="estado"
                >
                </v-select>
              </v-col>

              <v-col sm="6" md="4" cols="auto" class="py-0">
                <p class="ma-0">Tipo</p>
                <v-select
                  color="#2596be"
                  class="font-weight-regular"
                  outlined
                  dense
                  label="Por favor selecione"
                  v-model="tipo"
                >
                </v-select>
              </v-col>
            </v-row>



            <v-row>
            
            

              <v-col cols="12" sm="6" md="3" class="py-0">
                <p class="ma-0">Data</p>
                <v-menu
                  ref="data"
                  v-model="data"
                  :close-on-content-click="true"
                  :nudge-right="40"
                  :return-value.sync="data"
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
              </v-col>

              
            </v-row>

            <v-row>
              <v-col class="py-0">
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
              </v-col>
            </v-row>

            <v-row align="end" justify="end">
              <v-col cols="auto" class="pr-0">
                <v-btn color="#BDBDBD" small dark @click="dialog = true"
                  >Cancelar</v-btn
                >
              </v-col>
              <v-col cols="auto" class="pl-0">
                <v-btn color="#2596be" small dark class="ml-3">Registar</v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-col>
      </v-row>
      <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
        :color="color"
        :top="true"
        class="headline"
      >
        {{ text }}
      </v-snackbar>
    </v-container>

    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
      <v-card>
        <v-card-title class="cancel">
          Cancelar registo de novo utente
        </v-card-title>
        <v-card-text>
          Tem a certeza que pretende cancelar o registo?
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
            to="/clinica/utentes"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="dialogDono" width="100%" max-width="700">
      <v-card>
        <v-card-title>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table
          @click:row="handleClick"
          :headers="headers"
          :items="items"
          :search="search"
        ></v-data-table>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
//import moment from 'moment';
export default {
  data: () => ({
    emailSelected: "",
    nomeSelected: "",
    nome: "",
    chip: "",
    especie: "",
    estado: "",
    tipo: "",
    altura: "",
    data: null,
    sexo: "",
    cor: "",
    pelagem: "",
    cauda: "",
    observacoes: "",
    valid: true,
    snackbar: false,
    color: "",
    done: false,
    timeout: 0,
    text: "",
    dialog: false,
    dialogDono: false,
    date: new Date().toISOString().substr(0, 10),
    itemscor: [
      "Amarelo",
      "Azul",
      "Branco",
      "Castanho",
      "Cinzento",
      "Creme",
      "Dourado",
      "Fulvo",
      "Malhado",
      "Preto",
      "Vermelho",
    ],
    itemspelagem: [
      "Comprida",
      "Média",
      "Curta",
      "Lisa",
      "Ondulada",
      "Encaracolada",
      "Cerdosa",
    ],
    itemscauda: ["Comprida", "Curta", "Amputada"],
    itemsespecie: [
      "Canídeo",
      "Felídeo",
      "Pecuário",
      "Equino",
      "Selvagem",
      "Outro",
    ],
    search: "",
    headers: [
      {
        text: "Nome",
        align: "center",
        sortable: true,
        value: "nome",
      },
      {
        text: "Email",
        value: "email",
        sortable: true,
        align: "center",
      },
      {
        text: "NIF",
        value: "nif",
        sortable: true,
        align: "center",
      },
      {
        text: "Contacto",
        value: "contacto",
        sortable: true,
        align: "center",
      },
    ],
    items: [
      {
        nome: "Joao Mota",
        email: "joaomota@gmail.com",
        nif: "256862010",
        contacto: "935373306",
      },
      {
        nome: "Carolina Cunha",
        email: "carolinacunha@gmail.com",
        nif: "182092887",
        contacto: "919977230",
      },
    ],
  }),
  methods: {
    handleClick(row) {
      this.emailSelected = row.email;
      this.nomeSelected = row.nome;
      this.dialogDono = false;
    },
    registaAnimal: async function () {
      /*
      if (this.$refs.form.validate()) {
        try {
          var resposta = await axios.post("http://localhost:7777/clinica/registarUtente", {
            nome: this.nome,
            chip: this.chip,
            especie: this.especie,
            raca: this.raca,
            altura: this.altura,
            data: this.data,
            sexo: this.sexo,
            cor: this.cor,
            pelagem: this.pelagem, 
            cauda: this.cauda, 
            observacoes: this.observacoes,
            emailCliente: this.nomeSelected, 
            email: this.$store.state.user.email,
          });
          console.log(JSON.stringify(resposta.data));
          this.text = "Utente registado com sucesso.";
          this.color = "success";
          this.snackbar = true;
        } catch (e) {
          console.log("erro: " + e);
          this.text = "Ocorreu um erro no registo, por favor tente mais tarde!";
          this.color = "warning";
          this.snackbar = true;
        }
      } else {
        this.text = "Por favor preencha todos os campos.";
        this.color = "error";
        this.snackbar = true;
        this.done = false;
      }
      */
    },
  },
};
</script> 

<style scoped>
.indication {
  color: #616161;
}
</style>