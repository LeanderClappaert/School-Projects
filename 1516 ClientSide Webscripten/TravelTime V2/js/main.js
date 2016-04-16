/**
 * Project clientside webscripten - The map with functions
 * This is VERSION 2 of the assignment. Version 1 was a mis-interpreation of the assignment.
 * A lot of code is recovered and has been changed.
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 07/01/2016
 */

// variables
var origin_place_id = null;
var destination_place_id = null;
var travel_mode;
var directionsService;
var directionsDisplay;
var map;

/**
 * Initialize the map with their functions (autocomplete, event listeners...)
 * @param  depending on the token used, certain parts of the initMap function will be used
 * @return void
 */
function initMap(token) {
	// enable this method to clear localStorage
	// clearMethod();
	
	// load prefebricated input when local storage is empty and user access the site
	if (localStorage.length === 0) loadFirstTimeInput();

	// re-use of this method to prevent double code by using a token
	var oi = 'origin-input';
	var di = 'destination-input';
	var mo = 'mode-selector';
	var ch_w = 'changemode-walking';
	var ch_t = 'changemode-transit';
	var ch_d = 'changemode-driving';

	// set listeners to the input fields on the edit form
	if (token === 1) {
		oi = 'origin-input-edit';
		di = 'destination-input-edit';
		mo = 'mode-selector-edit';
		ch_w = 'changemode-walking-edit';
		ch_t = 'changemode-transit-edit';
		ch_d = 'changemode-driving-edit';

		// reset the id's
		origin_place_id = null;
		destination_place_id = null;
	}

	// load map the first time only
	if (token !== 1 && token !== 2) {
		travel_mode = google.maps.TravelMode.WALKING;

		map = new google.maps.Map(document.getElementById('map'), {
		    mapTypeControl: false, //enable or disable satellite/map
		    center: {lat: 51.061146, lng: 3.708754}, //center: Gebroeders de Smetstraat 1, 9000 Gent
		    zoom: 9
		});

		directionsService = new google.maps.DirectionsService;
		directionsDisplay = new google.maps.DirectionsRenderer;
		directionsDisplay.setMap(map);
	}

	// persistence: load last values on refresh
	if (localStorage.length !== 0 && token !== 1 && token !== 2) {
		loadSavedRoute();
		loadLocalStoragePersistence();
	}

	var origin_input = document.getElementById(oi);
	var destination_input = document.getElementById(di);
	var modes = document.getElementById(mo);

	var origin_autocomplete = new google.maps.places.Autocomplete(origin_input);
	origin_autocomplete.bindTo('bounds', map);
	var destination_autocomplete = new google.maps.places.Autocomplete(destination_input);
	destination_autocomplete.bindTo('bounds', map);

	// sets a listener on a radio button to change the filter type on Places Autocomplete.
	function setupClickListener(id, mode) {
		var radioButton = document.getElementById(id);
		radioButton.addEventListener('click', function() {
			travel_mode = mode;
		});
	}

	setupClickListener(ch_w, google.maps.TravelMode.WALKING);
	setupClickListener(ch_t, google.maps.TravelMode.TRANSIT);
	setupClickListener(ch_d, google.maps.TravelMode.DRIVING);

	origin_autocomplete.addListener('place_changed', function() {
	    var place = origin_autocomplete.getPlace();
	    if (!place.geometry) {
	      window.alert("Autocomplete's returned place contains no geometry");
	      return;
	    }

	    // if the place has a geometry, store its place ID and route if we have the other place ID
	    origin_place_id = place.place_id;
	});

	destination_autocomplete.addListener('place_changed', function() {
	    var place = destination_autocomplete.getPlace();
	    if (!place.geometry) {
	      window.alert("Autocomplete's returned place contains no geometry");
	      return;
	    }

	    // if the place has a geometry, store its place ID and route if we have the other place ID
	    destination_place_id = place.place_id;
	});
}

/**
 * Load default route with custome values as an example
 * @return void
 */
function loadFirstTimeInput() {
	// set custome user input
	var origin = 'Lochristi, België';
	origin_place_id = 'ChIJUXD1nVZow0cRxYTNB2M3F8Q';
	var destination = 'Sportpaleis Antwerpen, Schijnpoortweg, Antwerpen, België';
	destination_place_id = 'ChIJEYM826L3w0cRX8qAJflWk8c';
	var mode = 'DRIVING';
	travel_mode = google.maps.TravelMode.DRIVING

	// make new object with all the user input and object with time input
	var userObject = [];
	var timeObject = [];

	// make 2 object arrays
	userObject.push({'origin_input': origin, 'destination_input': destination, 'mode': mode, 'origin_place_id': origin_place_id, 'destination_place_id': destination_place_id, 'travel_mode': travel_mode});
	timeObject.push({'time_map': 0, 'time_user': 0})
	timeObject.push({'time_map': 2220, 'time_user': 2100});
	timeObject.push({'time_map': 2520, 'time_user': 2640});
	timeObject.push({'time_map': 2280, 'time_user': 2460});
	timeObject.push({'time_map': 2880, 'time_user': 2880});
	timeObject.push({'time_map': 2160, 'time_user': 2100});

	// create 1 array with both objects
	var newArray = [{'userObject': userObject, 'timeObject': timeObject}]; // store both objects in 1 global objects
	// store array in local storage
	localStorage.setItem('allObjects', JSON.stringify(newArray));
}

/**
 * Show the current trafic on the map
 * @param map, the map which will be used to show the traffic on
 * @param remove, decides if the traffic has to be showed or not
 * @return void
 */
function setTrafficLayer(map, remove) {
	var trafficLayer = new google.maps.TrafficLayer();
	if (remove === true) trafficLayer.setMap(map);
	else trafficLayer.setMap(null);
}

/**
 * Show the route on the map
 * @param token, depending on the token, certain parts of this function will be used
 * @param or origin-input or origin-input-edit
 * @param de destination-input or destination-input-edit
 * @param mode the travelmode
 * @return void
 */
function showOnMap(token, or, de, mode) {
	// default values
	if (typeof(or)==='undefined') or = 'origin-input';
	if (typeof(de)==='undefined') de = 'destination-input';
	if (token === 1) travel_mode = mode;

	// show error if id('s) are/has not (been) found
    if (!origin_place_id && !destination_place_id) {
    	document.getElementById(or).value = 'Please enter a valid origin!';
    	document.getElementById(de).value = 'Please enter a valid destination!';
    	return;
    }
    if (!origin_place_id || !destination_place_id) {
    	if (!origin_place_id) document.getElementById(or).value = 'Please enter a valid origin!';
    	else document.getElementById(de).value = 'Please enter a valid destination!';
    	return;
    }
	if (token === 0 && localStorage.length !== 0) document.getElementById('directionButton').style.display = 'none';

    directionsService.route({
     	origin: {'placeId': origin_place_id},
    	destination: {'placeId': destination_place_id},
    	travelMode: travel_mode
    }, 	
    function(response, status) {
    	clearShow(); // close down dropdown
    	document.getElementById('formEdit').style.display = 'none'; // close the edit form

		if (status === google.maps.DirectionsStatus.OK) {
			if (token === 1) localStorage.clear(); // clear before making changes to local storage
			directionsDisplay.setDirections(response);
			if (token !== 1) setTrafficLayer(map, true); // show traffic on the map
			var time = response.routes[0].legs[0];
			if (token !== 1) storeLocalStorage(); // store new route
			else storeLocalStorage(0, 0, 'origin-input-edit', 'destination-input-edit', 'changemode-walking-edit', 'changemode-transit-edit')
			routeDuration(time.duration.text);
		}
		else window.alert('Directions request failed due to ' + status);
	});

	clearShow(); // close down dropdown
}

/**
 * Store objects into the local storage
 * This method will be re-used when editting a saved route (params)
 * Source: http://www.w3schools.com/html/html5_webstorage.asp
 * @param time time of the route
 * @param user user input time
 * @param or origin-input or origin-input-edit
 * @param de destination-input or destination-input-edit
 * @param ch_w changemode-walking or changemode-walking-edit
 * @param ch_t changemode-transit or changemode-transit-edit
 * @return void
 */
function storeLocalStorage(time, user, or, de, ch_w, ch_t) {
	// default values
	if (typeof(time)==='undefined') time = 0;
	if (typeof(user)==='undefined') user = 0;
	if (typeof(or)==='undefined') or = 'origin-input';
	if (typeof(de)==='undefined') de = 'destination-input';
	if (typeof(ch_w)==='undefined') ch_w = 'changemode-walking';
	if (typeof(ch_t)==='undefined') ch_t = 'changemode-transit';

	// get user input
	var origin = document.getElementById(or).value;
	var destination = document.getElementById(de).value;
	var mode;

	if (document.getElementById(ch_w).checked) mode = 'WALKING';
	else if (document.getElementById(ch_t).checked) mode = 'TRANSIT';
	else mode = 'DRIVING';

	// make time couple;
	var timeInput = [{'time_map': time, 'time_user': user}];
	//make new object with all the user input
	var userInput = [{'origin_input': origin, 'destination_input': destination, 'mode': mode, 'origin_place_id': origin_place_id, 'destination_place_id': destination_place_id, 'travel_mode': travel_mode}]

	if (localStorage.length !== 0) {
		var oldArray = JSON.parse(localStorage.getItem('allObjects'));
		var userObject = oldArray[0].userObject;
		var timeObject = oldArray[0].timeObject;

		timeObject.push(timeInput);
		userObject.push(userInput);
		oldArray = [{'userObject': userObject, 'timeObject': timeObject}]; // store both objects in 1 global objects

		localStorage.setItem('allObjects', JSON.stringify(oldArray));
	}
	else {
		var storeArray = [{'userObject': userInput, 'timeObject': timeInput}];
		localStorage.setItem('allObjects', JSON.stringify(storeArray));
	}
}

/**
 * Persistence: show last user input on screen
 * @param or origin-input or origin-input-edit
 * @param de destination-input or destination-input-edit
 * @param ch_w changemode-walking or changemode-walking-edit
 * @param ch_t changemode-transit or changemode-transit-edit
 * @param ch_d changemode-driving or changemode-driving-edit
 * @return void
 */
function loadLocalStoragePersistence(or, de, ch_w, ch_t, ch_d) {
	if(localStorage.length !== 0) document.getElementById('directionButton').style.display = 'none';
	else return // if local storage is empty, do nothing
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	var lastUserInput = neededArray[0].userObject[neededArray[0].userObject.length - 1];

	if (typeof(or)==='undefined') or = 'origin-input';
	if (typeof(de)==='undefined') de = 'destination-input';
	if (typeof(ch_w)==='undefined') ch_w = 'changemode-walking';
	if (typeof(ch_t)==='undefined') ch_t = 'changemode-transit';
	if (typeof(ch_d)==='undefined') ch_d = 'changemode-driving';

	document.getElementById(or).value = lastUserInput.origin_input;
	document.getElementById(de).value = lastUserInput.destination_input;

	//persistence radiobuttons, WALKING is default
	var mode = lastUserInput.mode;
	if (mode === null || mode === 'WALKING') document.getElementById(ch_w).checked = true;
	else if (mode !== null && mode === 'TRANSIT') document.getElementById(ch_t).checked = true;
	else document.getElementById(ch_d).checked = true;
}

/**
 * Set and show the estimated google maps time to user
 * Source: https://developers.google.com/maps/documentation/javascript/directions
 * @param time the time which was estimated by google maps
 * @return void
 */
function routeDuration(time) {
	document.getElementById('setTime').innerHTML = 'Estimated time: ' + time;
	document.getElementById('setTime').style.fontSize = '1.5rem';
	document.getElementById('duration').style.display = 'block';
}

/**
 * Clear the local storage
 * @return void
 */
function clearMethod() {
	localStorage.clear();
}
/* EOF */