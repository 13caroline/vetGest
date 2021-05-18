<template>
	<div>
		<div class="row pt-3 px-3">
			<v-col cols="auto">
				<v-img
					src="@/assets/CVetMinho2.png"
					max-height="50"
					max-width="50"
					@click="home()"
				></v-img>
			</v-col>

			<v-col cols="auto" class="show_large mx-auto">
				<v-btn
					tile
					depressed
					text
					class="font-weight-regular no-background-hover"
					to="/cliente/consultas"
				>
					Consultas
				</v-btn>

				<v-btn
					tile
					depressed
					text
					class="font-weight-regular no-background-hover"
					to="/cliente/cirurgias"
				>
					Cirurgias
				</v-btn>

				<v-btn
					tile
					depressed
					text
					class="font-weight-regular no-background-hover"
					to="/books"
				>
					Clínica
				</v-btn>

				<v-btn
					tile
					depressed
					text
					class="font-weight-regular no-background-hover"
					to="/my/lists/books"
				>
					Conselhos Úteis
				</v-btn>

				<v-menu offset-y tile open-on-click>
					<template v-slot:activator="{ on, attrs }">
						<v-btn
							tile
							depressed
							text
							v-bind="attrs"
							v-on="on"
							class="font-weight-regular no-background-hover"
						>
							Perfil
						</v-btn>
					</template>

					<v-list dense color="#FFFFFF">
						<v-list-item @click="preferences()">
							<v-list-item-title class="caption">
								<v-icon x-small class="mt-n1 mr-1">fas fa-user-cog</v-icon>
								Gerir Perfil
							</v-list-item-title>
						</v-list-item>
						<v-list-item @click="logout()">
							<v-list-item-title class="caption">
								<v-icon x-small class="mt-n1 mr-1">fas fa-sign-out-alt</v-icon>
								Terminar Sessão
							</v-list-item-title>
						</v-list-item>
					</v-list>
				</v-menu>
			</v-col>

			<v-col cols="auto" class="hide_large ml-auto">
				<v-menu offset-y tile open-on-click>
					<template v-slot:activator="{ on, attrs }">
						<v-btn icon v-bind="attrs" v-on="on" class="no-background-hover">
							<v-icon>fas fa-bars</v-icon>
						</v-btn>
					</template>

					<v-list dense color="#FFFFFF">
						<v-list-item to="/cliente/consultas">
							<v-list-item-title class="caption">Consultas</v-list-item-title>
						</v-list-item>
						<v-list-item to="/cliente/cirurgias">
							<v-list-item-title class="caption">Cirurgias</v-list-item-title>
						</v-list-item>
						<v-list-item to="/books">
							<v-list-item-title class="caption">Clínica</v-list-item-title>
						</v-list-item>
						<v-list-item to="/my/lists/books">
							<v-list-item-title class="caption">Conselhos Úteis</v-list-item-title>
						</v-list-item>
						<v-list-item @click="preferences()">
							<v-list-item-title class="caption">
								<v-icon x-small class="mt-n1 mr-1">fas fa-user-cog</v-icon>
								Gerir Perfil
							</v-list-item-title>
						</v-list-item>
						<v-list-item @click="logout()">
							<v-list-item-title class="caption">
								<v-icon x-small class="mt-n1 mr-1">fas fa-sign-out-alt</v-icon>
								Terminar Sessão
							</v-list-item-title>
						</v-list-item>
					</v-list>
				</v-menu>
			</v-col>
		</div>
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
		home: function () {
			this.$router.push("/cliente/home");
		},
		preferences: function () {
			this.$router.push("/preferences");
		},
	},
};
</script>

<style>
.no-background-hover::before {
	background-color: transparent !important;
}

.v-image {
	cursor: pointer;
}

.show_large {
	display: none;
}

@media screen and (min-width: 705px) {
	.show_large {
		display: flex;
	}

  .hide_large {
    display: none;
  }
}
</style>