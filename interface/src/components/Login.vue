<template>
  <div id="login">
    <v-img src="../assets/login.png" class="img">
      <v-container>
        <v-row align="center" justify="center" class="login_wrapper">
          <v-card color="white" flat class="loginform my-12">
            <v-card-text class="justify-center">
              <v-form ref="form" lazy-validation class="form">
                <v-text-field
                  background-color="transparent"
                  solo
                  flat
                  dense
                  single-line
                  color="black"
                  placeholder="Email"
                  type="email"
                  name="email"
                  v-model="email"
                  prepend-icon="fas fa-user"
                  required
                />
                <v-row align="center" justify="center">
                  <v-card flat class="divider">
                    <v-divider></v-divider>
                  </v-card>
                </v-row>
                <v-text-field
                  background-color="transparent"
                  solo
                  flat
                  single-line
                  color="black"
                  prepend-icon="fas fa-lock"
                  name="password"
                  placeholder="Palavra-Passe"
                  type="password"
                  v-model="password"
                  required
                />
              </v-form>
            </v-card-text>
            <v-card-actions class="card-actions mt-n6">
              <v-col>
                <v-btn
                  tile
                  depressed
                  x-large
                  dark
                  block
                  color="#2596be"
                  type="submit"
                  @click="loginUtilizador()"
                >
                  Iniciar Sessão
                </v-btn>

                <v-row>
                  <v-spacer></v-spacer>
                  <v-btn
                    depressed
                    tile
                    class="mr-3 mt-6 grey--text"
                    x-small
                    color="transparent"
                  >
                    Esqueceu a palavra-passe?
                  </v-btn>
                </v-row>
              </v-col>
            </v-card-actions>
          </v-card>
        </v-row>
      </v-container>
    </v-img>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "mainpage",
  data() {
    return {
      email: "",
      password: "",
      text: "",
      color: "",
    };
  },
  methods: {
    async loginUtilizador() {
      if (this.$refs.form.validate()) {
        try {
          var res = await axios.post("http://localhost:7777/authenticate", {
            username: this.email,
            password: this.password,
          });
          if (res.data.jwt != undefined) {
            this.$store.commit("guardaTokenUtilizador", res.data.jwt);
            this.$store.commit("guardaTipoUtilizador", res.data.dtype);
            this.$store.commit("guardaEmailUtilizador", this.email);
            if (res.data.dtype == "Cliente") {
              this.$router.push("/cliente/inicio");
            }
            if (res.data.dtype == "Clinica") {
              this.$router.push("/clinica/inicio");
            }
            if (res.data.dtype == "Veterinario") {
              this.$router.push("/medico/inicio");
            }
          }
        } catch (error) {
          if (error.response.data == "Email ou password estão errados") {
            this.text = "O email ou palavra-passe introduzidos estão errados.";
            this.color = "error";
          } else {
            this.text = "Ocorreu um erro. Por favor tente mais tarde!";
            this.color = "warning";
          }
          // Nota snackbar
          this.$snackbar.showMessage({
            show: true,
            color: this.color,
            text: this.text,
            timeout: 4000,
          });
        }
      }
    },
  },
};
</script>


<style scoped>
.divider {
  width: 80%;
  height: 3vh;
}

.card-actions {
  margin-top: 1px;
  width: 100%;
  margin-left: auto;
  margin-right: auto;
}

.form {
  margin-top: 1rem;
}

.img {
  height: 70vh;
  width: 100vw;
}

.login_wrapper {
  min-height: 60vh;
}

.loginform {
  width: 100%;
  max-width: 475px;
}
</style>