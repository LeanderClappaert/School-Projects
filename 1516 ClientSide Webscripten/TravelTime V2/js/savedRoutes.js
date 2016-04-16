/**
 * Project clientside webscripten - SAVED ROUTES menu item
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 05/01/2016
 */

// variables
var listNumber = 0;
var numSaves = 0;

/**
 * Load all objects from local storage and show them as a list
 * @return void
 */
function loadLocalStorageEdit() {
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	if (neededArray === null) return; // there is nothing to show
	var userObject = neededArray[0].userObject;
	var lastUserInput = userObject[0]; //get last item

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
	s.innerHTML = lastUserInput.origin_input + " to " + lastUserInput.destination_input + " by " + lastUserInput.mode;

	l.appendChild(a);
	l.appendChild(s);

	document.getElementById('addItems').appendChild(l);
	document.getElementById('myDropdown2').style.height = 4 + 4.3 + 'rem';

	listNumber++;
}

/**
 * Edit a list item
 * @param id the id of the list item which will be edited
 * @return void
 */
function edit(id) {
	// make sure user input is always empty	
	document.getElementById('user-input-edit').value = '';
	// make eventlisteners listen to form input boxes and radio buttons
	initMap(1);
	// get id
	convertedId = id.charAt(id.length - 1);
	// persistence: get values
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	var getEditInfo = neededArray[0].userObject[0];

	document.getElementById('formEdit').style.display = 'none';
	loadLocalStoragePersistence('origin-input-edit', 'destination-input-edit', 'changemode-walking-edit', 'changemode-transit-edit', 'changemode-driving-edit');
	// create shove effect when clicking on other edit picture
	setTimeout(function() {
		// show form at right position
		document.getElementById('formEdit').style.top = 4 + 4.3 + 'rem'; 
		document.getElementById('formEdit').style.display = 'block';
	}, 100);
}

/**
 * Load the route when clicking on (the span of) the list item
 * @param id the id of the list item which will be loaded onto the map
 * @return void
 */
function loadSavedRoute() {
	// load needed object depending on the id
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	var getEditInfo = neededArray[0].userObject[0];

	// get the data from that object
	var origin_place_id = getEditInfo.origin_place_id;
	var destination_place_id = getEditInfo.destination_place_id;
	var travel_mode = getEditInfo.travel_mode;

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
	var mode;
	if (document.getElementById('changemode-walking-edit').checked) mode = 'WALKING';
	else if (document.getElementById('changemode-transit-edit').checked) mode = 'TRANSIT';
	else mode = 'DRIVING';
	showOnMap(1, 'origin-input-edit', 'destination-input-edit', mode);
	// close down warning box, but showOnMap will decide if all dropdowns need to close down
	document.getElementById('dialogBox').style.display = 'none';
	document.getElementById('dialogBoxBackground').style.display = 'none';
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
	if (userTime.length !== 8) {
		document.getElementById('user-input-edit').value = 'Use the 00:00:00 format';
		return;
	}

	for (var i = 0; i < userTime.length; i++) {
		if (userTime.charAt(2) !== ':' || userTime.charAt(5) !== ':') {
			document.getElementById('user-input-edit').value = 'Use the 00:00:00 format';
			return;			
		}

		if (i !== 2 && i !== 5) {
			if (isNaN(userTime.charAt(i))) {
				document.getElementById('user-input-edit').value = 'Use the 00:00:00 format';
				return;
			}
		}
	}

	// succeeded the checking = convert input into seconds
	var hour = parseInt(userTime.slice(0, 2));
	var minute = parseInt(userTime.slice(3, 5));
	var second = parseInt(userTime.slice(6, userTime.length));
	var time = second + minute * 60 + hour * 3600;

	getAndStoreBothTimes(time);
	clearShow(); // close down dropdowns
}

/**
 * Get new google maps time and store it together with the user time input
 * @param time_user user time input in seconds
 * @return void
 */
var getAndStoreBothTimes = function(time_user) {
	var xhr = new XMLHttpRequest();
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));	
	var origin = neededArray[0].userObject[0].origin_input;
	var destination = neededArray[0].userObject[0].destination_input;
	var mode = neededArray[0].userObject[0].travel_mode;
	var seconds = 0;

	if (mode === "WALKING") mode = 'walking';
	else if (mode === 'TRANSIT') mode = 'transit';
	else mode = 'driving';

    xhr.open('GET', "api.php?origin=" + origin + "&destination=" + destination + "&mode=" + mode, false);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 ) {
            // response
            var json = JSON.parse(xhr.responseText);

            // results which handle an error if duration_in_traffic is undefined
            var nonDrivingValue = json["rows"][0]["elements"][0]["duration"]["value"];
            var drivingValue = typeof json["rows"][0]["elements"][0]["duration_in_traffic"] === "undefined" ? nonDrivingValue : json["rows"][0]["elements"][0]["duration_in_traffic"]["value"];

            time_map = mode.value === "driving" ? Math.round(Number(drivingValue / 60)) : Math.round(Number(nonDrivingValue));

			storeTimeCouple(time_map, time_user);
        }
    };
    
    xhr.send();
    numSaves++;
}

/**
 * Store google map time and the user time as 1 object in local storage.
 * @param time_map google estimated time in seconds
 * @param time_user user time input in seconds
 * @return void
 */
var storeTimeCouple = function(time_map, time_user) {
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	neededArray[0].timeObject.push({'time_map': time_map, 'time_user': time_user});
	localStorage.setItem('allObjects', JSON.stringify(neededArray));
}
/* EOF */