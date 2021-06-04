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
              O agendamento de consultas deverá ser apenas para visitas de
              rotina.
            </span>
            <p class="subtitle">
              Se o seu animal de estimação necessita de uma observação urgente,
              por favor contacte a clínica o quanto antes.
            </p>
          </v-card-subtitle>
          <v-card-text>
            <span class="group font-weight-light text-uppercase">Identificação e motivo da consulta</span>

            <div>
              <p class="mb-0 mt-5">Escolha o animal</p>
              <v-autocomplete
                flat
                color="#2596be"
                dense
                outlined
                :items="animais"
                v-model="animal"
              ></v-autocomplete>
            </div>

            <div>
              <p class="ma-0">Motivo</p>
              <v-select
                flat
                color="#2596be"
                dense
                outlined
                :items="motivos"
                v-model="motivo"
              ></v-select>
            </div>

            <div>
              <p
                v-if="
                  motivo == 'Consulta extraordinária/Por doença' ||
                  motivo == 'Procedimentos específicos'
                "
                class="ma-0"
              >
                Descrição
              </p>
              <v-select
                v-if="motivo == 'Consulta extraordinária/Por doença'"
                flat
                color="#2596be"
                dense
                outlined
				v-model="descricao"
                :items="items"
              ></v-select>

              <v-select
                v-if="motivo == 'Procedimentos específicos'"
                flat
                color="#2596be"
                dense
                outlined
				v-model="descricao"
                :items="tech"
              ></v-select>
            </div>

            <v-divider class="mb-5"></v-divider>

            <span class="group font-weight-light text-uppercase">Detalhes da consulta</span>

            <div>
              <div>
                    <p class="ma-0 mt-5">Data *</p>
                    <v-menu
                      v-model="menu2"
                      :close-on-content-click="false"
                      :nudge-right="40"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="date"
                          append-icon="fas fa-calendar-alt"
                          readonly
                          dense
                          outlined
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="date"
                        @input="menu2 = false"
                        locale="pt PT"
                        :min='new Date().toISOString().substr(0, 10)'
                      ></v-date-picker>
                    </v-menu>
                  </div>
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

            <v-row align="end" justify="end">
                        <v-col cols="auto">
                          <v-btn
                            color="#BDBDBD"
                            small
                            dark
                            @click="dialog = true"
                          >
                            Cancelar
                          </v-btn>
                          <v-btn
                            color="#2596be"
                            small
                            dark
                            class="registaConsulta()"
                            @click="getAnimais()"
                          >
                            Confirmar
                          </v-btn>
                        </v-col>
                      </v-row>
          </v-card-text>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
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
          <v-btn
            depressed
            large
            dark
            color="#2596be"
            width="50%"
            to="/cliente/inicio"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
//import moment from 'moment';
import axios from "axios"
import store from "@/store.js"

export default {
  data: () => ({
    e1: 1,
    dialog: false,
    menu2: false, 
    horaMarcacao: null,
    date: new Date().toISOString().substr(0, 10),
    hora: new Date().getHours() + ":" + new Date().getMinutes(),
    animais: [],
    /*animais: ["Rubi", "Puscas", "Nikita", "Rudi"], */
    medico: ["Sem Preferência", "Drº José Vieira", "Drª Joana Ferreira"],
    motivo: [],
    animal: "",
    medicoVet: "Sem Preferência",
    items: [
      "Vómitos/Diarreia/Recusa em comer",
      "Comportamento letárgico",
      "Alterações da marcha",
      "Problema de olhos ou ouvidos",
      "Problemas de dentes ou boca",
      "Problemas cutâneos",
      "Problemas urinários",
      "Outros",
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
    motivos: [
      "Consulta anual/Vacinação",
      "Consulta extraordinária/Por doença",
      "Consulta de seguimento",
      "Procedimentos específicos",
    ],
  }),
  methods:{
   registaConsulta: async function() {
           try{
              var resposta = await axios.post("http://localhost:7777/cliente/consulta", {
                data: this.data,
                hora: this.hora,
                descricao: this.descricao,
                motivo: this.motivo,
                animais: this.animais,
                veterinario: this.veterinario,
           });
         } catch(e){
           console.log("erro: +" + e);
         }
         console.log(resposta);

    
  },
  }, 
  created: async function() {
           try{
              var response = await axios.post("http://localhost:7777/cliente/animais", {
              email: this.$store.state.email,
           },
           {
      headers: {
            "Authorization": 'Bearer ' +store.getters.token.toString()
       }   
       
       });
         } catch(e){
           console.log("erro: +" + e);
         }
         for(var i=0; i<response.data.animais.length;i++ ){
         this.animais.push(response.data.animais[i].nome)
         }
  
},
}
</script>

<style scoped>
.subtitle {
  color: #2596be;
}

.motivo {
  font-size: 13px;
}

span {
  font-size: 20px;
}

.group{
	color: #BDBDBD;
}
</style>
