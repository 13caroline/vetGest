import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  data() {
    return {
      type: 'bar',
      data: {
        labels: ["January", "February", "March", "April", "May", "June", "July"],
        datasets: [{
          type: 'bar',
          label: 'Dataset 1',
          backgroundColor: "red",
          data: [65, 0, 80, 81, 56, 85, 40],
        }, {
          type: 'bar',
          label: 'Dataset 3',
          backgroundColor: "blue",
          data: [125, 2, 20, 51, 126, 95, 10]
        }]
      },
      options: {
        scales: {
          xAxes: [{
            stacked: true
          }],
          yAxes: [{
            stacked: true
          }]
        }
      }
    }
  },
  async mounted() {

    this.renderChart(this.data, this.options);


  }
};