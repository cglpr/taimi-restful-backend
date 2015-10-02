<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://www.google.com/jsapi"></script>
<script>
google.load('visualization', '1', {'packages':['corechart','columnchart','piechart','gauge']});
google.setOnLoadCallback(drawChart);
      
function fetchJSONFile(path, callback) {
    var httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState === 4) {
            if (httpRequest.status === 200) {
                if (callback) callback(httpRequest.responseText);
            }
        }
    };
    httpRequest.open('GET', path);
    httpRequest.send(); 
}

function drawChart() {  // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
	fetchJSONFile('/TaimiBackend/rest/techdata/demand', function(responseText){
        var data = new google.visualization.DataTable(responseText);

        var options = {
          title: 'Global Skill Demand',
          width: 450, 
          height: 500,
          is3D: true
        };
        
        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        if(document.getElementById('pieChart').checked) {
            chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        } else if(document.getElementById('gaugeChart').checked) {
            options = {
                <!-- TODO: Set values according to _total_ demand! -->
                min: 0, max: 150, yellowFrom: 20, yellowTo: 50,
                redFrom: 50, redTo: 130, minorTicks: 5,
                width: 450, height: 500
            };
            chart = new google.visualization.Gauge(document.getElementById('chart_div'));
        } 
        
        chart.draw(data, options);
	});
}
</script>
</head>

  <body style="font-family: Arial;border: 0 none;">
      <div id="chart_div"></div>
      <input type="radio" name="chartType" id="barChart" value="BarChart" checked="checked" onclick="drawChart()">BarChart</input>
      <input type="radio" name="chartType" id="pieChart" value="PieChart" onclick="drawChart()">PieChart</input>
      <input type="radio" name="chartType" id="gaugeChart" value="Gauge" onclick="drawChart()">Gauge</input>
  </body>

</html>