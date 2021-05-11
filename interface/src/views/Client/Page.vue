<template>
	<div id="page" class="d-flex flex-column">
		<Header />
		<v-container>
			<v-row justify="center">
				<v-col cols="12" lg="4">
					<h3 class="font-weight-regular text-uppercase mb-4 mt-10">
						Os meus animais
					</h3>
					<v-item-group>
						<v-row>
							<v-col
								class="d-flex child-flex animal"
								cols="5"
								v-for="a in animal"
								:key="a.nome"
							>
								<v-card flat tile color="#fafafa">
									<v-item v-slot:default="{ toggle }">
										<v-img
											src="@/assets/animais/Rubi.jpg"
											aspect-ratio="1"
											class="grey lighten-2"
											@click="toggle"
										>
											<template v-slot:placeholder>
												<v-row class="fill-height ma-0" align="center" justify="center">
													<v-progress-circular
														indeterminate
														color="grey lighten-5"
													></v-progress-circular>
												</v-row>
											</template>
										</v-img>
									</v-item>
									<v-row justify="center">
										<div>
											<p class="text-uppercase font-weight-regular mt-3 ml-5">
												{{ a.nome }}
											</p>
											<v-btn
												class="mt-n5 ml-n1"
												text
												outlined
												small
												id="no-background-hover"
												to="/cliente/animal"
											>
												Ver mais
											</v-btn>
										</div>
									</v-row>
								</v-card>
							</v-col>
						</v-row>
					</v-item-group>
				</v-col>

				<v-col>
					<h3 class="font-weight-regular text-uppercase mb-4 mt-10">
						Consultas Agendadas
					</h3>

					<v-row justify="end">
            <v-col cols="auto">
              <v-btn
                class="mb-2 body-2 mr-2"
                small
                color="#2596be"
                dark
                to="/cliente/agendar/consulta"
              >
                Agendar Consulta
              </v-btn>

              <v-btn
                class="mb-2 body-2"
                small
                color="#2596be"
                dark
                to="/cliente/registar/animal"
              >
                Registar Animal
              </v-btn>
            </v-col>
          </v-row>
          
					<v-data-table
						:headers="headers"
						:items="consultas"
						class="elevation-1"
						hide-default-footer
					>
						<template v-slot:[`item.estado`]="{ item }">
							<v-chip :color="estadopedido(item.estado)" small>
								{{ item.estado }}
							</v-chip>
						</template>

						<template v-slot:[`item.detalhes`]="{ item }">
							<v-tooltip right>
								<template v-slot:activator="{ on, attrs }">
									<v-icon
										v-if="item.estado == 'Agendada'"
										v-bind="attrs"
										v-on="on"
										small
									>
										fas fa-calendar-times
									</v-icon>
								</template>
								<span class="caption">Cancelar marcação</span>
							</v-tooltip>
						</template>
					</v-data-table>
					<!--<v-row class="my-5">
						<v-col cols="auto">
							<v-btn color="#2596be" small dark to="/cliente/agendar/consulta">
								Agendar Consulta
							</v-btn>
						</v-col>
						<v-col cols="auto">
							<v-btn color="#2596be" small dark to="/cliente/registar/animal">
								Registar Animal
							</v-btn>
						</v-col>
					</v-row>-->
				</v-col>
			</v-row>
		</v-container>
		<Footer />
	</div>
</template>

<script>
import Header from "@/components/Headers/ClientHeader.vue";
import Footer from "@/components/Footer.vue";

export default {
	data() {
		return {
			animal: [
				{ scr: "Rubi.jpg", nome: "Rubi" },
				{ scr: "Rubi.jpg", nome: "Nikita" },
			],
			headers: [
				{
					text: "Nome",
					align: "start",
					sortable: true,
					value: "paciente",
				},
				{
					text: "Médico Veterinário",
					value: "medico",
					sortable: true,
					align: "start",
				},
				{
					text: "Data de Marcação",
					value: "data",
					sortable: true,
					align: "start",
				},
				{ text: "Estado", value: "estado", sortable: true, align: "center" },
				{
					text: "Mais detalhes",
					value: "detalhes",
					sortable: false,
					align: "center",
				},
			],
			consultas: [
				{
					paciente: "Runa",
					medico: "Drª Joana Ferreira",
					data: "22/08/2021 10:00",
					estado: "Agendada",
				},
				{
					paciente: "Puscas",
					medico: "Drª Joana Ferreira",
					data: "19/05/2021 16:30",
					estado: "Pendente",
				},
			],
		};
	},
	components: {
		Header,
		Footer,
	},
	methods: {
		estadopedido(estado) {
			if (estado == "Agendada") return "#C5E1A5";
			else return "#FFE082";
		},
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
	max-width: 250px;
}
</style>
