class Aggregate {

    constructor() {
        this.width = "50%";
        this.height = "370px";
        $("#lastrow").append('<div id="aggregateChartRealPower" class="chart float-left" style="height: '+this.height+'; width:'+this.width+';"></div>');
        this.dataLength = 150; // 1 minute
        this.real_power = []; // dataPoints
        this.real_power_setpoints = []; // dataPoints
        this.reactive_power = []; // dataPoints
        this.reactive_power_setpoints = []; // dataPoints

        // charts
        this.real_power_chart = new CanvasJS.Chart("aggregateChartRealPower", {
          title :{
            text: "Total Active(kW) & Reactive (kVAr) Power"
          },
          legend:{
            fontSize: 24,
          },
          data: [{
            type: "line",
            color: "blue",
            showInLegend: true,
            legendText: "P measured",
            xValueType: "dateTime",
            dataPoints: this.real_power
          },
          {
            type: "line",
            color: "blue",
            xValueType: "dateTime",
            showInLegend: true,
            legendText: "P setpoint",
            lineDashType: "dash",
            dataPoints: this.real_power_setpoints
          },
          {
            type: "line",
            showInLegend: true,
            color: "green",
            legendText: "Q measured",
            xValueType: "dateTime",
            dataPoints: this.reactive_power
          },
          {
            type: "line",
            color: "green",
            xValueType: "dateTime",
            showInLegend: true,
            legendText: "Q setpoint",
            lineDashType: "dash",
            dataPoints: this.reactive_power_setpoints
          }
          ]
        });
    }

    update() {
        var parent = this;
        $.get("/aggregate", function( data ) {
            // Real Power
            parent.real_power.push(data["real_power"]);
            parent.real_power_setpoints.push(data["real_power_setpoint"]);
            if (parent.real_power.length > parent.dataLength) {
              parent.real_power.shift();
            }
            if (parent.real_power_setpoints.length > parent.dataLength) {
              parent.real_power_setpoints.shift();
            }
            // Reactive Power
            parent.reactive_power.push(data["reactive_power"]);
            parent.reactive_power_setpoints.push(data["reactive_power_setpoint"]);
            if (parent.reactive_power.length > parent.dataLength) {
              parent.reactive_power.shift();
            }
            if (parent.reactive_power_setpoints.length > parent.dataLength) {
              parent.reactive_power_setpoints.shift();
            }

            parent.real_power_chart.render();
        });

    }

}