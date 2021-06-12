<template>
  <div>
    <v-container>
      <v-row class="w-100 mt-4 mb-0" align="center">
        <v-col cols="auto">
          <h3 class="font-weight-regular text-uppercase">
            <v-icon class="mr-2">fas fa-cog</v-icon>
            Preferências
          </h3>
        </v-col>
        <div class="w-100 d-sm-none"></div>
        <v-col cols="auto" class="ml-auto pl-0">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2"
                small
                color="#2596be"
                v-bind="attrs"
                v-on="on"
                fab
                dark
                to="/clinica/clientes/registar"
              >
                <v-icon small>fas fa-pen</v-icon>
              </v-btn>
            </template>
            <span class="caption">Editar dados</span>
          </v-tooltip>
        </v-col>
      </v-row>
      <v-card flat color="#fafafa">
        <h3 class="pa-3">Dados de Acesso</h3>
        <v-divider></v-divider>

        <v-row class="w-100" align="start">
          <v-col>
            <v-card class="h-100 mt-5" outlined>
              <v-list-item>
                <v-list-item-content>
                  <div>
                    <v-row>
                      <v-col>
                        <p class="infos">E-mail</p>
                      </v-col>
                      <v-col>
                        <p class="respos">{{ utilizador.email }}</p>
                      </v-col>
                    </v-row>
                  </div>
                  <div>
                    <v-row>
                      <v-col>
                        <p class="infos">Palavra-passe</p>
                      </v-col>
                      <v-col>
                        <p class="respos">*****</p>
                      </v-col>
                    </v-row>
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
                    <v-row>
                      <v-col>
                        <p class="infos">Nome</p>
                      </v-col>
                      <v-col>
                        <p class="respos">{{ utilizador.nome }}</p>
                      </v-col>
                    </v-row>
                  </div>
                  <div>
                    <v-row>
                      <v-col>
                        <p class="infos">Morada</p>
                      </v-col>
                      <v-col>
                        <p class="respos">{{ utilizador.morada }}</p>
                      </v-col>
                    </v-row>
                  </div>
                  <div>
                    <v-row>
                      <v-col>
                        <p class="infos mb-0">Freguesia</p>
                      </v-col>
                      <v-col>
                        <p class="respos mb-0">{{ utilizador.freguesia }}</p>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col>
                        <p class="infos mb-0">Concelho</p>
                      </v-col>
                      <v-col>
                        <p class="respos mb-0">{{ utilizador.concelho }}</p>
                      </v-col>
                    </v-row>
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
                    <v-row>
                      <v-col>
                        <p class="infos">Número de telefone</p>
                      </v-col>
                      <v-col>
                        <p class="respos">{{ utilizador.contacto }}</p>
                      </v-col>
                    </v-row>
                  </div>
                  <div>
                    <v-row>
                      <v-col>
                        <p class="infos">Número de identificação fiscal</p>
                      </v-col>
                      <v-col>
                        <p class="respos">{{ utilizador.nif }}</p>
                      </v-col>
                    </v-row>
                  </div>
                  <div v-if="this.$store.state.tipo == 'Veterinario'">
                    <v-row>
                      <v-col>
                        <p class="infos">IBAN</p>
                      </v-col>
                      <v-col>
                        <p class="respos">{{ utilizador.iban }}</p>
                      </v-col>
                    </v-row>
                  </div>
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
    utilizador: {},
  }),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
  },
  created: async function () {
    let response = await axios.post(
      "http://localhost:7777/clinica",
      {
        email: this.$store.state.email,
      },
      {
        headers: {
          Authorization: "Bearer " + store.getters.token.toString(),
        },
      }
    );
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

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}


</style>