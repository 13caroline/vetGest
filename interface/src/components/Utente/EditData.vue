<template>
  <div>
    <v-container>
      <v-card-title class="font-weight-bold text-uppercase">
        <v-icon small class="mr-2">fas fa-paw</v-icon>
        Editar dados de {{ animal.nome }}
      </v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-row>
            <v-col cols="auto" md="3" class="ml-4">
              <v-card width="200" class="mx-auto" flat color="transparent">
                <v-img
                  src="@/assets/animais/Rubi.jpg"
                  aspect-ratio="1"
                  class="grey lighten-2 ml-n3"
                  cover
                >
                  <template v-slot:placeholder>
                    <v-row
                      class="fill-height ma-0"
                      align="center"
                      justify="center"
                    >
                      <v-progress-circular
                        indeterminate
                        color="grey lighten-5"
                      ></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
              </v-card>
            </v-col>

            <v-col cols="12" md class="mt-10 mt-md-0">
              <v-row>
                <v-col cols="12" sm="4" class="py-0">
                  <p class="ma-0">Nome</p>
                  <v-text-field
                    color="#2596be"
                    :rules="nomeRules"
                    outlined
                    dense
                    v-model="animal.nome"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="3" class="py-0">
                  <p class="ma-0">Espécie</p>
                  <v-text-field
                    color="#2596be"
                    class="font-weight-regular"
                    flat
                    outlined
                    dense
                    disabled
                    :value="animal.especie"
                  >
                  </v-text-field>
                </v-col>
                <v-col cols="12" sm="5" class="py-0">
                  <p class="ma-0">Raça</p>
                  <v-text-field
                    disabled
                    color="#2596be"
                    class="font-weight-regular"
                    outlined
                    dense
                    :value="animal.raca"
                  >
                  </v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="8" sm="4" class="py-0">
                  <p class="ma-0">Data de Nascimento</p>
                  <v-text-field
                    color="#2596be"
                    outlined
                    dense
                    :value="animal.dataNascimento"
                    disabled
                  ></v-text-field>
                </v-col>
                <v-col sm="2" class="py-0">
                  <p class="ma-0">Altura</p>
                  <v-text-field
                    color="#2596be"
                    class="font-weight-regular"
                    flat
                    outlined
                    dense
                    suffix="cm"
                    v-model="animal.altura"
                    :rules="alturaRules"
                  >
                  </v-text-field>
                </v-col>
                <v-col cols="12" sm="6" class="py-0">
                  <p class="ma-0">Número de Chip</p>
                  <v-text-field
                    disabled
                    color="#2596be"
                    class="font-weight-regular"
                    outlined
                    dense
                    :value="animal.chip"
                  >
                  </v-text-field>
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
                    :items="itemscor"
                    multiple
                    v-model="animal.cor"
                  ></v-select>
                </v-col>

                <v-col cols="12" sm="4" class="py-0">
                  <p class="ma-0">Pelagem</p>
                  <v-select
                    color="#2596be"
                    flat
                    outlined
                    dense
                    :items="itemspelagem"
                    multiple
                    v-model="animal.pelagem"
                  ></v-select>
                </v-col>

                <v-col cols="12" sm="3" class="py-0">
                  <p class="ma-0">Cauda</p>
                  <v-select
                    color="#2596be"
                    flat
                    outlined
                    dense
                    :items="itemscauda"
                    v-model="animal.cauda"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col class="py-0">
                  <p class="ma-0">Observações</p>
                  <v-textarea
                    v-if="animal.observacoes"
                    outlined
                    color="#2596be"
                    rows="2"
                    clearable
                    clear-icon="fas fa-times-circle"
                    no-resize
                    v-model="animal.observacoes"
                  ></v-textarea>

                  <v-textarea
                    v-else
                    outlined
                    color="#2596be"
                    value="Sem observações"
                    rows="2"
                    clearable
                    clear-icon="fas fa-times-circle"
                    no-resize
                    v-model="animal.observacoes"
                  ></v-textarea>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" sm="auto" class="py-0">
                  <v-radio-group v-model="animal.sexo" row disabled>
                    <template v-slot:label>
                      <div>Sexo</div>
                    </template>
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
                </v-col>
                <v-col cols="12" sm="auto" class="py-0">
                  <v-radio-group v-model="animal.castracao" row>
                    <template v-slot:label>
                      <div>Castração</div>
                    </template>
                    <v-radio value="Sim" color="#2596be">
                      <template v-slot:label>
                        <div class="body-2">Sim</div>
                      </template>
                    </v-radio>
                    <v-radio value="Não" color="#2596be">
                      <template v-slot:label>
                        <div class="body-2">Não</div>
                      </template>
                    </v-radio>
                  </v-radio-group>
                </v-col>
              </v-row>

              <v-row align="end" justify="end">
                <v-col cols="auto" class="pr-0">
                  <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
                </v-col>
                <v-col cols="auto" class="pl-0">
                  <v-btn
                    color="#2596be"
                    small
                    dark
                    class="ml-3"
                    @click="editarDados()"
                    >Confirmar</v-btn
                  >
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-form>
      </v-card-text>
    </v-container>
    
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
import axios from "axios";
import Cancelar from "@/components/Dialogs/Cancel.vue"

export default {
  props: ["id"],
  data: () => ({
    dialog: false,
    dialogs: {},
    cancelar: {title: "edição de dados", text: "a edição dos dados"},
    animal: { },
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
    valid: true,
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",
    nomeRules: [
        (value) => {
          const pattern = /^([a-zA-Z]+)$/;
          return pattern.test(value) || "O nome só pode conter letras";
        },
      ],
    alturaRules: [
        (value) => {
          const pattern = /^([0-9]+)$/;
          return pattern.test(value) || "Introduza apenas dígitos";
        },
      ],
  }),
  components: {
    Cancelar
  },
  methods: {
    close(){
      // if medico 
      this.$router.push("/medico/utente");
    },
    editarDados: async function () {
      if (this.$refs.form.validate()) {
        try {
          var resposta = await axios.post("http://localhost:7777//cliente/editaDadosAnimal", {
            id: this.animal.id,
            nome: this.animal.nome,
            altura: this.animal.altura,
            cor: this.animal.cor,
            pelagem: this.animal.pelagem, 
            cauda: this.animal.cauda, 
            observacoes: this.animal.observacoes,
            castracao: this.animal.castracao
          });
          console.log(JSON.stringify(resposta.data));
          this.text = "Dados editados com sucesso.";
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
  created: async function () {
    try {
      let response = await axios.post("http://localhost:7777/medico/utente", {
        id: this.id,
      });
      this.animal = response.data;
    } catch (e) {
      console.log(e);
    }
  },
};
</script>