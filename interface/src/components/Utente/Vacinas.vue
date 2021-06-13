<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-syringe</v-icon>
                Vacinas e desparasitações
              </h3>
            </v-col>
            <v-col
              cols="auto"
              class="ml-auto pl-0"
              v-if="this.$store.state.tipo == 'Veterinario'"
            >
              <NovaVacina
                :animal="animal"
                @clicked="imunizacao_adicionada"
              ></NovaVacina>
            </v-col>
            <v-col
              cols="auto"
              class="pl-0"
              v-if="this.$store.state.tipo == 'Veterinario'"
            >
              <NovaDesparasitacao
                :dados="animal.id"
                @clicked="imunizacao_adicionada"
              ></NovaDesparasitacao>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="items"
            class="elevation-1"
            hide-default-footer
            no-data-text="Não existem vacinas ou desparasitações registadas."
            no-results-text="Não foram encontrados resultados."
          >
            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estadopedido(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:[`item.veterinario`]="{ item }">
              <span v-if="item.veterinario">{{ item.veterinario.nome }}</span>
            </template>

            <template v-slot:[`item.data`]="{ item }">
              {{ format(item.data) }}
            </template>

            <template v-slot:[`item.data_toma`]="{ item }">
              {{ format(item.data_toma) }}
            </template>

            <template v-slot:[`item.confirmar`]="{ item }">
              <v-icon v-if="item.estado == 'Administrada'" small color="green">
                fas fa-check-circle
              </v-icon>

              <div
                v-if="
                  (item.estado == 'Atualizada' ||
                    item.estado == 'Atrasada' ||
                    item.estado == 'Agendada') &&
                  $store.state.tipo == 'Veterinario'
                "
              >
                <ConfirmaDespar
                  :dataPrev="item.dataPrev"
                  :id="item.id"
                  @clicked="imunizacao_adicionada"
                ></ConfirmaDespar>
              </div>
            </template>
          </v-data-table>
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
import axios from "axios";
import ConfirmaDespar from "@/components/Dialogs/ConfirmaDesparasitacao.vue";
import NovaVacina from "@/components/Dialogs/NovaVacina.vue";
import NovaDesparasitacao from "@/components/Dialogs/NovaDesparasitacao.vue";
import moment from "moment";
import store from "@/store.js";
export default {
  props: ["animal"],
  data: () => ({
    dataPrev: {},
    snackbar: false,
    color: "",
    done: false,
    timeout: -1,
    text: "",
    headers: [
      {
        text: "Data Prevista",
        align: "start",
        sortable: true,
        value: "data",
      },
      {
        text: "Data de Toma",
        value: "data_toma",
        sortable: true,
        align: "start",
      },
      {
        text: "Tipo",
        value: "tipo",
        sortable: true,
        align: "start",
      },
      {
        text: "Vacina contra",
        value: "vacina",
        sortable: true,
        align: "start",
      },
      {
        text: "Tratamento Utilizado",
        value: "tratamento",
        sortable: true,
        align: "start",
      },
      {
        text: "Médico Veterinário",
        value: "veterinario",
        sortable: false,
        align: "start",
      },
      {
        text: "Estado",
        value: "estado",
        sortable: true,
        align: "center",
      },
      {
        text: "Confirmar Administração",
        value: "confirmar",
        sortable: false,
        align: "center",
      },
    ],
    items: [],
  }),
  components: {
    ConfirmaDespar,
    NovaVacina,
    NovaDesparasitacao,
  },
  methods: {
    close() {
      // if medico
      this.$router.push("/medico/utente");
    },
    estadopedido(estado) {
      if (estado == "Administrada") return "#9AE5FF";
      else if (estado == "Atrasada") return "#EF9A9A";
      else return "#C5E1A5";
    },

    format(data) {
      if (data) return moment(data).locale("pt").format("DD/MM/YYYY");
    },

    imunizacao_adicionada(value) {
      this.snackbar = value.snackbar;
      this.color = value.color;
      this.text = value.text;
      this.timeout = value.timeout;
      this.loadData();
    },

    loadData: async function () {
      let route =
        this.$store.state.tipo == "Veterinario"
          ? "http://localhost:7777/medico/imunizacao"
          : "http://localhost:7777/clinica/imunizacao";

      try {
        let response = await axios.post(
          route,
          {
            id: this.animal.id,
          },
          {
            headers: { Authorization: "Bearer " + store.getters.token },
          }
        );
        this.items = response.data;
      } catch (e) {
        console.log(e);
      }
    },
  },
  created() {
    this.loadData();
  },
};
</script>