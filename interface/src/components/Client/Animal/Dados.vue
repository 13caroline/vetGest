<template>
  <div class="dadosback">
    <v-row align="center" class="py-3">
      <v-col>
        <h3 class="font-weight-regular text-uppercase">
          <v-icon small>fas fa-paw</v-icon>
          Informações Gerais
        </h3>
      </v-col>
      <v-col cols="auto" class="ml-auto pl-0">
        <v-btn
          class="body-2 ma-2"
          small
          color="#2596be"
          dark
          @click="addFoto()"
        >
          Adicionar Fotografia
          <v-icon small class="ml-4">fas fa-camera</v-icon>
        </v-btn>
        <v-btn
          class="body-2 ma-2"
          small
          color="#2596be"
          dark
          @click="editar()"
        >
          Editar Dados
          <v-icon small class="ml-4">fas fa-pen</v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <h3>Identificação</h3>
    <v-divider class="mb-3"></v-divider>

    <v-row class="w-100" align="stretch">
      <v-col>
        <v-card class="h-100" outlined>
          <v-list-item>
            <v-list-item-content>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Nome</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.nome }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Data de Nascimento</p>
                  </v-col>
                  <v-col>
                    <p class="respos">
                      {{ format(animal.dataNascimento) }}
                    </p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Sexo</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.sexo }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos mb-0">Chip</p>
                  </v-col>
                  <v-col>
                    <p class="respos mb-0">{{ animal.chip }}</p>
                  </v-col>
                </v-row>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
      <v-col>
        <v-card class="h-100" outlined>
          <v-list-item>
            <v-list-item-content>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Espécie</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.especie }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Raça</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.raca }}</p>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Esterilizado</p>
                  </v-col>
                  <v-col>
                    <p v-if="animal.castracao === true" class="respos">Sim</p>
                    <p v-else class="respos">Não</p>
                  </v-col>
                </v-row>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>

      <v-col cols="auto">
        <div class="foto">
          <v-img
            :src="imagem"
            aspect-ratio="1"
            class="grey lighten-2 mx-2 rounded"
            cover
          >
            <template v-slot:placeholder>
              <v-row class="fill-height ma-0" align="center" justify="center">
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

    <h3 class="mt-6">Características</h3>
    <v-divider class="my-3"></v-divider>

    <v-row class="w-100" align="stretch">
      <v-col>
        <v-card class="h-100" outlined>
          <v-list-item>
            <v-list-item-content>
              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Altura</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.altura }} (cm)</p>
                  </v-col>
                </v-row>
              </div>

              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Pelagem</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.pelagem }}</p>
                  </v-col>
                </v-row>
              </div>

              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Cor</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.cor }}</p>
                  </v-col>
                </v-row>
              </div>

              <div>
                <v-row>
                  <v-col>
                    <p class="infos">Cauda</p>
                  </v-col>
                  <v-col>
                    <p class="respos">{{ animal.cauda }}</p>
                  </v-col>
                </v-row>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>

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
  </div>
</template>

<script>
import moment from "moment";
import axios from "axios";
import store from "@/store.js"

export default {
  props: ["animal"],
  data: () => ({
    url: null,
    file: "",
    imagem: "",
    dialog: false,
  }),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
      this.url = URL.createObjectURL(this.file);
    },
    addFoto() {
      this.dialog = true;
    },
    submitFile() {
      let formData = new FormData();

      formData.append("imageFile", this.file);
      formData.append("userid", this.animal.id);
      try {
        axios.post("http://localhost:7777/cliente/adicionaFoto", formData, {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        });
        this.$snackbar.showMessage({
          show: true,
          color: "success",
          text: "Fotografia adicionada com sucesso",
          timeout: 4000,
        });
        this.imagem = this.url;
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
    editar() {
      this.$router.push("/cliente/animal/editar/" + this.animal.id);
    },
  },
  created() {
    console.log(this.animal.image)
     this.imagem = this.animal.image
      ? "data:image/jpeg;charset=utf-8;base64," + this.animal.image
      : require("@/assets/image_placeholder.png");
    this.url = this.imagem;
  }
};
</script>

<style scoped>
.dadosback{
  background-color: #fafafa;
}

.foto {
  width: 200px;
}

.body-2 {
  font-size: 0.8rem !important;
}

.head {
  font-size: 2.75rem !important;
}

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