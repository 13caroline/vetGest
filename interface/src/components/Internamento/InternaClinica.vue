<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12" class="my-4">
          <h3 class="font-weight-regular text-uppercase">
            <v-icon class="mr-2">fas fa-procedures</v-icon>
            Internamento
          </h3>
        </v-col>
        <v-col cols="12">
          <v-data-table
            :headers="headers"
            :items="internamento"
            class="elevation-1"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
          >
            <template v-slot:[`item.animal`]="{ item }">
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
              <span class="ml-1 blue--text">{{ item.animal.nome }}</span>
              <span>({{ item.animal.especie }})</span>
            </template>

            <template v-slot:[`item.data`]="{ item }">
              <span class="ml-1">{{ formats(item.data) }}</span>
            </template>

            <template v-slot:[`item.veterinario`]="{ item }">
              <span>{{ item.veterinario.nome }}</span>
            </template>

            <template v-slot:[`item.detalhes`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon v-bind="attrs" v-on="on" small @click="more(item)">
                    fas fa-arrow-right
                  </v-icon>
                </template>
                <span class="caption">Mais detalhes</span>
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
    </v-container>
  </div>
</template>


<script>
import axios from "axios";
import moment from "moment";

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
          text: "LOCALIZAÇÃO",
          align: "start",
          sortable: true,
          value: "localizacao",
        },
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "animal",
        },
        {
          text: "DATA DE INTERNAMENTO",
          value: "data",
          sortable: true,
          align: "start",
        },
        {
            text: "MÉDICO VETERINÁRIO",
            value: "veterinario",
            sortable: true, 
            align: "start"
        },
        {
          text: "MOTIVO",
          value: "motivo",
          sortable: true,
          align: "start",
        },
        {
          text: "VER",
          value: "detalhes",
          sortable: false,
          align: "start",
        },
      ],

      internamento: [],
    };
  },
  methods: {
    more(item) {
      this.$router.push("/clinica/internamento/detalhes/" + item.animal.id);
    },
    utente(item) {
      this.$router.push("/clinica/utente/" + item.animal.id);
    },
    formats(data){
        return moment(
            data,
            "YYYY-MM-DD HH:mm",
            true
          )
            .locale("pt")
            .format("DD/MM/YYYY HH:mm");
    }
  },
  created: async function() {
    
    let response = await axios.get("http://localhost:7777/clinica/internamentos", 
     { headers: { Authorization: "Bearer " + this.$store.getters.token } });

    this.internamento = response.data;
    console.log(this.internamento)
    
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