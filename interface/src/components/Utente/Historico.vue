<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row class="w-100" align="start">
        <v-col>
          <v-card class="h-100 mt-5" max-width="344" outlined>
            <v-list-item>
              <v-list-item-content>
                <div>
                  <v-row>
                    <v-col>
                      <p class="infos">Tem alergias?</p>
                    </v-col>
                    <v-col>
                      <p class="respos">Sim</p>
                    </v-col>
                  </v-row>
                </div>
                <div>
                  <v-row>
                    <v-col>
                      <p class="infos">Alergias</p>
                    </v-col>
                    <v-col>
                      <p class="respos">Ácaros, Pólen</p>
                    </v-col>
                  </v-row>
                </div>
                <div>
                  <v-row>
                    <v-col>
                      <p class="infos">Transfusões</p>
                    </v-col>
                    <v-col>
                      <p class="respos">Não</p>
                    </v-col>
                  </v-row>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
        <v-col>
          <v-card class="h-100 mt-5" max-width="344" outlined>
            <v-list-item>
              <v-list-item-content>
                <div>
                  <p class="font-weight-bold">Medicação habitual</p>
                </div>
                <div>
                  <p class="infos">Sem medicação registada.</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>

      <v-row class="w-100" align="start">
        <v-col>
          <v-card class="h-100 mt-5" max-width="750" outlined>
              <v-list-item>
              <v-list-item-content>
                <div>
                  <p class="font-weight-bold">História médica prévia</p>
                </div>
                <div>
                  <p class="infos">Sem história médica prévia.</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>

      <v-row class="w-100" align="start">
        <v-col>
          <v-card class="h-100 mt-5" max-width="750" outlined>
              <v-list-item>
              <v-list-item-content>
                <div>
                  <p class="font-weight-bold">Cirurgias prévias</p>
                </div>
                <div>
                  <p class="infos">Sem registo de cirurgias prévias.</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>

      <v-row class="w-100" align="start">
        <v-col>
          <v-card class="h-100 mt-5" max-width="750" outlined>
              <v-list-item>
              <v-list-item-content>
                <div>
                  <p class="font-weight-bold">História médica prévia</p>
                </div>
                <div>
                  <p class="infos">Sem história médica prévia.</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>
    </v-card>

    <v-row class="w-100" align="start">
        <v-col>
          <v-card class="h-100 mt-5" max-width="344" outlined>
            <v-list-item>
              <v-list-item-content>
                <div>
                  <v-row>
                    <v-col>
                      <p class="infos">Antecedentes familiares</p>
                    </v-col>
                    <v-col>
                      <p class="respos">Sem antecedentes familiares</p>
                    </v-col>
                  </v-row>
                </div>
                
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
        <v-col>
          <v-card class="h-100 mt-5" max-width="344" outlined>
            <v-list-item>
              <v-list-item-content>
                <div>
                  <p class="font-weight-bold">História Ginecológica</p>
                </div>
                <div>
                  <p class="infos">Sem história ginecológica.</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>

  </div>
</template>

<script>
import moment from "moment";
export default {
  props: ["animal"],
  data: () => ({}),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
  },
};
</script>

<style scoped>
.infos {
  text-align: start;
}
.respos {
  text-align: end;
  font-weight: bold;
}

.body-2 {
  font-size: 0.8rem !important;
}

.head {
  font-size: 2.75rem !important;
}

.font-weight-bold {
  font-size: 15px;
}
.font-weight-regular {
  font-size: 14px;
}

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}
</style>