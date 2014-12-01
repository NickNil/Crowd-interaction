<?php 
include_once('constants.php');
include_once('model/user.class.php');

if ($_POST)
{

	
	$phone_number		 	= $_POST[PHONE_NUMBER];
	$passcode 				= $_POST[PASSCODE];
	$firstname 				= $_POST[FIRSTNAME];
	$lastname 				= $_POST[LASTNAME];
	$nationality 			= $_POST[NATIONALITY];
	$regid					= $_POST[REGID];
	//$regid = NULL;
	
	//print_r(array(PHONE_NUMBER => $phone_number, PASSCODE => $passcode, FIRSTNAME => $firstname, LASTNAME => $lastname, NATIONALITY => $nationality, REGID => $regid));
	
	$user = new User($database);

	$response = 0;
	if ($user->set_user_data($phone_number, $passcode, $firstname, $lastname, $nationality, $regid))
	{
		$response = $user->store_in_database();
	}
	 echo json_encode(array(array("param" => "r", "data" => array("id" => (string)$response))));
}


// return 1 is ok, 0 is not ok, 11000 duplicate phone number.
?>