var statenames = new Array();
var countynames = new Array();
function init() {
 changeState();
}

function callJSON(url,callback) {
   var script = document.createElement('script');
   var head = document.getElementsByTagName('head')[0];
   script.src = url + "&jsonp="  + callback;
   script.id = callback;
   head.appendChild(script);
}

function changeState() {
  var statemenu = document.getElementById("state");
  var stateopts = statemenu.options;
  var stateselected = statemenu.selectedIndex;
  var statenum = parseInt(stateopts[stateselected].value);
  var copts = document.getElementById("county");
  county.innerHTML = counties[statenum];

}

function getAlerts() {
  document.getElementById("alerts").innerHTML = "<h3>Current Alerts</h3><p>Show alerts....</p>";
  var statemenu= document.getElementById("state");
  var countymenu = document.getElementById("county");
  var statenum = statemenu.options[statemenu.selectedIndex].value;
  var countynum = countymenu.options[countymenu.selectedIndex].value;
  var currenttime = 0;
//  Uncomment the following line to only show alerts that are currently active. 
//  currenttime = (new Date()).getMilliseconds();

  var url = "http://localhost:8080/api/1.0/" +
            "?Procedure=GetAlertsByLocation&Parameters=" +
            "[" + statenum + "," + countynum + "," + currenttime + "]";
  callJSON(url,"loadAlertsCallback");
}
function loadAlertsCallback(data) {

    if (data.status == 1) {
      var output = "";
      var results = data.results[0].data;
      if (results.length > 0) {
          var datarow = null;
          for (var i = 0; i < results.length; i++) {
              datarow = results[i];
              var link = datarow[0];
              var descr = datarow[1];
              var type = datarow[2];
              var severity = datarow[3];
              var starttime = datarow[4]/1000;
              var endtime = datarow[5]/1000;
              output += '<p><a href="' + link + '">' + type + '</a> ' 
                         + severity + '<br/>' + descr + '</p>';
          } 
       }  else {
          output = "<p>No current weather alerts for this location.</p>";
       }
       var panel = document.getElementById('alerts');
       panel.innerHTML = "<h3>Current Alerts</h3>" + output;
       
       
    } else {
       alert("Failure: " + data.statusstring);
    }
}

     
