<template>
    <div>
        <v-card flat color="#fafafa">
            <v-row>
        <v-col>
          <v-row align="center" class="my-5">
            <v-col>
          <h3 class="font-weight-regular text-uppercase ">
            <v-icon small>fas fa-syringe</v-icon>
            Vacinas e desparasitações
          </h3>
</v-col>
          <v-col cols="auto" class="pl-0">
              <NovaDesparasitacao :dados="idAnimal" @clicked="registar"></NovaDesparasitacao>
            </v-col>
</v-row>
          <v-data-table
          :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
            :headers="headers"
            :items="items"
            class="elevation-1"
            hide-default-footer
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
              <v-btn
                v-if="item.estado == 'Atualizada' || item.estado == 'Atrasada' && item.Tipo=='Desparasitacao' " 
                small
                outlined
                rounded
                color="#2596be"
                @click="openDialog(item)"
              >
                Confirmar
              </v-btn>
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
      <v-dialog v-model="dialog" width="100%" max-width="700">
        <v-card>
          <v-card-title class="headline mb-6">
            Confirmar desparasitação
          </v-card-title>
          <v-card-text class="black--text">
            <div>
              <p class="ma-0">Data prevista</p>
              <v-text-field
                color="#2596be"
                flat
                disabled
                outlined
                dense
                readonly
                :value="dataPrevista"
                append-outer-icon="fas fa-calendar-alt"
              ></v-text-field>
            </div>
            <div>
              <p class="mb-0">Data de toma</p>
              <v-menu
                ref="dataToma"
                v-model="dataToma"
                :close-on-content-click="true"
                :nudge-right="40"
                :return-value.sync="dataToma"
                transition="scale-transition"
                offset-y
                max-width="290px"
                min-width="290px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    append-outer-icon="fas fa-calendar-alt"
                    outlined
                    color="#2596be"
                    v-on="on"
                    v-bind="attrs"
                    v-model="date"
                    dense
                    readonly
                  ></v-text-field>
                </template>
                <v-date-picker
                  full-width
                  color="#2596be"
                  :min="new Date().toISOString().substr(0, 10)"
                  v-model="date"
                  locale="pt-PT"
                ></v-date-picker>
              </v-menu>
            </div>
            <div>
              <p class="ma-0">Tratamento Utilizado</p>
              <v-text-field
                outlined
                color="#2596be"
                dense
                v-model="tratamento"
              ></v-text-field>
            </div>
            <v-row align="end" justify="end">
              <v-col cols="auto">
                <v-btn color="#BDBDBD" small dark @click="dialogCancel = true">
                  Cancelar
                </v-btn>
                <v-btn
                  color="#2596be"
                  small
                  dark
                  class="ml-3"
                  @click="confirma()"
                >
                  Confirmar
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogCancel" persistent width="100%" max-width="460">
        <v-card>
          <v-card-title class="justify-center cancel">
            Cancelar desparasitação
          </v-card-title>
          <v-card-text>
            Tem a certeza que pretende cancelar a confirmação de desparasitação?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              large
              width="50%"
              dark
              color="#BDBDBD"
              @click="dialogCancel = false"
            >
              Não
            </v-btn>
            <v-btn
              depressed
              large
              dark
              color="#2596be"
              width="50%"
              @click="cancelar()"
            >
              Sim
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
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
import axios from 'axios'
import store from "@/store.js";
import NovaDesparasitacao from "@/components/Dialogs/NovaDesparasitacao.vue";
import moment from "moment";
export default {
  props:["animal"],
  data: () => ({
    page: 1,
      pageCount: 0,
      itemsPerPage: 10,

    idAnimal: {},
    dialog: false,
    dialogCancel: false,
    dataPrevista: "",
    dataToma: null,
    tratamento: "", 
    id:0,
    date: new Date().toISOString().substr(0, 10),
    snackbar: false,
    color: "",
    nomeMedico: "",
    done: false,
    timeout: -1,
    text: "",
    headers: [
      {
        text: "Data Prevista",
        align: "start",
        sortable: true,
        value: "dataPrevista",
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
      
    ],
  }),
  methods: {
    registar(value){
      this.snackbar=value.snackbar
      this.color=value.color
      this.text=value.text
      this.timeout=value.timeout
      this.atualiza()
    },
    atualiza: async function(){
      this.items=[];
      try {
      var response = await axios.post(
        "http://localhost:7777/cliente/animal/"+this.animal.id+"/getvacinas",
        {
          email: this.$store.state.email,
        },
        {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        }
      );
    } catch (e) {
      console.log("erro: +" + e);
    }
   for (var i = 0; i < response.data.length; i++) {
     if(!response.data[i].veterinario) this.nomeMedico="Sem Referência"
     else this.nomeMedico= response.data[i].veterinario.nome
      this.items.push({
        dataPrevista:  moment(response.data[i].data,"YYYY-MM-DD",true).locale("pt").format("DD/MM/YYYY"),
        tipo: response.data[i].tipo,
        tratamento: response.data[i].tratamento,
        medico: this.nomeMedico,
        estado: response.data[i].estado,
        id: response.data[i].id
      })
    } 
    },
    estadopedido(estado) {
      if (estado == "Administrada") return "#9AE5FF";
      else if (estado == "Atrasada") return "#EF9A9A";
      else return "#C5E1A5";
    },
    more(item) {
      console.log(item.data);
    },
    openDialog(item) {
      this.dataPrevista = item.dataPrevista;
      console.log(item.id)
      this.id = item.id;
      this.dialog = true;
    },
    cancelar() {
      this.dialogCancel = false;
      this.dialog = false;
    },
    confirma: async function () {
      try {
          var resposta = await axios.post("http://localhost:7777/cliente/animal/confirma/imunizacao", {
            id: this.id,
            data: this.dataToma,
            tratamento: this.tratamento,
            dataProx:"1213123"
          });
          console.log(JSON.stringify(resposta.data));
          this.dialog = false;
          this.text = "Desparasitação confirmada com sucesso.";
          this.color = "success";
          this.snackbar = true;
          this.atualiza();
        } catch (e) {
          console.log("erro: " + e);
          this.dialog = false;
          this.text = "Ocorreu um erro, por favor tente mais tarde!";
          this.color = "warning";
          this.snackbar = true;
        }
        
    },
  },
created: async function () {
    try {
      var response = await axios.post(
        "http://localhost:7777/cliente/animal/"+this.animal.id+"/getvacinas",
        {
          email: this.$store.state.email,
        },
        {
          headers: {
            Authorization: "Bearer " + store.getters.token.toString(),
          },
        }
      );
    } catch (e) {
      console.log("erro: +" + e);
    }
 
    if(response.data.length!=0){
   for (var i = 0; i < response.data.length; i++) {
     if(!response.data[i].veterinario) this.nomeMedico="Sem Referência"
     else this.nomeMedico= response.data[i].veterinario.nome
      this.items.push({
        dataPrevista: moment(response.data[i].data,"YYYY-MM-DD",true).locale("pt").format("DD/MM/YYYY"),
        tipo: response.data[i].tipo,
        tratamento: response.data[i].tratamento,
        medico: this.nomeMedico,
        estado: response.data[i].estado,
        id: response.data[i].id
      })
    } 
          this.idAnimal= response.data[0].animal.id;
    }

  },
  components: {
    NovaDesparasitacao,
  }
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