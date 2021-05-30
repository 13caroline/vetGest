import { Bar } from "vue-chartjs";

export default {
    extends: Bar,
    data() {
        return {
            info: {
                labels: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
                datasets: [
                    {
                        label: "2020",
                        borderColor: "#EF9A9A",
                        data: [10,20,25],
                        fill: false,
                        borderWidth: 5
                    },
                    {
                        label: "2019",
                        borderColor: "#C5E1A5",
                        data: [40,89,70],
                        fill: false,
                        borderWidth: 5
                    },
                    {
                        label: "2018",
                        borderColor: "#B3E5FC",
                        data: [5,16,178],
                        fill: false,
                        borderWidth: 5
                    },

                ]
            },
            options: {
                scales: {
                    x: {
                        stacked: true
                    },
                    y: {
                        stacked: true
                    }
                }
            }
        }
    },
    mounted () {
        this.renderChart(this.info, this.options)
      }
}