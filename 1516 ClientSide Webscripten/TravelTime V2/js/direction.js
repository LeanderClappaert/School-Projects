/**
 * Clientside webscripten project - Menu functions
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 05/01/2016
 */

/* 
 * When the user clicks on the button, toggle between hiding and showing the 'direction' content
 * @return void
 */
var myFunction = function() {
	initMap(2); //set all listeners and autocomplete functions to the direction route inputs

	if (document.getElementById('myDropdown').classList.contains('show')) {
		document.getElementById('myDropdown').classList.toggle('show');
		document.getElementById('myChart').style.display = 'none';
	}
	else {
		clearShow();
		document.getElementById('myDropdown').classList.toggle('show');
		document.getElementById('myChart').style.display = 'none';
	}
};

/* 
 * When the user clicks on the button, toggle between hiding and showing the 'saved routes' content
 * @return void
 */
var myFunction2 = function() {
	initMap(1); //set all listeners and autocomplete functions to the saved route inputs

	if (document.getElementById('myDropdown2').classList.contains('show')) {
		document.getElementById('myDropdown2').classList.toggle('show');
		document.getElementById('myChart').style.display = 'none';
	}
	else {
		clearShow();
		document.getElementById('myDropdown2').classList.toggle('show');
		document.getElementById('myChart').style.display = 'none';
		// remove current list item(s)
		document.getElementById('addItems').innerHTML = '';
		//reload list item(s)
		loadLocalStorageEdit();
	}
};

/* 
 * When the user clicks on the button, toggle between hiding and showing the 'graphical' content
 * @return void
 */
var myFunction3 = function() {
	if (document.getElementById('myDropdown3').classList.contains('show')) {
		document.getElementById('myDropdown3').classList.toggle('show');
		document.getElementById('myChart').style.display = 'none';
	}
	else {
		clearShow();
		document.getElementById('myDropdown3').classList.toggle('show');
		document.getElementById('myChart').style.display = 'block';
		loadGraph();
		loadLegend();
	}
};

/**
 * Close the dropdown menu if the user clicks outside of it
 * @return void
 */
document.getElementById('map').onclick = function(event) {
	if (!event.target.matches('.dropbtn')) clearShow();
};

/**
 * Close down all dropdowns and close the edit of an list item in saved routes
 * @return void
 */
var clearShow = function() {
	var dropdowns = document.getElementsByClassName('show');

	for (var i = 0; i < dropdowns.length; i++) {
    	var openDropdown = dropdowns[i];
    	if (openDropdown.classList.contains('show')) openDropdown.classList.remove('show');
	}

	document.getElementById('formEdit').style.display = 'none';
}
/* EOF */