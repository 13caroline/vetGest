<template>
  <div>
    <v-container>
      <v-card-title class="font-weight-bold text-uppercase">
        <v-icon small class="mr-2">fas fa-paw</v-icon>
        Editar dados de {{ cao.nome }}
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="auto" md="3" class="ml-4">
            <v-card width="200" flat color="transparent">
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
            <v-row>
              <v-btn class="mt-5 body-2" small color="#2596be" dark
                >Alterar Fotografia</v-btn
              >
            </v-row>
          </v-col>

          <v-col cols="12" md class="mt-10 mt-md-0">
            <v-row>
              <v-col cols="12" sm="4" class="py-0">
                <p class="ma-0">Nome</p>
                <v-text-field
                  color="#2596be"
                  outlined
                  dense
                  :value="cao.nome"
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
                  :value="cao.especie"
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
                  :value="cao.raca"
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
                  :value="cao.data"
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
                  :value="cao.altura"
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
                  :value="cao.chip"
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
                  :value="cao.cor"
                  :items="itemscor"
                  multiple
                ></v-select>
              </v-col>

              <v-col cols="12" sm="4" class="py-0">
                <p class="ma-0">Pelagem</p>
                <v-select
                  color="#2596be"
                  flat
                  outlined
                  dense
                  :value="cao.pelagem"
                  :items="itemspelagem"
                  multiple
                ></v-select>
              </v-col>

              <v-col cols="12" sm="3" class="py-0">
                <p class="ma-0">Cauda</p>
                <v-select
                  color="#2596be"
                  flat
                  outlined
                  dense
                  :value="cao.cauda"
                  :items="itemscauda"
                ></v-select>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="py-0">
                <p class="ma-0">Observações</p>
                <v-textarea
                  v-if="cao.observacoes"
                  outlined
                  color="#2596be"
                  :value="cao.observacoes"
                  rows="2"
                  clearable
                  clear-icon="fas fa-times-circle"
                  no-resize
                ></v-textarea>

                <v-textarea
                  v-else
                  outlined
                  color="#2596be"
                  value="Sem observações"
                  rows="2"
                ></v-textarea>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" sm="auto" class="py-0">
                <v-radio-group v-model="cao.sexo" row>
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
                <v-radio-group v-model="cao.castracao" row>
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
                <v-btn color="#BDBDBD" small dark @click="dialog = true"
                  >Cancelar</v-btn
                >
              </v-col>
              <v-col cols="auto" class="pl-0">
                <v-btn color="#2596be" small dark class="ml-3">Guardar</v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card-text>
    </v-container>
    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
      <v-card>
        <v-card-title class="justify-center cancel">
          Cancelar edição de dados
        </v-card-title>
        <v-card-text>
          Tem a certeza que pretende cancelar a edição dos dados?
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
            to="/cliente/animal"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
    cao: {
      nome: "Rubi",
      especie: "Canídeo",
      raca: "Serra da Estrela",
      sexo: "Macho",
      cor: "Castanho",
      data: "02/01/2014",
      altura: 70,
      pelagem: ["Média", "Lisa"],
      cauda: "Comprida",
      chip: "AC14ASC7984",
      castracao: "Não",
      observacoes: "Observações Rubi",
    },
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
  }),
};
</script>