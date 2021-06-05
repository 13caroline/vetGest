<template>
  <div>
    <v-dialog v-model="dialog" persistent width="100%" max-width="460">
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="#BDBDBD" small dark v-bind="attrs" v-on="on">
          Cancelar
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="justify-center cancel">
          Cancelar {{dialogs.title}}
        </v-card-title>
        <v-card-text>
          Tem a certeza que pretende cancelar {{dialogs.text}}?
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
            @click="close()"
          >
            Sim
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  props: ["dialogs"],
  data: () => ({
      dialog: false, 
  }),
  methods:{
    close(){
      this.dialog = false, 
      this.$emit('clicked', 'cancel')
    }
  }
};
</script>