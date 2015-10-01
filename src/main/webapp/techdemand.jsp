<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://www.google.com/jsapi"></script>
<script>
google.load('visualization', '1', {packages:['corechart']});
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
          title: 'Global Skill Demand'
        };
        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(data, {title: 'Global Skill Demand', width: 450, height: 500});
	});
}
</script>
</head>

  <body style="font-family: Arial;border: 0 none;">
      <div id="chart_div"></div>
  </body>

</html>