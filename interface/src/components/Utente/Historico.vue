<template>
  <div>
    <v-card flat color="#fafafa">
      <v-row class="w-100 pt-9">
        <v-col>
          <h3 class="font-weight-regular text-uppercase">
            <v-icon small>fas fa-notes-medical</v-icon>
            Histórico Clínico
          </h3>
        </v-col>
        <v-col cols="auto" class="ml-auto pl-0">
          <EditaHistorico @clicked="close()"></EditaHistorico>
        </v-col>
      </v-row>

      <v-row class="w-100" align="stretch">
        <v-col>
          <v-card class="h-100" outlined>
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
                      <p class="infos mb-0">Transfusões</p>
                    </v-col>
                    <v-col>
                      <p class="respos mb-0">Não</p>
                    </v-col>
                  </v-row>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
        <v-col>
          <v-card class="h-100" outlined>
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
        <v-col cols="12">
          <v-card class="h-100" outlined>
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

        <v-col cols="12">
          <v-card class="h-100" outlined>
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

        <v-col cols="12">
          <v-card class="h-100" outlined>
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

        <v-col>
          <v-card class="h-100" outlined>
            <v-list-item>
              <v-list-item-content>
                <div>
                  <p class="font-weight-bold">Antecedentes familiares</p>
                </div>
                <div>
                  <p class="infos">Sem antecedentes familiares</p>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
        <v-col>
          <v-card class="h-100" outlined>
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
    </v-card>
  </div>
</template>

<script>
import moment from "moment";
import EditaHistorico from "@/components/Dialogs/EditaHistorico.vue";
export default {
  props: ["animal"],
  data: () => ({}),
  methods: {
    format(data) {
      return moment(data).locale("pt").format("DD/MM/YYYY");
    },
  },
  components: {
    EditaHistorico,
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

.font-weight-bold.col-sm-12.col-md-auto.col-auto {
  padding-bottom: 0;
}

.row.col.col-6 {
  margin-top: 0;
}
</style>