window.onload = function () {

    var sunnyboy = new SunnyBoy();

    var updateInterval = 1000;
    var dataLength = 60; // number of dataPoints visible at any point

    var updateChart = function (count) {
        sunnyboy.update();
    };

    updateChart(dataLength);
    setInterval(function () {
        updateChart()
    }, updateInterval);

}