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
            <span class="caption">Marcar consulta</span>
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
                <v-icon>fas fa-syringe</v-icon>
              </v-btn>
            </template>
            <span class="caption">Marcar cirurgia</span>
          </v-tooltip>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <v-data-table
            :headers="headers"
            :items="agendamento"
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
              <span class="ml-2 mr-1 blue--text">{{ item.utente }}</span>
              <span>({{ item.especie }})</span>
            </template>

            <template v-slot:[`item.cliente`]="{ item }">
              <span class="ml-1 blue--text">{{ item.cliente }}</span>
            </template>

            <template v-slot:[`item.servico`]="{ item }">
              <v-chip :color="servico(item.servico)" small>
                {{ item.servico }}
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
                  <v-icon v-bind="attrs" v-on="on" small @click="more(item)">
                    mdi-plus-circle
                  </v-icon>
                </template>
                <span class="caption">Mais detalhes</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    color="#66BB6A"
                    v-bind="attrs"
                    v-on="on"
                    small
                    v-if="item.estado != 'A decorrer'"
                    disabled
                    @click="conclude(item)"
                  >
                    mdi-clipboard-check-outline
                  </v-icon>
                  <v-icon
                    color="#66BB6A"
                    v-bind="attrs"
                    v-on="on"
                    small
                    v-else
                    @click="conclude(item)"
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
                    color="#E57373"
                    v-bind="attrs"
                    v-on="on"
                    small
                    v-if="item.estado == 'A decorrer'"
                    disabled
                    @click="cancela(item)"
                  >
                    mdi-close-circle
                  </v-icon>
                  <v-icon
                    color="#E57373"
                    v-bind="attrs"
                    v-on="on"
                    small
                    v-else
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

      <v-dialog v-model="detalhes" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="cancel">Consulta agendada</v-card-title>
          <v-card-text>
            <v-row class="mt-2">
              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Nome do Animal</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Rubi</strong>
                  (Serra da Estrela)
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Motivo da Consulta</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Vómito/Diarreia/Recusa a comer</strong>
                </span>
                <br />
                <span>Consulta extraordinária/Por doença</span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Data</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>19/04/2021 15:30</strong>
                </span>
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              small
              dark
              color="#2596be"
              @click="detalhes = false"
            >
              Fechar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

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
                  <strong>Rubi</strong>
                  (Serra da Estrela)
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Motivo da Consulta</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Vómito/Diarreia/Recusa a comer</strong>
                </span>
                <br />
                <span>Consulta extraordinária/Por doença</span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Data</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>19/04/2021 15:30</strong>
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Médico</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Dr. José Vieira</strong>
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
              @click="concluir = false"
            >
              Admitir
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="cancelar" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="cancel"> Cancelar consulta </v-card-title>
          <v-card-text>
            <v-row class="mt-2">
              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Nome do Animal</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Rubi</strong>
                  (Serra da Estrela)
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Motivo da Consulta</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Vómito/Diarreia/Recusa a comer</strong>
                </span>
                <br />
                <span>Consulta extraordinária/Por doença</span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Data</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>21/05/2021 15:45</strong>
                </span>
              </v-col>

              <v-col class="pb-0" align="right" cols="5">
                <span class="text-uppercase">Médico</span>
              </v-col>
              <v-col class="pl-0 pb-0" cols="7">
                <span class="black--text">
                  <strong>Sem preferência</strong>
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
              @click="cancelar()"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>


<script>
export default {
  data() {
    return {
      cancelar: false,
      detalhes: false,
      concluir: false,
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      search: "",
      headers: [
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "utente",
        },
        {
          text: "CLIENTE",
          align: "start",
          sortable: true,
          value: "cliente",
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

      agendamento: [
        {
          utente: "Rubi",
          cliente: "Carolina Cunha",
          data: "21/05/2021 15:00",
          motivo: "Consulta Anual/Vacinação",
          servico: "Consulta",
          estado: "A decorrer",
          especie: "Canídeo",
        },
        {
          utente: "Runa",
          cliente: "Carolina Cunha",
          data: "05/05/2021 10:00",
          servico: "Cirurgia",
          motivo: "Castração",
          estado: "Agendada",
          especie: "Canídeo",
        },
        {
          utente: "Puscas",
          cliente: "Carolina Cunha",
          data: "29/04/2021 16:30",
          servico: "Consulta",
          motivo: "Consulta de seguimento",
          estado: "Agendada",
          especie: "Canídeo",
        },
        {
          utente: "Nikita",
          cliente: "Carolina Cunha",
          data: "21/05/2021 15:30",
          servico: "Consulta",
          motivo: "Consulta extraordinária/Por doença",
          estado: "A decorrer",
          especie: "Canídeo",
        },
        {
          utente: "Zuki",
          cliente: "Carolina Cunha",
          data: "07/06/2021 11:00",
          servico: "Cirurgia",
          motivo: "Castração",
          estado: "Agendada",
          especie: "Canídeo",
        },
        {
          utente: "Rudi",
          cliente: "Carolina Cunha",
          data: "15/07/2021 17:30",
          servico: "Cirurgia",
          motivo: "Castração",
          estado: "Agendada",
          especie: "Canídeo",
        },
      ],
    };
  },
  methods: {
    more(item) {
      this.detalhes = true;
      console.log(item);
    },
    conclude(item) {
      this.concluir = true;
      console.log(item);
    },
    cancela(item) {
      this.cancelar = true;
      console.log(item);
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
      this.$router.push("/medico/utente/");
      console.log(item);
    },
  },
  created() {
    /*
    let response = await axios.post("http://localhost:7777/clinica/getClientes", {
      email: this.$store.state.user.email,
    });

    */
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