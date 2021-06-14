<template>
  <div>
    <nav>
      <v-app-bar flat app color="white">
        <v-app-bar flat color="white" class="text-uppercase black--text">
          <v-btn
            icon
            class="no-background-hover d-lg-none"
            @click.stop="drawer = !drawer"
          >
            <v-icon>fas fa-bars</v-icon>
          </v-btn>
          <span class="font-weight-light ml-2 ml-lg-0">CVet</span>
          <span>Minho</span>
        </v-app-bar>
        <v-spacer></v-spacer>
        <v-btn text @click="logout">
          <span class="d-none d-sm-block">Terminar Sessão</span>
          <v-icon right>fas fa-sign-out-alt</v-icon>
        </v-btn>
      </v-app-bar>
      <v-layout align-center justify-space-between column fill-height>
        <v-navigation-drawer dark app v-model="drawer" class="grey darken-3">
          <v-list-item two-line>
            <v-list-item-avatar>
              <v-img src="@/assets/CVetMinho2.png"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title class="text-uppercase color"
                >Médico Veterinário</v-list-item-title
              >
            </v-list-item-content>
          </v-list-item>

          <v-divider></v-divider>

          <v-list nav>
            <v-list-item
              v-for="item in items"
              :key="item.title"
              router
              :to="item.route"
              link
            >
              <v-list-item-icon>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title class="caption">{{
                  item.title
                }}</v-list-item-title>
              </v-list-item-content>
              <v-card height="20" flat></v-card>
            </v-list-item>
          </v-list>
          <template v-slot:append>
            <div class="pa-2">
              <v-btn text class="caption" @click="preferencias()">
                <v-icon left>fas fa-cog</v-icon>Preferências
              </v-btn>
            </div>
          </template>
        </v-navigation-drawer>
      </v-layout>
    </nav>
  </div>
</template>



<script>
import axios from "axios";
import store from "@/store.js";

export default {
  name: "Navbar",
  data: () => ({
    drawer: true,
    utilizador: {},
    items: [
      {
        title: "Dashboard",
        icon: "fas fa-tachometer-alt",
        route: "/medico/inicio/",
      },
      {
        title: "Ficha do utente",
        icon: "fas fa-paw",
        route: "/medico/utentes",
      },
      {
        title: "Marcações de Consultas",
        icon: "fas fa-scroll",
        route: "/medico/consultas",
      },
      {
        title: "Marcações de Cirurgias",
        icon: "fas fa-band-aid",
        route: "/medico/cirurgias",
      },
      {
        title: "Internamento",
        icon: "fas fa-procedures",
        route: "/medico/internamento",
      },
    ],
  }),
  methods: {
    logout: async function () {
      try {
        await axios.get(
          "http://localhost:7777/terminar-sessao",
          { headers: { Authorization: "Bearer " + store.getters.token } }
        );
        this.$store.commit("limpaStore");
        this.$router.push("/");
      } catch (e) {
        this.$store.commit("limpaStore");
        this.$router.push("/");
      }
    },
    toMainPage: function () {
      this.$router.push("/medico/inicio");
    },
    preferences: function () {
      this.$router.push("/preferencias");
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

.color {
  color: #2596be;
}
</style>