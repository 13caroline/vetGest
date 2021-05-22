<template>
  <div class="mt-auto">
    <v-footer class="pt-5" dark v-bind:class="{ 'mt-10': marginTop }">
      <v-col class="text-center" cols="12">
        <p class="caption font-weight-light">
          Copyright &copy; {{ new Date().getFullYear() }} - Todos os direitos
          reservados Clínica Veterinária do Minho
        </p>
      </v-col>
    </v-footer>
  </div>
</template>

<script>
export default {
  props: {
    marginTop: {
      type: Boolean,
      default: true,
    },
  },
};
</script>
