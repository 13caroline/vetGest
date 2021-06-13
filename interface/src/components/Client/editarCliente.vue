<template>
  <div>
    <v-container>
      <v-card flat color="#fafafa">
        <h3 class="pa-3">Dados de Acesso</h3>
        <v-divider></v-divider>
        <v-form ref="form" lazy-validation class="form">
          <v-row class="w-100" align="start">
            <v-col>
              <v-card class="h-100 mt-5" outlined>
                <v-list-item>
                  <v-list-item-content>
                    <div>
                      Email
                      <v-col>
                        <v-text-field
                          outlined
                          dense
                          :value="utilizador.email"
                          disabled
                        ></v-text-field>
                      </v-col>
                    </div>
                    <div>
                      <v-col>
                        <v-text-field
                          type="password"
                          placeholder="*****"
                          outlined
                          dense
                          v-model="utilizador.password"
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
                        outlined
                        dense
                        v-model="utilizador.nome"
                      ></v-text-field>
                    </div>
                    <div>
                      <v-text-field
                        outlined
                        dense
                        v-model="utilizador.morada"
                      ></v-text-field>
                    </div>
                    <div>
                      <v-text-field
                        outlined
                        dense
                        v-model="utilizador.freguesia"
                      ></v-text-field>
                      <v-text-field
                        outlined
                        dense
                        v-model="utilizador.concelho"
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
                        outlined
                        dense
                        v-model="utilizador.contacto"
                      ></v-text-field>
                    </div>
                    <div>
                      <v-text-field
                        outlined
                        disabled
                        dense
                        v-model="utilizador.nif"
                      ></v-text-field>
                    </div>
                    <div></div>
                  </v-list-item-content>
                </v-list-item>
              </v-card>
            </v-col>
          </v-row>
        </v-form>
      </v-card>
      <v-row align="end" justify="end">
        <v-col cols="auto">
          <!-- <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar> -->
        </v-col>
        <v-col cols="auto">
          <v-btn color="#2596be" small dark @click="editarDados()"
            >Registar</v-btn
          >
        </v-col>
      </v-row>
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

    editarDados: async function () {
      if (this.$refs.form.validate()) {
        try {
          await axios.post(
            "http://localhost:7777/cliente/preferencias",
            {
              email: store.state.email,
              nome: this.utilizador.nome,
              morada: this.utilizador.morada,
              concelho: this.utilizador.concelho,
              freguesia: this.utilizador.freguesia,
              contacto: this.utilizador.contacto,
              password: this.utilizador.password,
            },
            {
              headers: {
                Authorization: "Bearer " + store.getters.token.toString(),
              },
            }
          );
          this.$router.push("/cliente/preferencias/");
          this.$snackbar.showMessage({
            show: true,
            color: "success",
            text: "Dados editados com sucesso.",
            timeout: 4000,
          });
        } catch (e) {
          console.log("erro: " + e);
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