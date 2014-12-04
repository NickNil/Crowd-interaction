<?php

include_once('app-api/constants.php');

ini_set('display_errors',1);
ini_set('display_startup_errors',1);
error_reporting(-1);

$api_key 			= "DmMi5CtV0HMmqD4GO2uVc44qT0aMWNd8";

$phone_number 		= "12344321";
$passcode 			= "12369";
$firstname 			= "Jim";
$lastname 			= "Lladrovci";
$nationality 		=  "{\"iso\" : \"AL\", \"ioc\" : \"ALB\"}";  //array("iso" => "ALB", "ioc" => "AL");
$regid 				= "APA91bGudP-506VRNtf7kbkbBObZp09HUX0jLQXGwODgf8XhNMYhOSqkUZ3pa0fqpSJKulfHl5Bxpp7Kq4QL7lJgJ3mF4ySFYO_FBF3pXCm-JennHivAgKXW8CXtEM3XCyLveRNYxAQTHEIJFzy6He_b2DHs0MViDDPBicTZNLCLyfaXef9S7ao";

$data = array(
PHONE_NUMBER => $phone_number, 
PASSCODE => $passcode,
FIRSTNAME => $firstname,
LASTNAME => $lastname,
NATIONALITY => $nationality,
REGID => $regid,
);

//print_r($data);

$url = 'http://ci.harnys.net/api/register_android';

// use key 'http' even if you send the request to https://...
$options = array(
    'http' => array(
        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
        'method'  => 'POST',
        'content' => http_build_query($data),
    ),
);

$context  = stream_context_create($options);
$result = file_get_contents($url, false, $context);
print_r($result);




?>