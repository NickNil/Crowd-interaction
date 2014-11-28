<?php
include_once('constants.php');

$id             = $_POST[ID];
$athlete_id    	= $_POST[STARTING_NUMBER];
$score          = $_POST[SCORE];
$platform 		= $_POST[PLATFORM];
$regid 			= $_POST[REGID];
$user_mongo_id 	= $_POST[USER_MONGO_ID];

if (!isset($id) || !isset($athlete_id) || !isset($score) || !isset($platform) || !isset($regid) || !isset($user_mongo_id))
{
	echo json_encode(array(array("param" => "s", "data" => array("response" => "0"))));
	return;	
}

$new_score = array(STARTING_NUMBER => $athlete_id, SCORE => $score, PLATFORM => $platform, REGID => $regid, USER_MONGO_ID => $user_mongo_id);

$mongo_id = new MongoId($id);
$ok = false;

$criteria = array("_id" => $mongo_id);
$fields = array("_id" => false, "voting_enabled" => true);
$enabled = iterator_to_array($database->event->find($criteria, $fields)->limit(1))[0]['voting_enabled'];
if($enabled == "false")
{
	echo json_encode(array(array("param" => "s", "data" => array("response" => "2"))));
	return;
}

$criteria = array("_id" => $mongo_id, "scores.user_mongo_id" => $user_mongo_id, "scores.start_number" => $athlete_id);
$fields = array("_id" => false);
$scored_before = iterator_to_array($database->userscore->find($criteria, $fields)->limit(1))[0];

if(count($scored_before) > 0)
{
	echo json_encode(array(array("param" => "s", "data" => array("response" => "3"))));
	return;
}


try
{
	$database->userscore->insert(array("_id" => $mongo_id, "scores" => array($new_score)));
	$ok = "1";
}
catch(MongoCursorException $e)
{
	if ($e->getCode() == 11000)
	{
		$ok = $database->userscore->update(array("_id" => $mongo_id), array('$addToSet' => array("scores" => $new_score)));
		$ok = $ok['n'];
	}
}

if ($ok)
{
  echo json_encode(array(array("param" => "s", "data" => array("response" => "1"))));
}
else
{
  echo json_encode(array(array("param" => "s", "data" => array("response" => "0"))));
}

?>
