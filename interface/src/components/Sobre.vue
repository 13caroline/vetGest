<template>
  <div>
    <v-img src="../assets/sobre.png" class="img" >
      <v-row>
        <h2>Sobre</h2>
      </v-row>

      <v-row>
        <v-card color="transparent" flat class="sobre py-4" max-width="40%">
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
        </v-card>
      </v-row>
    </v-img>

    <v-container fluid pa-0 style="height: 100%">
      <v-row align="center" justify="center">
        <span class="servicos ma-16 text-uppercase">Os nossos serviços</span>
      </v-row>
      <v-row align="center" justify="center" class="potencialidades" dense>
        <v-col
          cols="12"
          lg="4"
          md="4"
          class="white fill-height d-flex flex-column justify-center align-center"
        >
          <v-card flat tile class="ma-5">
            <v-img
              src="../assets/consultas.png"
              max-height="150"
              max-width="150"
              contain
              class="imgin"
            >
            </v-img>
            <span class="span" align="end" justify="center">Consultas</span>
          </v-card>
        </v-col>
        <v-col
          cols="12"
          lg="4"
          md="4"
          class="white fill-height d-flex flex-column justify-center align-center"
        >
          <v-card flat tile class="ma-5">
            <v-img
              src="../assets/cirurgias.png"
              max-height="150"
              max-width="150"
              contain
              class="imgin"
            >
            </v-img>
            <span class="span" align="end" justify="center">Cirurgias</span>
          </v-card>
        </v-col>
        <v-col
          cols="12"
          lg="4"
          md="4"
          class="white fill-height d-flex flex-column justify-center align-center"
        >
          <v-card flat tile class="ma-5">
            <v-img
              src="../assets/produtos.png"
              max-height="150"
              max-width="150"
              contain
              class="imgin"
            >
            </v-img>
            <span class="span" align="end" justify="center">Produtos</span>
          </v-card>
        </v-col>
      </v-row>

      <v-card flat width="80%" class="mx-auto my-10">
            <v-divider></v-divider>
      </v-card>

      <v-row align="center" justify="center" dense class="potencialidades">
        <v-col
          cols="12"
          lg="4"
          md="4"
          class="white fill-height d-flex flex-column justify-center align-center"
        >
          <v-card flat tile class="ma-5">
            <v-img
              src="../assets/marcacoes.png"
              max-height="150"
              max-width="150"
              contain
              class="imgin"
            >
            </v-img>
            <span class="span" align="end" justify="center">Marcações Online</span>
          </v-card>
        </v-col>
        <v-col
          cols="12"
          lg="4"
          md="4"
          class="white fill-height d-flex flex-column justify-center align-center"
        >
          <v-card flat tile class="ma-5">
            <v-img
              src="../assets/medico.png"
              max-height="150"
              max-width="150"
              contain
              class="imgin"
            >
            </v-img>
            <span class="span" align="end" justify="center">Escolha do Médico Veterinário</span>
          </v-card>
        </v-col>
        <v-col
          cols="12"
          lg="4"
          md="4"
          class="white fill-height d-flex flex-column justify-center align-center"
        >
          <v-card flat tile class="ma-5">
            <v-img
              src="../assets/processo.png"
              max-height="150"
              max-width="150"
              contain
              class="imgin"
            >
            </v-img>
            <span class="span" align="end" justify="center">Visualização do Processo Clínico</span>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style scoped>
h2 {
  font-size: 5rem;
  color: #424242;
  font-family: "Old Standard TT";
  margin-left: 8rem;
  margin-top: 5rem;
}

.sobre {
  font-size: 20px;
  font-family: "Old Standard TT";
  margin-left: 8rem;
  margin-top: 1rem;
  text-align: justify;
  text-justify: inter-word;
}

p {
  color: #424242;
}

.services {
  font-size: 1rem;
  font-family: "Old Standard TT";
  margin-top: 1rem;
}

.servicos {
  font-size: 3rem;
  color: #424242;
  font-family: "Old Standard TT";
  margin-top: 2rem;
}

.span {
  font-size: 30px;
  font-family: "Old Standard TT";
  text-align: center;
  text-justify: inter-word;
  display: inline-block;
  width: 95%;
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
  margin-right: auto
}
</style>