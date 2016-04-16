<?php
/**
 * Save canvas to image
 * @author Rogier van der Linde <rogier.vanderlinde@odisee.be>
 * Edited by Leander Clappaert <leander.clappaert@student.odisee.be>
 * @version 05/01/2016
 */

$data = $_POST['base64data'];

list($type, $data) = explode(';', $data);
list(, $data)      = explode(',', $data);
$data = base64_decode($data);

$filename = uniqid(rand(), true) . '.png';
file_put_contents('img/' . $filename, $data);

echo $filename;

?>