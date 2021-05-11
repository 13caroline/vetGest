<template>
  <div>
    <Header />
    <v-data-table
      :headers="headers"
      :items="consultas"
      class="elevation-1"
      hide-default-footer
    >

      <template v-slot:[`item.detalhes`]="{ item }">
        <v-tooltip right>
          <template v-slot:activator="{ on, attrs }">
            <v-icon
              v-bind="attrs"
              v-on="on"
              small
              @click="more(item)"
            >
              fas fa-plus-circle
            </v-icon>
          </template>
          <span class="caption">Mais detalhes</span>
        </v-tooltip>
      </template>
    </v-data-table>

    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Headers/ClinicHeader.vue";
import Footer from "@/components/Footer.vue";

export default {
  data() {
    return {
      headers: [
        {
          text: "Paciente",
        //  align: "start",
          sortable: true,
          value: "paciente",
        },
        {
          text: "Idade",
          value: "idade",
          sortable: true,
        //  align: "start",
        },
        {
          text: "Cliente",
          value: "cliente",
          sortable: true,
        //  align: "start",
        },
        {
          text: "Espécie",
          value: "especie",
          sortable: true,
        //  align: "start",
        },
        {
          text: "Raça",
          value: "raca",
          sortable: true,
         // align: "start",
        },
        {
          text: "Mais detalhes",
          value: "detalhes",
          sortable: false,
         // align: "start",
        },
      ],

      consultas: [
        {
          paciente: "Runa",
          idade: "10",
          cliente: "Carolina Cunha",
          especie: "Canídeo",
          raca: "Labrador",
        },
        {
          paciente: "Riscas",
          idade: "4",
          cliente: "João Mota",
          especie: "Felídeo",
          raca: "Gato Europeu",
        },
      ],
    };
  },
  components: {
    Header,
    Footer,
  },
  methods:{
      more(item){
          console.log(item)
      }
  }
};
</script>