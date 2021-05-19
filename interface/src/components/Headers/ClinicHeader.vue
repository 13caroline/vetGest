<template>
  <div>
    <nav>
            <v-app-bar flat app color="white">
                <v-app-bar flat color = "white" class = "text-uppercase black--text">
                    <span class= "font-weight-light">CVet</span>
                    <span>Minho</span>
                </v-app-bar>
                <v-spacer></v-spacer>
                <v-btn text  @click="logout">
                    <span>Terminar Sessão</span>
                    <v-icon right>fas fa-sign-out-alt</v-icon>
                </v-btn>
            </v-app-bar>
            <v-layout align-center justify-space-between column fill-height>
            <v-navigation-drawer dark app v-model="drawer" class = "grey darken-3">
            <v-list-item two-line>
                <v-list-item-avatar>
                    <v-img src="@/assets/CVetMinho2.png"></v-img>
                </v-list-item-avatar>

                <v-list-item-content>
                <v-list-item-title class="text-uppercase color">Clínica</v-list-item-title>
                </v-list-item-content>
            </v-list-item>

        <v-divider></v-divider>

        <v-list nav> 
            <v-list-item 
                v-for="item in items"
                :key="item.title"
                router :to="item.route"
                link
            >
                <v-list-item-icon>
                    <v-icon >{{ item.icon }}</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                    <v-list-item-title class="caption">{{ item.title }}</v-list-item-title>
                </v-list-item-content>
                <v-card height = "20" flat></v-card>
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
export default {
  name: "Navbar",
  data: () => ({
    drawer: true,
            utilizador: {},
            items: [
            { title: 'Dashboard', icon: 'fas fa-tachometer-alt', route: "/pagina/utilizador/" },
            { title: 'Ficha do utente', icon: 'fas fa-paw', route: "/clinica/pacientes" },
            { title: 'Ficha do cliente', icon: 'fas fa-user', route: "/clinica/clientes" },
            { title: 'Médicos Veterinários', icon: 'fas fa-user-md', route: "/clinica/medicos" },
            { title: 'Marcações de Consultas', icon: 'fas fa-scroll', route: "/clinica/consultas/" },
            { title: 'Marcações de Cirurgias', icon: 'fas fa-procedures', route: "/donativos/"},
            { title: 'Venda de Produtos', icon: 'fas fa-pump-medical', route: "/donativos/"},
            ],
  }),
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

.color{
  color: #2596be;
}
</style>