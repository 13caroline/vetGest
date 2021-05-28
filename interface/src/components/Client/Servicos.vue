<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-scroll</v-icon>
                    Serviços
              </h3>
              <p>The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through.</p>
            </v-col>
          </v-row>
          <v-row align="center" justify="center" class="potencialidades" dense>
        <v-col
          cols="12"
          md="4"
          sm="6"
        >
					<v-img
						src="@/assets/consultas.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
                        @click="dialog=true"
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
						src="@/assets/cirurgias.png"
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
						src="@/assets/produtos.png"
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
						src="@/assets/marcacoes.png"
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
						src="@/assets/medico.png"
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
						src="@/assets/processo.png"
						max-height="100"
						max-width="100"
						contain
						class="imgin"
					>
					</v-img>
					<span class="span" align="end" justify="center">Visualização do Processo Clínico</span>
        </v-col>
      </v-row>
        </v-col>
      </v-row>

      <v-dialog v-model="dialog" width="100%" max-width="460">
        <v-card>
          <v-card-text class="black--text">
            <v-row align="center">
              <v-col cols="auto">
                <v-img
                  max-height="100"
                  max-width="100"
                  src="@/assets/consultas.png"
                >
                </v-img>
              </v-col>
              <v-col>
                <span class="font-weight-bold headline indication">
                  CONSULTAS
                </span>
              </v-col>
            </v-row>

            <div>
              <p>Consulta de rotina</p>
              <v-divider></v-divider>
              <p>Vacinação</p>
              <v-divider></v-divider>
              <p>Desparasitação</p>
              <v-divider></v-divider>
              <p>Consulta de especialidade</p>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
  })
}
</script>