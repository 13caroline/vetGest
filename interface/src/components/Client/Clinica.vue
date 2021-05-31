<template>
  <div>
    <v-container>
      <v-row class="mt-5">
        <v-col cols="12">
          <h3 class="font-weight-regular text-uppercase">
            <v-icon small>fas fa-address-card</v-icon>
            Contactos
          </h3>
        </v-col>
      </v-row>

      <v-row class="w-100">
        <v-col cols="6">
          <div>
            <h4 class="font-weight-regular text-uppercase">
            Informação de contacto
          </h4>
          </div>
          <div class="mt-2">
            <v-icon > fab fa-pagelines</v-icon>
            <span class="ml-1 grey--text font-weight-light">Rua da Universidade</span>
            <p class="ml-5 grey--text font-weight-light">4710-057 Braga</p>
          </div>
          <div class="mt-2">
            <v-icon small> fas fa-phone</v-icon>
            <span class="ml-1">Telefone:</span><span class="ml-1 grey--text font-weight-light">253 601 100</span>
          </div>
          <div class="mt-2">
            <v-icon small> fas fa-envelope</v-icon>
            <span class="ml-1" >Email:</span><span class="ml-1 grey--text font-weight-light">clinicaveterinariadominho@gmail.com</span>
          </div>
        </v-col>

        <v-col cols="6">
          <div>
            <h4 class="font-weight-regular text-uppercase">
            Horário de Funcionamento
          </h4>
          </div>
          <div class="mt-3">
            <span class="grey--text font-weight-bold" >Segunda a Sexta</span>
            <br>
            <span class="grey--text font-weight-light" >das 10:00h às 13:00h</span>
            <br>
            <span class="grey--text font-weight-light" >das 14:30h às 20:00h</span>
          </div>
          <div class="mt-3">
            <span class="grey--text font-weight-bold" >Sábado e Feriados</span>
            <br>
            <span class="grey--text font-weight-light" >das 10:00h às 13:00h</span>
          </div>
          <div class="mt-3">
            <span class="grey--text font-weight-bold" >Domingo</span>
            <br>
            <span class="grey--text font-weight-light" >Fechado</span>
          </div>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-first-aid</v-icon>
                Serviços
              </h3>
              <p>
                Saiba mais sobre os serviços médico-veterinários da Clínica Veterinária do Minho.
              </p>
            </v-col>
          </v-row>
          <v-row align="center" justify="center" class="potencialidades" dense>
            <v-col cols="12" md="4" sm="6">
              <v-img
                src="@/assets/consultas.png"
                max-height="100"
                max-width="100"
                contain
                class="imgin"
                @click="dialog = true"
              >
              </v-img>
              <span class="span" align="end" justify="center">Consultas</span>
            </v-col>
            <v-col cols="12" md="4" sm="6">
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
            <v-col cols="12" md="4" sm="6">
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
            <v-col cols="12" md="4" sm="6">
              <v-img
                src="@/assets/marcacoes.png"
                max-height="100"
                max-width="100"
                contain
                class="imgin"
              >
              </v-img>
              <span class="span" align="end" justify="center"
                >Marcações Online</span
              >
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-img
                src="@/assets/medico.png"
                max-height="100"
                max-width="100"
                contain
                class="imgin"
              >
              </v-img>
              <span class="span" align="end" justify="center"
                >Escolha do Médico Veterinário</span
              >
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-img
                src="@/assets/processo.png"
                max-height="100"
                max-width="100"
                contain
                class="imgin"
              >
              </v-img>
              <span class="span" align="end" justify="center"
                >Visualização do Processo Clínico</span
              >
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-row>
      <v-col cols="12">
          <v-row align="center" class="my-5">
            <v-col>
              <h3 class="font-weight-regular text-uppercase">
                <v-icon small>fas fa-user-md</v-icon>
                Equipa
              </h3>
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
  }),
};
</script>