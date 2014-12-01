<?php

include_once('constants.php');

$register_response = array("0" => "Not OK", "1" => "OK", "11000" => "Duplicate phone number");
$login_response = array("0" => "Not OK - Does not exists or wrong info", "JSON object" => "Correct info provided (_id, regid, nationality and name from database returned)");
$add_user_score_response = array("0" => "Insert not OK", "1" => "Insert OK", "2" => "Voting is disabled", "3" => "User already scored");

$keys = array(
"REGISTER" => array(PHONE_NUMBER => "", PASSCODE => "", FIRSTNAME => "" , LASTNAME => "", NATIONALITY => array("iso" => "2 characters e.g 'NO'", "ioc" => "3 characters e.g 'NOR'", "HELP (do not add me in POST request)" => "Codes can be found here: http://ci.harnys.net/api/ioc") , REGID => "", "api_key" => "", "response" => $register_response),
"LOGIN" => array(PHONE_NUMBER => "", PASSCODE => "", "api_key" => "", "response" => $login_response),
"ADD_USER_SCORE" => array(ID => "mongoId string", STARTING_NUMBER => "", USER_MONGO_ID => "mongo id", PLATFORM => "ios/android", SCORE => "points", REGID => "", "api_key" => "", "response" => $add_user_score_response));

?>
/**************************************************************
This is a list of all POST keys and responses.
All request must contain the correct keys to work.
NB! All requests done to /api/ requires an API KEY.
add "api_key" to post request as well to get a response from server
**************************************************************/
<?php
print_r($keys);
?>
