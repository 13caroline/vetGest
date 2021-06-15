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
        <v-col cols="auto" class="ml-auto pl-0" >
          <v-tooltip top v-if="this.$store.state.tipo == 'Veterinario'">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="body-2 mx-2"
                small
                color="#2596be"
                v-bind="attrs"
                v-on="on"
                fab
                dark
                @click="addFoto()"
              >
                <v-icon small>fas fa-camera</v-icon>
              </v-btn>
            </template>
            <span class="caption">Adicionar fotografia</span>
          </v-tooltip>

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
                @click="preferencias()"
              >
                <v-icon small>fas fa-pen</v-icon>
              </v-btn>
            </template>
            <span class="caption">Editar dados</span>
          </v-tooltip>
        </v-col>
      </v-row>
      <v-card flat color="#fafafa">
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

          <v-col cols="auto" v-if="this.$store.state.tipo == 'Veterinario'">
            <div class="foto h-100 mt-5" >
              <v-img
                :src="imagem"
                aspect-ratio="1"
                class="grey lighten-2 mx-2 rounded"
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
            </div>
          </v-col>
        </v-row>

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
                      <v-col v-if="this.$store.state.tipo == 'Clinica'">
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
      <v-dialog v-model="dialog" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title>Adicionar fotografia</v-card-title>
          <v-card-text>
            <v-row justify="center" align="center">
              <div class="foto">
                <v-img
                  :src="url"
                  aspect-ratio="1"
                  class="grey lighten-2 ma-2 rounded"
                  cover
                >
                  <template v-slot:placeholder>
                    <v-row
                      class="fill-height ma-0"
                      align="center"
                      justify="center"
                    >
                    </v-row>
                  </template>
                </v-img>
              </div>
            </v-row>
            <v-row>
              <input
                type="file"
                id="file"
                ref="file"
                v-on:change="handleFileUpload()"
              />
            </v-row>
            <v-row align="end" justify="end">
              <v-col cols="auto">
                <v-btn
                  color="#BDBDBD"
                  class="mx-2"
                  small
                  dark
                  @click="dialog = false"
                  >Cancelar</v-btn
                >
                <v-btn color="#2596be" small dark @click="submitFile()"
                  >Registar</v-btn
                >
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>
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
    dialog: false,
    url: null,
    file: "",
    imagem: "",
  }),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
    preferencias() {
      store.state.tipo == "Clinica"
        ? this.$router.push("/clinica/preferencias/editar")
        : this.$router.push("/medico/preferencias/editar");
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
      this.url = URL.createObjectURL(this.file);
    },
    addFoto() {
      this.dialog = true;
    },
    submitFile() {
      console.log(this.file)
      let formData = new FormData();

      formData.append("imageFile", this.file);
      formData.append("userid", this.utilizador.id)
      try {
        axios.post(
          "http://localhost:7777/medico/adicionaFoto",
          formData,
          {
            headers: {
              Authorization: "Bearer " + store.getters.token.toString(),
            },
          }
        );
        this.$snackbar.showMessage({
            show: true,
            color: "success",
            text: "Fotografia adicionada com sucesso",
            timeout: 4000,
          });
          this.dialog = false;
      } catch (e) {
        this.$snackbar.showMessage({
            show: true,
            color: "success",
            text: "Ocorreu um erro, por favor tente mais tarde!",
            timeout: 4000,
          });
      }
    },
  },
  created: async function () {
    let route =
      store.state.tipo == "Clinica"
        ? "http://localhost:7777/clinica"
        : "http://localhost:7777/medico";
    let response = await axios.post(
      route,
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
     this.imagem = 'data:image/jpeg;base64,' + btoa(response.data.image);
    console.log(this.imagem)
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

.foto {
  width: 200px;
}
</style>