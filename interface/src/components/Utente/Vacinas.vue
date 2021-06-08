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
            <v-col cols="auto" class="ml-auto pl-0">
              <!-- v-if tipo == medico -->
              <NovaVacina @clicked="close()"></NovaVacina>
            </v-col>
            <v-col cols="auto" class="pl-0">
              <NovaDesparasitacao @clicked="close()"></NovaDesparasitacao>
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

            <template v-slot:[`item.confirmar`]="{ item }">
              <v-icon v-if="item.estado == 'Administrada'" small color="green">
                fas fa-check-circle
              </v-icon>
              <div
                v-if="item.estado == 'Atualizada' || item.estado == 'Atrasada'"
              >
                <ConfirmaDespar
                  :dataPrev="item.dataPrev"
                  @clicked="close()"
                ></ConfirmaDespar>
              </div>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
// import axios from 'axios'
import ConfirmaDespar from "@/components/Dialogs/ConfirmaDesparasitacao.vue";
import NovaVacina from "@/components/Dialogs/NovaVacina.vue";
import NovaDesparasitacao from "@/components/Dialogs/NovaDesparasitacao.vue";

export default {
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
        value: "dataPrev",
      },
      {
        text: "Data de Toma",
        value: "dataToma",
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
        text: "Tratamento Utilizado",
        value: "tratamento",
        sortable: true,
        align: "start",
      },
      {
        text: "Médico Veterinário",
        value: "medico",
        sortable: false,
        align: "center",
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
    items: [
      {
        dataPrev: "05/04/2021",
        dataToma: "06/04/2021",
        tipo: "Desparasitação",
        tratamento: "Bravacto",
        medico: "Drº José Vieira",
        estado: "Administrada",
      },
      {
        dataPrev: "05/05/2021",
        tipo: "Desparasitação",
        tratamento: "Bravacto",
        estado: "Atualizada",
      },
      {
        dataPrev: "05/04/2021",
        dataToma: "05/04/2021",
        tipo: "Vacina",
        tratamento: "Rabis",
        medico: "Drº José Vieira",
        estado: "Administrada",
      },
      {
        dataPrev: "05/04/2021",
        tipo: "Desparasitação",
        estado: "Atrasada",
      },
    ],
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
      if (estado == "Administrada") return "#C5E1A5";
      else if (estado == "Atrasada") return "#EF9A9A";
      else return "#FFE082";
    },
  },
  created() {
    /*
    let response = await axios.post("http://localhost:7777/cliente/getVacinas", {
      email: this.$store.state.user.email,
      animal
    });

    */
  },
};
</script>