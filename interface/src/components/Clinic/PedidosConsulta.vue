<template>
  <div>
    <v-container>
      <v-data-table
        :headers="headers"
        :items="pedidos"
        class="elevation-1"
        hide-default-footer
        no-data-text="Não existem pedidos de consulta."
        no-results-text="Não foram encontrados resultados."
      >
        <template v-slot:[`item.animal`]="{ item }">
          <span class="ml-1">{{ item.animal.nome }}</span>
        </template>

        <template v-slot:[`item.veterinario`]="{ item }">
          <span class="ml-1">{{ item.veterinario.nome }}</span>
        </template>

        <template v-slot:[`item.marcacao`]="{ item }">
          {{ format(item.marcacao) }}
        </template>

        <template v-slot:[`item.data_pedido`]="{ item }">
          {{ format2(item.data_pedido) }}
        </template>

        <template v-slot:[`item.detalhes`]="{ item }">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                class="mx-1"
                @click="confirmar(item)"
                small
                v-on="on"
                color="#66BB6A"
                v-bind="attrs"
              >
                mdi-calendar-check
              </v-icon>
            </template>
            <span class="caption">Confirmar consulta</span>
          </v-tooltip>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                class="mx-1"
                @click="openDialog(item)"
                small
                color="#E57373"
                v-on="on"
                v-bind="attrs"
              >
                mdi-calendar-remove
              </v-icon>
            </template>
            <span class="caption">Rejeitar consulta</span>
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

    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
      <v-card>
        <v-card-title class="cancel"> Cancelar consulta </v-card-title>
        <v-card-text>
          <v-row class="mt-2">
            <v-col class="pb-0" align="right" cols="5">
              <span class="text-uppercase">Nome do Animal</span>
            </v-col>
            <v-col class="pl-0 pb-0" cols="7">
              <span class="black--text">
                <strong>{{ nome }}</strong>
                ({{ raca }})
              </span>
            </v-col>

            <v-col class="pb-0" align="right" cols="5">
              <span class="text-uppercase">Motivo da consulta</span>
            </v-col>
            <v-col class="pl-0 pb-0" cols="7">
              <span class="black--text">
                <strong>{{ dados.descricao }}</strong>
              </span>
              <br />
              <span>{{ dados.motivo }}</span>
            </v-col>

            <v-col class="pb-0" align="right" cols="5">
              <span class="text-uppercase">Data</span>
            </v-col>
            <v-col class="pl-0 pb-0" cols="7">
              <span class="black--text">
                <strong>{{ format(dados.marcacao) }}</strong>
              </span>
            </v-col>

            <v-col class="pb-0" align="right" cols="5">
              <span class="text-uppercase">Médico</span>
            </v-col>
            <v-col class="pl-0 pb-0" cols="7">
              <span class="black--text">
                <strong>Dr.º {{ veterinario }}</strong>
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
            @click="rejeitar(dados.id)"
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
import store from "@/store.js";
import moment from "moment";
export default {
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemsPerPage: 8,
      pedidos: [],
      headers: [
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "animal",
        },
        {
          text: "MÉDICO",
          align: "start",
          sortable: true,
          value: "veterinario",
        },
        {
          text: "DATA PEDIDO",
          value: "data_pedido",
          sortable: true,
          align: "start",
        },
        {
          text: "DATA",
          value: "marcacao",
          sortable: true,
          align: "start",
        },
        {
          text: "MOTIVO",
          value: "descricao",
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
      dialog: false,
      dados: {},
      nome: "",
      raca: "",
      veterinario: "",
    };
  },
  methods: {
    format(data) {
      return moment(data, "YYYY-MM-DD HH:mm", true)
        .locale("pt")
        .format("DD/MM/YYYY HH:mm");
    },
    format2(data) {
      return moment(data, "YYYY-MM-DDTHH:mm", true)
        .locale("pt")
        .format("DD/MM/YYYY HH:mm");
    },
    confirmar: async function (item) {
      try {
        await axios.post(
          "http://localhost:7777/clinica/intervencao/alterar",
          {
            id: item.id,
            estado: "Agendada",
          },
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );
        this.$snackbar.showMessage({
          show: true,
          color: "success",
          text: "Consulta confirmada com sucesso",
          timeout: 4000,
        });
        this.atualiza();
      } catch (e) {
        this.$snackbar.showMessage({
          show: true,
          color: "warning",
          text: "Ocorreu um erro, por favor tente mais tarde!",
          timeout: 4000,
        });
      }
    },
    rejeitar: async function (item) {
      try {
        await axios.post(
          "http://localhost:7777/clinica/intervencao/alterar",
          {
            id: item,
            estado: "Cancelada",
          },
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );

        this.$snackbar.showMessage({
          show: true,
          color: "success",
          text: "Consulta cancelada com sucesso",
          timeout: 4000,
        });
        this.atualiza();
      } catch (e) {
        this.$snackbar.showMessage({
          show: true,
          color: "warning",
          text: "Ocorreu um erro, por favor tente mais tarde!",
          timeout: 4000,
        });
      }
    },
    atualiza: async function () {
      this.pedidos = [];
      let response = await axios.get(
        "http://localhost:7777/clinica/intervencoes/pedidos",
        {
          headers: { Authorization: "Bearer " + store.getters.token },
        }
      );

      for (var i = 0; i < response.data.length; i++) {
        var element = response.data[i];
        element.marcacao = response.data[i].data + " " + response.data[i].hora;
        this.pedidos.push(element);
      }
    },
    openDialog(item) {
      this.dados = item;
      this.nome = item.animal.nome;
      this.raca = item.animal.raca;
      this.veterinario = item.veterinario.nome;
      console.log(this.dados);
      this.dialog = true;
    },
  },
  created: async function () {
    let response = await axios.get(
      "http://localhost:7777/clinica/intervencoes/pedidos",
      {
        headers: { Authorization: "Bearer " + store.getters.token },
      }
    );
    for (var i = 0; i < response.data.length; i++) {
      var element = response.data[i];
      element.marcacao = response.data[i].data + " " + response.data[i].hora;
      this.pedidos.push(element);
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
