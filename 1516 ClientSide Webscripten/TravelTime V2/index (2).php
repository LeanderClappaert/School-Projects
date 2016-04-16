<!--
	HTML file with a PHP section in it.
	@author Leander Clappaert <leander.clappaert@student.odisee.be>
	@version 05/01/2016
-->

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Google Maps</title>	
	<meta charset="utf-8">

	<!--[if lt IE 9]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
	<!--<link href='http://fonts.googleapis.com/css?family=Quicksand:300' rel='stylesheet' type='text/css'>-->
	<link rel="stylesheet" href="css/library.css" media="screen" />
	<link rel="stylesheet" href="css/dropdown.css" media="screen" />
	<link rel="stylesheet" href="css/main.css" media="screen" />
	<link rel="stylesheet" href="css/direction.css" media="screen" />
	<link rel="stylesheet" href="css/savedRoutes.css" media="screen" />
	<link rel="stylesheet" href="css/graphical.css" media="screen" />
	<style>
		html {
			font-size: 62.5%
		}
		body {
			font-size: 1.6rem;
		}
	</style>
</head>
<body>
	<!-- Container with map and the functionalities -->
	<div class="container">
		<header id="above">
			<h1 class=""><a title="Header">Welcome To Traveltime</a></h1>
			<nav>
				<h2 class="structural">Main menu</h2>
				<ul>
					<li id="firstListItem">
						<!-- Source: http://www.w3schools.com/howto/howto_js_dropdown.asp -->
						<a onclick="myFunction()" id="direction" title="Direction" class="menuItem dropbtn">Direction</a>
					</li>
					<li>
						<a onclick="myFunction2()" title="Saved routes" class="MiddleMenuItem dropbtn">Saved Routes</a>
					</li>
					<li>
						<a onclick="myFunction3()" title="Graphical" class="lastMenuItem dropbtn">Graphical</a>
					</li>
				</ul>
				<div class="dropdown">
					<!-- Direction menu item -->
					<div id="myDropdown" class="dropdown-content">
						<input id="origin-input" class="controls test" type="text" placeholder="Enter an origin location"></input>
						<input id="destination-input" class="controls" type="text" placeholder="Enter a destination location"/></input>
						<div id="mode-selector"> <!-- class="controls" -->
							<input type="radio" name="type" id="changemode-walking" checked="checked"></input>
							<label for="changemode-walking">Walking</label>

							<input type="radio" name="type" id="changemode-transit">
							<label for="changemode-transit">Transit</label>

							<input type="radio" name="type" id="changemode-driving">
							<label for="changemode-driving">Driving</label>
						</div>
						<button type="button" id="directionButton" onclick="showOnMap(0)">Calculate</button>
					</div>
					<!-- Saved routes menu item -->
					<div id="myDropdown2" class="dropdown-content box">
						<div class="boxInner">
							<h2>Your saved routes</h2>
							<ul id="addItems">
							</ul>
						</div>
						<!-- Form pops open when user wants to edit the current route -->
						<form id="formEdit" class="clearfix">
							<h2>Edit current saved route</h2>
							<dl>
								<dt id="origin">
									<label for="origin-input-edit">Origin</label>
								</dt>
								<dt id="destination">
									<label for="destination-input-edit">Destination</label>
								</dt>
								<dd>
									<input type="text" id="origin-input-edit" name="origin-input-edit" placeholder="Enter an origin location"></input>
								</dd>
								<dd>
									<input type="text" id="destination-input-edit" name="destination-input-edit" placeholder="Enter a destination location"></input>
								</dd>
								<dd id="mode-selector-edit">
									<input type="radio" name="type" id="changemode-walking-edit" checked="checked"></input>
									<label for="changemode-walking">Walking</label>
									<input type="radio" name="type" id="changemode-transit-edit">
									<label for="changemode-transit">Transit</label>	
									<input type="radio" name="type" id="changemode-driving-edit">
									<label for="changemode-driving">Driving</label>
								</dd>
								<button type="button" id="editChangeButton">Change</button>
								<dt id="userTime">
									<label for="user-input-edit">My time</label>
								</dt>
								<dd>
									<input type="text" id="user-input-edit" name="user-input-edit" placeholder="00:00:00"></input>
								</dd>
								<button type="button" id="editSaveButton">Save</button>
							</dl>
						</form>
						<!-- Pops up when user wants to change their address -->
						<div id="dialogBox">
							<h2>Warning</h2>
							<p>All travel times will be erased if you continue!</p>
							<button type="button" id="declineButton">Decline</button>
							<button type="button" id="acceptButton">Accept</button>
						</div>
						<div id="dialogBoxBackground"></div>
					</div>
					<!-- Graphical menu item -->
					<div id="myDropdown3" class="dropdown-content">
						<canvas id="myChart"></canvas>
						<h2 id="chartLegendTitle">Legend: <br> x = trips and y = traveltime in minutes <br> green = user time and blue = google time
						<button type="button" id="graphSaveButton">Save</button>
						<div id="chartLegend"></div>
					</div>
				</div>
			</nav>	
		</header>
		<!-- Estimated time for the selected route -->
		<div id="duration">
			<h1 id="setTime">ESTIMATED TIME:</h1>
		</div>
		<div id="map"></div>
	</div>
	<!-- Show the stored images below the map -->
	<div id="charResult">
		<h2>Saved Graphs</h2>
		<?php
			$dir = 'img/';
			$images = glob($dir.'*.png');
			$count = 1;

			foreach($images as $img) {
	 			echo '<div id="savedImages"><img src="' . htmlentities($img) . '" /><h2>'. 'Image ' . htmlentities($count) . '</h2></div>';
				$count++;
			}
		?>
	</div>

	<!-- All needed scripts -->
	<script src="js/direction.js"></script>
	<script src="js/main.js"></script>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDKmNopGjXbCc2Xy77xilyEvQgBPOC76e0&signed_in=true&libraries=places&callback=initMap">
	</script>
	<script src="js/savedRoutes.js"></script>
	<script src="js/chart.min.js"></script>
	<script src="js/graphical.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://www.google.com/jsapi"></script>

</body>
</html>