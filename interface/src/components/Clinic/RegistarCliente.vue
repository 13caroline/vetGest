<template>
  <div>
    <v-container class="fill-height">
      <v-row>
        <v-col cols="12">
          <v-row class="w-100">
            <h3 class="font-weight-regular text-uppercase mb-4 mt-10 ml-3">
              <v-icon class="mr-2">fas fa-user</v-icon>
              Registar novo cliente
            </h3>
          </v-row>
          <div class="indication">
            <span class="body-2"
              >Por favor preencha o seguinte formulário
            </span>
          </div>

          <v-form ref="form" v-model="valid" lazy-validation>
            <p class="mb-0 mt-4">Nome completo</p>
            <v-text-field
              outlined
              flat
              dense
              single-line
              :rules="nameRules"
              color="#2596be"
              placeholder="Nome"
              v-model="nome"
              name="nome"
              required
            />

            <p class="ma-0">E-mail</p>
            <v-text-field
              outlined
              flat
              dense
              v-model="email"
              single-line
              :rules="ruaEmail"
              color="#2596be"
              name="email"
              placeholder="E-mail"
              required
            />

            <v-row class="mt-1">
              <v-col cols="12" sm="6" class="py-0">
                <p class="ma-0">Morada</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  v-model="rua"
                  single-line
                  :rules="ruaRules"
                  color="#2596be"
                  name="rua"
                  placeholder="Morada"
                  required
                />
              </v-col>
              <v-col class="py-0">
                <p class="ma-0">Freguesia</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  v-model="freguesia"
                  :rules="freguesiaRules"
                  color="#2596be"
                  name="freguesia"
                  placeholder="Freguesia"
                  required
                />
              </v-col>

              <v-col class="py-0">
                <p class="ma-0">Concelho</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  color="#2596be"
                  :rules="concelhoRules"
                  name="concelho"
                  placeholder="Concelho"
                  v-model="concelho"
                  required
                />
              </v-col>
            </v-row>

            <v-row align="end">
              <v-col cols="auto" class="dial_code py-0">
                <v-text-field
                  outlined
                  flat
                  background-color="#E0E0E0"
                  dense
                  single-line
                  color="#2596be"
                  value="+351"
                  readonly
                />
              </v-col>
              <v-col class="py-0">
                <p class="ma-0">Contacto telefónico</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  color="#2596be"
                  placeholder="Contacto telefónico"
                  name="contacto"
                  v-model="contacto"
                  maxlength="9"
                  required
                />
              </v-col>
              <v-col cols="12" sm class="py-0">
                <p class="ma-0">Número de identificação fiscal</p>
                <v-text-field
                  outlined
                  flat
                  dense
                  single-line
                  color="#2596be"
                  placeholder="Número de Identificação Fiscal"
                  name="nif"
                  v-model="nif"
                  maxlength="9"
                  required
                />
              </v-col>
            </v-row>
          </v-form>
          <v-row align="end" justify="end">
            <v-col cols="auto" class="pr-0">
              <Cancelar :dialogs="cancelar" @clicked="close()"></Cancelar>
            </v-col>
            <v-col cols="auto" class="pl-0">
              <v-btn color="#2596be" small dark class="ml-3">Registar</v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
//import moment from 'moment';
import Cancelar from "@/components/Dialogs/Cancel.vue"
export default {
  data: () => ({
   dialogs: {},
    cancelar: {title: "registo de cliente", text: "o registo de um cliente"},
  }),
  components:{
    Cancelar
  },
  methods: {
     close(){
      this.$router.push("/clinica/clientes")
    },
  }
};
</script> 

<style scoped>
.dial_code {
  width: 90px;
}
</style>
