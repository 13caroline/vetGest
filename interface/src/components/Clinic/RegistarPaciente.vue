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

          <v-row class="mt-2">
            <v-col cols="12" sm="5" class="py-0">
              <div>
                <p class="ma-0">Nome</p>
                <v-text-field
                  color="#2596be"
                  flat
                  outlined
                  dense
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
            <v-col sm="6" md="4" cols="12" class="py-0">
              <p class="ma-0">Raça</p>
              <v-select
                color="#2596be"
                class="font-weight-regular"
                outlined
                dense
                label="Por favor selecione"
              >
              </v-select>
            </v-col>
            <v-col sm="6" md="2" cols="12" class="py-0">
              <p class="ma-0">Altura</p>
              <v-text-field
                class="font-weight-regular"
                outlined
                dense
                color="#2596be"
                suffix="cm"
              >
              </v-text-field>
            </v-col>

            <v-col cols="12" sm="6" md="3" class="py-0">
              <p class="ma-0">Data de Nascimento</p>
              <v-menu
                ref="dataMarcacao"
                v-model="dataMarcacao"
                :close-on-content-click="true"
                :nudge-right="40"
                :return-value.sync="dataMarcacao"
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

            <v-col cols="12" sm="6" md="3" class="py-0">
              <p class="ma-0">Sexo</p>
              <v-radio-group row class="ma-0">
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
        </v-col>
      </v-row>
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
    dialog: false,
    dialogDono: false,
    dataMarcacao: null,
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
  },
};
</script> 

<style scoped>
.indication {
  color: #616161;
}
</style>