<template>
  <div>
    <v-container class="fill-height">
      <v-row class="w-100">
        <v-card width="100%">
          <v-row>
            <v-col>
              <v-card flat height="50" width="100">
                <v-row>
                  <v-col>
                    <!--<v-img
                      src="@/assets/animais/Rubi.jpg"
                      aspect-ratio="1"
                      class="grey lighten-2 mx-2"
                      cover
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
                    </v-img>-->
                  </v-col>
                  <v-col>
                    <div>RUBI</div>
                    <div>8 ANOS</div>
                  </v-col>
                </v-row>
              </v-card>
            </v-col>
          </v-row>
        </v-card>
      </v-row>

      <v-row class="w-100">
        <v-col>
          <v-card>
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
          <v-card>
            <v-card-text>
              <div>
                <p class="ma-0">Motivo de internamento</p>
                <v-textarea outlined rows="1" flat no-resize> </v-textarea>
              </div>

              <div>
                <p class="ma-0">História médica prévia</p>
                <v-textarea outlined rows="3" flat no-resize> </v-textarea>
              </div>

              <div>
                <p class="ma-0">Evolução no internamento</p>
                <v-textarea outlined rows="3" flat no-resize> </v-textarea>
              </div>

              <div>
                <p class="ma-0">Proposta terapêutica</p>
                <v-textarea outlined rows="3" flat no-resize> </v-textarea>
              </div>

              <div>
                <p class="ma-0">Orientação</p>
                <v-textarea outlined rows="1" flat no-resize> </v-textarea>
              </div>
            </v-card-text>
            <v-card-actions
              ><v-btn
                depressed
                dark
                color="#2596be"
                outlined
                to="/medico/consultas"
              >
                Gravar
              </v-btn>
              <v-btn
                depressed
                dark
                color="#2596be"
                @click="cancelDialog = true"
              >
                Confirmar alta
              </v-btn>
              
              </v-card-actions
            >
          </v-card>
        </v-col>
      </v-row>

      <v-dialog v-model="cancelDialog" persistent width="100%" max-width="460">
            <v-card>
              <v-card-title class="justify-center cancel">
                Confirmar alta
              </v-card-title>
              <v-card-text>
                Tem a certeza que pretende confirmar a alta de Rubi?
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  depressed
                  large
                  width="50%"
                  dark
                  color="#BDBDBD"
                  @click="cancelDialog = false"
                >
                  Não
                </v-btn>
                <v-btn
                @click="cancela()"
                  depressed
                  large
                  dark
                  color="#2596be"
                  width="50%"
                >
                  Sim
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
      cancelDialog: false,
  })
}

</script>