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
                  <p class="ma-0">Nome *</p>
                  <v-text-field
                    color="#2596be"
                    flat
                    outlined
                    dense
                    v-model="nome"
                    :rules="nameRules"
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
                  <p class="ma-0">Espécie *</p>
                  <v-select
                    class="font-weight-regular"
                    outlined
                    dense
                    color="#2596be"
                    label="Por favor selecione"
                    :items="itemsespecie"
                    v-model="especie"
                    :rules="nullRule"
                  >
                  </v-select>
                </div>
              </v-col>
            </v-row>

            <v-row align="center">
              <v-col class="py-0">
                <p class="ma-0">Dono *</p>
                <v-text-field
                  color="#2596be"
                  flat
                  readonly
                  outlined
                  dense
                  v-model="nomeSelected"
                  :rules="nullRule"
                  @click="dialogDono = true"
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
                <p class="ma-0">Raça *</p>
                <v-select
                  color="#2596be"
                  class="font-weight-regular"
                  outlined
                  dense
                  label="Por favor selecione"
                  v-model="raca"
                  v-if="especie == 'Canídeo'"
                  :items="racasCao"
                  :rules="nullRule"
                >
                </v-select>
                <v-select
                  color="#2596be"
                  class="font-weight-regular"
                  outlined
                  dense
                  label="Por favor selecione"
                  v-model="raca"
                  v-else
                  :items="racasGato"
                  :rules="nullRule"
                >
                </v-select>
              </v-col>
              <v-col sm="6" md="4" cols="12" class="py-0">
                <p class="ma-0">Altura *</p>
                <v-text-field
                  class="font-weight-regular"
                  outlined
                  dense
                  color="#2596be"
                  suffix="cm"
                  v-model="altura"
                  :rules="alturaRules"
                >
                </v-text-field>
              </v-col>

              <v-col cols="12" sm="6" md class="py-0">
                <p class="ma-0">Data de Nascimento *</p>
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
                      :rules="nullRule"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="date"
                    @input="menu2 = false"
                    locale="pt PT"
                    :max="new Date().toISOString().substr(0, 10)"
                  ></v-date-picker>
                </v-menu>
              </v-col>

              <v-col cols="6" md class="py-0">
                <p class="ma-0">Sexo *</p>
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
                <p class="ma-0">Castração *</p>
                <v-radio-group row class="ma-0" v-model="castracao">
                  <v-radio :value="true" color="#2596be">
                    <template v-slot:label>
                      <div class="body-2">Sim</div>
                    </template>
                  </v-radio>
                  <div class="w-100 d-sm-none"></div>
                  <v-radio :value="false" color="#2596be">
                    <template v-slot:label>
                      <div class="body-2">Não</div>
                    </template>
                  </v-radio>
                </v-radio-group>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" sm="5" class="py-0">
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
                  :rules="nullRule"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="4" class="py-0">
                <p class="ma-0">Pelagem *</p>

                <v-select
                  color="#2596be"
                  flat
                  outlined
                  dense
                  label="Por favor selecione"
                  multiple
                  :items="itemspelagem"
                  v-model="pelagem"
                  :rules="nullRule"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="3" class="py-0">
                <p class="ma-0">Cauda *</p>
                <v-select
                  color="#2596be"
                  flat
                  outlined
                  label="Por favor selecione"
                  dense
                  :items="itemscauda"
                  v-model="cauda"
                  :rules="nullRule"
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

            <span class="ma-0 caption">* Campos obrigatórios</span>

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
                  @click="registar()"
                  >Registar</v-btn
                >
              </v-col>
            </v-row>
          </v-form>
        </v-col>
      </v-row>
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
          no-data-text="Não existem clientes registados."
          no-results-text="Não foram encontrados resultados."
        ></v-data-table>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
//import moment from 'moment';
import axios from "axios";
import store from "@/store.js";
import Cancelar from "@/components/Dialogs/Cancel.vue";
export default {
  data: () => ({
    emailSelected: "",
    nomeSelected: "",
    nome: "",
    chip: "",
    especie: "",
    raca: "",
    altura: "",
    sexo: "",
    cor: "",
    pelagem: "",
    cauda: "",
    castracao: false,
    observacoes: "",
    dialogDono: false,
    date: new Date().toISOString().substr(0, 10),
    menu2: false,
    valid: true,
    color: "",
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
    itemsespecie: ["Canídeo", "Felídeo"],
    racasCao: [
      "Affenpinscher",
      "Airedale Terrier",
      "Akbash",
      "Akita Inu",
      "American Toy Terrier",
      "Anglo Francês",
      "Barbado da Terceira",
      "Basenji",
      "Basset Hound",
      "Beagle",
      "Bernese",
      "Bichon Frise",
      "Bloodhound",
      "Bobtail",
      "Boerboel",
      "Boiadeiro Australiano",
      "Border Collier",
      "Borzoi",
      "Boston Terrier",
      "Boxer",
      "Braco Francês",
      "Braco Italiano",
      "Briard",
      "Brittany",
      "Bull Terrier",
      "Bulldog Americano",
      "Bulldog Inglês",
      "Cairn Terrier",
      "Cane Corso",
      "Caniche",
      "Cão d'Água Irlandês",
      "Cão d'Água Português",
      "Cavalier King Charles Spaniel",
      "Chesapeake Bay Retriever",
      "Chihuahua",
      "Chow Chow",
      "Clumber Spaniel",
      "Cocker Inglês",
      "Collie",
      "Cocker Americano",
      "Crista Chinês",
      "Csky Terrier",
      "Cuvac",
      "Dálmata",
      "Dandie Dinmont Terrier",
      "Drever",
      "Doberman",
      "Dogue Alemão",
      "Dogue Argentino",
      "Dogue de Bordeaux",
      "Dogue Canário",
      "Elkhund Norueguês",
      "Epagneul Breton",
      "Fila Brasileiro",
      "Fila de S.Miguel",
      "Fox Terrier",
      "Foxhound",
      "Gado Transmontano",
      "Galgo Afegão",
      "Galgo Polaco",
      "Golden Retriever",
      "Hokkaido",
      "Husky Siberiano",
      "Irish Wolfhound",
      "Ibizian Hound",
      "Irish Soft Coated Wheaten Terrier",
      "Jack Russel Terrier",
      "Keeshond",
      "Komondor",
      "Labrador Retriever",
      "Lakeland Terrier",
      "Lhasa Apso",
      "Leonberger",
      "Maltês",
      "Mastiff Inglês",
      "Mastim Napoletano",
      "Ovelheiro Gaúcho",
      "Papillon",
      "Pastor Alemão",
      "Pastor Australiano",
      "Pastor Belga",
      "Pastor Branco Suiço",
      "Pastor do Cáucaso",
      "Perdigueiro",
      "Pequinês",
      "Pitbull",
      "Podengo Português",
      "Pointer",
      "Pug",
      "Retriever de Pêlo Encaracolado",
      "Retriever de Pêlo Liso",
      "Rottweiler",
      "Rhodesian Ridgeback",
      "Samoiedo",
      "São Bernardo",
      "Schnauzer",
      "Scottish-Terrier",
      "Serra da Estrela",
      "Serra de Aires",
      "Setter Gordon",
      "Setter Inglês",
      "Setter Irlandês",
      "Shar Pei",
      "Shih-Tzu",
      "Sheepdog",
      "Spitz Alemão",
      "Staffordshire Bull Terrier",
      "Staffordshire Terrier Americano",
      "Terra Nova",
      "Teckel",
      "Tosa Inu",
      "Vizsla",
      "Veadeiro Pampeano",
      "Volpino Italiano",
      "Whippet",
      "Weimaraner",
      "West Highland White Terrier",
      "Yorkshire Terrier",
      "Xoloitzcuintli",
      "ZwergPinscher",
    ],
    racasGato: [
      "Abissínio",
      "American Wirehair",
      "Angorá Turco",
      "Asian",
      "Australian Mist",
      "Balinês",
      "Bobtail Americano",
      "Bobtail Japonês",
      "Bombay",
      "Burmês",
      "Burmilla",
      "Chartreux",
      "Cornish Rex",
      "Curl Americano",
      "Cymric",
      "Devon Rex",
      "Don Sphynx",
      "Egyptian Mau",
      "Europeu Comum",
      "Gato Bosques da Noruega",
      "German Rex",
      "Havana",
      "Khao Manee",
      "Korat",
      "Kurilian Bobtail",
      "LaPerm",
      "Longhair Britânico",
      "Maine Coon",
      "Manx",
      "Munchkin",
      "Neva Masquerade",
      "Ocicat",
      "Oriental",
      "Peterbald",
      "Pixiebob",
      "RagaMuffin",
      "Ragdoll",
      "Russian",
      "Sagrado da Birmânia",
      "Scottish Straight",
      "Selkirk Rex",
      "Seychellois",
      "Shorthair Americano",
      "Shorthair Britânico",
      "Siamês",
      "Siberiano",
      "Singapura",
      "Snowshoe",
      "Sokoke",
      "Somali",
      "Tonkinese",
      "Van Turco",
      "Vankedisi Turco",
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
    items: [],
    dialogs: {},
    cancelar: { title: "registo de utente", text: "o registo de um utente" },
    nameRules: [
      (v) => !!v || "Insira o nome completo.",
      (v) => {
        const pattern = /^[a-zA-Z\sÀ-ÿ]+$/;
        return (
          pattern.test(v) ||
          "Nome inválido. Insira apenas caracteres do alfabeto."
        );
      },
    ],
    nullRule: [(v) => !!v || "Este campo não pode estar vazio."],
    alturaRules: [
      (v) => {
        const pattern = /^[0-9]+$/;
        return pattern.test(v) || "Altura inválida. Insira apenas dígitos.";
      },
    ],
  }),
  components: {
    Cancelar,
  },
  methods: {
    handleClick(row) {
      this.emailSelected = row.email;
      this.nomeSelected = row.nome;
      this.dialogDono = false;
    },
    close() {
      this.$router.push("/clinica/utentes");
    },
    registar: async function () {
      if (this.$refs.form.validate()) {
        try {
          await axios.post(
            "http://localhost:7777/clinica/utentes/registar",
            {
              cliente: {
                email: this.emailSelected,
              },
              animal: {
                nome: this.nome,
                especie: this.especie,
                chip: this.chip,
                raca: this.raca,
                altura: this.altura,
                dataNascimento: this.date,
                sexo: this.sexo,
                cor: this.cor.toString(),
                pelagem: this.pelagem.toString(),
                cauda: this.cauda,
                castracao: this.castracao,
                observacoes: this.observacoes,
              },
            },
            { headers: { Authorization: "Bearer " + store.getters.token } }
          );

          this.$snackbar.showMessage({
            show: true,
            color: "success",
            text: "Utente registado com sucesso.",
            timeout: 4000,
          });
          this.$router.push("/clinica/utentes");
        } catch (e) {
          this.$snackbar.showMessage({
            show: true,
            color: "warning",
            text: "Ocorreu um erro no registo, por favor tente mais tarde!",
            timeout: 4000,
          });
        }
      } else {
        this.$snackbar.showMessage({
          show: true,
          color: "error",
          text: "Por favor preencha todos os campos.",
          timeout: 4000,
        });
        this.done = false;
      }
    },
  },
  created: async function () {
    try {
      let response = await axios.get("http://localhost:7777/clinica/clientes", {
        headers: { Authorization: "Bearer " + store.getters.token },
      });
      this.items = response.data;
    } catch (e) {
      console.log(e);
    }
  },
};
</script> 

<style scoped>
.indication {
  color: #616161;
}
</style>