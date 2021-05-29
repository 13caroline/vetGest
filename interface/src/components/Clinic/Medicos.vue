<template>
  <div>
    <v-container>
      <v-row class="w-100">
        <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
          <v-icon class="mr-2">fas fa-user-md</v-icon>
          Médicos Veterinários
        </h3>
        <v-row justify="end">
          <v-col cols="auto">
            <v-btn
              class="mb-2 mt-6 body-2"
              small
              color="#2596be"
              dark
              to="/clinica/registar/medico"
            >
              Registar Médico Veterinário
            </v-btn>
          </v-col>
        </v-row>
      </v-row>
      <v-row>
        <v-col
          v-for="n in medicos"
          :key="n.nif"
          class="d-flex child-flex"
          cols="6"
          sm="4"
          md="3"
        >
          <div>
            <v-hover v-slot:default="{ hover }">
              <v-img
                src="@/assets/sobre.png"
                aspect-ratio="1"
                @click="openInfo(n)"
                style="border-radius: 100%"
              >
                <v-expand-transition>
                  <div
                    v-bind:class="{ hover: hover }"
                    class="d-flex transition-fast-in-fast-out black v-card--reveal white--text hover-div"
                  >
                    <v-btn
                      text
                      dark
                      depressed
                      class="text-capitalize"
                      @click="toBooks()"
                      >Detalhes</v-btn
                    >
                  </div>
                </v-expand-transition>
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
            </v-hover>
            <p align="center" class="mt-2 w-100">{{ n.nome }}</p>
          </div>
        </v-col>
      </v-row>

      <v-dialog v-model="dialog" width="100%" max-width="460">
        <v-card>
          <v-card-title class="mb-6 grey--text">
            Identificação pessoal
            <v-tooltip right>
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon small class="ml-2">
                  <v-icon v-bind="attrs" v-on="on" small>fas fa-pen</v-icon>
                </v-btn>
              </template>
              <span class="caption">Editar dados</span>
            </v-tooltip>

            <v-spacer></v-spacer>
            <v-btn icon small @click="dialog = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <v-row align="center">
              <v-col cols="auto">
                <v-img
                  max-height="100"
                  max-width="100"
                  src="@/assets/exemplo.png"
                >
                </v-img>
              </v-col>
              <v-col>
                <span class="font-weight-bold headline indication">
                  {{ this.nome }}
                </span>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold"
                  >Data de nascimento:</span
                >
              </v-col>

              <v-col>
                <div class="information font-weight-light">{{ this.data }}</div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Telemóvel:</span>
              </v-col>

              <v-col>
                <div class="information font-weight-light">
                  {{ this.contacto }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Email:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.email }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">Morada:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.morada }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold"
                  >Número de contribuinte:</span
                >
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.nif }}
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <span class="indication font-weight-bold">IBAN</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">
                  {{ this.iban }}
                </div>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
    nome: "",
    data: "",
    morada: "",
    nif: "",
    email: "",
    contacto: "",
    iban: "",
    medicos: [
      {
        nome: "Joana Ferreira",
        data: "15/04/1988",
        morada: "Rua da Pica, nº 123",
        nif: "123123123",
        email: "joanaferreira@gmail.com",
        contacto: "919191915",
        iban: "PT50-213125041231405214212",
      },
      {
        nome: "Vitor Alves",
        data: "25/09/1989",
        morada: "Rua da Pica, nº 122",
        nif: "456456456",
        email: "vitoralves@gmail.com",
        contacto: "9123456781",
        iban: "PT50-213125041231405214212",
      },
      {
        nome: "Pedro Moura",
        data: "01/02/1978",
        morada: "Rua da Porta, nº 121",
        nif: "789789789",
        email: "pedromoura@gmail.com",
        contacto: "934567841",
        iban: "PT50-213125041231405214212",
      },
      {
        nome: "Cristina Freitas",
        data: "16/10/1980",
        morada: "Rua da Praça, nº 1",
        nif: "369369369",
        email: "cristinafreitas@gmail.com",
        contacto: "969645133",
        iban: "PT50-213125041231405214212",
      },
      {
        nome: "Cristina Freitas",
        data: "16/10/1980",
        morada: "Rua da Praça, nº 1",
        nif: "369369369",
        email: "cristinafreitas@gmail.com",
        contacto: "969645133",
        iban: "PT50-213125041231405214212",
      },
    ],
  }),
  methods: {
    openInfo(n) {
      this.nome = n.nome;
      this.data = n.data;
      this.morada = n.morada;
      this.nif = n.nif;
      this.email = n.email;
      this.contacto = n.contacto;
      this.iban = n.iban;

      this.dialog = true;
    },
  },
  created: {
    /*
    let response = await axios.post("http://localhost:7777/clinica/getMedicos", {
      email: this.$store.state.user.email,
    });

    */
  },
};
</script>

<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.5;
  position: absolute;
  width: 100%;
}

.information {
  text-align: right;
  color: #616161;
}

.indication {
  color: #424242;
}

.hover-div {
  height: 100%;
  border-radius: 100%;
  opacity: 0;
}

.hover-div.hover {
  opacity: 0.7;
}
</style>