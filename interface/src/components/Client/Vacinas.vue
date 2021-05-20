<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <h3 class="font-weight-regular text-uppercase mt-10 mb-4">
            <v-icon small>fas fa-syringe</v-icon>
            Vacinas e desparasitações
          </h3>

          <v-data-table
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
                v-if="item.estado == 'Atualizada' || item.estado == 'Atrasada'"
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
        </v-col>
      </v-row>
      <v-dialog v-model="dialog" width="50%">
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
            <v-row align="end" justify="end">
              <v-col cols="auto">
                <v-btn color="#BDBDBD" small dark @click="dialogCancel = true">
                  Cancelar
                </v-btn>
                <v-btn color="#2596be" small dark class="ml-3">
                  Confirmar
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogCancel" persistent width="30%">
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
					<v-btn depressed large dark color="#2596be" width="50%" to="/cliente/vacinas">
						Sim
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
    dialogCancel: false,
    dataPrevista: "",
    dataToma: null,
    date: new Date().toISOString().substr(0, 10),
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
  methods: {
    estadopedido(estado) {
      if (estado == "Administrada") return "#C5E1A5";
      else if (estado == "Atrasada") return "#EF9A9A";
      else return "#FFE082";
    },
    more(item) {
      console.log(item.data);
    },
    openDialog(item) {
      this.dataPrevista = item.dataPrev;

      this.dialog = true;
    },
  },
};
</script>