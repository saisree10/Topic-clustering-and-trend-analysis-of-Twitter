
<html>
  <head>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      //google.load("visualization", "1", {packages:["corechart"]});
      //google.setOnLoadCallback(drawChart);
      $(document).ready(function() {
          $.post( "GetClusterJSON.php", { action: "init", cluster: $( this ).text() },function(data){
           $("#selectCluster").add(data);
           alert(data);
          }
      });
		$( "select" ).change(function () {
    		var str;
    		$( "select option:selected" ).each(function() {

      				$.post( "GetClusterJSON.php", { action: "getData", cluster: $( this ).text() },function(data){
      				
      				        var data = google.visualization.arrayToDataTable(data);
                            var options = { title: 'Company Performance' };

                            var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                            chart.draw(data, options);
      				
      				} );
    		});
  		});

    </script>
  </head>
  <body>
    <div id="selectCluster"> </div>

    <div id="chart_div" style="width: 900px; height: 500px;"></div>
  </body>
</html>