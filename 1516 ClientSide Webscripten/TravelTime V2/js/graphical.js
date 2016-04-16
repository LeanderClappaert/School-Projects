/**
 * Clientside webscripten project - GRAPHICAL menu item
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 05/01/2016
 */
var height = 3.25;

/**
 * Load the graph onto the canvas
 * @return void
 */
var loadGraph = function() {
	// get the context of the canvas element we want to select
	var ctx = document.getElementById("myChart").getContext("2d");
	// load current memory from local storage
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	if (neededArray === null) return; // there is nothing to show
	var timeObject = neededArray[0].timeObject;
	// variables
	var labsGoogle = [];
	var srsGoogle = [];
	var srsUser = [];

	for (var i = 1; i < timeObject.length; i++) { //i = 0 has 0 as time values
		labsGoogle.push('trip' + i);
		srsGoogle.push(Math.round(timeObject[i].time_map / 60)); // /60 because minutes are easier to read
		srsUser.push(Math.round(timeObject[i].time_user / 60)); // /60 because minutes are easier to read
	}
	
	var data = {
		// a labels array that can contain any sort of values
		labels: labsGoogle,
		// our series array that contains series objects or in this case series data arrays
		datasets: [
			{
	            label: "Google's estimated time",
	            strokeColor: "rgba(83,187,232,1)",
	            pointColor: "rgba(151,187,205,1)",
	            pointStrokeColor: "#fff",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(220,220,220,1)",
				data: srsGoogle
			},
			{
	            label: "Your time",
	            strokeColor: "rgba(75,255,174,1)",
	            pointColor: "rgba(52,178,121,1)",
	            pointStrokeColor: "#fff",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(151,187,205,1)",
				data: srsUser
			}
		]
	};

	var options = {
		datasetFill : false,
		responsive : true
	}

	new Chart(ctx).Line(data, options);
}

/**
 * Load the used routes below the graph so that x-axis makes sence.
 * @return void
 */
var loadLegend = function() {
	// remove previous legend
	document.getElementById('chartLegend').innerHTML = '';
	// load current memory from local storage
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	if (neededArray === null) return; // there is nothing to show
	var userObject = neededArray[0].userObject[0];

	// add info to legend
	var l = document.createElement('li');
	var s = document.createElement('span');

	l.setAttribute('id', 'item-' + 1);
	l.setAttribute('class', 'item clearfix');
	s.setAttribute('id', 's-' + 1);
	s.innerHTML = 'Route: ' + userObject.origin_input + " to " + userObject.destination_input + " by " + userObject.mode;

	l.appendChild(s);
	document.getElementById('chartLegend').appendChild(l);
	document.getElementById('chartLegend').style.height = 3.25 + 'rem';
}

/**
 * Save the graph as a png file and show the file on screen (below main container which contains map and functionalities)
 * @return void
 */
document.getElementById('graphSaveButton').onclick = function() {
	var img = document.getElementById('myChart').toDataURL("image/png");
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	var userObject = neededArray[0].userObject[0];

    $.ajax({ 
        type: 'POST', 
        url: 'save.php',
        dataType: 'text',
        data: {
            base64data : img
        }
    }).done(function(resp) {
    	var newImg = document.createElement('IMG');
    	newImg.src = 'img/' + resp;
    	document.getElementById('charResult').appendChild(newImg);
    	console.log('img has been saved');
    }).fail(function(resp) {
    	console.log('error');
    });
}
/* EOF */