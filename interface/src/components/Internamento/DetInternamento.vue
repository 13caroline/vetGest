<template>
  <div>
    <v-container class="fill-height">
      <v-row class="w-100">
        <v-card flat height="100" width="190" outlined class="ma-4">
          <v-row>
            <v-col>
              <v-img
                src="@/assets/animais/Rubi.jpg"
                aspect-ratio="1"
                class="grey lighten-2 ma-2 rounded"
                cover
                min-height="80"
                min-width="80"
              >
                <template v-slot:placeholder>
                  <v-row
                    class="fill-height ma-0"
                    align="center"
                    justify="center"
                  >
                    <v-progress-circular
                      indeterminate
                      color="grey lighten-5"
                    ></v-progress-circular>
                  </v-row>
                </template>
              </v-img>
            </v-col>
            <v-col class="details my-auto">
              <div class="text-uppercase font-weight-bold">Rubi</div>
              <span class="text-uppercase body-2 text--secondary" >8 ANOS</span>
            </v-col>
          </v-row>
        </v-card>
      </v-row>

      <v-row class="w-100">
        <v-col>
          <v-card class="h-100">
            <v-card-text class="py-0">
              <v-timeline align-top dense>
                <v-timeline-item color="pink" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>5pm</strong>
                    </v-col>
                    <v-col>
                      <strong>New Icon</strong>
                      <div class="text-caption">Mobile App</div>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="pink" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>12pm</strong>
                    </v-col>
                    <v-col>
                      <strong>Lunch break</strong>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="teal lighten-3" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>21/05/2021 9-11am</strong>
                    </v-col>
                    <v-col>
                      <strong>Finish Home Screen</strong>
                      <div class="text-caption">Web App</div>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="pink" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>12pm</strong>
                    </v-col>
                    <v-col>
                      <strong>Lunch break</strong>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="teal lighten-3" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>21/05/2021 9-11am</strong>
                    </v-col>
                    <v-col>
                      <strong>Finish Home Screen</strong>
                      <div class="text-caption">Web App</div>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="pink" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>12pm</strong>
                    </v-col>
                    <v-col>
                      <strong>Lunch break</strong>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="teal lighten-3" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>21/05/2021 9-11am</strong>
                    </v-col>
                    <v-col>
                      <strong>Finish Home Screen</strong>
                      <div class="text-caption">Web App</div>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="pink" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>12pm</strong>
                    </v-col>
                    <v-col>
                      <strong>Lunch break</strong>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="pink" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>12pm</strong>
                    </v-col>
                    <v-col>
                      <strong>Lunch break</strong>
                    </v-col>
                  </v-row>
                </v-timeline-item>

                <v-timeline-item color="teal lighten-3" small>
                  <v-row class="pt-1">
                    <v-col cols="3">
                      <strong>21/05/2021 9-11am</strong>
                    </v-col>
                    <v-col>
                      <strong>Finish Home Screen</strong>
                      <div class="text-caption">Web App</div>
                    </v-col>
                  </v-row>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col>
          <v-card class="h-100">
            <v-card-text class="pb-0">
              <v-textarea color="#2596be" outlined rows="20" flat no-resize> </v-textarea>
            </v-card-text>
            <v-card-actions class="pt-0"
              ><v-btn
                depressed
                dark
                color="#2596be"
                to="/medico/consultas"
                class="ml-auto mr-2"
              >
                Gravar
              </v-btn></v-card-actions
            >
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style scoped>
  .details{
    text-align: left;
  }
</style>