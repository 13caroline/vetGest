<template>
  <div id="nav">
    <v-toolbar color="#FFFFFF" flat>
      <v-img
        src="@/assets/CVetMinho2.png"
        max-height="50"
        max-width="50"
        @click="home()"
      ></v-img>

      <v-spacer></v-spacer>
      <!--<v-btn
        tile
        depressed
        text
        class = "font-weight-regular"
        id="no-background-hover"
        to="/books"
      >
        Consultas
      </v-btn>

      <v-btn
        tile
        depressed
        text
        class = "font-weight-regular"
        id="no-background-hover"
        to="/my/lists/books"
      >
        Cirurgias
      </v-btn>

     <v-btn
        tile
        depressed
        text
        class = "font-weight-regular"
        id="no-background-hover"
        to="/clinica/pacientes"
      >
        Pacientes
      </v-btn>

 <v-btn
        tile
        depressed
        text
        class = "font-weight-regular"
        id="no-background-hover"
        to="/my/lists/books"
      >
        Clientes
      </v-btn>

      <v-btn
        tile
        depressed
        text
        class = "font-weight-regular"
        id="no-background-hover"
        to="/my/lists/books"
      >
        Conselhos Úteis
      </v-btn>-->

      <v-btn-toggle tile background-color="white" color="grey" group>
        <v-btn value="consultas"> Consultas </v-btn>

        <v-btn value="cirurgias"> Cirurgias </v-btn>

        <v-btn value="pacientes" to="/clinica/pacientes"> Pacientes </v-btn>

        <v-btn value="clientes"> Clientes </v-btn>

        <v-btn value="conselhos"> Conselhos Úteis </v-btn>
      </v-btn-toggle>

      <v-spacer></v-spacer>

      <v-menu offset-y tile open-on-hover>
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on" id="no-background-hover">
            <v-icon>fas fa-bars</v-icon>
          </v-btn>
        </template>

        <v-list dense color="#FFFFFF">
          <v-list-item @click="toMainPage()">
            <v-list-item-title class="caption">
              <v-icon x-small class="mt-n1 mr-1"> fas fa-home </v-icon> Página
              Inicial</v-list-item-title
            >
          </v-list-item>
          <v-list-item @click="preferences()">
            <v-list-item-title class="caption">
              <v-icon x-small class="mt-n1 mr-1"> fas fa-user-cog </v-icon>Gerir
              Perfil</v-list-item-title
            >
          </v-list-item>
          <v-list-item @click="logout()">
            <v-list-item-title class="caption">
              <v-icon x-small class="mt-n1 mr-1"> fas fa-sign-out-alt </v-icon>
              Terminar Sessão</v-list-item-title
            >
          </v-list-item>
        </v-list>
      </v-menu>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  name: "Navbar",
  data: () => ({}),
  methods: {
    logout: async function () {
      this.$store.commit("eliminarToken");
      this.$router.push("/");
    },
    toMainPage: function () {
      this.$router.push("/cliente/home");
    },
    preferences: function () {
      this.$router.push("/preferences");
    },
    home: function () {
      this.$router.push("/menu");
    },
  },
};
</script>

<style>
#no-background-hover::before {
  background-color: transparent !important;
}
</style>