<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row class="w-100">
            <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
              <v-icon class="mr-2">fas fa-paw</v-icon>
              Registar novo utente
            </h3>
          </v-row>
          <div class="indication">
            <span class="body-2"
              >Por favor preencha o seguinte formulário
            </span>
          </div>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-row class="mt-2">
              <v-col cols="12" sm="5" class="py-0">
                <div>
                  <p class="ma-0">Nome</p>
                  <v-text-field
                    color="#2596be"
                    flat
                    outlined
                    dense
                    v-model="nome"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="12" sm="3" class="py-0">
                <div>
                  <p class="ma-0">Número de Chip</p>
                  <v-text-field
                    color="#2596be"
                    flat
                    outlined
                    dense
                    v-model="chip"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="12" sm="4" class="py-0">
                <div>
                  <p class="ma-0">Espécie</p>
                  <v-select
                    class="font-weight-regular"
                    outlined
                    dense
                    color="#2596be"
                    label="Por favor selecione"
                    :items="itemsespecie"
                    v-model="especie"
                  >
                  </v-select>
                </div>
              </v-col>
            </v-row>

            <v-row align="center">
              <v-col class="py-0">
                <p class="ma-0">Dono</p>
                <v-text-field
                  color="#2596be"
                  flat
                  readonly
                  outlined
                  dense
                  v-model="nomeSelected"
                ></v-text-field>
              </v-col>
              <v-col cols="auto" class="py-0">
                <v-btn
                  @click="dialogDono = true"
                  class="ma-0"
                  fab
                  depressed
                  x-small
                  dark
                  color="#2596be"
                  ><v-icon dark> fas fa-search </v-icon>
                </v-btn>
              </v-col>
            </v-row>

            <v-row>
              <v-col md="8" cols="12" class="py-0">
                <p class="ma-0">Raça</p>
                <v-select
                  color="#2596be"
                  class="font-weight-regular"
                  outlined
                  dense
                  label="Por favor selecione"
                  v-model="raca"
                >
                </v-select>
              </v-col>
              <v-col sm="6" md="4" cols="12" class="py-0">
                <p class="ma-0">Altura</p>
                <v-text-field
                  class="font-weight-regular"
                  outlined
                  dense
                  color="#2596be"
                  suffix="cm"
                  v-model="altura"
                >
                </v-text-field>
              </v-col>

              <v-col cols="12" sm="6" md class="py-0">
                <p class="ma-0">Data de Nascimento</p>
                <v-menu
                  v-model="menu2"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="date"
                      append-icon="fas fa-calendar-alt"
                      readonly
                      dense
                      outlined
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="date"
                    @input="menu2 = false"
                    locale="pt PT"
                    :min="new Date().toISOString().substr(0, 10)"
                  ></v-date-picker>
                </v-menu>
              </v-col>

              <v-col cols="6" md class="py-0">
                <p class="ma-0">Sexo</p>
                <v-radio-group row class="ma-0" v-model="sexo">
                  <v-radio value="Macho" color="#2596be">
                    <template v-slot:label>
                      <div class="body-2">Macho</div>
                    </template>
                  </v-radio>
                  <div class="w-100 d-sm-none"></div>
                  <v-radio value="Fêmea" color="#2596be">
                    <template v-slot:label>
                      <div class="body-2">Fêmea</div>
                    </template>
                  </v-radio>
                </v-radio-group>
              </v-col>

              <v-col cols="6" md class="py-0">
                <p class="ma-0">Castração</p>
                <v-radio-group row class="ma-0" v-model="sexo">
                  <v-radio value="1" color="#2596be">
                    <template v-slot:label>
                      <div class="body-2">Sim</div>
                    </template>
                  </v-radio>
                  <div class="w-100 d-sm-none"></div>
                  <v-radio value="0" color="#2596be">
                    <template v-slot:label>
                      <div class="body-2">Não</div>
                    </template>
                  </v-radio>
                </v-radio-group>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" sm="5" class="py-0">
                <p class="ma-0">Cor</p>
                <v-select
                  color="#2596be"
                  flat
                  outlined
                  dense
                  label="Por favor selecione"
                  multiple
                  :items="itemscor"
                  v-model="cor"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="4" class="py-0">
                <p class="ma-0">Pelagem</p>

                <v-select
                  color="#2596be"
                  flat
                  outlined
                  dense
                  label="Por favor selecione"
                  multiple
                  :items="itemspelagem"
                  v-model="pelagem"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="3" class="py-0">
                <p class="ma-0">Cauda</p>
                <v-select
                  color="#2596be"
                  flat
                  outlined
                  label="Por favor selecione"
                  dense
                  :items="itemscauda"
                  v-model="cauda"
                ></v-select>
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
                 <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
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
import Cancelar from "@/components/Dialogs/Cancel.vue"
export default {
  data: () => ({
    emailSelected: "",
    nomeSelected: "",
    nome: "",
    chip: "",
    especie: "",
    raca: "",
    altura: "",
    data: null,
    sexo: "",
    cor: "",
    pelagem: "",
    cauda: "",
    castracao: "",
    observacoes: "",
    valid: true,
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",
    dialogDono: false,
    date: new Date().toISOString().substr(0, 10),
    menu2: false,
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
    dialogs: {},
    cancelar: {title: "registo de utente", text: "o registo de um utente"},
  }),
  components:{
    Cancelar
  },
  methods: {
    handleClick(row) {
      this.emailSelected = row.email;
      this.nomeSelected = row.nome;
      this.dialogDono = false;
    },
    close(){
      this.$router.push("/clinica/utentes")
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
            idCliente: this.nomeSelected, 
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