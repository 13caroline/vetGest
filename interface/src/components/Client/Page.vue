<template>
  <div id="page" class="d-flex flex-column">
    <v-container>
      <v-row justify="center">
        <v-col cols="12" lg="4">
          <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Os meus animais
          </h3>

          <v-row justify="start">
            <v-col cols="auto">
              <v-btn
                class="body-2"
                small
                color="#2596be"
                dark
                to="/cliente/registar/animal"
              >
                Registar Animal
                <v-icon small class="ml-2">far fa-plus-square</v-icon>
              </v-btn>
            </v-col>
          </v-row>

          <v-item-group>
            <v-row>
              <v-col
                cols="auto"
                lg="6"
                class="mx-auto mx-sm-0"
                v-for="a in animals"
                :key="a.nome"
              >
                <div class="animal">
                  <img
                    src="@/assets/animais/Rubi.jpg"
                    class="w-100 grey lighten-2 rounded"
                  />
                  <!-- <v-item v-slot:default="{ toggle }">
										<v-img
											src="@/assets/animais/Rubi.jpg"
											aspect-ratio="1"
											class="grey lighten-2 rounded"
											@click="toggle"
										>
											<template v-slot:placeholder>
												<v-row align="center" justify="center">
													<v-progress-circular
														indeterminate
														color="grey lighten-5"
													></v-progress-circular>
												</v-row>
											</template>
										</v-img>
									</v-item> -->
                  <v-tooltip top>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        class="mt-2 w-100"
                        text
                        outlined
                        v-bind="attrs"
                        v-on="on"
                        small
                        id="no-background-hover"
                        @click="toAnimal(a.id)"
                      >
                        {{ a.nome }}
                      </v-btn>
                    </template>
                    <span class="caption">Ver Mais</span>
                  </v-tooltip>
                </div>
              </v-col>
            </v-row>
          </v-item-group>
        </v-col>

        <v-col lg="7" class="ml-auto mb-5">
          <h3 class="font-weight-regular text-uppercase mb-4 mt-10">
            Intervenções Agendadas
          </h3>

          <v-row justify="start">
            <v-col cols="auto">
              <v-btn
                class="mb-3 body-2"
                small
                color="#2596be"
                dark
                to="/cliente/consultas/agendar"
              >
                Agendar Consulta
                <v-icon small class="ml-2">far fa-calendar-alt</v-icon>
              </v-btn>
            </v-col>
          </v-row>

          <v-data-table
            :headers="headers"
            :items="filteredData"
            class="elevation-1"
            hide-default-footer
            v-if="consultas.length"
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
            no-data-text="Não existem intervenções agendadas."
            no-results-text="Não foram encontrados resultados."
          >
            <template v-slot:[`item.estado`]="{ item }">
              <v-chip :color="estadopedido(item.estado)" small>
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:[`item.detalhes`]="{ item }">
              <CancelarConsulta
                v-if="item.estado == 'Agendada' || item.estado == 'Pendente'"
                :dialogs="cancelar"
                :dados="item"
                @clicked="registar"
              ></CancelarConsulta>
            </template>
          </v-data-table>
          <small v-else> <em> sem consultas agendadas </em></small>
          <div class="text-center pt-2">
            <v-pagination
              v-if="consultas.length"
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
    <v-carousel
      class="mt-auto"
      cycle
      height="100"
      hide-delimiter-background
      :show-arrows="false"
      delimiter-icon="mdi-minus"
    >
      <v-carousel-item v-for="(slide, i) in slides" :key="i">
        <v-sheet :color="colors[i]" height="100%" style="margin-top: 12px">
          <v-row class="fill-height" align="center" justify="center">
            <v-col cols="auto" class="display-3">{{ slide }} Slide</v-col>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
import CancelarConsulta from "@/components/Dialogs/CancelarComDados.vue";
export default {
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemsPerPage: 8,
      dados: {},
      dialogs: {},
      cancelar: { title: "o agendamento da consulta", text: "consulta" },
      animals: [],
      headers: [
        {
          text: "Nome",
          align: "start",
          sortable: true,
          value: "utente",
        },
        {
          text: "Médico Veterinário",
          value: "veterinario_nome",
          sortable: true,
          align: "start",
        },
        {
          text: "Data de Agendamento",
          value: "marcacao",
          sortable: true,
          align: "start",
        },
        { text: "Estado", value: "estado", sortable: true, align: "center" },
        {
          text: "Ações",
          value: "detalhes",
          sortable: false,
          align: "center",
        },
      ],
      consultas: [],
      colors: [
        "indigo",
        "warning",
        "pink darken-2",
        "red lighten-1",
        "deep-purple accent-4",
      ],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"],
    };
  },
  components: {
    CancelarConsulta,
  },
  methods: {
    registar(value) {
      this.$snackbar.showMessage({
        show: true,
        color: value.color,
        text: value.text,
        timeout: value.timeout,
      });
      this.atualiza();
    },
    atualiza: async function () {
      this.consultas = [];
      let response = await axios.post(
        "http://localhost:7777/cliente",
        {
          email: this.$store.state.email,
        },
        {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        }
      );
      let response2 = await axios.post(
        "http://localhost:7777/cliente/intervencoes",
        {
          cliente: this.$store.state.email,
        },
        {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        }
      );
      for (var i = 0; i < response2.data.length; i++) {
        this.consultas.push({
          idConsulta: response2.data[i].id,
          utente: response2.data[i].animal.nome,
          veterinario_nome: response2.data[i].veterinario.nome,
          marcacao: response2.data[i].data + " " + response2.data[i].hora,
          estado: response2.data[i].estado,
          descricao: response2.data[i].descricao,
          motivo: response2.data[i].motivo,
          animal: response2.data[i].animal,
        });
      }
      this.animals = response.data.cliente.animais;
    },
    estadopedido(estado) {
      if (estado == "Agendada") return "#C5E1A5";
      else return "#FFE082";
    },
    toAnimal(id) {
      this.$router.push("/cliente/animal/" + id);
    },
  },
  computed: {
    filteredData() {
      return this.consultas.filter(
        (item) => item.estado === "Agendada" || item.estado === "Pendente"
      );
    },
  },
  created: async function () {
    let response = await axios.post(
      "http://localhost:7777/cliente",
      {
        email: this.$store.state.email,
      },
      {
        headers: {
          Authorization: "Bearer " + store.getters.token.toString(),
        },
      }
    );
    let response2 = await axios.post(
      "http://localhost:7777/cliente/intervencoes",
      {
        cliente: this.$store.state.email,
      },
      {
        headers: {
          Authorization: "Bearer " + store.getters.token.toString(),
        },
      }
    );
    for (var i = 0; i < response2.data.length; i++) {
      this.consultas.push({
        idConsulta: response2.data[i].id,
        utente: response2.data[i].animal.nome,
        veterinario_nome: response2.data[i].veterinario.nome,
        marcacao: response2.data[i].data + " " + response2.data[i].hora,
        estado: response2.data[i].estado,
        descricao: response2.data[i].descricao,
        motivo: response2.data[i].motivo,
        animal: response2.data[i].animal,
      });
    }
    this.animals = response.data.cliente.animais;
  },
};
</script>

<style scoped>
.consulta.Agendada {
  border-left: 4px solid #c5e1a5;
}

.consulta.Pendente {
  border-left: 4px solid #ffe082;
}

#no-background-hover::before {
  background-color: transparent !important;
}
.animal {
  width: 100%;
  max-width: 250px;
}
</style>

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
</style>