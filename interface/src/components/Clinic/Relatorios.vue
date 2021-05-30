<template>
  <div>
    <v-container class="fill-height">
      <v-row align="center" class="w-100">
        <v-col class="auto">
          <v-card class="mx-auto" max-width="344" outlined>
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

        <v-col class="auto">
          <v-card class="mx-auto" max-width="344" outlined>
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

        <v-col class="auto">
          <v-card class="mx-auto" max-width="344" outlined>
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

        <v-col class="auto">
          <v-card class="mx-auto" max-width="344" outlined>
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
 
  <div class="small">
    

  </div>

        </v-col>
      </v-row>
    </v-container>
  </div>
</template>


<script>

// import axios from "axios";
export default {
  data: () => ({
      
  }), 
  components:{
   
  }
}
</script>