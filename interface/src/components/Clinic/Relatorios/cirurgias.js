import {Pie} from "vue-chartjs";

export default {
  extends: Pie,
  data() {
    return {
      info: {
        labels: [
          "Agendadas",
          "Concluídas",
          "Canceladas", 
        ],
        datasets: [
          {
            backgroundColor: [
              "#18627c",
              "#2596be",
              "#43b3da",

            ],
            data: [40, 50,10]
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: true,
          labels: {
              fontSize:12,
              usePointStyle: true
          },
          position: "right"
        },
        

      }
    };
  },

  mounted () {
    this.renderChart(this.info, this.options)
  }
}
