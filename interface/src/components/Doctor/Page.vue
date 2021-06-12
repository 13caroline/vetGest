<template>
  <div>
    <v-container class="fill-height">
      <v-row class="w-100 my-4" align="center">
        <v-col cols="auto">
          <h3 class="font-weight-regular text-uppercase">
            <v-icon class="mr-2">fas fa-calendar-alt</v-icon>
            Agendamento
          </h3>
        </v-col>
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
                to="/medico/consultas/"
              >
                <v-icon>fas fa-scroll</v-icon>
              </v-btn>
            </template>
            <span class="caption">Agendar consulta</span>
          </v-tooltip>
        </v-col>
        <v-col cols="auto" class="pl-0">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-bind="attrs"
                v-on="on"
                fab
                class="body-2"
                small
                color="#2596be"
                dark
                to="/medico/cirurgias/"
              >
                <v-icon>fas fa-band-aid</v-icon>
              </v-btn>
            </template>
            <span class="caption">Agendar cirurgia</span>
          </v-tooltip>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <v-data-table
            :headers="headers"
            :items="agendamentos"
            class="elevation-1"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
          >
            <template v-slot:[`item.utente`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    color="blue"
                    v-bind="attrs"
                    v-on="on"
                    x-small
                    @click="utente(item)"
                  >
                    fas fa-book
                  </v-icon>
                </template>
                <span class="caption">Dados do utente</span>
              </v-tooltip>
              <span class="ml-2 mr-1 blue--text">{{ item.animal.nome }}</span>
              <span>({{ item.animal.especie }})</span>
            </template>

            <template v-slot:[`item.servico`]="{ item }">
              <v-chip :color="servico(item.tipo)" small>
                {{ item.tipo }}
              </v-chip>
            </template>

            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estado(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>
            <template v-slot:[`item.detalhes`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    class="mx-1"
                    color="#66BB6A"
                    v-bind="attrs"
                    v-on="on"
                    small
                    :disabled="item.estado != 'A decorrer'"
                  >
                    mdi-clipboard-check-outline
                  </v-icon>
                </template>
                <span v-if="item.servico == 'Consulta'" class="caption"
                  >Concluir consulta</span
                >
                <span v-else class="caption">Concluir cirurgia</span>
              </v-tooltip>

              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    class="mx-1"
                    color="#E57373"
                    v-bind="attrs"
                    v-on="on"
                    small
                    @click="abrirInternamento(item)"
                    >fas fa-procedures</v-icon
                  >
                </template>
                <span>Admitir em internamento</span>
              </v-tooltip>

              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    class="mx-1"
                    color="#E57373"
                    v-bind="attrs"
                    v-on="on"
                    small
                    :disabled="item.estado == 'A decorrer'"
                    @click="cancela(item)"
                  >
                    mdi-close-circle
                  </v-icon>
                </template>
                <span class="caption">Cancelar agendamento</span>
              </v-tooltip>
            </template>
          </v-data-table>
          <div class="text-center pt-2">
            <v-pagination
              v-model="page"
              :length="pageCount"
              circle
              :total-visible="4"
              color="#2596be"
              class="custom"
            ></v-pagination>
          </div>
        </v-col>
      </v-row>

      <v-dialog v-model="concluir" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="cancel">Terminar consulta</v-card-title>
          <v-card-text>
            <v-row class="mt-2">
              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Nome do Animal</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>{{ details_item.animal.nome }}</strong>
                  ({{ details_item.animal.raca }})
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Motivo da Consulta</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>{{ details_item.motivo }}</strong>
                </span>
                <br />
                <span>{{ details_item.descricao }}</span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Data</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>{{
                    details_item.data + " " + details_item.hora
                  }}</strong>
                </span>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12">
                <v-row align="center" class="mt-5">
                  <v-col>
                    <h3 class="font-weight-regular text-uppercase">
                      <v-icon small>fas fa-file-medical</v-icon>
                      Notas médicas
                    </h3>
                  </v-col>
                </v-row>
                <v-row align="center">
                  <v-col>
                    <v-textarea
                      clearable
                      clear-icon="mdi-close-circle"
                      rows="3"
                      outlined
                      color="#2596be"
                      no-resize
                      v-model="observacoes"
                    ></v-textarea>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="concluir = false"
            >
              Cancelar
            </v-btn>
            <v-btn
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              @click="updateStatus('Concluída')"
            >
              Admitir
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="cancelar" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="cancel"> Cancelar agendamento </v-card-title>
          <v-card-text>
            <v-row class="mt-2">
              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Nome do Animal</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>{{ details_item.animal.nome }}</strong>
                  ({{ details_item.animal.raca }})
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Motivo da Consulta</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>{{ details_item.motivo }}</strong>
                </span>
                <br />
                <strong>{{ details_item.descricao }}</strong>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Data</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>{{
                    details_item.data + " " + details_item.hora
                  }}</strong>
                </span>
              </v-col>
            </v-row>

            <p class="mt-12">Tem a certeza que pretende cancelar a consulta?</p>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="cancelar = false"
            >
              Não
            </v-btn>
            <v-btn
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              @click="updateStatus('Cancelada')"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="internamento" width="100%" max-width="500" persistent>
        <v-card>
          <v-form>
            <v-card-title class="font-weight-regular text-uppercase">
              Admitir em internamento
            </v-card-title>
            <v-card-subtitle
              >Por favor preencha o seguinte formulário</v-card-subtitle
            >
            <v-card-text>
              <v-row align="center">
                <v-col cols="12" class="pb-0">
                  <p class="ma-0">Motivo de internamento</p>
                  <v-textarea
                    no-resize
                    dense
                    color="#2596be"
                    flat
                    outlined
                    rows="2"
                    v-model="motivo"
                  ></v-textarea>
                </v-col>

                <v-col cols="12" class="py-0">
                  <p class="ma-0">Nota de admissão</p>
                  <v-textarea
                    color="#2596be"
                    flat
                    outlined
                    rows="5"
                    no-resize
                    v-model="nota"
                  ></v-textarea>
                </v-col>

                <v-col cols="12" class="py-0">
                  <p class="ma-0">Localização</p>
                  <v-text-field
                    color="#2596be"
                    flat
                    outlined
                    dense
                    no-resize
                    v-model="localizacao"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row align="end" justify="end">
                <v-col cols="auto">
                  <Cancelar
                    :dialogs="cancelar"
                    @clicked="internamento = false"
                  ></Cancelar>
                </v-col>
                <v-col cols="auto">
                  <v-btn
                    color="#2596be"
                    small
                    dark
                    @click="admitirInternamento()"
                    >Admitir</v-btn
                  >
                </v-col>
              </v-row>
            </v-card-text>
          </v-form>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>


<script>
import axios from "axios";
import store from "@/store.js";
import Cancelar from "@/components/Dialogs/Cancel.vue";
export default {
  data() {
    return {
      cancelar: false,
      concluir: false,
      internamento: false,
      details_item: { animal: {} },
      page: 1,
      pageCount: 0,
      itemsPerPage: 8,
      search: "",
      observacoes: "",
      motivo: "",
      nota: "",
      localizacao: "",
      headers: [
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "utente",
        },
        {
          text: "DATA",
          value: "data",
          sortable: true,
          align: "start",
        },
        {
          text: "MOTIVO",
          value: "motivo",
          sortable: true,
          align: "start",
        },
        {
          text: "SERVIÇO",
          value: "servico",
          sortable: true,
          align: "start",
        },
        {
          text: "ESTADO",
          value: "estado",
          sortable: true,
          align: "start",
        },
        {
          text: "AÇÕES",
          value: "detalhes",
          sortable: false,
          align: "start",
        },
      ],

      agendamentos: [],
    };
  },
  methods: {
    conclude(item) {
      this.concluir = true;
      this.details_item = item;
    },
    cancela(item) {
      this.cancelar = true;
      this.details_item = item;
    },
    abrirInternamento(item) {
      this.internamento = true;
      this.details_item = item;
    },
    estado(item) {
      if (item == "Agendada") return "#C5E1A5";
      else return "#FFECB3";
    },
    servico(item) {
      if (item == "Consulta") return "#B2DFDB";
      else return "#FFCCBC";
    },
    utente(item) {
      this.$router.push("/medico/utente/" + item.animal.id);
    },
    admitirInternamento: async function () {
      try {
        let res = await axios.post(
          "http://localhost:7777/medico/internar",
          {
            email: this.$store.state.email,
            intervencao: this.details_item.id,
            animal: this.details_item.animal.id,
            internamento: {
              motivo: this.motivo,
              nota: this.nota,
              localizacao: this.localizacao,
            },
          },
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );

        if (res) {
          this.internamento = false;
        }
      } catch (e) {
        console.log(e);
      }
    },
    updateStatus: async function (status) {
      try {
        let res = await axios.post(
          "http://localhost:7777/medico/intervencao/alterar",
          {
            id: this.details_item.id,
            estado: status,
            observacoes: status !== "Concluída" ? null : this.observacoes,
          },
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );
        if (res) {
          this.cancelar = false;
          this.concluir = false;
          this.created();
        }
      } catch (e) {
        console.log(e);
      }
    },
  },
  components: {
    Cancelar,
  },
  created: async function () {
    try {
      let response = await axios.post(
        "http://localhost:7777/medico/intervencoes",
        { email: this.$store.state.email },
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );
      if (typeof response.data == "object") this.agendamentos = response.data;
    } catch (e) {
      console.log(e);
    }
  },
};
</script>

<style>
.custom {
  width: auto;
  margin-left: auto;
}

.custom .v-pagination__navigation {
  height: 26px !important;
  width: 26px !important;
}

.custom .v-pagination__navigation .v-icon {
  font-size: 16px !important;
}

.custom .v-pagination__item {
  height: 26px !important;
  min-width: 26px !important;
  font-size: 0.85rem !important;
}
.body-2 .v-icon.v-icon {
  font-size: 15px;
}
</style>