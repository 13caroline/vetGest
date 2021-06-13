<template>
  <div>
    <v-container>
      <v-card-title class="font-weight-bold text-uppercase">
        <v-icon small class="mr-2">fas fa-paw</v-icon>
        Editar dados de {{ dados.nome }}
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
                    disabled
                    :rules="nomeRules"
                    outlined
                    dense
                    v-model="dados.nome"
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
                    v-model="dados.especie"
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
                    :value="dados.raca"
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
                    :value="dados.dataNascimento"
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
                    v-model="dados.altura"
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
                    :value="dados.chip"
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
                    :items="itemspelagem"
                    multiple
                    v-model="pelagem"
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
                    v-model="dados.cauda"
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
                    v-model="dados.observacoes"
                  ></v-textarea>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" sm="auto" class="py-0">
                  <v-radio-group v-model="dados.sexo" row disabled>
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
                  <v-radio-group v-model="dados.castracao" row>
                    <template v-slot:label>
                      <div>Castração</div>
                    </template>
                    <v-radio :value="true" color="#2596be">
                      <template v-slot:label>
                        <div class="body-2">Sim</div>
                      </template>
                    </v-radio>
                    <v-radio :value="false" color="#2596be">
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
  </div>
</template>

<script>
import axios from "axios";
import Cancelar from "@/components/Dialogs/Cancel.vue";
import store from "@/store.js";

export default {
  props: ["id", "animal"],
  data: () => ({
    dialog: false,
    dialogs: {},
    cancelar: { title: "edição de dados", text: "a edição dos dados" },
    dados: {},
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
    done: false,
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
    cor: [],
    pelagem: [],
  }),
  components: {
    Cancelar,
  },
  methods: {
    close() {
      let route =
        this.$store.state.tipo == "Clinica"
          ? "/clinica/utente/"
          : "/medico/utente/";
      this.$router.push(route + this.id);
    },
    editarDados: async function () {
      let route =
        this.$store.state.tipo == "Clinica"
          ? "http://localhost:7777/clinica/utente/editar"
          : "http://localhost:7777/medico/utente/editar";
      if (this.$refs.form.validate()) {
        try {
          await axios.post(
            route,
            {
              id: this.id,
              animal: {
                raca: this.dados.raca,
                dataNascimento: this.dados.dataNascimento,
                nome: this.dados.nome,
                sexo: this.dados.sexo,
                especie: this.dados.especie,
                altura: this.dados.altura,
                cor: this.cor.toString(),
                pelagem: this.pelagem.toString(),
                cauda: this.dados.cauda,
                observacoes: this.dados.observacoes,
                castracao: this.dados.castracao,
                chip: this.dados.chip,
              },
            },
            { headers: { Authorization: "Bearer " + store.getters.token } }
          );
          if (this.$store.state.tipo == "Clínica")
            this.$router.push("/clinica/utente/" + this.id);
          else this.$router.push("/medico/utente/" + this.id);

          this.$snackbar.showMessage({
            show: true,
            color: "success",
            text: "Dados editados com sucesso.",
            timeout: 4000,
          });
        } catch (e) {
          this.$snackbar.showMessage({
            show: true,
            color: "warning",
            text: "Ocorreu um erro no registo, por favor tente mais tarde!",
            timeout: 4000,
          });
        }
      } else {
        this.done = false;
        this.$snackbar.showMessage({
          show: true,
          color: "error",
          text: "Por favor preencha todos os campos.",
          timeout: 4000,
        });
      }
    },
  },
  created: async function () {
    let route =
      this.$store.state.tipo == "Clinica"
        ? "http://localhost:7777/clinica/utente"
        : "http://localhost:7777/medico/utente";
    let response = await axios.post(
      route,
      {
        id: this.id,
      },
      { headers: { Authorization: "Bearer " + store.getters.token } }
    );
    this.dados = response.data;
    this.cor = response.data.cor.split(",");
    this.pelagem = response.data.pelagem.split(",");
    if (this.dados.observacoes.length == 0)
      this.dados.observacoes = "Sem observações";
  },
};
</script>