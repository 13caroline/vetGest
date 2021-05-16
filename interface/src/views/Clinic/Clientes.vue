<template>
  <div id="page" class="d-flex flex-column">
    <Header />
    <v-container>
      <v-row class="w-100">

           <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
             <v-icon class="mr-2">fas fa-user</v-icon>
            Clientes
          </h3>
        <v-col cols="auto" class="ml-auto">
          <v-btn dark small color="#2596be" to="/clinica/registar/cliente">
            Registar Cliente
          </v-btn>
        </v-col>
      </v-row>

      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Procurar"
        outlined
        dense
        class="my-4"
        color="#2596be"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="clientes"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:[`item.detalhes`]="{ item }">
          <v-tooltip right>
            <template v-slot:activator="{ on, attrs }">
              <v-icon v-bind="attrs" v-on="on" small @click="more(item)">
                fas fa-plus-circle
              </v-icon>
            </template>
            <span class="caption">Mais detalhes</span>
          </v-tooltip>
        </template>
      </v-data-table>

      <v-dialog v-model="dialog" width="30%">
        <v-card>
          <v-card-title class="mb-6 grey--text">
            Identificação pessoal
             <v-tooltip right>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon small class="ml-2">
                <v-icon v-bind="attrs" v-on="on" small>fas fa-pen</v-icon>
              </v-btn>
            </template>
            <span class="caption">Editar dados</span>
          </v-tooltip>
            
            <v-spacer></v-spacer>
            <v-btn icon small @click="dialog = false">
              <v-icon>fas fa-times</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="black--text">
            <v-row>
           
            <v-img
              max-height="100"
              max-width="100"
              src="@/assets/medico.png"
            >
            </v-img>
              <span class="font-weight-bold headline indication">  CAROLINA ALVES DA CUNHA </span>
            
            </v-row>

            <v-row>
              <v-col>
                  <span class="indication font-weight-bold">Telemóvel:</span>
              </v-col>

              <v-col>
                <div class="information font-weight-light">91529375</div>
              </v-col>
            </v-row>
            
            <v-row>
              <v-col>
                  <span class="indication font-weight-bold">Email:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">f7carolina.cunha@gmail.com</div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                  <span class="indication font-weight-bold">Morada:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">Rua de Teibães</div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                  <span class="indication font-weight-bold">Localidade:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">fafe</div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                  <span class="indication font-weight-bold">Número de contribuinte:</span>
              </v-col>

              <v-col>
                <div class="information text-uppercase font-weight-light">249705672</div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                  <span class="indication font-weight-bold">Utentes associados:</span>
              </v-col>

              <v-col>
                <v-btn class="font-weight-light text-decoration-underline" color="#616161" text depressed to="/clinica/utente">Runa</v-btn>
                <v-btn class="font-weight-light text-decoration-underline" color="#616161" text depressed to="/clinica/utente">Rubi</v-btn>
              </v-col>

            </v-row>

          </v-card-text>
        </v-card>
      </v-dialog>

    </v-container>

    <Footer class="mt-auto" />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
      dialog: false, 
      search: '',
      headers: [
        {
          text: "NOME COMPLETO",
          align: "start",
          sortable: true,
          value: "cliente",
        },
        {
          text: "NÚMERO TELEFONE",
          value: "telemovel",
          sortable: true,
          align: "start",
        },
        {
          text: "LOCALIDADE",
          value: "localidade",
          sortable: false,
          align: "start",
        },
        {
          text: "MAIS DETALHES",
          value: "detalhes",
          sortable: false,
          align: "start",
        },
      ],

      clientes: [
        {
          cliente: "Carolina Alves Cunha",
          telemovel: "915293785",
          localidade: "Fafe",
        },
        {
          cliente: "Carolina Alves Cunha",
          telemovel: "915293785",
          localidade: "Fafe",
        },
        {
          cliente: "Carolina Alves Cunha",
          telemovel: "915293785",
          localidade: "Fafe",
        },
      ],
    };
  },
  components: {
    Header,
    Footer,
  },
  methods: {
    more(item) {
      this.dialog = true;
      console.log(item);
    },
  },
};
</script>

<style>
#no-background-hover::before {
  background-color: transparent !important;
}

.information{
  text-align: right;
  color: #616161;
}

.indication{
  color: #424242;
}
</style>