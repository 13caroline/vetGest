<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row class="w-100">
            <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
              <v-icon class="mr-2">fas fa-user</v-icon>
              Registar novo cliente
            </h3>
          </v-row>
          <div class="indication">
            <span class="body-2"
              >Por favor preencha o seguinte formulário
            </span>
          </div>

          <v-form ref="form" v-model="valid" lazy-validation>
            <p class="mb-0 mt-4">Nome completo *</p>
            <v-text-field
              outlined
              flat
              dense
              single-line
              :rules="textRules"
              color="#2596be"
              placeholder="Nome"
              v-model="nome"
              name="nome"
              required
            />

            <p class="ma-0">E-mail *</p>
            <v-text-field
              outlined
              flat
              dense
              v-model="email"
              single-line
              :rules="emailRules"
              color="#2596be"
              name="email"
              placeholder="E-mail"
              required
            />

            <v-row class="mt-1">
              <v-col cols="12" sm="6" class="py-0">
                <p class="ma-0">Morada *</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  v-model="rua"
                  single-line
                  color="#2596be"
                  name="rua"
                  placeholder="Morada"
                  required
                />
              </v-col>
              <v-col class="py-0">
                <p class="ma-0">Freguesia *</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  v-model="freguesia"
                  :rules="textRules"
                  color="#2596be"
                  name="freguesia"
                  placeholder="Freguesia"
                  required
                />
              </v-col>

              <v-col class="py-0">
                <p class="ma-0">Concelho *</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  color="#2596be"
                  :rules="textRules"
                  name="concelho"
                  placeholder="Concelho"
                  v-model="concelho"
                  required
                />
              </v-col>
            </v-row>

            <v-row align="end">
              <v-col cols="auto" class="dial_code py-0">
                <v-text-field
                  outlined
                  flat
                  background-color="#E0E0E0"
                  dense
                  single-line
                  color="#2596be"
                  value="+351"
                  readonly
                />
              </v-col>
              <v-col class="py-0">
                <p class="ma-0">Contacto telefónico *</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  color="#2596be"
                  placeholder="Contacto telefónico"
                  name="contacto"
                  v-model="contacto"
                  :rules="contactoRules"
                  maxlength="9"
                  required
                />
              </v-col>
              <v-col cols="12" sm class="py-0">
                <p class="ma-0">Número de identificação fiscal</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  color="#2596be"
                  placeholder="Número de Identificação Fiscal"
                  name="nif"
                  v-model="nif"
                  maxlength="9"
                  required
                  :rules="nifRules"
                />
              </v-col>
            </v-row>
          </v-form>
          <span class="ma-0 caption">* Campos obrigatórios</span>
          <v-row align="end" justify="end">
            <v-col cols="auto" class="pr-0">
              <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
            </v-col>
            <v-col cols="auto">
              <v-btn
                color="#2596be"
                class="white--text"
                small
                :disabled="!valid"
                @click="registarCliente()"
                >Registar</v-btn
              >
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
//import moment from 'moment';
import axios from "axios";
import store from "@/store.js";
import Cancelar from "@/components/Dialogs/Cancel.vue";
export default {
  data: () => ({
    dialogs: {},
    valid: false,
    cancelar: { title: "registo de cliente", text: "o registo de um cliente" },
    nome: "",
    email: "",
    rua: "",
    freguesia: "",
    concelho: "",
    contacto: "",
    nif: "",
    emailRules: [
      (value) => !!value || "Insira um endereço eletrónico.",
      (value) => {
        const pattern =
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return pattern.test(value) || "E-mail inválido";
      },
    ],
    textRules: [
      (v) => !!v || "Este campo não pode estar vazio.",
      (v) => {
        const pattern = /^[a-zA-Z\sÀ-ÿ]+$/;
        return (
          pattern.test(v) ||
          "Campo inválido. Insira apenas caracteres do alfabeto."
        );
      },
    ],
    contactoRules: [
      (v) => !!v || "Insira um contacto telefónico.",
      (v) => {
        const pattern = /^[0-9]{9}$/;
        return (
          pattern.test(v) || "Contacto telefónico inválido. Insira 9 dígitos."
        );
      },
    ],
    nifRules: [
      (v) => {
        const pattern = /^[0-9]{9}$/;
        return (
          pattern.test(v) ||
          "Número de identificação fiscal inválido. Insira 9 dígitos."
        );
      },
    ],
  }),
  components: {
    Cancelar,
  },
  methods: {
    close() {
      this.$router.push("/clinica/clientes");
    },
    registarCliente: async function () {
      if (this.$refs.form.validate()) {
        try {
          await axios.post(
            "http://localhost:7777/clinica/clientes/registar",
            {
              email: this.email,
              password: "1234",
              concelho: this.concelho,
              contacto: this.contacto,
              freguesia: this.freguesia,
              morada: this.rua,
              nif: this.nif,
              nome: this.nome,
            },
            { headers: { Authorization: "Bearer " + store.getters.token } }
          );
          this.text = "Utilizador criado com sucesso.";
          this.color = "success";

          this.$snackbar.showMessage({
            show: true,
            color: "warning",
            text: "Ocorreu um erro, por favor tente mais tarde!",
            timeout: 4000,
          });

          this.$router.push("/clinica/clientes");
        } catch (error) {
          if (error.response.data == "Email já se encontra registado") {
            this.$snackbar.showMessage({
              show: true,
              color: "warning",
              text: "Este email já se encontra registado!",
              timeout: 4000,
            });
          } else {
            this.$snackbar.showMessage({
              show: true,
              color: "warning",
              text: "Ocorreu um erro no registo, por favor tente mais tarde!",
              timeout: 4000,
            });
          }
        }
      } else {
        this.$snackbar.showMessage({
          show: true,
          color: "error",
          text: "Por favor preencha todos os campos.",
          timeout: 4000,
        });
        this.done = false;
      }
    },
  },
};
</script> 

<style scoped>
.dial_code {
  width: 90px;
}
</style>
