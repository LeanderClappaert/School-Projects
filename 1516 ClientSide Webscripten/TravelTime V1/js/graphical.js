/**
 * Clientside webscripten project - GRAPHICAL menu item
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 03/12/2015
 */
var height = 3.25;

/**
 * Load the graph
 * @return void
 */
var loadGraph = function() {
	// load current memory from local storage
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	// variables
	var labsGoogle = [];
	var srsGoogle = [];
	var srsUser = [];
	var iterator = 1; // custome iterator

	for (var i = 0; i < neededArray.length; i++) {
		if (neededArray[i][0].time_user === 0) continue; // filter all empty ones
		else {
			labsGoogle.push('route' + iterator);
			srsGoogle.push(neededArray[i][0].time_map);
			srsUser.push(neededArray[i][0].time_user);
			iterator++;
		}
	}

	// don't show map when there's no good data to feed the graph with
	if (iterator === 1) return;
	
	var data = {
		// a labels array that can contain any sort of values
		labels: labsGoogle,
		// our series array that contains series objects or in this case series data arrays
		series: [
			srsGoogle,
			srsUser
		]
	};

	// create a new line chart object where as first parameter we pass in a selector that is resolving to our chart container element. 
	// the Second parameter is the actual data object.
	new Chartist.Line('.ct-chart', data);
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
	// variables
	var iterator = 1; // custome iterator
	height = 3.25; // reset height

	// add each line of the legend
	for (var i = 0; i < neededArray.length; i++) {
		if (neededArray[i][0].time_user === 0) continue;
		else {
			console.log('succeeded, ' + neededArray[i][0].time_user);
			var l = document.createElement('li');
			var s = document.createElement('span');

			l.setAttribute('id', 'item-' + i);
			l.setAttribute('class', 'item clearfix');
			s.setAttribute('id', 's-' + i);
			s.innerHTML = 'route ' + iterator + ': ' + neededArray[i][0].origin_input + " to " + neededArray[i][0].destination_input + " by " + neededArray[i][0].mode;

			l.appendChild(s);
			document.getElementById('chartLegend').appendChild(l);
			document.getElementById('chartLegend').style.height = height + 'rem';
			height += 3.25;
			iterator++;
		}
	}
}

/**
 * Save the graph as a svg file and show the file on screen (below graph)
 * @return void
 */
document.getElementById('graphSaveButton').onclick = function() {
	var pablo_svg = Pablo(document.getElementById('chart').getElementsByTagName('svg')[0]);
    console.log('save');
    console.log(pablo_svg);
    console.log(pablo_svg[0]);
    var data = pablo_svg.dataUrl();
    console.log(data);
    $.ajax({ 
        type: 'POST', 
        url: 'save.php',
        dataType: 'text',
        data: {
            base64data : data
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