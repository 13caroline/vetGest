<template>
	<div>
		<v-container class="fill-height">
			<v-row justify="center">
				<v-col cols="12" class="mt-8">
					<h4 class="mx-4 font-weight-bold text-uppercase">
						<v-icon small class="mr-2">fas fa-calendar-alt</v-icon>
						Agendar Consulta Online
					</h4>
					<v-card-subtitle class="mt-1">
						<span class="subtitle">
							O agendamento de consultas deverá ser apenas para visitas de rotina.
						</span>
						<p class="subtitle">
							Se o seu animal de estimação necessita de uma observação urgente, por
							favor contacte a clínica o quanto antes.
						</p>
					</v-card-subtitle>
					<v-card-text>
						<v-stepper v-model="e1">
							<v-stepper-header>
								<v-stepper-step :complete="e1 > 1" step="1" editable color="#2596be">
									Identificação e motivo da consulta
								</v-stepper-step>

								<v-divider></v-divider>

								<v-stepper-step :complete="e1 > 2" step="2" editable color="#2596be">
									Escolher data e médico veterinário
								</v-stepper-step>

								<v-divider></v-divider>

								<v-stepper-step :complete="e1 > 3" step="3" color="#2596be">
									Rever informação
								</v-stepper-step>
							</v-stepper-header>

							<v-stepper-items>
								<v-stepper-content step="1">
									<v-card class="mb-2" flat>
										<div>
											<p class="ma-0">Escolha o animal</p>
											<v-autocomplete
												flat
												color="#2596be"
												dense
												outlined
												:items="animais"
											></v-autocomplete>
										</div>

										<div>
											<p class="ma-0">Motivo</p>
											<v-expansion-panels accordion class="mt-2">
												<v-expansion-panel>
													<v-expansion-panel-header class="font-weight-bold motivo">
														Consulta anual/Vacinação
													</v-expansion-panel-header>
													<v-expansion-panel-content>
														<span class="motivo">
															Como os animais envelhecem mais rapidamente que os humanos, a
															alteração da saúde do seu animal doméstico pode ocorrer em
															períodos de 6 a 12 meses. A avaliação periódica permite detetar
															essas alterações.
														</span>

														<p class="motivo">
															A vacinação ajuda o sistema imune do seu animal a lutar contra
															organismos causadores de doenças. Algumas vacinas enquadram-se
															nos requerimentos legais (como por exemplo, a vacina contra a
															raiva canina).
														</p>
														<v-row justify="end">
															<v-col cols="auto">
																<v-tooltip right>
																	<template v-slot:activator="{ on, attrs }">
																		<v-btn icon small @click="e1 = 2">
																			<v-icon color="green" v-bind="attrs" v-on="on">
																				far fa-calendar-check
																			</v-icon>
																		</v-btn>
																	</template>
																	<span class="caption">Agendar Consulta</span>
																</v-tooltip>
															</v-col>
														</v-row>
													</v-expansion-panel-content>
												</v-expansion-panel>

												<v-expansion-panel>
													<v-expansion-panel-header class="font-weight-bold motivo">
														Consulta extraordinária/Por doença
													</v-expansion-panel-header>
													<v-expansion-panel-content>
														<v-list flat>
															<v-list-item-group v-model="selectedItem" color="#2596be">
																<template v-for="(item, index) in items">
																	<v-list-item :key="item.text">
																		<v-list-item-content>
																			<v-list-item-title
																				class="motivo"
																				v-text="item.text"
																			></v-list-item-title>
																		</v-list-item-content>
																		<v-list-item-action>
																			<v-list-item-action-text
																				v-text="item.action"
																			></v-list-item-action-text>

																			<v-tooltip right>
																				<template v-slot:activator="{ on, attrs }">
																					<v-btn icon small @click="e1 = 2">
																						<v-icon color="green" v-bind="attrs" v-on="on">
																							far fa-calendar-check
																						</v-icon>
																					</v-btn>
																				</template>
																				<span class="caption">Agendar Consulta</span>
																			</v-tooltip>
																		</v-list-item-action>
																	</v-list-item>

																	<v-divider :key="index"></v-divider>
																</template>
															</v-list-item-group>
														</v-list>
													</v-expansion-panel-content>
												</v-expansion-panel>

												<v-expansion-panel>
													<v-expansion-panel-header class="font-weight-bold motivo">
														Consulta de seguimento
													</v-expansion-panel-header>
													<v-expansion-panel-content>
														<p class="motivo">
															Agende uma consulta de seguimento para o seu animal.
														</p>
														<v-row justify="end">
															<v-tooltip right>
																<template v-slot:activator="{ on, attrs }">
																	<v-btn icon small @click="e1 = 2">
																		<v-icon color="green" v-bind="attrs" v-on="on">
																			far fa-calendar-check
																		</v-icon>
																	</v-btn>
																</template>
																<span class="caption">Agendar Consulta</span>
															</v-tooltip>
														</v-row>
													</v-expansion-panel-content>
												</v-expansion-panel>

												<v-expansion-panel>
													<v-expansion-panel-header class="font-weight-bold motivo">
														Procedimentos específicos
													</v-expansion-panel-header>
													<v-expansion-panel-content>
														<v-list flat>
															<v-list-item-group v-model="selectedItem" color="#2596be">
																<template v-for="(item, index) in tech">
																	<v-list-item :key="item.text">
																		<v-list-item-content>
																			<v-list-item-title
																				class="motivo"
																				v-text="item.text"
																			></v-list-item-title>
																		</v-list-item-content>
																		<v-list-item-action>
																			<v-list-item-action-text
																				v-text="item.action"
																			></v-list-item-action-text>

																			<v-tooltip right>
																				<template v-slot:activator="{ on, attrs }">
																					<v-btn icon small @click="e1 = 2">
																						<v-icon color="green" v-bind="attrs" v-on="on">
																							far fa-calendar-check
																						</v-icon>
																					</v-btn>
																				</template>
																				<span class="caption">Agendar Consulta</span>
																			</v-tooltip>
																		</v-list-item-action>
																	</v-list-item>

																	<v-divider :key="index"></v-divider>
																</template>
															</v-list-item-group>
														</v-list>
													</v-expansion-panel-content>
												</v-expansion-panel>
											</v-expansion-panels>
										</div>
									</v-card>
								</v-stepper-content>

								<v-stepper-content step="2">
									<v-card class="mb-2" flat>
										<div>
											<p class="mb-0 mt-2">Data</p>
											<v-menu
												ref="dataMarcacao"
												v-model="dataMarcacao"
												:close-on-content-click="true"
												:nudge-right="40"
												:return-value.sync="dataMarcacao"
												transition="scale-transition"
												offset-y
												max-width="290px"
												min-width="290px"
											>
												<template v-slot:activator="{ on, attrs }">
													<v-text-field
														append-icon="fas fa-calendar-day"
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
											<p class="ma-0">Hora</p>
											<v-menu
												ref="horaMarcacao"
												v-model="horaMarcacao"
												:close-on-content-click="false"
												:nudge-right="40"
												:return-value.sync="horaMarcacao"
												transition="scale-transition"
												offset-y
												max-width="290px"
												min-width="290px"
											>
												<template v-slot:activator="{ on }">
													<v-text-field
														append-icon="fas fa-clock"
														color="#2596be"
														v-on="on"
														outlined
														dense
														v-model="hora"
													></v-text-field>
												</template>
												<v-time-picker
													format="24hr"
													v-model="hora"
													full-width
													color="#2596be"
												></v-time-picker>
											</v-menu>
										</div>

										<div>
											<p class="ma-0">Médico Veterinário</p>
											<v-autocomplete
												flat
												color="#2596be"
												dense
												outlined
												:items="medico"
												v-model="medicoVet"
											></v-autocomplete>
										</div>
									</v-card>

									<v-row align="end" justify="end">
										<v-col cols="auto">
											<v-btn color="#BDBDBD" small dark @click="dialog = true">
												Cancelar
											</v-btn>
											<v-btn color="#2596be" small dark class="ml-3" @click="e1 = 3">
												Continuar
											</v-btn>
										</v-col>
									</v-row>
								</v-stepper-content>

								<v-stepper-content step="3">
									<v-card class="mb-12" flat>
										<v-card-title class="font-weight-bold text-uppercase">
											Nova consulta para Rubi
										</v-card-title>
										<v-card-subtitle>
											<p class="subtitle">Vacinação</p>
										</v-card-subtitle>
										<v-card-text>
											<p class="font-weight-bold black--text">
												*Nome pessoa*, por favor reveja a informação e confirme o
												agendamento:
											</p>

											<span class="text-uppercase">Nome do Animal</span>
											<span class="black--text">
												<strong>Rubi</strong>
												(Serra da Estrela)
											</span>

											<div>
												<span class="text-uppercase">Motivo da Consulta</span>
												<span class="black--text">
													<strong>Vómito/Diarreia/Recusa a comer</strong>
												</span>
												<span>Consulta extraordinária/Por doença</span>
											</div>

											<div>
												<span class="text-uppercase">Data</span>
												<span class="black--text">
													<strong>21/05/2021 15:45</strong>
												</span>
											</div>

											<div>
												<span class="text-uppercase">Médico</span>
												<span class="black--text">
													<strong>Sem preferência</strong>
												</span>
											</div>

											<div>
												<span class="text-uppercase">
													Algum comentário específico para a sua visita?
												</span>
												<v-textarea rows="2" outlined></v-textarea>
											</div>

											<v-row align="end" justify="end">
												<v-col cols="auto">
													<v-btn color="#BDBDBD" small dark @click="dialog = true">
														Cancelar
													</v-btn>
													<v-btn color="#2596be" small dark class="ml-3" @click="e1 = 3">
														Confirmar
													</v-btn>
												</v-col>
											</v-row>
										</v-card-text>
									</v-card>
								</v-stepper-content>
							</v-stepper-items>
						</v-stepper>
					</v-card-text>
				</v-col>
			</v-row>
		</v-container>
		<v-dialog v-model="dialog" persistent width="30%">
			<v-card>
				<v-card-title class="justify-center cancel">
					Cancelar Agendamento da Consulta
				</v-card-title>
				<v-card-text>
					Tem a certeza que pretende cancelar o agendamento da consulta?
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn
						depressed
						large
						width="50%"
						dark
						color="#BDBDBD"
						@click="dialog = false"
					>
						Não
					</v-btn>
					<v-btn depressed large dark color="#2596be" width="50%" to="/cliente/home">
						Sim
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
//import moment from 'moment';
export default {
	data: () => ({
		e1: 1,
		dialog: false,
		dataMarcacao: null,
		horaMarcacao: null,
		date: new Date().toISOString().substr(0, 10),
		hora: new Date().getHours() + ":" + new Date().getMinutes(),
		animais: ["Rubi", "Puscas", "Nikita", "Rudi"],
		medico: ["Sem Preferência", "Drº José Vieira", "Drª Joana Ferreira"],
		medicoVet: "Sem Preferência",
		items: [
			{ text: "Vómitos/Diarreia/Recusa em comer" },
			{ text: "Comportamento letárgico" },
			{ text: "Alterações da marcha" },
			{ text: "Problema de olhos ou ouvidos" },
			{ text: "Problemas de dentes ou boca" },
			{ text: "Problemas cutâneos" },
			{ text: "Problemas urinários" },
			{ text: "Outros" },
		],
		tech: [
			{ text: "Cortar unhas" },
			{ text: "Expressão de glândulas anais" },
			{ text: "Análises" },
			{ text: "Limpeza de ouvidos" },
			{ text: "Cortar o pêlo" },
			{ text: "Lavagem" },
			{ text: "Desparasitação" },
			{ text: "Outros" },
		],
	}),
};
</script>

<style scoped>
.subtitle {
	color: #2596be;
}

.motivo {
	font-size: 13px;
}
</style>
