<template>
  <div>
    <v-container class="fill-height">
      <v-row align="stretch" class="w-100">
        <v-col>
          <v-card class="mx-auto h-100" max-width="344" outlined>
            <v-list-item three-line>
              <v-list-item-content>
                <div class="overline mb-4">
                  <v-icon small>fas fa-user</v-icon>
                  Clientes
                </div>
                <v-list-item-title class="headline mb-1">
                  100
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>

        <v-col>
          <v-card class="mx-auto h-100" max-width="344" outlined>
            <v-list-item three-line>
              <v-list-item-content>
                <div class="overline mb-4">
                  <v-icon small>fas fa-paw</v-icon>
                  Utentes
                </div>
                <v-list-item-title class="headline mb-1">
                  524
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>

        <v-col>
          <v-card class="mx-auto h-100" max-width="344" outlined>
            <v-list-item three-line>
              <v-list-item-content>
                <div class="overline mb-4">
                  <v-icon small>fas fa-user-md</v-icon>
                  Médicos Veterinários
                </div>
                <v-list-item-title class="headline mb-1">
                  10
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>

        <v-col>
          <v-card class="mx-auto h-100" max-width="344" outlined>
            <v-list-item three-line>
              <v-list-item-content>
                <div class="overline mb-4">
                  <v-icon small>fas fa-file-medical</v-icon>
                  Visitas
                </div>
                <v-list-item-title class="headline mb-1">
                  140 <span class="caption">(desde o início do ano)</span>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>

        <v-row>
      <v-divider></v-divider>
      </v-row>

      <v-row class="w-100">
        <v-col class="auto">
            <div>Consultas Agendadas</div>
            <div>
                <v-card max-width="344" outlined>
                    <consultas :width="200" :height="200" class="my-4"></consultas>
                </v-card>
            </div>
        </v-col>

        <v-col class="auto">
            <div>Cirurgias Agendadas</div>
            <div>
                <v-card max-width="344" outlined>
                    <cirurgias :width="200" :height="200" class="my-4"></cirurgias>
                </v-card>
            </div>
        </v-col>

       <v-col class="auto">
            <div>Espécies</div>
            <div>
                <v-card max-width="344" outlined>
                    <especie class="my-4" :height="230"></especie>
                </v-card>
            </div>
        </v-col>
      </v-row>

     <!-- <v-row class="w-100">
          <v-col class="auto">
            <div>Total de Visitas</div>
            <div>
                <v-card max-width="344" outlined>
                    <consultasMes class="my-4"></consultasMes>
                </v-card>
            </div>
        </v-col>
      </v-row>-->
    </v-container>
  </div>
</template>


<script>
import consultas from '@/components/Clinic/Relatorios/consultas'
import cirurgias from '@/components/Clinic/Relatorios/cirurgias'
import especie from '@/components/Clinic/Relatorios/especie'
//import consultasMes from '@/components/Clinic/Relatorios/consultasMes'
// import axios from "axios";
export default {
  data: () => ({
      
  }), 
  components:{
      consultas,
      cirurgias,
      especie,
      //consultasMes
  }
}
</script>