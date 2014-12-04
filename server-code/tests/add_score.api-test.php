<?php

include_once('app-api/constants.php');

$url = 'http://ci.harnys.net/api/add_user_score';

$id             = "54281393e4b0b7147c91f492";
$athlete_id    	= "2";
$score          = "19.0";
$platform 		= "ios";
$regid			= "65adb2c79ac885c6f780d97e754675d71d934b0e6bea7b35f804120d10fd6938";
$user_mongo_id	= "542e4416e4b0860bae2160bb";

$data = array(
ID => $id, 
STARTING_NUMBER => $athlete_id,
SCORE => $score,
PLATFORM => $platform,
REGID => $regid,
USER_MONGO_ID => $user_mongo_id,
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