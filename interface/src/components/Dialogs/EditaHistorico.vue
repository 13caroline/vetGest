<template>
  <v-dialog v-model="dialog" width="100%" max-width="500" persistent>
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-btn
            color="#2596be"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            fab
            dark
            @click="dialog = true"
          >
            <v-icon small> fas fa-pen</v-icon>
          </v-btn>
        </template>
        <span class="caption">Editar histórico clínico</span>
      </v-tooltip>
    </template>
  
    <v-card>
      <v-form>
        <v-card-title class="font-weight-regular text-uppercase">
          Editar histórico clínico
        </v-card-title>
        <v-card-subtitle
          >Por favor preencha o seguinte formulário</v-card-subtitle
        >

        <v-card-text>
          <v-row class="ma-2">
            <v-col class="py-0" cols="5">
              <p class="ma-0">Transfusões</p>
              <v-radio-group row class="ma-0" v-model="historico.transfusoes">
                <v-radio :value="true" color="#2596be">
                  <template v-slot:label>
                    <div class="body-2">Sim</div>
                  </template>
                </v-radio>
                <div class="w-100 d-sm-none"></div>
                <v-radio :value="false" color="#2596be">
                  <template v-slot:label>
                    <div class="body-2">Não</div>
                  </template>
                </v-radio>
              </v-radio-group>
            </v-col>

            <v-col class="py-0">
              <p class="ma-0">Tem alergias?</p>

              <v-radio-group row class="ma-0" v-model="historico.alergias">
                <v-radio :value="true" color="#2596be">
                  <template v-slot:label>
                    <div class="body-2">Sim</div>
                  </template>
                </v-radio>
                <div class="w-100 d-sm-none"></div>
                <v-radio :value="false" color="#2596be">
                  <template v-slot:label>
                    <div class="body-2">Não</div>
                  </template>
                </v-radio>
              </v-radio-group>
            </v-col>
          </v-row>
          <div>
            <v-col class="py-0">
              <p class="ma-0">Alergias</p>
              <v-text-field
                outlined
                color="#2596be"
                dense
                v-model="historico.tipo_alergias"
                :disabled="!historico.alergias"
              ></v-text-field>
            </v-col>
          </div>
          <div>
            <v-col class="py-0">
              <p class="ma-0">Medicação habitual</p>
              <v-textarea
                rows="2"
                outlined
                dense
                color="#2596be"
                v-model="historico.medicacao"
                no-resize
              >
              </v-textarea>
            </v-col>
          </div>
          <div>
            <v-col class="py-0">
              <p class="ma-0">História Ginecológica</p>
              <v-textarea
                rows="2"
                outlined
                dense
                color="#2596be"
                v-model="historico.historia_ginecologica"
                no-resize
              >
              </v-textarea>
            </v-col>
          </div>
          <div>
            <v-col class="py-0">
              <p class="ma-0">Antecedentes familiares</p>
              <v-textarea
                rows="2"
                outlined
                dense
                color="#2596be"
                v-model="historico.antecedentes"
                no-resize
              >
              </v-textarea>
            </v-col>
          </div>

          <div>
            <v-col class="py-0">
              <p class="ma-0">História médica prévia</p>
              <v-textarea
                rows="2"
                outlined
                dense
                color="#2596be"
                v-model="historico.historia_medica"
                no-resize
              >
              </v-textarea>
            </v-col>
          </div>

          <v-row align="end" justify="end">
            <v-col cols="auto">
              <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
            </v-col>
            <v-col cols="auto">
              <v-btn color="#2596be" small dark @click="editar()"
                >Confirmar</v-btn
              >
            </v-col>
          </v-row>
        </v-card-text>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
import Cancelar from "@/components/Dialogs/Cancel.vue";

export default {
  props: ["dados"],
  data: () => ({
    dialogs: {},
    cancelar: {
      title: "edição de histórico clínico",
      text: "a edição do histórico clínico",
    },
    dialog: false,
    historico: {}
  }),
  methods: {
    close() {
      this.dialog = false;
    },
    editar: async function () {
      let route = "http://localhost:7777/medico/animal/historico/alterar";
      await axios
        .post(
          route,
          {
            animal: this.dados.id,
            historico: {
              alergias: this.historico.alergias,
              transfusoes: this.historico.transfusoes,
              tipo_alergias: this.historico.tipo_alergias,
              historia_medica: this.historico.historia_medica,
              historia_ginecologica: this.historico.historia_ginecologica,
              antecedentes: this.historico.antecedentes,
              medicacao: this.historico.medicacao,
            },
          },
          { headers: { Authorization: "Bearer " + store.getters.token } }
        )
        .then((response) => {
          console.log(response);
          this.$emit("clicked", {
            text: "Histórico clínico registado com sucesso.",
            color: "success",
            snackbar: "true",
            timeout: 4000,
          });
          this.dialog = false;
        })
        .catch((error) => {
          console.log(error);
          this.$emit("clicked", {
            text: "Ocorreu um erro, por favor tente mais tarde!",
            color: "warning",
            snackbar: "true",
            timeout: 4000,
          });
        });
    },
  },
  computed: {
    filteredData() {
      let motivo = this.motivos;
      return this.desc.filter((item) => item.tipo === motivo);
    },
  },
  created: async function(){
    let response = await axios.post(
      "http://localhost:7777/medico/animal/historico",
      {
        animal: this.dados.id,
      },
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      }
    );
    this.historico = response.data;
    console.log(this.historico)
  },
  components: {
    Cancelar,
  },
};
</script>
