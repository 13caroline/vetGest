<template>
  <div>
    <v-row>
      <v-container fluid>
        <v-card class="mx-auto" width="70%" flat color="transparent">
          <v-card-title class="font-weight-regular text-uppercase mt-10">
            Registar um Novo Animal
          </v-card-title>
          <v-card-subtitle>
            <p class="subtitle">
              Por favor preencha o seguinte formulário
            </p></v-card-subtitle
          >

          <v-card-text>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-row>
                <v-col cols="5">
                  <div>
                    <p class="ma-0">Nome *</p>
                    <v-text-field
                      color="#2596be"
                      flat
                      outlined
                      dense
                      v-model="nome"
                    ></v-text-field>
                  </div>
                </v-col>
                <v-col cols="3">
                  <div>
                    <p class="ma-0">Número de Chip *</p>
                    <v-text-field
                      color="#2596be"
                      flat
                      outlined
                      dense
                      v-model="chip"
                    ></v-text-field>
                  </div>
                </v-col>
                <v-col cols="4">
                  <div>
                    <p class="ma-0">Espécie *</p>
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

              <v-row class="mt-n5">
                <v-col cols="4">
                  <div>
                    <p class="ma-0">Raça *</p>
                    <v-select
                      color="#2596be"
                      class="font-weight-regular"
                      outlined
                      dense
                      label="Por favor selecione"
                      v-model="raca"
                      :items="itemsraca"
                    >
                    </v-select>
                  </div>
                </v-col>
                <v-col cols="2">
                  <div>
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
                  </div>
                </v-col>

                <v-col>
                  <div>
                    <p class="ma-0">Data de Nascimento *</p>
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
                  </div>
                </v-col>

                <v-col>
                  <div>
                    <p class="ma-0">Sexo *</p>
                    <v-radio-group row class="ma-0" v-model="sexo">
                      <v-radio value="Macho" color="#2596be">
                        <template v-slot:label>
                          <div class="body-2">Macho</div>
                        </template>
                      </v-radio>
                      <v-radio value="Fêmea" color="#2596be">
                        <template v-slot:label>
                          <div class="body-2">Fêmea</div>
                        </template>
                      </v-radio>
                    </v-radio-group>
                  </div>
                </v-col>
              </v-row>

              <v-row class="mt-n5">
                <v-col cols="5">
                  <div>
                    <p class="ma-0">Cor *</p>
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
                  </div>
                </v-col>
                <v-col cols="4">
                  <div>
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
                  </div>
                </v-col>
                <v-col cols="3">
                  <div>
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
                  </div>
                </v-col>
              </v-row>

              <v-row class="mt-n5">
                <v-col>
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
                </v-col>
              </v-row>
              <span class="ma-0">* Campos obrigatórios</span>
              <v-row align="end" justify="end">
                <v-btn color="#BDBDBD" small dark @click="dialog = true"
                  >Cancelar</v-btn
                >
                <v-btn
                  color="#2596be"
                  small
                  dark
                  class="ml-3"
                  @click="registaAnimal()"
                  >Registar</v-btn
                >
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-container>
    </v-row>
    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
      <v-card>
        <v-card-title class="cancel">
          Cancelar registo de um novo animal
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
            to="/cliente/inicio"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar
      v-model="snackbar"
      :timeout="timeout"
      :color="color"
      :top="true"
      class="headline"
    >
      {{ text }}
    </v-snackbar>
  </div>
</template>

<script>
//import moment from 'moment';
import axios from "axios";
import store from "@/store.js"
export default {
  data: () => ({
    dialog: false,
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
    itemsraca: [
      "podengo"
    ],
    itemscauda: ["Comprida", "Curta", "Amputada"],
    itemsespecie: [
      "Bovino",
      "Canídeo",
      "Equino",
      "Felídeo",
      "Exótico",
      "Outro",
    ],
    nome: "",
    chip: "",
    especie: "",
    altura: "",
    data: null,
    sexo: "",
    cor: "",
    raca: "",
    pelagem: "",
    cauda: "",
    observacoes: "",
    valid: true,
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",
    
  }),
  methods: {
    registaAnimal: async function () {
      if (this.$refs.form.validate()) {
        try {
          console.log(store.getters.token)
          var t = store.getters.token.toString()
          var resposta = await axios.post("http://localhost:7777/cliente/animal/registar", {
            "cliente":{
                 email: this.$store.state.email,
            },
            "animal":{
            nome: this.nome,
            raca: this.raca,
            chip: this.chip,
            especie: this.especie,
            altura: this.altura,
            dataNascimento: this.data,
            sexo: this.sexo,
            cor: this.cor.toString(),
            pelagem: this.pelagem.toString(), 
            cauda: this.cauda, 
            castracao:1,
            observacoes: this.observacoes
            },
          },
          {
          headers: {
            "Authorization": 'Bearer ' +t
          }
          });
          console.log(JSON.stringify(resposta.data));
          this.text = "Animal registado com sucesso.";
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
    },
  },
};
</script>

<style scoped>
.subtitle {
  color: #2596be;
}
</style>