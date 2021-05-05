<template>
  <div>
    <v-img src="../assets/sobre.png" class="img" >

			<v-container>
				<h2 class="my-4">Sobre</h2>

        <v-row>
					<v-col cols="12" sm="8" md="7" class="sobre py-4">
            <p>
							A CVetMinho - Clínica Veterinária do Minho tem como principal
							objetivo a prestação de cuidados médico-veterinários.
            </p>

            <p>
                A nossa clínica é composta por uma equipa jovem e qualificada, que
                se compromete a prestar um acompanhamento responsável a todos os
                animais. Dela fazem parte médico-veterinários e enfermeiros, num
                serviço de atendimento permanente 24 horas por dia, 365 dias por
                ano.
            </p>

            <p>
                O valor do progresso tecnológico na área da medicina veterinária é
                irrevogável, pelo que a CVetMinho aposta na inovação e integração
                informática dos seus serviços.
            </p>

            <p>
                A nossa principal preocupação é o bem-estar e a qualidade de
                vida/saúde dos animais de companhia.
            </p>

            <p>
							Regemo-nos pelos mais elevados padrões éticos e aplicamos sempre os
							bons princípios médico-veterinários.
            </p>
					</v-col>
        </v-row>
      </v-container>
    </v-img>

    <v-container>
      <v-row align="center" justify="center">
        <span class="servicos mb-12 text-uppercase">Os nossos serviços</span>
      </v-row>
      <v-row align="center" justify="center" class="potencialidades" dense>
        <v-col
          cols="12"
          md="4"
          sm="6"
        >
					<v-img
						src="../assets/consultas.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
					>
					</v-img>
					<span class="span" align="end" justify="center">Consultas</span>
        </v-col>
        <v-col
          cols="12"
          md="4"
          sm="6"
        >
					<v-img
						src="../assets/cirurgias.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
					>
					</v-img>
					<span class="span" align="end" justify="center">Cirurgias</span>
        </v-col>
        <v-col
          cols="12"
          md="4"
          sm="6"
        >
					<v-img
						src="../assets/produtos.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
					>
					</v-img>
					<span class="span" align="end" justify="center">Produtos</span>
        </v-col>
      </v-row>

      <v-card flat width="80%" class="mx-auto my-10">
				<v-divider></v-divider>
      </v-card>

      <v-row align="center" justify="center" dense class="potencialidades">
        <v-col
          cols="12"
          md="4"
          sm="6"
        >
					<v-img
						src="../assets/marcacoes.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
					>
					</v-img>
					<span class="span" align="end" justify="center">Marcações Online</span>
        </v-col>
        <v-col
          cols="12"
          md="4"
          sm="6"
        >
					<v-img
						src="../assets/medico.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
					>
					</v-img>
					<span class="span" align="end" justify="center">Escolha do Médico Veterinário</span>
        </v-col>
        <v-col
          cols="12"
          md="4"
          sm="6"
        >
					<v-img
						src="../assets/processo.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
					>
					</v-img>
					<span class="span" align="end" justify="center">Visualização do Processo Clínico</span>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style scoped>

.sobre {
  font-size: 18px;
  text-align: justify;
  text-justify: inter-word;
}

p {
  color: #424242;
}

.servicos {
  color: #424242;
  margin-top: 2rem;
	font-size: 7vw;
	color: #424242;
}
@media screen and (min-width: 768px) {
	.servicos {
		font-size: 3rem;
	}
}

.span {
  font-size: 25px;
  text-align: center;
  text-justify: inter-word;
  display: inline-block;
  width: 75%;
	display: block;
	margin: auto;
}

.img {
  height: 30%;
  width:100vw;
}

.potencialidades{
  margin: 5%;
}

.imgin{
  margin-left: auto;
  margin-right: auto;
	margin-top: 20px;
}
</style>