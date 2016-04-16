<?php
/**
 * Get new google maps estimated time when user stores a new time
 * 
 * @author Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 05/01/2016
 */

//Accept request from all domains
header('Access-Control-Allow-Origin: *');
 
$key = 'AIzaSyDKmNopGjXbCc2Xy77xilyEvQgBPOC76e0';
$origin = $_GET['origin'];
$destination = $_GET['destination'];
$mode = $_GET['mode'];
$url = 'https://maps.googleapis.com/maps/api/distancematrix/json?origins=' . urlencode($origin) . '&destinations=' . urlencode($destination) . '&key='. urlencode($key) .'&mode=' . urlencode($mode) . '&departure_time=now';
$json = file_get_contents($url);
exit($json);
?>