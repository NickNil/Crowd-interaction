<?php

include_once('app-api/constants.php');

$url = 'http://ci.harnys.net/api/login';

$phone_number 	= "41760732";
$passcode 			= "1337";

$data = array(
PHONE_NUMBER => $phone_number, 
PASSCODE => $passcode,
);

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