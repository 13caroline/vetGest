<template>
  <div>
    <v-container class="fill-height">
      <v-row class="w-100 mt-4 mb-0" align="center">
        <v-col cols="auto">
          <h3 class="font-weight-regular text-uppercase">
            <v-icon class="mr-2">fas fa-calendar-alt</v-icon>
            Agendamento
          </h3>
        </v-col>
        <div class="w-100 d-sm-none"></div>
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
                to="/clinica/clientes/registar"
              >
                <v-icon small>fas fa-user-plus</v-icon>
              </v-btn>
            </template>
            <span class="caption">Registar cliente</span>
          </v-tooltip>
        </v-col>
        <v-col cols="auto" class="pl-0">
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
                to="/clinica/utentes/registar"
              >
                <v-icon small>fas fa-paw</v-icon>
              </v-btn>
            </template>
            <span class="caption">Registar utente</span>
          </v-tooltip>
        </v-col>
        <v-col cols="auto" class="pl-0">
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
                to="/clinica/consultas/"
              >
                <v-icon small>fas fa-scroll</v-icon>
              </v-btn>
            </template>
            <span class="caption">Marcar consulta</span>
          </v-tooltip>
        </v-col>
        <v-col cols="auto" class="pl-0">
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
                to="/clinica/cirurgias/"
              >
                <v-icon small>fas fa-band-aid</v-icon>
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
              <Detalhes :dados="item"></Detalhes>
              
              <Admissao :dados="item"></Admissao>
              
              <CancelarConsulta :dialogs="cancelar" :dados="item"></CancelarConsulta>
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
    </v-container>
  </div>
</template>


<script>
import Detalhes from '@/components/Dialogs/Detalhes.vue'
import Admissao from '@/components/Dialogs/Admissao.vue'
import CancelarConsulta from '@/components/Dialogs/CancelarComDados.vue'

export default {
  data() {
    return {
      dados:{},
      dialogs: {},
      cancelar: {
        title: "agendamento da consulta",
        text: "o agendamento da consulta",
      },
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
  components: {
    Detalhes,
    Admissao,
    CancelarConsulta
  },
  methods: {
    estado(item) {
      if (item == "Agendada") return "#C5E1A5";
      else return "#FFECB3";
    },
    servico(item) {
      if (item == "Consulta") return "#B2DFDB";
      else return "#FFCCBC";
    },
    utente(item) {
      this.$router.push("/clinica/utente/");
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