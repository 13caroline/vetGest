<template>
  <v-dialog v-model="dialog" width="100%" max-width="500" persistent>
    <template v-slot:activator="{ diag, attrs }">
      <v-tooltip top>
        <template v-slot:activator="{ on }">
          <v-btn
            color="#2596be"
            v-bind="attrs"
            v-on="{ ...on, ...diag }"
            small
            fab
            dark
            @click="dialog = true"
          >
            <v-icon small> fas fa-pen</v-icon>
          </v-btn>
        </template>
        <span class="caption">Editar histórico clínico</span>
      </v-tooltip>
    </template>
    

   
  </v-dialog>
</template>

<script>
import axios from "axios";
import store from "@/store.js";
//import Cancelar from "@/components/Dialogs/Cancel.vue";
export default {
  data: () => ({
    dialog: false,
    items: [],
    dialogs: {},
  }),
  components: {
  //  Cancelar,
  },
  methods: {
    close() {
      this.dialog = false;
    },
    registar: async function () {
      if (store.state.tipo == "Clinica") {
        await axios
          .post(
            "http://localhost:7777/clinica/intervencao/agendar",
            {
              intervencao: {
                data: this.date,
                hora: this.hora,
                descricao: this.descricao,
                motivo: this.motivos,
                tipo: "Consulta",
              },
              animal: this.id,
              veterinario: this.medico,
              cliente: this.dono,
            },
            { headers: { Authorization: "Bearer " + store.getters.token } }
          )
          .then((response) => {
            console.log(response);
            this.$emit("clicked", {
              text: "Consulta agendada com sucesso.",
              color: "success",
              snackbar: "true",
              timeout: 4000,
            });
            this.dialog = false;
          })
          .catch((error) => {
            if (
              error.response.data ==
              "Erro no agendamento de Consulta! Horario Indisponivel!"
            ) {
              this.$emit("clicked", {
                text: "Não é possível agendar uma consulta para o horário selecionado.",
                color: "warning",
                snackbar: "true",
                timeout: 4000,
              });
            } else {
              this.$emit("clicked", {
                text: "Ocorreu um erro na marcação, por favor tente mais tarde!",
                color: "warning",
                snackbar: "true",
                timeout: 4000,
              });
            }
          });
      }
    },
  },
  computed: {
    filteredData() {
      let motivo = this.motivos;
      return this.desc.filter((item) => item.tipo === motivo);
    },
  },

};
</script>