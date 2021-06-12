<template>
  <div>
    <v-container>
      <v-data-table
        :headers="headers"
        :items="consultas"
        class="elevation-1"
        hide-default-footer
        no-data-text="Não existem pedidos de consulta."
        no-results-text="Não foram encontrados resultados."
      >
        <template v-slot:[`item.estado`]="{ item }">
          <v-chip :color="estadopedido(item.estado)" small>
            {{ item.estado }}
          </v-chip>
        </template>

        <template v-slot:[`item.detalhes`]="{ item }">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                class="mx-1"
                v-if="item.estado == 'Concluída'"
                @click="dialog = true"
                small
                v-on="on"
                v-bind="attrs"
              >
                fas fa-info-circle
              </v-icon>
            </template>
            <span class="caption">Ver detalhes</span>
          </v-tooltip>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                class="mx-1"
                v-if="item.estado == 'Agendada' || item.estado == 'Pendente'"
                v-bind="attrs"
                v-on="on"
                small
                @click="cancelar = true"
              >
                fas fa-calendar-times
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
    </v-container>
  </div>
</template>


<script>
import axios from "axios";
import store from "@/store.js";
import moment from "moment";
export default {
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemsPerPage: 8,
      
      headers: [
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "utente",
        },
        {
          text: "MÉDICO",
          align: "start",
          sortable: true,
          value: "veterinario",
        },
        {
          text: "DATA",
          value: "marcacao",
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
          value: "tipo",
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
      agendamento: [],
      snackbar: false,
      color: "",
      text: "",
      timeout: -1,
    };
  },
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY HH:mm");
    },
    registar(value) {
      this.snackbar = value.snackbar;
      this.color = value.color;
      this.text = value.text;
      this.timeout = value.timeout;
      this.atualiza();
    },
    atualiza: async function () {
      this.agendamento = [];
      let response = await axios.get(
        "http://localhost:7777/clinica/consultas",
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );

      for (var i = 0; i < response.data.intervencoes.length; i++) {
        var element = response.data.intervencoes[i];
        element.utente = response.data.intervencoes[i].animal.nome;
        element.veterinario = response.data.intervencoes[i].veterinario_nome;
        element.especie = response.data.intervencoes[i].animal.especie;
        element.marcacao =
          response.data.intervencoes[i].data +
          " " +
          response.data.intervencoes[i].hora;
        this.agendamento.push(element);
      }
    },
  },
  created: async function () {
    let response = await axios.get("http://localhost:7777/clinica/consultas", {
      headers: { Authorization: "Bearer " + store.getters.token },
    });

    for (var i = 0; i < response.data.intervencoes.length; i++) {
      var element = response.data.intervencoes[i];
      element.utente = response.data.intervencoes[i].animal.nome;
      element.veterinario = response.data.intervencoes[i].veterinario_nome;
      element.especie = response.data.intervencoes[i].animal.especie;
      element.marcacao =
        response.data.intervencoes[i].data +
        " " +
        response.data.intervencoes[i].hora;
      this.agendamento.push(element);
    }
  },
  computed: {
    filteredData() {
      return this.agendamento.filter(
        (item) => item.estado === "Agendada" || item.estado === "A decorrer"
      );
    },
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
