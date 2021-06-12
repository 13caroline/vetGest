<template>
  <div>
    <v-container>
      <v-card flat color="#fafafa">
        <h3 class="pa-3">Dados de Acesso</h3>
        <v-divider></v-divider>

        <v-row class="w-100" align="start">
          <v-col>
            <v-card class="h-100 mt-5" outlined>
              <v-list-item>
                <v-list-item-content>
                  <div>
                      <v-col>
                        <v-text-field
                          label="Email"
                          outlined
                          dense
                          disabled
                        ></v-text-field>
                      </v-col>
                  </div>
                  <div>
                      <v-col>
                        <v-text-field
                          label="Pass"
                          type="password"
                          outlined
                          dense
                          v-model="pass"
                        ></v-text-field>
                      </v-col>
                  </div>
                  <div></div>
                </v-list-item-content>
              </v-list-item>
            </v-card>
          </v-col>
        </v-row>

        <h3 class="pa-3">Dados Pessoais</h3>
        <v-divider></v-divider>
        <v-row class="w-100" align="start">
          <v-col>
            <v-card class="h-100 mt-5" outlined>
              <v-list-item>
                <v-list-item-content>
                  <div>
                      <v-text-field
                        label="Nome"
                        outlined
                        dense
                        v-model="nome"
                      ></v-text-field>
                  </div>
                  <div>
                      <v-text-field
                        label="Pass"
                        outlined
                        dense
                        v-model="morada"
                      ></v-text-field>
                  </div>
                  <div>
                      <v-text-field
                        label="Freguesia"
                        outlined
                        dense
                        v-model="freguesia"
                      ></v-text-field>
                      <v-text-field
                        label="Concelho"
                        outlined
                        dense
                        v-model="concelho"
                      ></v-text-field>
                  </div>
                </v-list-item-content>
              </v-list-item>
            </v-card>
          </v-col>
        </v-row>

        <h3 class="pa-3">Dados de Contacto</h3>
        <v-divider></v-divider>
        <v-row class="w-100" align="start">
          <v-col>
            <v-card class="h-100 mt-5" outlined>
              <v-list-item>
                <v-list-item-content>
                  <div>
                    
                      <v-text-field
                        label="Telefone"
                        outlined
                        dense
                        v-model="telefone"
                      ></v-text-field>
                  </div>
                  <div>
                      <v-text-field
                        label="NIF"
                        outlined
                        disabled
                        dense
                        v-model="nif"
                      ></v-text-field>
                  </div>
                  <div></div>
                </v-list-item-content>
              </v-list-item>
            </v-card>
          </v-col>
        </v-row>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import moment from "moment";
import axios from "axios";
import store from "@/store.js";
export default {
  props: ["animal"],
  data: () => ({
    pass: "",
    morada: "",
    freguesia: "",
    concelho: "",
    telefone: "",
    utilizador: {},
  }),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
  },
  created: async function () {
    let response = await axios.post(
      "http://localhost:7777/cliente/getpreferencias",
      {
        email: this.$store.state.email,
      },
      {
        headers: {
          Authorization: "Bearer " + store.getters.token.toString(),
        },
      }
    );
    console.log(response);
    this.utilizador = response.data;
  },
};
</script>

<style scoped>
.infos {
  text-align: start;
}
.respos {
  text-align: end;
  font-weight: bold;
}

.body-2 {
  font-size: 0.8rem !important;
}

.head {
  font-size: 2.75rem !important;
}

.font-weight-bold {
  font-size: 15px;
}
.font-weight-regular {
  font-size: 14px;
}

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}
</style>