<template>
  <div>
    <v-row>
      <v-container fluid>
        <v-card class="mx-auto" width="80%" flat color="transparent">
          <v-card-title class="font-weight-regular text-uppercase mb-4 mt-10">
            Registar Animal
          </v-card-title>

          <v-card-text>
            <v-row class="mt-n5">
              <v-col cols="3">
                <div>
                  <p class="ma-0">Nome</p>
                  <v-select
                    color="#2596be"
                    flat
                    outlined
                    dense
                    :items="animais"
                  ></v-select>
                </div>
              </v-col>
              <v-col cols="3">
                <div>
                  <p class="ma-0">Número de Chip</p>
                  <v-select color="#2596be" flat outlined dense></v-select>
                </div>
              </v-col>
            </v-row>

            <v-row class="mt-n5">
              <v-col cols="3">
                <div>
                  <p class="ma-0">Espécie</p>
                  <v-text-field
                    class="font-weight-regular"
                    outlined
                    dense
                    color="#2596be"
                  >
                  </v-text-field>
                </div>
              </v-col>
              <v-col cols="4">
                <div>
                  <p class="ma-0">Raça</p>
                  <v-text-field
                    color="#2596be"
                    class="font-weight-regular"
                    outlined
                    dense
                  >
                  </v-text-field>
                </div>
              </v-col>
            </v-row>

            <v-row class="mt-n5">
              <v-col>
                <div>
                  <p class="ma-0">Altura</p>
                  <v-text-field
                    class="font-weight-regular"
                    outlined
                    dense
                    color="#2596be"
                    suffix="cm"
                  >
                  </v-text-field>
                </div>
              </v-col>

              <v-col>
                <div>
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
                </div>
              </v-col>

              <v-col>
                <div>
                    <p class="ma-0">Sexo</p>
                  <v-radio-group row class="ma-0">
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
                </div>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-container>
    </v-row>
  </div>
</template>

<script>
//import moment from 'moment';
export default {
  data: () => ({
    dataMarcacao: null,
    date: new Date().toISOString().substr(0, 10),
  }),
};
</script>