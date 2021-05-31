import { Bar } from "vue-chartjs";


export default {
    extends: Bar,
    data() {
        return {
          type: 'bar',
          
          data: {
            labels: ["Canídeo", "Felídeo", "Equino", "Pecuário", "Selvagem", "Outro"],
            datasets: [
              {
                data: [250,220,100,140,6,80],
                borderColor: "#2596be",
                backgroundColor: "#2596be",
              },
            ]
          },
          options: {
            legend: {
              display: false
           },
            responsive: true,
            plugins: {
              title: {
                display: false,
              }
            }
          },
        };
      },
            async mounted() {

                this.renderChart(this.data, this.options);


            }
        }
