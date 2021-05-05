<template>
  <div>
    <v-img src="../assets/login.png" class="img">
      <v-row align="center" justify="center" class="my-5">
        <v-card color="white" flat class="registerform my-12 mx-5">
          <v-card-text class="justify-center mt-4">
            <v-form ref="form" v-model="valid" lazy-validation>
              <span>Dados de Acesso</span>
              <v-text-field
                outlined
                flat
                dense
                single-line
                color="#2596be"
                placeholder="E-mail"
                type="email"
                name="email"
                required
                rules="rulesEmail"
                class="mt-4"
              />

              <v-text-field
                outlined
                flat
                dense
                single-line
                color="#2596be"
                name="password"
                placeholder="Palavra-Passe"
                type="password"
                required
              />

              <span>Dados Pessoais</span>
              <v-text-field
                outlined
                class="mt-4"
                flat
                dense
                single-line
                color="#2596be"
                placeholder="Nome"
                type="nome"
                name="nome"
                required
              />

              <v-text-field
                outlined
                flat
                dense
                single-line
                color="#2596be"
                name="rua"
                placeholder="Rua"
                type="rua"
                required
              />

              <v-row>
                <v-col>
                  <v-text-field
                    outlined
                    flat
                    dense
                    single-line
                    color="#2596be"
                    name="freguesia"
                    placeholder="Freguesia"
                    type="freguesia"
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
                    name="concelho"
                    placeholder="Concelho"
                    type="concelho"
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
                    placeholder="Contacto telefónico"
                    type="number"
                    name="contacto"
                    required
                  />
                </v-col>
              </v-row>
            </v-form>
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
              >
                Registar
              </v-btn>
            </v-row>
          </v-card-text>

          <v-snackbar
            v-model="snackbar1"
            :timeout="timeout"
            :color="color"
            :top="true"
            class="headline"
          >
            {{ text }}
          </v-snackbar>
        </v-card>
      </v-row>
    </v-img>

    <v-dialog v-model="dialog" persistent width="30%">
      <v-card>
        <v-card-title class="justify-center cancel"> Cancelar Registo </v-card-title>
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
          <v-btn depressed large dark color="#2596be" width="50%"> Sim </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      valid: true,
      dialog: false,
      email: "",
      password: "",
      nome: "",
      rua: "",
      freguesia: "",
      concelho: "",
      contacto: "",
      emailRules: [
        (value) => !!value || "E-mail inválido",
        (value) => {
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
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
    reset() {
      this.$refs.form.reset();
      this.dialog = false;
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
  font-size: 25px;
}

.cancel{
  font-size: 25px !important;
}
</style>