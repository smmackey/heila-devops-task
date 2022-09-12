class SunnyBoy {

    constructor() {
        this.width = "100%";
        this.height = "370px";
        $("#charts").append('<div class="clearfixdd" style="width: 100%; padding:2%;"><div id="sunnyboyChartRealPower" class="chart" style="height: '+this.height+'; width:'+this.width+';"></div><br/><br/><div id="sunnyboyChartReactivePower" class="chart" style="height: '+this.height+'; width:'+this.width+';"></div></div>');
        this.dataLength = 150; // 2.5 minute
        this.real_power = []; // dataPoints
        this.real_power_setpoints = []; // dataPoints
        this.reactive_power = []; // dataPoints
        this.reactive_power_setpoints = []; // dataPoints

        // charts
        this.real_power_chart = new CanvasJS.Chart("sunnyboyChartRealPower", {
          title :{
            text: "SunnyBoy Inverter: Real Power (kW)"
          },
          legend:{
            fontSize: 24,
          },
          data: [{
            type: "line",
            showInLegend: true,
            legendText: "P measured",
            xValueType: "dateTime",
            dataPoints: this.real_power
          },
          {
            type: "line",
            color: "green",
            xValueType: "dateTime",
            showInLegend: true,
            legendText: "P setpoint",
            dataPoints: this.real_power_setpoints
          }
          ]
        });
        this.reactive_power_chart = new CanvasJS.Chart("sunnyboyChartReactivePower", {
          title :{
            text: "SunnyBoy Inverter: Reactive Power (kVAr)"
          },
          legend:{
            fontSize: 24,
          },
          data: [{
            type: "line",
            color: "blue",
            xValueType: "dateTime",
            showInLegend: true,
            legendText: "Q measured",
            dataPoints: this.reactive_power
          },
          {
            type: "line",
            color: "green",
            xValueType: "dateTime",
            showInLegend: true,
            legendText: "Q setpoint",
            dataPoints: this.reactive_power_setpoints
          }
          ]
        });
    }

    update() {
        var parent = this;
        $.get("/sunnyboy_inverter", function( data ) {
            // Real Power
            parent.real_power.push(data["real_power"]);
            parent.real_power_setpoints.push(data["real_power_setpoint"]);
            if (parent.real_power.length > parent.dataLength) {
              parent.real_power.shift();
            }
            if (parent.real_power_setpoints.length > parent.dataLength) {
              parent.real_power_setpoints.shift();
            }
            parent.real_power_chart.render();
            // Reactive Power
            parent.reactive_power.push(data["reactive_power"]);
            parent.reactive_power_setpoints.push(data["reactive_power_setpoint"]);
            if (parent.reactive_power.length > parent.dataLength) {
              parent.reactive_power.shift();
            }
            if (parent.reactive_power_setpoints.length > parent.dataLength) {
              parent.reactive_power_setpoints.shift();
            }
            parent.reactive_power_chart.render();
        });

    }

}