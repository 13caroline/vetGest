<template>
  <div>
    <v-img src="../assets/login.png" class="img">
      <v-row align="center" justify="center" class="my-5">
        <v-card color="white" flat class="registerform my-12 mx-5">
          <v-card-text class="justify-center mt-2">
            <v-form ref="form" v-model="valid" lazy-validation>
              <span>Dados de Acesso</span>
              <v-text-field
                outlined
                flat
                dense
                single-line
                color="#2596be"
                placeholder="E-mail *"
                name="email"
                v-model="email"
                required
                :rules="emailRules"
                class="mt-4"
              />

              <v-text-field
                outlined
                flat
                dense
                single-line
                type="password"
                color="#2596be"
                name="password"
                v-model="password"
                :rules="passwordRules"
                placeholder="Palavra-Passe *"
                required
              />

              <span>Dados Pessoais</span>
              <v-text-field
                outlined
                class="mt-4"
                flat
                dense
                single-line
                :rules="nameRules"
                color="#2596be"
                placeholder="Nome *"
                v-model="nome"
                name="nome"
                required
              />

              <v-text-field
                outlined
                flat
                dense
                v-model="rua"
                single-line
                :rules="ruaRules"
                color="#2596be"
                name="rua"
                placeholder="Morada *"
                required
              />

              <v-row>
                <v-col>
                  <v-text-field
                    outlined
                    flat
                    dense
                    single-line
                    v-model="freguesia"
                    :rules="freguesiaRules"
                    color="#2596be"
                    name="freguesia"
                    placeholder="Freguesia *"
                    required
                  />
                </v-col>

                <v-col>
                  <v-text-field
                    outlined
                    flat
                    dense
                    single-line
                    color="#2596be"
                    :rules="concelhoRules"
                    name="concelho"
                    placeholder="Concelho *"
                    v-model="concelho"
                    required
                  />
                </v-col>
              </v-row>

              <span>Dados de Contacto</span>
              <v-row>
                <v-col cols="auto dial_code">
                  <v-text-field
                    class="mt-4"
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
                <v-col>
                  <v-text-field
                    class="mt-4"
                    outlined
                    flat
                    dense
                    single-line
                    color="#2596be"
                    placeholder="Contacto telefónico *"
                    name="contacto"
                    v-model="contacto"
                    maxlength="9"
                    required
                  />
                </v-col>
              </v-row>
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
              />
            </v-form>
            <span class="ma-0 caption">* Campos obrigatórios</span>
            <v-row justify="end">
              <v-btn
                class="mr-3 mb-2"
                tile
                depressed
                large
                dark
                color="#BDBDBD"
                @click="openDialog()"
              >
                Cancelar
              </v-btn>

              <v-btn
                class="mr-3 mb-2"
                tile
                depressed
                large
                dark
                color="#2596be"
                type="submit"
                @click="registUser()"
              >
                Registar
              </v-btn>
            </v-row>
          </v-card-text>
        </v-card>
      </v-row>
    </v-img>

    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
      <v-card>
        <v-card-title class="justify-center cancel">
          Cancelar Registo
        </v-card-title>
        <v-card-text>
          Tem a certeza que pretende cancelar o seu registo?
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
            @click="close()"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      valid: true,
      dialog: false,
      done: false,
      email: "",
      password: "",
      nome: "",
      rua: "",
      freguesia: "",
      concelho: "",
      contacto: "",
      nif: "",
      emailRules: [
        (value) => !!value || "E-mail inválido",
        (value) => {
          const pattern =
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
          return pattern.test(value) || "E-mail inválido";
        },
      ],
      passwordRules: [
        (v) => !!v || "Palavra-passe inválida",
        (v) => /(?=.*[A-Z])/.test(v) || "Deve ter uma letra maiúscula",
        (v) => /(?=.*\d)/.test(v) || "Deve ter um número",
        (v) =>
          (v && v.length >= 5) ||
          "A palavra-passe deve ter pelo menos 5 caracteres",
      ],
      nameRules: [(v) => !!v || "Nome inválido"],
      ruaRules: [(v) => !!v || "Rua inválida"],
      freguesiaRules: [(v) => !!v || "Freguesia inválida"],
      concelhoRules: [(v) => !!v || "Concelho inválido"],
      contactoRules: [(v) => !!v || "Contacto inválido"],
    };
  },
  methods: {
    openDialog() {
      this.dialog = true;
    },
    close() {
      (this.dialog = false), this.$emit("clicked", "cancel");
    },
    reset() {
      this.$refs.form.reset();
      this.dialog = false;
    },
    registUser: async function () {
      if (this.$refs.form.validate()) {
        try {
          await axios.post("http://localhost:7777/cliente/registar", {
            email: this.email,
            password: this.password,
            concelho: this.concelho,
            contacto: this.contacto,
            freguesia: this.freguesia,
            morada: this.rua,
            nif: this.nif,
            nome: this.nome,
          });

          this.$emit("registado", {
            text: "Utilizador criado com sucesso.",
            color: "success",
            snackbar: true,
            timeout: 4000,
          });
        } catch (e) {
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
};
</script>


<style scoped>
.dial_code {
  width: 90px;
}

span {
  color: "#2596be";
  font-size: 20px;
}

.cancel {
  font-size: 25px !important;
}
</style>