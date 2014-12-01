<?php

include_once('constants.php');
include_once('model/user.class.php');

if ($_POST)
{
	$phone_number		 	= $_POST[PHONE_NUMBER];
	$passcode 				= $_POST[PASSCODE];	
		
	$user = new User($database);
	$result = $user->login($phone_number, $passcode);
	
	if ($result != 0)
	{
		print_r(json_encode(array(array("param" => "l", "data" => array("id" => $result['_id']->{'$id'}, "nationality" => $result['nationality'], "highscore" => $result['highscore'], "name" => $result['name'], "regid" => $result['regid'])))));
	}
	else
	{
		print_r(json_encode(array(array("param" => "l", "data" => array("id" => "0")))));
	}
}

?>