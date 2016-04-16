/**
 * Project clientside webscripten - SAVED ROUTES menu item
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 03/01/2016
 */

// variables
var y = 4.3;
var listNumber = 0;
var convertedId;

/**
 * Load all objects from local storage and show them as a list
 * @return {[type]} [description]
 */
function loadLocalStorage() {
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	y = 4.3; // reset y value for background

	for (var i = 0; i < neededArray.length; i++) {
		var input = neededArray[i];

		var l = document.createElement('li');
		var a = document.createElement('a');
		var s = document.createElement('span');
		var linkText = document.createTextNode("my title text"); 

		l.setAttribute('id', 'item-' + i);
		l.setAttribute('class', 'item lastChildSet clearfix');

		a.appendChild(linkText);
		a.setAttribute('id', 'a-' + i);
		a.setAttribute('onClick', 'edit(this.id)');
		a.setAttribute('class', 'edit');
		a.innerHTML = 'edit';

		s.setAttribute('id', 's-' + i);
		s.setAttribute('onClick', 'loadSavedRoute(this.id)');
		s.innerHTML = input[0].origin_input + " to " + input[0].destination_input + " by " + input[0].mode;

		l.appendChild(a);
		l.appendChild(s);

		document.getElementById('addItems').appendChild(l);
		document.getElementById('myDropdown2').style.height = 4 + y + 'rem';
		y += 4.3;
		listNumber++;
	}
}

/**
 * Add a new item to the current list
 * @return void
 */
function addListItem() {
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	var lastUserInput = neededArray[neededArray.length - 1]; //get last item
	var l = document.createElement('li');
	var a = document.createElement('a');
	var s = document.createElement('span');
	var linkText = document.createTextNode("my title text"); 

	l.setAttribute('id', 'item-' + listNumber);
	l.setAttribute('class', 'item lastChildSet clearfix');
	a.appendChild(linkText);
	a.setAttribute('id', 'a-' + listNumber);
	a.setAttribute('onClick', 'edit(this.id)');
	a.setAttribute('class', 'edit');
	a.innerHTML = 'edit';

	s.setAttribute('id', 's-' + listNumber);
	s.setAttribute('onClick', 'loadSavedRoute(this.id)');
	s.innerHTML = lastUserInput[0].origin_input + " to " + lastUserInput[0].destination_input + " by " + lastUserInput[0].mode;

	l.appendChild(a);
	l.appendChild(s);

	document.getElementById('addItems').appendChild(l);
	document.getElementById('myDropdown2').style.height = 4 + y + 'rem';
	
	y += 4.3;
	listNumber++;
}

/**
 * Edit a list item
 * @param id the id of the list item which will be edited
 * @return void
 */
function edit(id) {
	// make eventlisteners listen to form input boxes and radio buttons
	initMap(1);
	// get id
	convertedId = id.charAt(id.length - 1);
	// create shove effect when clicking on other edit picture
	document.getElementById('formEdit').style.display = 'none';
	setTimeout(function() {
		// reset edit layout
		document.getElementById('origin-input-edit').value = '';
		document.getElementById('destination-input-edit').value = '';
		document.getElementById('origin-input-edit').placeholder = 'Enter an origin location';
		document.getElementById('destination-input-edit').placeholder = 'Enter a destination location';
		// show form at right position
		document.getElementById('formEdit').style.top = (y - 0.3) + 'rem'; 
		document.getElementById('formEdit').style.display = 'block';
	}, 100);
}

/**
 * Load the route when clicking on (the span of) the list item
 * @param id the id of the list item which will be loaded onto the map
 * @return void
 */
function loadSavedRoute(id) {
	// load needed object depending on the id
	var convertedId = id.charAt(id.length - 1);
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	var getEditInfo = neededArray[parseInt(convertedId)];
	// get the data from that object
	var origin_place_id = getEditInfo[0].origin_place_id;
	var destination_place_id = getEditInfo[0].destination_place_id;
	var travel_mode = getEditInfo[0].travel_mode;

	// check if data can be used
    if (!origin_place_id || !destination_place_id) return;
    directionsService.route({
     	origin: {'placeId': origin_place_id},
    	destination: {'placeId': destination_place_id},
    	travelMode: travel_mode
    }, 	
    function(response, status) {
		if (status === google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
			setTrafficLayer(map, true); // Show traffic on the map
			var time = response.routes[0].legs[0].duration.text;
			routeDuration(time);
		}
		else window.alert('Directions request failed due to ' + status);
	});
    // close down dropdown menu
	clearShow();
}

/**
 * Pops up a dialog box when user click on the change button
 * @return void
 */
document.getElementById('editChangeButton').onclick = function() {
	document.getElementById('dialogBox').style.display = 'block';
	document.getElementById('dialogBoxBackground').style.display = 'block';
}

/**
 * When user accepts their change, current object will be replaced by the new one
 * All saved times will be erased
 * @return void
 */
document.getElementById('acceptButton').onclick = function() {
	document.getElementById('dialogBox').style.display = 'none';
	document.getElementById('dialogBoxBackground').style.display = 'none';
	document.getElementById('formEdit').style.display = 'none';
	clearShow(); // close down dropdowns
	showOnMap(1); // token = 1, which means only storage method will be used

	// give the showOnMap(1) method time to store new entry into localStorage
	setTimeout(function() {
		var neededArray = JSON.parse(localStorage.getItem('allObjects'));
		var newArray = new Array();
		var iterator = 0;

		// remove old entry from localStorage + remove user time from all entries
		for (var i = 0; i < neededArray.length; i++) {
			if (i === parseInt(convertedId)) continue;
			newArray[iterator] = neededArray[i];
			console.log(newArray[iterator]);
			newArray[iterator][0].time_user = 0;
			iterator++;
		}

		// store definite array one last time
		localStorage.setItem('allObjects', JSON.stringify(newArray));
	}, 500);
}

/**
 * User don't want to change a thing, so every change iwll be ignored
 * @return void
 */
document.getElementById('declineButton').onclick = function() {
	document.getElementById('dialogBox').style.display = 'none';
	document.getElementById('dialogBoxBackground').style.display = 'none';
	document.getElementById('formEdit').style.display = 'none';
	clearShow(); // close down dropdowns
}

/**
 * Save user time data into the object
 * @return void
 */
document.getElementById('editSaveButton').onclick = function() {
	var userTime = document.getElementById('user-input-edit').value;

	// basic checking for right time input
	if (userTime.length !== 8 || userTime.charAt(2) !== ':' || userTime.charAt(5) !== ':') {
		window.alert("Please use the 00:00:00 format for your time input!");
		return;
	}
	else {
		// convert input into seconds
		var hour = parseInt(userTime.slice(0, 2));
		var minute = parseInt(userTime.slice(3, 5));
		var second = parseInt(userTime.slice(6, userTime.length));
		var time = second + minute * 60 + hour * 3600;
		//store user time in right array
		var neededArray = JSON.parse(localStorage.getItem('allObjects'));
		neededArray[convertedId][0].time_user = time;
		localStorage.setItem('allObjects', JSON.stringify(neededArray));
		clearShow(); // close down dropdowns
	}
}
/* EOF */