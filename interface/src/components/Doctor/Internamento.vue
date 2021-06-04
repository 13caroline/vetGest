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
              <span class="ml-1 blue--text">{{ item.utente }}</span>
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

            <template v-slot:[`item.alta`]="{ item }">
              <v-btn color="#2596be" depressed dark small @click="alta(item)">
                Criar alta
              </v-btn>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
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
          text: "LOCALIZAÇÃO",
          align: "start",
          sortable: true,
          value: "localizacao",
        },
        {
          text: "UTENTE",
          align: "start",
          sortable: true,
          value: "utente",
        },
        {
          text: "DATA DE INTERNAMENTO",
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
          text: "VER",
          value: "detalhes",
          sortable: false,
          align: "start",
        },
        {
          text: "DAR ALTA",
          value: "alta",
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
      this.$router.push("/medico/internamento/detalhes");
      console.log(item);
    },
    alta(item) {
      this.$router.push("/medico/internamento/alta");
      console.log(item);
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