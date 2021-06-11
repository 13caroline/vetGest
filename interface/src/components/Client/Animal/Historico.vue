<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row class="w-100 pt-9">
        <v-col>
          <h3 class="font-weight-regular text-uppercase">
            <v-icon small>fas fa-notes-medical</v-icon>
            Histórico Clínico
          </h3>
        </v-col>
      </v-row>

      <v-row class="w-100" align="stretch">
        <v-col>
          <v-card class="h-100" outlined>
            <v-list-item>
              <v-list-item-content>
                <div>
                  <v-row>
                    <v-col>
                      <p class="infos">Tem alergias?</p>
                    </v-col>
                    <v-col>
                      <p class="respos" v-if="historico.alergias">Sim</p>
                      <p class="respos" v-else>Não</p>
                    </v-col>
                  </v-row>
                </div>
                <div>
                  <v-row>
                    <v-col>
                      <p class="infos">Alergias</p>
                    </v-col>
                    <v-col>
                      <p class="respos">{{ historico.tipo_alergias }}</p>
                    </v-col>
                  </v-row>
                </div>
                <div>
                  <v-row>
                    <v-col>
                      <p class="infos mb-0">Transfusões</p>
                    </v-col>
                    <v-col>
                      <p class="respos" v-if="historico.transfusoes">Sim</p>
                      <p class="respos" v-else>Não</p>
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
                  <p class="font-weight-bold">Medicação habitual</p>
                </div>
                <div>
                  <p class="infos" v-if="historico.medicacao == ''">
                    Sem medicação registada.
                  </p>
                  <p class="infos" v-else>{{ historico.medicacao }}</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
        <v-col cols="12">
          <v-card class="h-100" outlined>
            <v-list-item>
              <v-list-item-content>
                <div>
                  <p class="font-weight-bold">História médica prévia</p>
                </div>
                <div>
                  <p class="infos" v-if="historico.historia_medica == ''">
                    Sem história médica prévia.
                  </p>
                  <p class="infos" v-else>{{ historico.historia_medica }}</p>
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
                  <p class="font-weight-bold">Antecedentes familiares</p>
                </div>
                <div>
                  
                  <p class="infos" v-if="historico.antecedentes == ''">
                    Sem antecedentes familiares.
                  </p>
                  <p class="infos" v-else>{{ historico.antecedentes }}</p>
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
                  <p class="font-weight-bold">História Ginecológica</p>
                </div>
                <div>
                  <p class="infos" v-if="historico.historia_ginecologica == ''">
                    Sem história ginecológica.
                  </p>
                  <p class="infos" v-else>{{ historico.historia_ginecologica }}</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>
    </v-card>

    <v-snackbar
      v-model="snackbar"
      :timeout="timeout"
      :color="color"
      :top="true"
      class="headline"
    >
      {{ text }}
    </v-snackbar>
  </div>
</template>

<script>
import moment from "moment";
import axios from "axios";
import store from "@/store.js";
import EditaHistorico from "@/components/Dialogs/EditaHistorico.vue";
import RegistaHistorico from "@/components/Dialogs/RegistaHistorico.vue";
export default {
  props: ["animal"],
  data: () => ({
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",
    historico: {},
  }),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },

  },
  components: {
    EditaHistorico,
    RegistaHistorico,
  },
  created: async function () {
    let response = await axios.post(
      "http://localhost:7777/cliente/animal/historico",
      {
        animal: this.animal.id,
        cliente: this.$store.state.email,
      },
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      }
    );
    this.historico = response.data;
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