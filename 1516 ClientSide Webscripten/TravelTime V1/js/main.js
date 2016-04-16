/**
 * Project clientside webscripten - The map with functions
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 03/01/2016
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
	clearMethod();

	// re-use of this method to prevent double code by using a token
	var oi = 'origin-input';
	var di = 'destination-input';
	var mo = 'mode-selector';
	var ch_w = 'changemode-walking';
	var ch_t = 'changemode-transit';
	var ch_d = 'changemode-driving';

	if (token === 1) {
		oi = 'origin-input-edit';
		di = 'destination-input-edit';
		mo = 'mode-selector-edit';
		ch_w = 'changemode-walking-edit';
		ch_t = 'changemode-transit-edit';
		ch_d = 'changemode-driving-edit';
	}
	// persistence: load last values on refresh
	if (localStorage.length !== 0 && token !== 1 && token !== 2) {
		loadLocalStorage();
		loadLocalStoragePersistence();
		loadLegend();
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
 * @return void
 */
function showOnMap(token) {
    if (!origin_place_id || !destination_place_id) return;
    directionsService.route({
    	origin: {'placeId': origin_place_id},
    	destination: {'placeId': destination_place_id},
    	travelMode: travel_mode
    }, 	
    function(response, status) {
		if (status === google.maps.DirectionsStatus.OK) {
			if (token !== 1) directionsDisplay.setDirections(response);
			if (token !== 1) setTrafficLayer(map, true); // Show traffic on the map
			var time = response.routes[0].legs[0];
			if (token !== 1) storeLocalStorage(time.duration.value); 		// Store new route
			else storeLocalStorage(time.duration.value, undefined, 'origin-input-edit', 'destination-input-edit', 'changemode-walking-edit', 'changemode-transit-edit')
			if (token !== 1) addListItem();
			if (token !== 1) routeDuration(time.duration.text);
			if (token !== 1) addLegend();
		}
		else window.alert('Directions request failed due to ' + status);
	});

	clearShow();
}

/**
 * LOCAL STORAGE
 */

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

	//make new object with all the user input
	var userInput = [{'origin_input': origin, 'destination_input': destination, 'mode': mode, 'origin_place_id': origin_place_id, 'destination_place_id': destination_place_id, 'travel_mode': travel_mode, 'time_map': time, 'time_user': user}]

	if (localStorage.length !== 0) {
		var oldArray = JSON.parse(localStorage.getItem('allObjects'));
		oldArray.push(userInput);
		localStorage.setItem('allObjects', JSON.stringify(oldArray));
	}
	else {
		var storeArray = [userInput];
		localStorage.setItem('allObjects', JSON.stringify(storeArray));
	}
}

/**
 * Persistence: show last user input on screen
 * @return void
 */
function loadLocalStoragePersistence() {
	var neededArray = JSON.parse(localStorage.getItem('allObjects'));
	var lastUserInput;

	try {
		var tempInput = neededArray[neededArray.length - 1];
		lastUserInput = tempInput[0];
	}
	catch(err) {
		lastUserInput = {'origin_input': null, 'destination_input': null, 'mode': null}
	}

	try {
		document.getElementById('origin-input').value = lastUserInput.origin_input;
	}
	catch(err) {
		document.getElementById('origin-input').value = "";
	}
	try {
		document.getElementById('destination-input').value = lastUserInput.destination_input;
	}
	catch(err) {
		document.getElementById('destination-input').value = "";
	}

	//persistence radiobuttons, WALKING is default
	try {
		var mode = lastUserInput.mode;
		if (mode === null || mode === 'WALKING') document.getElementById('changemode-walking').checked = true;
		else if (mode !== null && mode === 'TRANSIT') document.getElementById('changemode-transit').checked = true;
		else document.getElementById('changemode-driving').checked = true;
	}
	catch(err) {
		document.getElementById('changemode-walking').checked = true;
	}
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