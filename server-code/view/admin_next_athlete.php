<?php

include_once('./push/ios/new_athlete_push.php');
include_once('./push/android/gmc.php');

$criteria = array('_id' => new MongoId($event_id));
$fields = array("_id" => false, "event_id" => false);
$event = $database->event->find($criteria, $fields)->limit(1);
$event = iterator_to_array($event);
$event = $event[0];

$athletes = $event['event_data']['event_athelete_list'];

$last_scored_athlete;
for ($i = 0; $i < count($athletes); $i++)
{
  if ($i+1 < count($athletes))
  {
    if ($athletes[$i]['live'])
    {
      //echo $athletes[$i]['athelete_name'];
      unset($athletes[$i]['live']);
      $athletes[$i+1]['live'] = 1;
      $last_scored_athlete =  $athletes[$i];
      break;
    }
  }
  else
  {
    unset($athletes[count($athletes)-1]['live']);
    $athletes[0]['live'] = 1;
  }
}

$event_name = $event['event_data']['event_name'];
$event_gender = $event['event_data']['event_gender'];
$event_location = $event['event_data']['event_location'];
$event['event_data'] = array(
"event_athelete_list" => $athletes,
"event_name" => $event_name,
"event_gender" => $event_gender,
"event_location" => $event_location);
$database->event->update($criteria, $event);

$criteria = array("_id" => new MongoId($event_id));
$update = array('$set' => array("voting_enabled" => "true"));
$database->event->update($criteria, $update);

$criteria = array('_id' => new MongoId($event_id));
$fields = array("_id" => false);
$scores = iterator_to_array($database->judgescore->find($criteria, $fields)->limit(1))[0]['score'];

echo "<pre>";
$new_athlete;
for ($i = 0; $i < count($scores); $i++)
{
	if ($scores[$i]['athelete_nr'] == $last_scored_athlete['athelete_starting_nr'])
	{
		$new_athlete = $scores[$i];
		break;

	}
}

$criteria = array('_id' => new MongoId($event_id));
$fields = array("_id" => false);
$scores = iterator_to_array($database->userscore->find($criteria, $fields)->limit(1))[0]['scores'];

for ($i = 0; $i < count($scores); $i++)
{
	if ($scores[$i]['start_number'] == $new_athlete['athelete_nr'])
	{
		$token = $scores[$i]['regid'];
		if ($scores[$i]['platform'] == "ios")
		{
			send_new_athlete_notification($token, $new_athlete['athelete_nr']);
		}
		if ($scores[$i]['platform'] == "android")
		{
			$message = $new_athlete['athelete_nr'];
			$msg = array("param" => "n", "data" => $message);
			sendNewAthleteNotification($token, $msg);
		}
	}
}

//print_r($new_athlete);
//print_r($scores);
echo "</pre>";
header('location: ../../event/'.$event_id);

?>
