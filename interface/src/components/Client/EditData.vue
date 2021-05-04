<template>
  <div>
    <v-container fluid>
      <v-card
        height="60%"
        class="mx-auto mt-10"
        width="80%"
        flat
        color="#fafafa"
      >
        <v-row>
          <v-col cols="3">
            <v-card height="200" width="200" flat color="transparent">
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

          <v-col>
            <v-card color="#fafafa" tile flat class="mt-4">
              <v-row>
                <v-col>
                  <v-text-field
                    label="Nome"
                    outlined
                    disabled
                    dense
                    :value="cao.nome"
                    color="#2596be"
                  >
                  </v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="6">
                  <v-text-field
                    disabled
                    label="Espécie"
                    class="font-weight-regular"
                    outlined
                    dense
                    color="#2596be" 
                    :value="cao.especie"
                  >
                  </v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                    disabled
                    color="#2596be" 
                    label="Raça"
                    class="font-weight-regular"
                    outlined
                    dense
                    :value="cao.raca"
                  >
                  </v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col>
                  <v-text-field
                    label="Data de Nascimento"
                    class="font-weight-regular"
                    outlined
                    dense
                    color="#2596be" 
                    :value="cao.data"
                  >
                  </v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                    label="Altura"
                    class="font-weight-regular"
                    outlined
                    dense
                    color="#2596be" 
                    :value="cao.altura"
                    suffix="cm"
                  >
                  </v-text-field>
                </v-col>
                <v-col>
                  <v-select
                    label="Cor"
                    color="#2596be" 
                    flat
                    outlined
                    dense
                    :value="cao.cor"
                    :items="itemscor"
                    multiple
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col>
                  <v-select
                    color="#2596be" 
                    label="Pelagem"
                    flat
                    outlined
                    dense
                    :value="cao.pelagem"
                    :items="itemspelagem"
                    multiple
                  ></v-select>
                </v-col>
                <v-col>
                  <v-select
                    color="#2596be" 
                    label="Cauda"
                    flat
                    outlined
                    dense
                    :value="cao.cauda"
                    :items="itemscauda"
                    multiple
                  ></v-select>
                </v-col>
                <v-col>
                  <v-text-field
                    disabled
                    color="#2596be"
                    label="Número de Chip"
                    class="font-weight-regular"
                    outlined
                    dense
                    :value="cao.chip"
                  >
                  </v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="2">
                  <v-radio-group v-model="cao.sexo">
                    <template v-slot:label>
                      <div>Sexo</div>
                    </template>
                    <v-radio value="Macho" color="#2596be">
                      <template v-slot:label>
                        <div>Macho</div>
                      </template>
                    </v-radio>
                    <v-radio value="Fêmea" color="#2596be">
                      <template v-slot:label>
                        <div>Fêmea</div>
                      </template>
                    </v-radio>
                  </v-radio-group>
                </v-col>
                <v-col cols="2">
                  <v-radio-group v-model="cao.castracao">
                    <template v-slot:label>
                      <div>Castração</div>
                    </template>
                    <v-radio value="Sim" color="#2596be" >
                      <template v-slot:label>
                        <div>Sim</div>
                      </template>
                    </v-radio>
                    <v-radio value="Não" color="#2596be" >
                      <template v-slot:label>
                        <div>Não</div>
                      </template>
                    </v-radio>
                  </v-radio-group>
                </v-col>
              </v-row>
            </v-card>
          </v-col>
        </v-row>
      </v-card>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
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